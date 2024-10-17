package com.wr.system.enums;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登记状态美剧
 * @author lx970
 * @date 2024/2/6 11:01
 */
public enum RegisterStatusEnums {

    EXISTENCE("0","存续（在营、开业、在册）"),
    INDUSTRY("1","在业"),
    REVOKED("2","吊销"),
    LOGOUT("3","注销"),
    MOVE_IN("4","迁入"),
    MOVE_OUT("5","迁出"),
    CLOSED("6","停业"),
    LIQUIDOTION("7","清算"),
    ;
    private String code;
    private String desc;

    private RegisterStatusEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByCode(String code) {
        RegisterStatusEnums[] values = RegisterStatusEnums.values();
        for (RegisterStatusEnums value : values) {
            if (value.getCode().equals(code)) {
                return value.desc;
            }
        }
        return null;
    }

    public static List<RegisterStatusEnums> getList(){
        List<RegisterStatusEnums> list = EnumSet.allOf(RegisterStatusEnums.class).stream().collect(Collectors.toList());
        return list;
    }
}
