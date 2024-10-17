package com.wr.api.estate.service.impl.application.serve;

import com.wr.api.estate.entity.application.serve.MeterRecord;
import com.wr.api.estate.entity.vo.application.serve.MeterRecordVO;
import com.wr.api.estate.mapper.SensitiveMapper;
import com.wr.api.estate.mapper.application.serve.MeterRecordMapper;
import com.wr.api.estate.service.application.serve.MeterRecordService;
import com.wr.common.customize.util.SensitiveUtil;
import com.wr.remote.estate.center.Building;
import com.wr.remote.estate.manage.contract.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 16:34:50
 * @Desc: 业务处理层
 */
@Transactional
@Service
public class MeterRecordServiceImpl implements MeterRecordService {

    @Autowired
    private MeterRecordMapper meterRecordMapper;

    @Autowired
    private SensitiveMapper sensitiveMapper;

    @Override
    public List<MeterRecord> selectMeterCord(MeterRecord meterRecord) {
        // 默认水费
        if (null == meterRecord.getMeterType()) {
            meterRecord.setMeterType("0");
        }
        return meterRecordMapper.selectMeterCord(meterRecord);
    }


    @Override
    public int insertCord(MeterRecord meterRecord) {
        //获取上次抄表数
        BigDecimal lastConsume=meterRecordMapper.getLastMeterRecord(meterRecord);
        if(lastConsume==null){
            meterRecord.setLastConsume(null);
        }
        meterRecord.setLastConsume(lastConsume);
        return meterRecordMapper.insertCord(SensitiveUtil.sensitiveFilter(meterRecord, sensitiveMapper.selectSensitiveList()));
    }

    @Override
    public MeterRecordVO getMeterRecordDetail(Long recordId) {
        return meterRecordMapper.getMeterRecordDetail(recordId);
    }

    @Override
    public MeterRecordVO getOwnerDetail(Long ownerId) {
        return meterRecordMapper.getOwnerDetail(ownerId);
    }

    @Override
    public List<Building> getBuilderList(Long communityId) {
        return meterRecordMapper.getBuilderList(communityId);
    }
}
