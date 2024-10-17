package com.wr.remote.govern.autonomy.vote;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.estate.customer.vo.question.QuestionVo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: liubei
 * @Date: 2022/10/12 14:46
 * @Desc: 投票信息
 */
@Data
public class Vote extends BaseEntity {
    /**
     * 主键
     */
    private Long voteId;
    /**
     * 物业id
     */
    private Long estateId;
    /**
     * 投票名称
     */
    private String voteName;
    /**
     * 状态
     */
    private String voteStatus;
    /**
     * 发布开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 发布结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    /**
     * 发布内容
     */
    private  String content;

    /**
     * 标识
     */

    private  String  delFlag;
    /**
     * 数据来源
     */
    private String dataSource;
    /**
     * 社区ID
     */
    private  String  communityId;
    /**
     * 社区集合
     */
    private List<Long> communityIds;

    /**
     * 楼栋范围集合
     */
    private List<Long> buildingIds;

    /**
     * 单元范围集合
     */
    private List<Long> unitIds;
    /**单选题*/
    private List<QuestionVo> single;

    /**多选题*/
    private List<QuestionVo> multiple;

    /**单行文本题*/
    private List<QuestionVo> text;
    /**
     * 投票标题集合
     */
    private List<Vote>headingList;
    /**
     * 投票选项集合
     */
    private List<VoteItem> itemList;
    /**
     * 社区别名
     */
    private   String estateName;

    /**发布开始时间结束时间*/
    private String[] startEndTime;

    private Boolean hasFinish;

    /**
     * 楼栋范围名称
     */
    private String buildingNames;

    /**
     * 单位范围名称
     */
    private String unitNames;
}
