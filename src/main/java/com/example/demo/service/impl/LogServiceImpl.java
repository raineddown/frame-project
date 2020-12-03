package com.example.demo.service.impl;

import com.example.demo.entity.SysLog;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.code.BaseResponseCode;
import com.example.demo.mapper.SysLogMapper;
import com.example.demo.service.LogService;
import com.example.demo.utils.PageUtil;
import com.example.demo.vo.req.SysLogPageReqVO;
import com.example.demo.vo.resp.PageVO;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: LogServiceImpl
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private SysLogMapper sysLogMapper;
    @Override
    public PageVO<SysLog> pageInfo(SysLogPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        return PageUtil.getPageVO(sysLogMapper.selectAll(vo));
    }

    @Override
    public void deletedLog(List<String> logIds) {
        int i = sysLogMapper.batchDeletedLog(logIds);
        if(i==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }
}
