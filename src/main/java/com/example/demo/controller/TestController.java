package com.example.demo.controller;



import com.example.demo.entity.SysUser;
import com.example.demo.utils.DataResult;
import com.example.demo.vo.req.TestReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: TestController
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@RestController
@RequestMapping("/swagger")
@Api(tags = "测试接口模块",description = "主要是为了提供测试接口用")
public class TestController {

    @GetMapping("/test")
    @ApiOperation(value = "测试接口")
    public String testSwagger(){
        return "测试成功";
    }

    @GetMapping("/test/data")
    @ApiOperation(value = "统一的响应格式测试接口")
    public DataResult<SysUser> test(){
//        DataResult result=DataResult.success("统一的响应格式测试接口");
        SysUser sysUser=new SysUser();
        sysUser.setUsername("张三");
        sysUser.setPhone("13811111111");
//        DataResult result=DataResult.success(sysUser);
        SysUser sysUser1=new SysUser();
        sysUser.setUsername("李四");
        sysUser.setPhone("13611111111");
        List<SysUser> list=new ArrayList<>();
        list.add(sysUser);
        list.add(sysUser1);
        DataResult result=DataResult.success(list);
        int i=1/0;
        return result;
    }


    @PostMapping("/test/valid")
    @ApiOperation(value = "测试校验验证器")
    public DataResult testValid(@RequestBody @Valid TestReqVO vo){
        DataResult result=DataResult.success(vo);
        return result;
    }

}
