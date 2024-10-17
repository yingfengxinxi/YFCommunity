package com.wr.estate.mapper;

import com.wr.remote.estate.custom.vo.ComplaintDeatilVO;
import com.wr.remote.estate.custom.vo.ComplanintPraiseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 荀彧
 * @Date: 2022-10-11 15:37:07
 * @Desc: 投诉表扬Mapper
 */
public interface ComplanintMapper {

    /**
     * 查询表扬投诉表
     */
    List<ComplanintPraiseVO> getAllComplanint(ComplanintPraiseVO complanintPraiseVO);

    /**
     * 根据ID查询表扬投诉表
     *
     * @param complaintId
     * @return
     */


    //complaint_type==0
    ComplaintDeatilVO getComplaintNullById(Long complaintId);

    //complaint_type==1
    ComplaintDeatilVO getComplaintById(Long complaintId);

    //complaint_type==2
    ComplaintDeatilVO getParkById(Long complaintId);

    //complaint_type==3
    ComplaintDeatilVO getDisasterById(Long complaintId);

    String selectType(Long complaintId);

    /**
     * 公式
     * @param complaintId
     * @return: java.lang.Integer
     * @date: 2023/4/7 14:30
     * @author: SJiang
     **/
    Integer upPublicStatus(@Param("complaintId") Long complaintId);
}
