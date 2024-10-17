package com.wr.api.estate.config;

import com.wr.api.estate.service.order.OrderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-15 10:55:20
 * @Desc:
 */
@Component
public class OrderConfig {

    @Bean(name = "orderServiceMap")
    public Map<Integer, OrderService> geneActServiceMap(@Qualifier("cleanOrderService") OrderService cleanOrderService,
                                                        @Qualifier("decorateOrderService") OrderService decorateOrderService,
                                                        @Qualifier("deviceOrderService") OrderService deviceOrderService,
                                                        @Qualifier("inspectOrderService") OrderService inspectOrderService,
                                                        @Qualifier("nightPatrolOrderService") OrderService nightPatrolOrderService,
                                                        @Qualifier("patrolOrderService") OrderService patrolOrderService,
                                                        @Qualifier("repairOrderService") OrderService repairOrderService
    ) {
        Map<Integer, OrderService> map = new HashMap<>(11);
        map.put(0, patrolOrderService);
        map.put(1, nightPatrolOrderService);
        map.put(2, inspectOrderService);
        map.put(3, cleanOrderService);
        map.put(4, decorateOrderService);
        map.put(5, repairOrderService);
        map.put(6, deviceOrderService);
        map.put(7, repairOrderService);
        map.put(8, repairOrderService);
        return map;
    }
}
