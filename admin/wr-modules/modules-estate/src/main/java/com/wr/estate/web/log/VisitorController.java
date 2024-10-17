package com.wr.estate.web.log;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.TableUtils;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.estate.mapper.TableMapper;
import com.wr.estate.service.log.VisitorService;
import com.wr.remote.estate.visitor.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/12 11:36
 * @Version 1.5
 */

@RestController
@RequestMapping("/visitor")
public class VisitorController extends BaseController {

    @Autowired
    private VisitorService visitorService;
    @Autowired
    private TableMapper tableMapper;

    @PreAuthorize("@ss.hasPermi('system:visitor:list')")
    @GetMapping("/list")
    public TableDataInfo selectList(Visitor visitor){
        if (StringUtils.isNull(visitor.getCommunityId())){
            return getDataTable(new ArrayList<>());
        }
        if (StringUtils.isNull(visitor.getMonth())){
            String tableName = TableUtils.getDbTableName(TableConstants.PREFIX_VISITOR_LOG,visitor.getCommunityId(), new Date());
            visitor.setTableName(tableName);
            String tableName1=tableName.substring(tableName.indexOf(".")+1);
            int exist =tableMapper.existTable(tableName1);

            if (0!=exist){
                startPage();
                List<Visitor>list=visitorService.selectList(visitor);
                for (Visitor visitor1 : list) {
                    visitor1.setTableName(tableName);
                    visitor1.setUserPhone(
                            around(visitor1.getUserPhone(), 3, 4));
                    visitor1.setIdCard(around(visitor1.getIdCard(), 6, 4));
                }

                return getDataTable(list);
            }else {
                return getDataTable(new ArrayList<>());
            }
        }else if (StringUtils.isNotNull(visitor.getMonth())){
            String tableName = TableUtils.getDbTableName(TableConstants.PREFIX_VISITOR_LOG,visitor.getCommunityId(), visitor.getMonth());
            visitor.setTableName(tableName);
            String tableName1=tableName.substring(tableName.indexOf(".")+1);
            int exist =tableMapper.existTable(tableName1);
            if (0!=exist){
                startPage();
                List<Visitor>list=visitorService.selectList(visitor);
                for (Visitor visitor1 : list) {
                    visitor1.setTableName(tableName);
                    visitor1.setUserPhone(around(visitor1.getUserPhone(), 3, 4));
                    visitor1.setIdCard(around(visitor1.getIdCard(), 6, 4));}

                return getDataTable(list);
            }else {
                return getDataTable(new ArrayList<>());
            }
        }
        return getDataTable(new ArrayList<>());

    }

    @GetMapping("/communityId")
    public List<Visitor> selctList1()
    {
        return visitorService.selectCommunityIdList();
    }

    @GetMapping("/Id")
    public AjaxResult selectById(Visitor visitor){
        return AjaxResult.success(visitorService.selectById(visitor));
    }

    public static String around(String str, int index, int end) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        return StringUtils.left(str, index).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(str, end), StringUtils.length(str), "*"), "***"));
    }
}
