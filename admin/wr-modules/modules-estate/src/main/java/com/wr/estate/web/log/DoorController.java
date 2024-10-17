package com.wr.estate.web.log;

import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.estate.service.log.DoorService;
import com.wr.estate.web.info.BannerController;
import com.wr.remote.estate.door.VisEventAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lvzy
 * @Date 2022/10/13 20:34
 * @Version 1.5
 */

@RestController
@RequestMapping("/door")
public class DoorController extends BannerController {
    @Autowired
    private DoorService doorService;

    /**
     * 查出单元门禁设备出入记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:door:list')")
    @GetMapping("/list1")
    public TableDataInfo list(VisEventAccess visEventAccess) {

        return doorService.getDataTable(visEventAccess);
    }



}
