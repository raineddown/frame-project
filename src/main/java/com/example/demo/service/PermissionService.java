package com.example.demo.service;



import com.example.demo.entity.SysPermission;
import com.example.demo.vo.req.PermissionAddReqVO;
import com.example.demo.vo.req.PermissionUpdateReqVO;
import com.example.demo.vo.resp.PermissionRespNodeVO;

import java.util.List;

/**
 * @ClassName: PermissionService
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
public interface PermissionService {
    List<SysPermission> selectAll();
    List<PermissionRespNodeVO> selectAllMenuByTree();
    SysPermission addPermission(PermissionAddReqVO vo);
    List<PermissionRespNodeVO> permissionTreeList(String userId);
    List<PermissionRespNodeVO> selectAllTree();
    void updatePermission(PermissionUpdateReqVO vo);
    void deletedPermission(String permissionId);
    List<String> getPermissionByUserId(String userId);
    List<SysPermission> getPermissions(String userId);
}
