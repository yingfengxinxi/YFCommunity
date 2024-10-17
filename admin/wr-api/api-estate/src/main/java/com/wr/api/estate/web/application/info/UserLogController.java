package com.wr.api.estate.web.application.info;

import com.wr.api.estate.entity.vo.application.info.UserLogVo;
import com.wr.api.estate.entity.vo.application.info.VisitLogVo;
import com.wr.api.estate.mapper.application.TableMapper;
import com.wr.api.estate.service.application.info.UserLogService;
import com.wr.api.estate.service.application.info.VisitVoService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.TableUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:07:17
 * @Desc:
 */
@RestController
@RequestMapping("/v2/userLog")
public class UserLogController extends BaseController {
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private TableMapper tableMapper;

    /**
     * 列表
     * @param userLogVo
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(UserLogVo userLogVo) {
        String tableName = TableUtils.getTableName(TableConstants.PREFIX_USER_LOG, userLogVo.getCommunityId(),new Date());
        String newName = TableConstants.LOG_DB_NAME + "." + tableName;
        userLogVo.setTableName(newName);
        Integer num = tableMapper.existTable(tableName);
        if (num == 0) {
            return getDataTable(new ArrayList<>());
        }
        startPage();
        List<UserLogVo> list = userLogService.getList(userLogVo);
        return getDataTable(list);
    }

    /**
     * 表单详情
     * @param userLogVo
     * @return
     */
    @GetMapping("/detail")
    public AjaxResult getInfo(UserLogVo userLogVo) {
        return AjaxResult.success(userLogService.getDetail(userLogVo));
    }

    /**
     * 人行总数
     * @param userLogVo
     * @return
     */
    @GetMapping("/count")
    public AjaxResult getCount(UserLogVo userLogVo) {
        return AjaxResult.success(userLogService.getUserCount(userLogVo));
    }
}
