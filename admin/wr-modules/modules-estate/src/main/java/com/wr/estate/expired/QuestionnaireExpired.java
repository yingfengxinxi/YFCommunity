package com.wr.estate.expired;

import com.wr.common.core.constant.CacheConstants;
import com.wr.estate.entity.BusQuestionnaire;
import com.wr.estate.service.busQuestionnaire.BusQuestionnaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @ClassName QuestionnaireExpired
 * @Author lvzy
 * @Date 2023/6/10 0010 18:25
 * 调查问卷redis到期执行
 **/

@Component
@Slf4j
public class QuestionnaireExpired extends KeyExpirationEventMessageListener {


    @Autowired
    private BusQuestionnaireService busQuestionnaireService;


    public QuestionnaireExpired(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 过期redisKey
     *
     * @param message Key
     * @param pattern 不知道是个什么东东 父类也没有使用这个参数
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        super.onMessage(message, pattern);
        String redisKey = message.toString();
        String[] redisKeys = redisKey.split(":");
        if (redisKey.contains(CacheConstants.QUEST_IONNAIRE)) {
            String questionnaireId = redisKeys[1];
            BusQuestionnaire questionnaire = busQuestionnaireService.getById(questionnaireId);
            questionnaire.setId(questionnaireId);
            questionnaire.setStatus("1");
            busQuestionnaireService.updateById(questionnaire);

        }
        if (redisKey.contains(CacheConstants.QUEST_IONNAIRE_EXP)) {
            String questionnaireId = redisKeys[1];
            BusQuestionnaire questionnaire = busQuestionnaireService.getById(questionnaireId);
            questionnaire.setId(questionnaireId);
            questionnaire.setStatus("3");
            busQuestionnaireService.updateById(questionnaire);

        }
    }
}
