package com.wr.estate.service.log;

import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.estate.door.VisEventAccess;

/**
 * @Author lvzy
 * @Date 2022/10/13 19:45
 * @Version 1.5
 */
public interface DoorService {

    TableDataInfo getDataTable(VisEventAccess visEventAccess);
}
