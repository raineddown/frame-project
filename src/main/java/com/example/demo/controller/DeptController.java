package com.example.demo.controller;


import com.example.demo.aop.annotation.MyLog;
import com.example.demo.contants.Constant;
import com.example.demo.entity.SysDept;
import com.example.demo.service.DeptService;
import com.example.demo.utils.DataResult;
import com.example.demo.vo.req.DeptAddReqVO;
import com.example.demo.vo.req.DeptUpdateReqVO;
import com.example.demo.vo.resp.DeptRespNodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName: DeptController
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@RestController
@RequestMapping("/api")
@Api(tags = "组织管理-部门管理",description = "部门管理相关接口")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/depts")
    @ApiOperation(value = "查询所有部门数据接口")
    @MyLog(title = "组织管理-部门管理",action = "查询所有部门数据接口")
    @RequiresPermissions("sys:dept:list")
    public DataResult<List<SysDept>> getAllDept(){
        DataResult result =DataResult.success();
        result.setData(deptService.selectAll());
        return result;
    }

    @GetMapping("/dept/tree")
    @ApiOperation(value = "部门树形结构列表接口")
    @MyLog(title = "组织管理-部门管理",action = "部门树形结构列表接口")
    @RequiresPermissions(value = {"sys:user:update","sys:user:add","sys:dept:add","sys:dept:update"},logical = Logical.OR)
    public DataResult<List<DeptRespNodeVO>> getDeptTree(@RequestParam(required = false) String deptId){
        DataResult result=DataResult.success();
        result.setData(deptService.deptTreeList(deptId));
        return result;
    }

    @PostMapping("/dept")
    @ApiOperation(value = "新增部门数据接口")
    @MyLog(title = "组织管理-部门管理",action = "新增部门数据接口")
    @RequiresPermissions("sys:dept:add")
    public DataResult<SysDept> addDept(@RequestBody @Valid DeptAddReqVO vo){
        DataResult result=DataResult.success();
        result.setData(deptService.addDept(vo));
        return result;
    }

//    @PostMapping("/dept")
//    @ApiOperation(value = "新增部门数据接口")
//    @MyLog(title = "组织管理-部门管理",action = "新增部门数据接口")
//    public DataResult<SysDept> addDepttest(@RequestBody @Valid DeptAddReqVO vo, HttpServletRequest request){
//        DataResult result=DataResult.success();
//        result.setData(deptService.addDept(vo));
//        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
//        vo.setAccessToken(accessToken);
//        restTemplate.postForEntity("http://localhost:8079/api/dept", vo, DeptAddReqVO.class);
//        return result;
//    }

    @PutMapping("/dept")
    @ApiOperation(value = "更新部门数据接口")
    @MyLog(title = "组织管理-部门管理",action = "更新部门数据接口")
    @RequiresPermissions("sys:dept:update")
    public DataResult updateDept(@RequestBody @Valid DeptUpdateReqVO vo){
        deptService.updateDept(vo);
        DataResult result=DataResult.success();
        return result;
    }
    @DeleteMapping("/dept/{id}")
    @ApiOperation(value = "删除部门接口")
    @MyLog(title = "组织管理-部门管理",action = "删除部门接口")
    @RequiresPermissions("sys:dept:delete")
    public DataResult deletedDepts(@PathVariable("id") String id){
        deptService.deletedDept(id);
        DataResult result=DataResult.success();
        return result;
    }
}
