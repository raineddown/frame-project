package com.example.demo.controller;

import com.google.common.base.Charsets;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.aop.annotation.MyLog;
import com.example.demo.contants.Constant;
import com.example.demo.entity.SysRole;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.code.BaseResponseCode;
import com.example.demo.service.RedisService;
import com.example.demo.service.RoleService;
import com.example.demo.utils.DataResult;
import com.example.demo.vo.req.AddRoleReqVO;
import com.example.demo.vo.req.RolePageReqVO;
import com.example.demo.vo.req.RoleUpdateReqVO;
import com.example.demo.vo.resp.PageVO;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName: RoleController
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@RestController
@RequestMapping("/api")
@Api(tags = "组织管理-角色管理",description = "角色管理相关接口")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisService redisService;

    /**
     * 布隆过滤器预计要插入多少数据
     */
    private static int size = 10000;

    /**
     * 布隆过滤器期望的误判率
     */
    private static double fpp = 0.01;

    /**
     * 布隆过滤器
     */
    private static BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), size, fpp);

    private static void bloomPutData(RoleService roleService){
        for (SysRole sysRole : roleService.selectAll()){
            bloomFilter.put(sysRole.getId());
        }
    }

    @PostMapping("/roles")
    @ApiOperation(value = "分页获取角色数据接口")
    @MyLog(title = "组织管理-角色管理",action = "分页获取角色数据接口")
    @RequiresPermissions("sys:role:list")
    public DataResult<PageVO<SysRole>> pageInfo(@RequestBody RolePageReqVO vo){
        DataResult result =DataResult.success();
        result.setData(roleService.pageInfo(vo));
        return result;
    }

    @PostMapping("/role")
    @ApiOperation(value = "新增角色接口")
    @MyLog(title = "组织管理-角色管理",action = "新增角色接口")
    @RequiresPermissions("sys:role:add")
    public DataResult<SysRole> addRole(@RequestBody @Valid AddRoleReqVO vo){
        DataResult result =DataResult.success();
        result.setData(roleService.addRole(vo));
        return result;
    }

    @GetMapping("/role/{id}")
    @ApiOperation(value = "获取角色详情接口")
    @MyLog(title = "组织管理-角色管理",action = "获取角色详情接口")
    @RequiresPermissions("sys:role:detail")
    public DataResult<SysRole> detailInfo(@PathVariable("id") String id){
        bloomPutData(roleService);
        SysRole sysRole;
        if (bloomFilter.mightContain(id)){
            if ( redisService.get(Constant.REDIS_CACHE_SYS_ROLE + id) == null ){
                sysRole = roleService.detailInfo(id);
                redisService.set(Constant.REDIS_CACHE_SYS_ROLE + id, sysRole);
                bloomFilter.put(id);
            }else {
                String sysRoleJson = redisService.get(Constant.REDIS_CACHE_SYS_ROLE + id).toString();
                sysRole = JSONObject.parseObject(sysRoleJson, SysRole.class);
            }
        } else {
            throw new BusinessException(BaseResponseCode.BLOOMFILTER_NO_CONTAIN_ERRO);
        }
        DataResult result=DataResult.success();
        result.setData(sysRole);
        return result;
    }

    @PutMapping("/role")
    @ApiOperation(value = "更新角色信息接口")
    @MyLog(title = "组织管理-角色管理",action = "更新角色信息接口")
    @RequiresPermissions("sys:role:update")
    public DataResult updateRole(@RequestBody @Valid RoleUpdateReqVO vo){
        DataResult result=DataResult.success();
        roleService.updateRole(vo);
        return result;
    }

    @DeleteMapping("/role/{id}")
    @ApiOperation(value = "删除角色接口")
    @MyLog(title = "组织管理-角色管理",action = "删除角色接口")
    @RequiresPermissions("sys:role:delete")
    public DataResult deletedRole(@PathVariable("id") String id){
        roleService.deletedRole(id);
        DataResult result=DataResult.success();
        return result;
    }
}
