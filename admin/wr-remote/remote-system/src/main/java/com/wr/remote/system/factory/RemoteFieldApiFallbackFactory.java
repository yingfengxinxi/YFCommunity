package com.wr.remote.system.factory;

import com.wr.common.core.domain.R;
import com.wr.remote.domain.TableColumn;
import com.wr.remote.system.RemoteFieldApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: RainCity
 * @Date: 2022-08-23 15:48:18
 * @Desc: 字段属性降级处理
 */
@Component
public class RemoteFieldApiFallbackFactory implements FallbackFactory<RemoteFieldApiService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteFieldApiFallbackFactory.class);

    @Override
    public RemoteFieldApiService create(Throwable throwable) {
        log.error("接口端字段属性服务调用失败:{}", throwable.getMessage());
        return new RemoteFieldApiService() {
            @Override
            public R<String> selectFiledId(TableColumn tableColumn)
            {
                return R.fail("获取失败:" + throwable.getMessage());
            }

        };
    }
}
