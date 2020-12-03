package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.service.HomeService;
import com.example.demo.service.PermissionService;
import com.example.demo.vo.resp.HomeRespVO;
import com.example.demo.vo.resp.PermissionRespNodeVO;
import com.example.demo.vo.resp.UserInfoRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PermissionService permissionService;

    @Override
    public HomeRespVO getHome(String userId) {
        HomeRespVO homeRespVO=new HomeRespVO();
/**
 * mock 导航菜单数据后期直接从DB获取
 */
        //String home="[{\"children\":[{\"children\":[{\"children\":[{\"children\":[{\"children\":[],\"id\":\"6\",\"title\":\"五级类目5-6\",\"url\":\"string\"}],\"id\":\"5\",\"title\":\"四级类目4-5\",\"url\":\"string\"}],\"id\":\"4\",\"title\":\"三级类目3-4\",\"url\":\"string\"}],\"id\":\"3\",\"title\":\"二级类目2-3\",\"url\":\"string\"}],\"id\":\"1\",\"title\":\"类目1\",\"url\":\"string\"},{\"children\":[],\"id\":\"2\",\"title\":\"类目2\",\"url\":\"string\"}]";
//        String home="[\n" +
//                "    {\n" +
//                "        \"children\": [\n" +
//                "            {\n" +
//                "                \"children\": [],\n" +
//                "                \"id\": \"3\",\n" +
//                "                \"title\": \"菜单权限管理\",\n" +
//                "                \"url\": \"/index/menus\"\n" +
//                "            }\n" +
//                "        ],\n" +
//                "        \"id\": \"1\",\n" +
//                "        \"title\": \"组织管理\",\n" +
//                "        \"url\": \"string\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"children\": [],\n" +
//                "        \"id\": \"2\",\n" +
//                "        \"title\": \"类目2\",\n" +
//                "        \"url\": \"string\"\n" +
//                "    }\n" +
//                "]";
//        List<PermissionRespNodeVO> list= JSON.parseArray(home,PermissionRespNodeVO.class);
        List<PermissionRespNodeVO> list=permissionService.permissionTreeList(userId);
        System.out.println("主页侧边栏"+list);
        homeRespVO.setMenus(list);
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        UserInfoRespVO respVO=new UserInfoRespVO();
        if(sysUser!=null){
            respVO.setUsername(sysUser.getUsername());
            respVO.setDeptName("radarSoftware.cn");
            respVO.setId(sysUser.getId());
        }
        homeRespVO.setUserInfoVO(respVO);
        return homeRespVO;
    }
}