package com.example.demo.controller;

import com.example.demo.contants.Constant;
import com.example.demo.service.HomeService;
import com.example.demo.utils.DataResult;
import com.example.demo.utils.JwtTokenUtil;
import com.example.demo.vo.resp.HomeRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@Api(tags = "首页数据")
public class HomeController {
    @Autowired
    private HomeService homeService;
    @GetMapping("/home")
    @ApiOperation(value ="获取首页数据接口")
    public DataResult<HomeRespVO> getHomeInfo(HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
/**
 * 通过access_token拿userId
 */
        String userId= JwtTokenUtil.getUserId(accessToken);
        DataResult<HomeRespVO> result=DataResult.success();
        result.setData(homeService.getHome(userId));
        return result;
    }
}