package com.example.demo.service;



import com.example.demo.entity.SysRole;
import com.example.demo.vo.req.AddRoleReqVO;
import com.example.demo.vo.req.RolePageReqVO;
import com.example.demo.vo.req.RoleUpdateReqVO;
import com.example.demo.vo.resp.PageVO;

import java.util.List;

/**
 * @ClassName: RoleService
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
public interface RoleService {
    PageVO<SysRole> pageInfo(RolePageReqVO vo);
    SysRole addRole(AddRoleReqVO vo);
    List<SysRole> selectAll();
    SysRole detailInfo(String id);
    void updateRole(RoleUpdateReqVO vo);
    void deletedRole(String roleId);

    List<String> getNamesByUserId(String userId);
}
