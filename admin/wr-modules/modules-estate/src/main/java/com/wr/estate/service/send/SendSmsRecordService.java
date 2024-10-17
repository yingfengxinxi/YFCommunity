package com.wr.estate.service.send;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.estate.entity.vo.SendSmsRecordVo;
import com.wr.remote.domain.SendSmsRecord;


import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/29 21:00
 */

public interface SendSmsRecordService extends IService<SendSmsRecord> {

    /**
     *
     * @param sendSmsRecordVo
     * @return
     */
    List<SendSmsRecordVo> getSendSmsOwnerList(SendSmsRecordVo sendSmsRecordVo);

    /**
     *
     * @param sendSmsRecordVo
     * @return
     */
    List<SendSmsRecordVo> getSendSmsEstateList(SendSmsRecordVo sendSmsRecordVo);

    /**
     *
     * @param festivalName
     * @return
     */
    Integer getByYearFestivalNameCount(String festivalName);
}
