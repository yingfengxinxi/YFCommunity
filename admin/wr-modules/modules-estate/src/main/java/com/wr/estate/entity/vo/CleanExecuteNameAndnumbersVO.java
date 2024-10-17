package com.wr.estate.entity.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-12-09 17:26:14
 * @Desc:
 */
@Data
@ToString
public class CleanExecuteNameAndnumbersVO {

    List<CleanExecuteNameVO> cleanExecuteNameVOList;

    Integer numbers;
}
