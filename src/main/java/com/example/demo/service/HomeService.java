package com.example.demo.service;


import com.example.demo.vo.resp.HomeRespVO;


public interface HomeService {
    HomeRespVO getHome(String userId);
}
