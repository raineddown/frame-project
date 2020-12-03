package com.example.demo.service.impl;


import com.example.demo.entity.SysUserRole;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.code.BaseResponseCode;
import com.example.demo.mapper.SysUserRoleMapper;
import com.example.demo.service.UserRoleService;
import com.example.demo.vo.req.UserOwnRoleReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: UserRoleServiceImpl
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public List<String> getRoleIdsByUserId(String userId) {
        return sysUserRoleMapper.getRoleIdsByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserRoleInfo(UserOwnRoleReqVO vo) {
        //删除他们关联数据
        sysUserRoleMapper.removeRoleByUserId(vo.getUserId());
        if(vo.getRoleIds()==null||vo.getRoleIds().isEmpty()){
            return;
        }
        List<SysUserRole> list=new ArrayList<>();
        for (String roleId:
             vo.getRoleIds()) {
            SysUserRole sysUserRole=new SysUserRole();
            sysUserRole.setId(UUID.randomUUID().toString());
            sysUserRole.setCreateTime(new Date());
            System.out.println("时间"+sysUserRole.getCreateTime());
            sysUserRole.setUserId(vo.getUserId());
            sysUserRole.setRoleId(roleId);
            list.add(sysUserRole);
        }
        int i = sysUserRoleMapper.batchInsertUserRole(list);
        if(i==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    @Override
    public List<String> getUserIdsByRoleIds(List<String> roleIds) {
        return sysUserRoleMapper.getUserIdsByRoleIds(roleIds);
    }

    @Override
    public List<String> getUserIdsBtRoleId(String roleId) {
        return sysUserRoleMapper.getUserIdsByRoleId(roleId);
    }

    @Override
    public int removeUserRoleId(String roleId) {
        return sysUserRoleMapper.removeUserRoleId(roleId);
    }
}
