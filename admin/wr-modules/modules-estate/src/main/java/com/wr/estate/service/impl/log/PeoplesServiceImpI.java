package com.wr.estate.service.impl.log;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.TableUtils;
import com.wr.estate.mapper.log.PeoplesMapper;
import com.wr.estate.mapper.TableMapper;
import com.wr.estate.service.log.PeoplesService;
import com.wr.remote.estate.peoples.Peoples;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/11 16:41
 * @Version 1.5
 */

@Service

public class PeoplesServiceImpI implements PeoplesService {

    @Autowired
    private  PeoplesMapper peoplesMapper;

    @Autowired
    private TableMapper tableMapper;

    @Override
    public List<Peoples> selectList(Peoples peoples)  {
        /*if(peoples.getCommunityId()==null||peoples.getCommunityId()==0){
            return Lists.newArrayList();        }
        if (StringUtils.isNull(peoples.getDiscernTime())) {
            String tableName = TableUtils.getDbTableName(TableConstants.PREFIX_USER_LOG,peoples.getCommunityId(), new Date());
            peoples.setTableName(tableName);
            String tableName1=tableName.substring(tableName.indexOf(".")+1);
            int exist =tableMapper.existTable(tableName1);
            if (0!=exist) {
                List<Peoples> list = peoplesMapper.selectList(peoples);
                for (Peoples peoples1 : list) {
                    peoples1.setTableName(tableName);
                    peoples1.setUserPhone(around(peoples1.getUserPhone(), 3, 4));
                    peoples1.setIdCard(around(peoples1.getIdCard(), 6, 4));
                }
                return list;
            } else {

                return Lists.newArrayList();
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
                    List<Peoples> list = peoplesMapper.selectList(peoples);
                    for (Peoples peoples1 : list) {
                        peoples1.setTableName(tableName);
                        peoples1.setUserPhone(around(peoples1.getUserPhone(), 3, 4));
                        peoples1.setIdCard(around(peoples1.getIdCard(), 6, 4));
                    }
                    return list;
                } else {
                    return Lists.newArrayList();
                }
            }
            return Lists.newArrayList();*/
        return peoplesMapper.selectList(peoples);
    }

    @Override
    public List<Peoples> selectCommunityIdList() {
        return peoplesMapper.selectCommunityIdList();
    }

    @Override
    public Peoples selectPeoplesById(Peoples peoples) {
        return peoplesMapper.selectPeoplesById(peoples);
    }


    /*public static String around(String str, int index, int end) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        return StringUtils.left(str, index).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(str, end), StringUtils.length(str), "*"), "***"));
    }*/

}
