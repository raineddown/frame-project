package com.example.demo.service;


import com.example.demo.entity.SysDept;
import com.example.demo.vo.req.DeptAddReqVO;
import com.example.demo.vo.req.DeptUpdateReqVO;
import com.example.demo.vo.resp.DeptRespNodeVO;

import java.util.List;

/**
 * @ClassName: DeptService
 * TODO:类文件简单描述
 * @Version: 0.0.1
 */
public interface DeptService {

    List<SysDept> selectAll();

    List<DeptRespNodeVO> deptTreeList(String deptId);

    SysDept addDept(DeptAddReqVO vo);

    void updateDept(DeptUpdateReqVO vo);

    void deletedDept(String id);

}
