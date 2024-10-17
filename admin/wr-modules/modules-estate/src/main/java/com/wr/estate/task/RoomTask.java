package com.wr.estate.task;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.estate.service.center.RoomService;
import com.wr.estate.service.center.personnelfilemanage.TenantFileService;
import com.wr.estate.service.customer.rent.RentService;
import com.wr.estate.service.manage.contract.LeaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/23 22:30
 */
@Component
@Slf4j
public class RoomTask {


    @Autowired
    private TenantFileService tenantFileService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RentService rentService;


    /**
     * 每天凌晨执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
    //@Scheduled(cron = "0 0/5 * * * ?")
    public void updateRoomStatus() {
        log.info("=======================START执行修改房屋状态定时任务=======================");
        List<Long> roomIds = tenantFileService.getContractEndRoomIds();
        if (CollectionUtils.isNotEmpty(roomIds)) {
            for (Long roomId : roomIds) {
                //租房到期修改状态为入住
                rentService.updateByRoomIdStatus("2", roomId);
            }
        }
        log.info("=======================END执行修改房屋状态定时任务=======================");
    }
}