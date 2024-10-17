package com.wr.estate.service.send;

/**
 * @Author lvzy
 * @Date 2024/3/1 15:27
 */
public interface SendSmsTaskService {

    /**
     *
     */
    public void sendBirthdayMessages();

    /**
     *
     */
    public void sendFestivalMessages() throws Exception;
}
