package com.example.demo.service;



import com.example.demo.entity.SysLog;
import com.example.demo.vo.req.SysLogPageReqVO;
import com.example.demo.vo.resp.PageVO;

import java.util.List;

/**
 * @ClassName: LogService
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
public interface LogService {

    PageVO<SysLog> pageInfo(SysLogPageReqVO vo);

    void deletedLog(List<String> logIds);
}
