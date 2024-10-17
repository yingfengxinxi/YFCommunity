package com.wr.estate.service.impl.work.orderLedger;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.CommunityMapper;
import com.wr.estate.mapper.work.orderLedger.ParkLedgerBillMapper;
import com.wr.estate.service.work.orderLedger.ParkLedgerBillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: lvzy
 * @Date: 2022-10-31 18:00:07
 * @Desc:
 */
@Service
public class ParkLedgerBillServiceImpl implements ParkLedgerBillService {

    @Resource
    private ParkLedgerBillMapper parkLedgerBillMapper;

    @Resource
    private CommunityMapper communityMapper;

    @Override
    public List<LinkedHashMap<String, Object>> query(Map<String, Object> params) {
        List<LinkedHashMap<String, Object>> list = parkLedgerBillMapper.query(params);
        Map<String, Object> map = new HashMap<>(2);
        list.forEach(e->{
            map.put("id",Long.valueOf(e.get("audit_id")+""));
            map.put("type","1");
             e.put("orderImg",parkLedgerBillMapper.queryOrderImg(map));
        });
        if (StringUtils.isNotNull(params.get("accept_person"))){//接单人员
            String acceptPerson = String.valueOf(params.get("accept_person"));
            return list.stream().filter(e->String.valueOf(e.get("staff_name")).indexOf(acceptPerson)!=-1).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryRepair() {


        return parkLedgerBillMapper.queryRepair(SecurityUtils.getLoginUser().getSysUser().getEstateId());
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryAssist(Long id) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("id",id);
        map.put("type",7);

        return parkLedgerBillMapper.queryAssist(map);
    }
}
