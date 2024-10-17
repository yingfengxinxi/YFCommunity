package com.wr.remote.system;

import com.wr.common.core.domain.R;
import com.wr.remote.domain.TableColumn;

/**
 * @Author: RainCity
 * @Date: 2022-08-23 15:48:18
 * @Desc: 字段属性服务
 */
public interface RemoteFieldService {
    /**
     * 通过用户名查询用户信息
     *
     * @param tableColumn 验证属性
     * @return 结果
     */
    R<String> selectFiledId(TableColumn tableColumn);

}
