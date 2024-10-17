package com.wr.api.device.service;

import com.alibaba.fastjson2.JSONObject;

/**
 * @Author: RainCity
 * @Date: 2021-09-24 15:58:27
 * @Desc: 云+控制器出入记录
 */
public interface CloudService {

    /**
     * 识别支持
     * @param card 社保卡原数据
     * @return
     */
    Boolean support(String card);

    /**
     * 社保卡动作执行
     * @param cloudData
     *      type 数据类型，base64加密后均为9; Reader 读头0=进，1=出;
     *      DataLen 数据长度; Index 随机值; Serial 控制器序列号，用于区别设备;
     *      Status 当前门状态, 16进制值字符串; Input 输入状态16进制值;
     *      Ver 固件详细版本; ID 设备的自定义标识符;IP 设备ip; MACc 设备MAC地址;
     *      Card 接收到的卡号、密码、二维码、身份证号码等;
     * @return
     */
    String execute(JSONObject cloudData);
}
