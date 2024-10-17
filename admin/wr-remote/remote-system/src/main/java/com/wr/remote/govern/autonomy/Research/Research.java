package com.wr.remote.govern.autonomy.Research;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.estate.customer.vo.question.QuestionVo;
import com.wr.remote.govern.autonomy.investigation.Question;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: liubei
 * @Date: 2022/10/12 14:46
 * @Desc: 问题
 */
@Data
public class Research extends BaseEntity {
    /**
     * 主键
     */
    private Long researchId;

    /**
     * 问题ID
     */
    private Long questionId;

    /**
    * 账号ID
    **/
    private Long accountId;
    /**
     * 党建id
     */
    private String communityId;

    /**
     * 小区ID
     */
    private Long community;

    /**
     * 党建调研名称
     */
    private String researchName;

    /**
     * 简介
     */
    private String researchIntro;
    /**
     * 状态：0=启用，1=关闭
     */
    private String researchStatus;
    /**
     * 党建调研 状态0=未发布1=进行中2=已提交3=已结束
     */
    private String status;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadlineTime;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 标识
     */
    private String delFlag;
    /**
     * 社区名称
     */

    private String estateName;
    /**
     * 类型
     */
    private String questionType;
    /**
     * 社区集合
     */

    private Long communityIds;
    /**
     * 单选题
     */
    private List<QuestionVo> single;

    /**
     * 多选题
     */
    private List<QuestionVo> multiple;

    /**
     * 单行文本题
     */
    private List<QuestionVo> text;
    /**
     * 标题集合
     */
    private List<Question> headingList;


}
