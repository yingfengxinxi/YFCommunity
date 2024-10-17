package com.wr.remote.system;

import com.wr.common.core.constant.ServiceNameConstants;
import com.wr.common.core.domain.R;
import com.wr.remote.domain.TableColumn;
import com.wr.remote.system.factory.RemoteFieldApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: RainCity
 * @Date: 2022-08-23 15:48:18
 * @Desc: 字段属性服务
 */
@FeignClient(contextId = "remoteFiledApiService", value = ServiceNameConstants.API_BASE_SERVICE, fallbackFactory = RemoteFieldApiFallbackFactory.class)
public interface RemoteFieldApiService extends RemoteFieldService {
    /**
     * 通过用户名查询用户信息
     *
     * @param tableColumn 验证属性
     * @return 结果
     */
    @Override
    @GetMapping("/api_field/id_value")
    R<String> selectFiledId(TableColumn tableColumn);

}
