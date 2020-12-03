package com.example.demo.service;


import com.example.demo.entity.SysUser;
import com.example.demo.vo.req.*;
import com.example.demo.vo.resp.LoginRespVO;
import com.example.demo.vo.resp.PageVO;
import com.example.demo.vo.resp.UserOwnRoleRespVO;

import java.util.List;

/**
 * @ClassName: UserService
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
public interface UserService {
//swagger测试用
    //LoginRespVO login(LoginReqVO vo);

    //PageVO<SysUser> pageInfo(UserPageReqVO vo);

    SysUser selectByUsername(String username);

    int insertSelective(SysUser sysUser);
    //swagger测试截止
    LoginRespVO login(LoginReqVO vo);


    PageVO<SysUser> pageInfo(UserPageReqVO vo);

    void addUser(UserAddReqVO vo);

    UserOwnRoleRespVO getUserOwnRole(String userId);

    void setUserOwnRole(UserOwnRoleReqVO vo);

    String refreshToken(String refreshToken);

    void updateUserInfo(UserUpdateReqVO vo, String operationId);

    void deletedUsers(List<String> list, String operationId);

    List<SysUser> selectUserInfoByDeptIds(List<String> deptIds);

    SysUser detailInfo(String userId);

    //个人用户编辑信息接口
    void userUpdateDetailInfo(UserUpdateDetailInfoReqVO vo,String userId);

    void userUpdatePwd(UserUpdatePwdReqVO vo,String accessToken,String refreshToken);

    void logout(String accessToken,String refreshToken);

}
