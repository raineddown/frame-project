package com.example.demo.mapper;

import com.example.demo.entity.SysUser;
import com.example.demo.vo.req.UserPageReqVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
//    int deleteByPrimaryKey(String id);
//
//    int insert(SysUser record);
//
//    int insertSelective(SysUser record);
//
//    SysUser selectByPrimaryKey(String id);
//
//    int updateByPrimaryKeySelective(SysUser record);
//
//    int updateByPrimaryKey(SysUser record);
//
//    SysUser selectByUsername(String username);
//
//    List<SysUser> selectAll();
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);//未判断密码为空更新

    SysUser getUserInfoByName(String username);

    List<SysUser> selectAll(UserPageReqVO vo);

    int deletedUsers(@Param("sysUser") SysUser sysUser, @Param("list") List<String> list);

    //根据部门id集合查找用户
    List<SysUser> selectUserInfoByDeptIds (List<String> deptIds);
}