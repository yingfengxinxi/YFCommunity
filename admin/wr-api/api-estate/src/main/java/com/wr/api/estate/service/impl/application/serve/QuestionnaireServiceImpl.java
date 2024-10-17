package com.wr.api.estate.service.impl.application.serve;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.api.estate.entity.application.serve.QuestionnaireVo;
import com.wr.api.estate.mapper.application.QuestionnaireMapper;
import com.wr.api.estate.service.application.serve.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/1/11 14:13
 */
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Override
    public List<QuestionnaireVo> getList(QuestionnaireVo questionnaireVo) {
        String status = questionnaireVo.getStatus();
        if (questionnaireVo.getStatus().equals("2")) {
            questionnaireVo.setStatus("");
        }
        List<QuestionnaireVo> list = questionnaireMapper.getList(questionnaireVo);
        if (status.equals("2")) {
            //已提交状态查询已经提交过的业主数据
            if (CollectionUtils.isNotEmpty(list)) {
                return list.stream().filter(questionnaireVo1 -> questionnaireVo1.getNum() >= 1).collect(Collectors.toList());
            }
        }
        return list;
    }
}
