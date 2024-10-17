package com.wr.remote.govern.autonomy.investigation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.estate.customer.vo.question.QuestionVo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: liubei
 * @Date: 2022/10/12 14:46
 * @Desc: 问题
 */
@Data
public class Questionnaire extends BaseEntity {
    /**
     * 主键
     */
    private Long questionnaireId;
    /**
     * 问题ID
     */

    private Long questionId;
    /**
     * 物业Id
     */
    private Long estateId;
    /**
     * 问卷调查名称
     */
    private String quesName;
    /**
     * 状态
     */
    private String quesStatus;

    /**
     * 标识
     */
    private String  delFlag;
    /**
     * 社区别名
     */
    private   String estateName;
    /**
     * 问题类型
     */
    private  String questionType;
    /**
     * 社区ID
     */
    private  String   communityId;
    /**
     * 社区ID集合
     */
    private List<Long> communityIds;
    /**单选题*/
    private List<QuestionVo> single;

    /**多选题*/
    private List<QuestionVo> multiple;

    /**单行文本题*/
    private List<QuestionVo> text;
    /**
     * 标题集合
     */

    private List<Question>headingList;

    /**
     * 发布开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 发布结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**发布开始时间结束时间*/
    private String[] startEndTime;


}
