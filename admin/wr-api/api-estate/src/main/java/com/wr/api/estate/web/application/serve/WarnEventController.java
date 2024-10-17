package com.wr.api.estate.web.application.serve;

import com.wr.api.estate.service.application.serve.WarnEventService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.business.annotation.EncryptResponse;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.constant.HikConstants;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.security.utils.DictUtils;
import com.wr.remote.aiot.WarnEvent;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.SysDictData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 09:44:54
 * @Desc:
 */
@RestController
@RequestMapping("v2/warn")
public class WarnEventController extends BaseController {

    @Resource
    private WarnEventService warnEventService;

    @GetMapping("search")
    @EncryptResponse
    @AuthRequired
    public TableDataInfo search(Long communityId, @RequestParam(name = "srcName",required = false,defaultValue = "") String srcName){
        startPage();
        return getDataTable(warnEventService.selectWarnEventList(communityId, srcName));
    }
    @GetMapping(value = "getInfo")
    @EncryptResponse
    @AuthRequired
    public AjaxResult getInfo(Long warnId) {
        WarnEvent warnEvent = warnEventService.selectWarnEventById(warnId);
        if(null == warnEvent){
            return AjaxResult.error("未查询到相关数据");
        }
        List<SysDictData> dictDatas = DictUtils.getDictCache("sys_event_type");
        CommunityHik hik = warnEventService.selectCommunityHikById(warnEvent.getCommunityId());
        boolean getImg = false;
        if(null != hik){
            HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
            getImg = true;
        }
        SysDictData sysDictData = new SysDictData();
        String dictLabel = "";
        // 门禁事件
        if(Arrays.asList(HikConstants.EVENT_ACS).contains(warnEvent.getEventType())){
            sysDictData.setDictLabel("门禁事件");
            dictLabel = getWarnTypeDict(dictDatas, sysDictData, String.valueOf(warnEvent.getEventType()));
            return AjaxResult.success(warnEventService.selectAcsDataList(warnId, getImg, dictLabel));
        }
        // 可视对讲事件
        if(Arrays.asList(HikConstants.EVENT_VIS).contains(warnEvent.getEventType())){
            sysDictData.setDictLabel("可视对讲事件");
            dictLabel = getWarnTypeDict(dictDatas, sysDictData, String.valueOf(warnEvent.getEventType()));
            return AjaxResult.success(warnEventService.selectVisDataList(warnId, getImg, dictLabel));
        }
        // 视频预警
        if(Arrays.asList(HikConstants.EVENT_VIDEO).contains(warnEvent.getEventType())){
            sysDictData.setDictLabel("视频预警");
            dictLabel = getWarnTypeDict(dictDatas, sysDictData, String.valueOf(warnEvent.getEventType()));
            List<LinkedHashMap<String, Object>> list = warnEventService.selectVideoDataList(warnId);
            for (LinkedHashMap<String, Object> map : list) {
                return AjaxResult.success(warnEventService.selectVideoResultList(Long.valueOf(String.valueOf(map.get("dataId"))), getImg, dictLabel));

            }

        }
        // 烟火预警
        if(Arrays.asList(HikConstants.EVENT_FIRE).contains(warnEvent.getEventType())){
            sysDictData.setDictLabel("烟火预警");
            dictLabel = getWarnTypeDict(dictDatas, sysDictData, String.valueOf(warnEvent.getEventType()));
            List<LinkedHashMap<String, Object>> list = warnEventService.selectVideoDataList(warnId);
            for (LinkedHashMap<String, Object> map  : list) {
                return AjaxResult.success(warnEventService.selectSmokeFireList(Long.valueOf(String.valueOf(map.get("dataId"))), getImg, dictLabel));
            }

        }
        return AjaxResult.error();
    }

    private static String getWarnTypeDict(List<SysDictData> dictDataList, SysDictData sysDictData, String eventType){
        if(null != dictDataList && !dictDataList.isEmpty()){
            return dictDataList.stream()
                    .filter(dictData -> dictData.getDictValue().equals(eventType))
                    .findFirst().orElse(sysDictData).getDictLabel();
        }
        return "";
    }

}
