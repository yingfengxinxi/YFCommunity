package com.wr.estate.service.impl.financial;
import com.wr.estate.mapper.financial.EstateCashMapper;
import com.wr.estate.service.financial.EstateCashService;
import com.wr.remote.estate.financial.EstateCash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:40:31
 * @Desc:
 */
@Service
public class EstateCashServiceImpl implements EstateCashService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private EstateCashMapper estateCashMapper;


    @Override
    public List<EstateCash> selectbuildingList(EstateCash etateCash) {
        return estateCashMapper.selectbuildingList(etateCash);
    }

    @Override
    public int selectOwnerCount(EstateCash etateCash) {
        return estateCashMapper.selectOwnerCount(etateCash);
    }

    @Override
    public BigDecimal selectBuildArea(EstateCash etateCash) {
        return estateCashMapper.selectBuildArea(etateCash);
    }

    @Override
    public EstateCash selectStandardById(Long communityId) {
        return estateCashMapper.selectStandardById(communityId);
    }

    @Override
    public BigDecimal selectPayed(EstateCash etateCash) {
        return estateCashMapper.selectPayed(etateCash);
    }

    @Override
    public int selectPayedCount(EstateCash etateCash) {
        return estateCashMapper.selectPayedCount(etateCash);
    }

    @Override
    public List<EstateCash> selectbuildingOwnerList(EstateCash estateCash) {
        List<EstateCash> list = estateCashMapper.selectbuildingOwnerList(estateCash);
        for (EstateCash owner : list) {
            String RoomName = owner.getRoomName();
            String unitName = owner.getUnitName();
            owner.setBuildingName(unitName + " " + RoomName);
            if("1".equals(owner.getFeeType())){
                    BigDecimal ac = owner.getFeeAmount();
                    owner.setShouldPay(ac);
                }else{
                    BigDecimal ac = owner.getFeeAmount();
                    owner.setShouldPay(ac.multiply(owner.getRoomArea()));
                }
                if(null == owner.getPayTime()){
                    owner.setPayStatus("未缴费");
                }else{
                    owner.setPayStatus("已缴费");
                }
        }
        return list;
    }

    @Override
    public EstateCash selectPayInfoById(EstateCash estateCash) {
        EstateCash estateCash1 = estateCashMapper.selectPayInfoById(estateCash);
        if("1".equals(estateCash1.getFeeType())){
            BigDecimal ac = estateCash1.getFeeAmount();
            estateCash1.setShouldPay(ac);
        }else{
            BigDecimal ac = estateCash1.getFeeAmount();
            BigDecimal roomArea1 = estateCash1.getRoomArea();
            BigDecimal multiply = ac.multiply(roomArea1);
            estateCash1.setShouldPay(multiply);
        }
        if(null == estateCash1.getPayTime()){
            estateCash1.setPayStatus("未缴费");
        }else{
            estateCash1.setPayStatus("已缴费");
        }

        return estateCash1;
    }

    @Override
    public List<EstateCash> selectOwnerPayList(EstateCash etateCash) {
        List<EstateCash> estateCashes = estateCashMapper.selectOwnerPayList(etateCash);
        for(EstateCash estateCash : estateCashes){
            if("1".equals(estateCash.getFeeType())){
                BigDecimal ac = estateCash.getFeeAmount();
                estateCash.setShouldPay(ac);
            }else{
                BigDecimal ac = estateCash.getFeeAmount();
                BigDecimal roomArea1 = estateCash.getRoomArea();
                BigDecimal multiply = ac.multiply(roomArea1);
                estateCash.setShouldPay(multiply);
            }
        }

        return estateCashes ;
    }
}
