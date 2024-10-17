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
import com.wr.estate.service.log.PeoplesService;
import com.wr.remote.estate.peoples.Peoples;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/11 17:41
 * @Version 1.5
 */
@RestController
@RequestMapping("/peoples")
public class PeoplesControLLer extends BaseController {
    @Autowired
    private PeoplesService peoplesService;
    @Autowired
    private TableMapper tableMapper;


    @GetMapping("/list")
    public TableDataInfo selectList(Peoples peoples) throws ParseException {
        if(peoples.getCommunityId()==null||peoples.getCommunityId()==0){
            return getDataTable(new ArrayList<>());        }
        if (StringUtils.isNull(peoples.getDiscernTime())) {
            String tableName = TableUtils.getDbTableName(TableConstants.PREFIX_USER_LOG,peoples.getCommunityId(), new Date());
            peoples.setTableName(tableName);
            String tableName1=tableName.substring(tableName.indexOf(".")+1);
            int exist =tableMapper.existTable(tableName1);
            if (0!=exist) {
                startPage();
                List<Peoples> list = peoplesService.selectList(peoples);
                for (Peoples peoples1 : list) {
                    peoples1.setTableName(tableName);
                    peoples1.setUserPhone(around(peoples1.getUserPhone(), 3, 4));
                    peoples1.setIdCard(around(peoples1.getIdCard(), 6, 4));
                }
                return getDataTable(list);
            } else {

                return getDataTable(new ArrayList<>());
            }
        }else if (StringUtils.isNotNull(peoples.getDiscernTime())){
            String tableName = TableUtils.getDbTableName(TableConstants.PREFIX_USER_LOG,peoples.getCommunityId(), peoples.getDiscernTime());
            peoples.setTableName(tableName);
            String tableName1=tableName.substring(tableName.indexOf(".")+1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = simpleDateFormat.format(peoples.getDiscernTime());
            peoples.setTime(dateStr);
            int exist =tableMapper.existTable(tableName1);
            if (0!=exist) {
                startPage();
                List<Peoples> list = peoplesService.selectList(peoples);
                for (Peoples peoples1 : list) {
                    peoples1.setTableName(tableName);
                    peoples1.setUserPhone(around(peoples1.getUserPhone(), 3, 4));
                    peoples1.setIdCard(around(peoples1.getIdCard(), 6, 4));
                }
                return getDataTable(list);
            } else {
                return getDataTable(new ArrayList<>());
            }
        }
        return getDataTable(new ArrayList<>());
        /*startPage();
        List<Peoples> list=peoplesService.selectList(peoples);
        return getDataTable(list);*/
    }


    @GetMapping("id")
    public AjaxResult selectById(Peoples peoples)
    {
        return AjaxResult.success(peoplesService.selectPeoplesById(peoples));

    }


    @GetMapping("/communityId")
    public List<Peoples> selctList1()
    {
        return peoplesService.selectCommunityIdList();

    }

    public static String around(String str, int index, int end) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        return StringUtils.left(str, index).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(str, end), StringUtils.length(str), "*"), "***"));
    }
}
