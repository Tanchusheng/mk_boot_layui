package com.swjd.modules.system.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.swjd.modules.system.dao.LogDao;
import com.swjd.modules.system.entity.Log;
import com.swjd.modules.system.service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/28 14:44
 * @Version：1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LogServiceImpl extends ServiceImpl<LogDao, Log> implements LogService {
}
