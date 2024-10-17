package com.wr.work.service.patrolWork.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.remote.domain.Patrol;
import com.wr.remote.domain.SysUser;
import com.wr.work.mapper.patorlWork.PatrolMapper;
import com.wr.work.service.patrolWork.PatrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 巡查工单
 *
 * @Author: 奔波儿灞
 * @Date: 2022-10-27 14:25:03
 * @Desc:
 */
@Service
public class PatrolServiceImpl extends ServiceImpl<PatrolMapper, Patrol> implements PatrolService {


}
