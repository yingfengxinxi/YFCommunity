package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.vo.serve.ExaminerVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-11 11:29:52
 * @Desc: 服务-体检报告 业务
 */
public interface ExaminerService {

    /**
     * 获取体检报告
     * @param communityId 小区id
     * @param idCard 业主身份证号
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.ExaminerVO>
     */
    List<ExaminerVO> getExaminerAll(Long communityId, String idCard);

    /**
     * 获取详情
     * @param examinerId 主键id
     * @return: com.wr.api.owner.entity.vo.serve.ExaminerVO
     */
    ExaminerVO getExaminerById(@Param("examinerId") Long examinerId);
}
