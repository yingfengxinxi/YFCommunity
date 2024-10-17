package com.wr.estate.service.impl.log;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.TableUtils;
import com.wr.estate.mapper.TableMapper;
import com.wr.estate.mapper.log.VisitorMapper;
import com.wr.estate.service.log.VisitorService;
import com.wr.remote.estate.visitor.Visitor;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/12 11:08
 * @Version 1.5
 */

@Service
public class VisitorServiceImpI implements VisitorService {
    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private TableMapper tableMapper;

    @Override
    public List<Visitor> selectList(Visitor visitor) {
        /*if (StringUtils.isNull(visitor.getCommunityId())){
            return  Lists.newArrayList();
        }
        if (StringUtils.isNull(visitor.getMonth())){
            String tableName = TableUtils.getDbTableName(TableConstants.PREFIX_VISITOR_LOG,visitor.getCommunityId(), new Date());
            visitor.setTableName(tableName);
            String tableName1=tableName.substring(tableName.indexOf(".")+1);
            int exist =tableMapper.existTable(tableName1);
            if (0!=exist){
                List<Visitor>list=visitorMapper.selectList(visitor);
                for (Visitor visitor1 : list) {
                    visitor1.setTableName(tableName);
                    visitor1.setUserPhone(
                            around(visitor1.getUserPhone(), 3, 4));
                    visitor1.setIdCard(around(visitor1.getIdCard(), 6, 4));
                }
                return list;
            }else {
                    return Lists.newArrayList();
            }
        }else if (StringUtils.isNotNull(visitor.getMonth())){
            String tableName = TableUtils.getDbTableName(TableConstants.PREFIX_VISITOR_LOG,visitor.getCommunityId(), visitor.getMonth());
            visitor.setTableName(tableName);
            String tableName1=tableName.substring(tableName.indexOf(".")+1);
            int exist =tableMapper.existTable(tableName1);
            if (0!=exist){
                List<Visitor>list=visitorMapper.selectList(visitor);
                for (Visitor visitor1 : list) {
                    visitor1.setTableName(tableName);
                    visitor1.setUserPhone(around(visitor1.getUserPhone(), 3, 4));
                    visitor1.setIdCard(around(visitor1.getIdCard(), 6, 4));}
                return list;
            }else {
                return Lists.newArrayList();
            }
        }
        return  Lists.newArrayList();*/
        return visitorMapper.selectList(visitor);
    }

    @Override
    public List<Visitor> selectCommunityIdList() {
        return visitorMapper.selectCommunityIdList();
    }

    @Override
    public Visitor selectById(Visitor visitor) {
        return visitorMapper.selectById(visitor);
    }

    /*public static String around(String str, int index, int end) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        return StringUtils.left(str, index).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(str, end), StringUtils.length(str), "*"), "***"));
    }*/

}
