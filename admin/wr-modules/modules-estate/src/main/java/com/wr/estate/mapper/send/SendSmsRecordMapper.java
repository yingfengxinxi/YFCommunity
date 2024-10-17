package com.wr.estate.mapper.send;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.common.core.web.domain.BaseEntity;
import com.wr.estate.entity.vo.SendSmsRecordVo;
import com.wr.remote.domain.SendSmsRecord;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/29 21:00
 */

public interface SendSmsRecordMapper extends BaseMapper<SendSmsRecord> {

    /**
     * @param sendSmsRecordVo
     * @return
     */
    List<SendSmsRecordVo> getSendSmsOwnerList(SendSmsRecordVo sendSmsRecordVo);

    /**
     * @param sendSmsRecordVo
     * @return
     */
    List<SendSmsRecordVo> getSendSmsEstateList(SendSmsRecordVo sendSmsRecordVo);


    /**
     *
     * @param festivalName
     * @return
     */
    Integer getByYearFestivalNameCount(@Param("festivalName") String festivalName);
}
