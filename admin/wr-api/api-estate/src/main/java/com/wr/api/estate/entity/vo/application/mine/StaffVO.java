package com.wr.api.estate.entity.vo.application.mine;

import com.wr.api.estate.entity.application.mine.Staff;
import lombok.Data;

/**
 * @author DIANWEI
 * 员工 信息
 * @date 2022/11/22 17:20
 */
@Data
public class StaffVO extends Staff {

    /**
     * 账号id
     **/
    private Long userId;
    /**
     * 用户姓名
     **/
    private String userName;
    /**
     * 用户类型 10=物业管理员；11=物业端自建人员，12=物业端员工账号
     **/
    private String userType;

    /**
     * 性别：0=男，1=女 2 = 未知
     **/
    private String sex;

}
