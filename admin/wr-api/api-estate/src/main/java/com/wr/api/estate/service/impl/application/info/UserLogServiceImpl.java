package com.wr.api.estate.service.impl.application.info;

import com.wr.api.estate.entity.vo.application.info.UserLogVo;
import com.wr.api.estate.mapper.application.TableMapper;
import com.wr.api.estate.mapper.application.info.UserLogMapper;
import com.wr.api.estate.service.application.info.UserLogService;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.TableUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-17 10:20:27
 * @Desc: 业务处理层
 */
@Service
public class UserLogServiceImpl implements UserLogService {
    @Autowired
    private UserLogMapper userLogMapper;
    @Autowired
    private TableMapper tableMapper;

    /**
     * 列表
     * @param userLogVo
     * @return
     */
    @Override
    public List<UserLogVo> getList(UserLogVo userLogVo) {
        return userLogMapper.getList(userLogVo);
    }

    /**
     * 表单详情
     * @param userLogVo
     * @return
     */
    @Override
    public UserLogVo getDetail(UserLogVo userLogVo) {
        String tableName = TableUtils.getTableName(TableConstants.PREFIX_USER_LOG, userLogVo.getCommunityId(),new Date());
        String newName = TableConstants.LOG_DB_NAME + "." + tableName;
        userLogVo.setTableName(newName);
        UserLogVo userLog =  userLogMapper.getDetail(userLogVo);
        int num = 0;
        String cardNo = userLog.getIdCard();
        if(cardNo.length() == 18){
            //如果身份证号18位，取身份证号倒数第二位
            char c = cardNo.charAt(cardNo.length()-2);
            num = Integer. parseInt(String.valueOf(c));
        }else {
            //如果身份证号15位，取身份证号最后一位

            char c = cardNo.charAt(cardNo.length() - 1);
            num = Integer.parseInt(String.valueOf(c));
        }
        if(num% 2 == 1){
            userLog.setGender("男");
        }else {
            userLog.setGender("女");
        }
        return userLog;
    }

    /**
     * 今日人行总数
     * @param userLogVo
     * @return
     */
    @Override
    public int getUserCount(UserLogVo userLogVo) {
        String tableName = TableUtils.getTableName(TableConstants.PREFIX_USER_LOG, userLogVo.getCommunityId(),new Date());
        String newName = TableConstants.LOG_DB_NAME + "." + tableName;
        userLogVo.setTableName(newName);
        Integer num = tableMapper.existTable(tableName);
        if (num == 0) {
            return 0;
        }
        return userLogMapper.getUserCount(userLogVo);
    }
}
