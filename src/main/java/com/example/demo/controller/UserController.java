package com.example.demo.controller;


import com.example.demo.aop.annotation.MyLog;
import com.example.demo.contants.Constant;
import com.example.demo.entity.SysUser;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.RedisService;
import com.example.demo.service.UserService;
import com.example.demo.utils.DataResult;
import com.example.demo.utils.JwtTokenUtil;
import com.example.demo.utils.PasswordUtils;
import com.example.demo.vo.req.*;
import com.example.demo.vo.resp.LoginRespVO;
import com.example.demo.vo.resp.PageVO;
import com.example.demo.vo.resp.UserOwnRoleRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: UserController
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@RestController
@RequestMapping("/api")
@Api(tags = "组织管理-用户管理",description = "用户模块相关接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "用户登录接口")
    @PostMapping("/user/login")
    public DataResult<LoginRespVO> login(@RequestBody @Valid LoginReqVO vo){
        DataResult result=DataResult.success();
        result.setData(userService.login(vo));
        return result;
    }
    @PostMapping("/user")
    @ApiOperation(value = "新增用户接口")
    @MyLog(title = "组织管理-用户管理",action = "新增用户接口")
    @RequiresPermissions("sys:user:add")
    public DataResult addUser(@RequestBody @Valid UserAddReqVO vo){
        DataResult result=DataResult.success();
        userService.addUser(vo);
        return result;
    }

    @PostMapping("/users")
    @ApiOperation(value = "分页查询用户接口")
    @RequiresPermissions("sys:user:list")
    public DataResult<PageVO<SysUser>> pageInfo(@RequestBody UserPageReqVO vo){
        DataResult result=DataResult.success();
        result.setData(userService.pageInfo(vo));
        return result;
    }

    @PostMapping("/user/getCode")
    @ApiOperation(value = "用户获取注册验证码")
    public String getCode(String phone){
        //验证手机号是否合法
        Pattern pattern = Pattern.compile("^1(3|4|5|7|8)\\d{9}$");
        Matcher matcher = pattern.matcher(phone);
        if(!matcher.matches()) {
            throw  new BusinessException(4001004,"手机号格式错误");
        }
        //判断手机号是否超限
        long count = redisService.incrby(Constant.REGISTER_CODE_COUNT_KEY+phone,1);
        if(count>5){
            throw new BusinessException(4001004,"当日发送已达上限");
        }

        //生成6位随机数
        String code=generateCode();
        //发送短信(具体根据你们公司所用的api文档来)
        //存入 redis 过期时间为 5 分钟
        redisService.set(Constant.REGISTER_CODE_COUNT_VALIDITY_KEY+phone,code,5, TimeUnit.MINUTES);
        //发送短信这里用输出模拟
        System.out.println(code);
        return code;
    }


    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册接口")
    public String register(@RequestBody RegisterVO vo){

        //判断验证码是否有效
        if(!redisService.hasKey(Constant.REGISTER_CODE_COUNT_VALIDITY_KEY+vo.getPhone())){
            throw new BusinessException(4001008,"验证码已失效请重新获取");
        }
        //校验验证码是否正确
        if(!vo.getCode().equals(redisService.get(Constant.REGISTER_CODE_COUNT_VALIDITY_KEY+vo.getPhone()))){
            throw new BusinessException(4001009,"请输入正确的验证码");
        }
        SysUser sysUser = userService.selectByUsername(vo.getUsername());
        if(sysUser!=null){
            throw new BusinessException(4001010,"该账号已被占用");
        }
        SysUser user=new SysUser();
        BeanUtils.copyProperties(vo,user);
        user.setId(UUID.randomUUID().toString());
        user.setCreateTime(new Date());
        String salt= PasswordUtils.getSalt();
        String ecdPwd=PasswordUtils.encode(vo.getPassword(),salt);
        user.setSalt(salt);
        user.setPassword(ecdPwd);
        int i = userService.insertSelective(user);
        if(i!=1){
            throw new BusinessException(4001011,"操作失败");
        }
        redisService.delete(Constant.REGISTER_CODE_COUNT_VALIDITY_KEY+vo.getPhone());
        redisService.delete(Constant.REGISTER_CODE_COUNT_KEY+vo.getPhone());

        return "注册成功";
    }


    /**
     * 生成六位验证码
     * @return
     */
    private String generateCode(){
        Random random = new Random();
        int x = random.nextInt(899999);
        String code = String.valueOf(x + 100000);
        return code;
    }

    @GetMapping("/user/roles/{userId}")
    @ApiOperation(value = "查询用户拥有的角色数据接口")
    @MyLog(title = "组织管理-用户管理",action = "查询用户拥有的角色数据接口")
    @RequiresPermissions("sys:user:role:update")
    public DataResult<UserOwnRoleRespVO> getUserOwnRole(@PathVariable("userId") String userId){
        DataResult result=DataResult.success();
        result.setData(userService.getUserOwnRole(userId));
        return result;
    }

    @PutMapping("/user/roles")
    @ApiOperation(value = "保存用户拥有的角色信息接口")
    @MyLog(title = "组织管理-用户管理",action = "保存用户拥有的角色信息接口")
    @RequiresPermissions("sys:user:role:update")
    public DataResult saveUserOwnRole(@RequestBody @Valid UserOwnRoleReqVO vo){
        DataResult result=DataResult.success();
        userService.setUserOwnRole(vo);
        return result;
    }


    @GetMapping("/user/token")
    @ApiOperation(value = "jwt token 刷新接口")
    @MyLog(title = "组织管理-用户管理",action = "jwt token 刷新接口")
    public DataResult<String> refreshToken(HttpServletRequest request){
        String refreshToken=request.getHeader(Constant.REFRESH_TOKEN);
        String newAccessToken = userService.refreshToken(refreshToken);
        System.out.println("用户刷新token:"+newAccessToken);
        DataResult result=DataResult.success();
        result.setData(newAccessToken);
        return result;
    }

    @PutMapping("/user")
    @ApiOperation(value ="列表修改用户信息接口")
    @MyLog(title = "组织管理-用户管理",action = "列表修改用户信息接口")
    @RequiresPermissions("sys:user:update")
    public DataResult updateUserInfo(@RequestBody @Valid UserUpdateReqVO vo,HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String userId= JwtTokenUtil.getUserId(accessToken);
        DataResult result=DataResult.success();
        userService.updateUserInfo(vo,userId);
        return result;
    }

    @DeleteMapping("/user")
    @ApiOperation(value = "批量/删除用户接口")
    @MyLog(title = "组织管理-用户管理",action = "批量/删除用户接口")
    @RequiresPermissions("sys:user:delete")
    public DataResult deletedUsers(@RequestBody @ApiParam(value = "用户id集合") List<String> list, HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String operationId=JwtTokenUtil.getUserId(accessToken);
        userService.deletedUsers(list,operationId);
        DataResult result=DataResult.success();
        return result;
    }

    @GetMapping("/user/info")
    @ApiOperation(value = "用户信息详情接口")
    @MyLog(title = "组织管理-用户管理",action = "用户信息详情接口")
    public DataResult<SysUser> detailInfo(HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String id=JwtTokenUtil.getUserId(accessToken);
        DataResult result=DataResult.success();
        result.setData(userService.detailInfo(id));
        return result;
    }

    @PutMapping("/user/info")
    @ApiOperation(value = "保存个人信息接口")
    @MyLog(title = "组织管理-用户管理",action = "保存个人信息接口")
    public DataResult saveUserInfo(@RequestBody UserUpdateDetailInfoReqVO vo,HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String id=JwtTokenUtil.getUserId(accessToken);
        userService.userUpdateDetailInfo(vo,id);
        DataResult result=DataResult.success();
        return result;
    }

    @PutMapping("/user/pwd")
    @ApiOperation(value = "修改个人密码接口")
    public DataResult updatePwd(@RequestBody @Valid UserUpdatePwdReqVO vo, HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String refresgToken=request.getHeader(Constant.REFRESH_TOKEN);
        userService.userUpdatePwd(vo,accessToken,refresgToken);
        DataResult result=DataResult.success();
        return result;
    }

    @GetMapping("/user/logout")
    @ApiOperation(value = "用户退出登录")
    public DataResult logout(HttpServletRequest request){
        try {
            String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
            String refreshToken=request.getHeader(Constant.REFRESH_TOKEN);
            userService.logout(accessToken,refreshToken);
        } catch (Exception e) {
            log.error("logout:{}",e);
        }
        return DataResult.success();
    }
}
