package com.wr.estate.web.financial;

import cn.hutool.json.JSONObject;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.sms.SendMsgRequest;
import com.wr.common.core.utils.sms.SendSmsUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.DictUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.center.RoomService;
import com.wr.estate.service.center.useraccountmanage.AccountManageService;
import com.wr.estate.service.financial.PropertyFeeService;
import com.wr.estate.service.financial.PropertyLogService;
import com.wr.estate.service.info.NoticeEstateService;
import com.wr.estate.service.info.NoticePropertyService;
import com.wr.estate.service.manage.contract.OwnerService;
import com.wr.estate.service.send.SendSettingService;
import com.wr.estate.service.send.SendSmsRecordService;
import com.wr.estate.service.send.SendSmsService;
import com.wr.remote.domain.*;
import com.wr.remote.estate.center.vo.RoomDetailVO;
import com.wr.remote.estate.manage.contract.Account;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: bajie
 * @create: 2022/11/3
 * @Description:
 * @FileName: PropertyFeeController
 * @History:
 */
@RestController
@RequestMapping("/propertyLog")
@Api(tags = "物管平台-物业缴费账单")

public class PropertyLogController extends BaseController {

    @Autowired
    private PropertyLogService propertyLogService;

    @Autowired
    private SendSettingService sendSettingService;

    @Autowired
    private SendSmsService sendSmsService;

    @Autowired
    private AccountManageService accountManageService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private NoticePropertyService noticePropertyService;

    @Autowired
    private NoticeEstateService noticeEstateService;

    /**
     * 查询物业缴费账单列表
     */

    @PostMapping("/list")
    @ApiOperation("分页列表")
    public TableDataInfo list(@RequestBody PropertyLog propertyLog) {

        LambdaQueryWrapper<NoticeEstate> queryWrapper = new LambdaQueryWrapper<>();
        if (propertyLog.getCommunityId() == null) {
            return getDataTable(Lists.newArrayList());
        }
        queryWrapper.eq(NoticeEstate::getCommunityId, propertyLog.getCommunityId());
        queryWrapper.eq(NoticeEstate::getReadStatus, "0");
        List<NoticeEstate> estateList = noticeEstateService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(estateList)) {
            estateList.stream().forEach(no -> {
                no.setReadStatus("1");
                noticeEstateService.updateById(no);
            });
        }
        PageUtils.startPage(propertyLog.getPageNum(), propertyLog.getPageSize());
        List<PropertyLog> list = propertyLogService.getList(propertyLog);
        return getDataTable(list);
    }

    /**
     * @param logIds
     * @return
     */
    @ApiOperation("发送账单")
    @GetMapping("bachUpdatePayStatus")
    public AjaxResult bachUpdatePayStatus(@RequestParam("logIds") String logIds) {
        List<String> logIdList = Arrays.asList(logIds.split(","));
        propertyLogService.bachUpdatePayStatus(logIdList);
        return AjaxResult.success();
    }

    /**
     * @param ids
     * @return
     */
    @ApiOperation("重新发送短信")
    @GetMapping("sendSms")
    public AjaxResult sendSms(@RequestParam("ids") String ids, @RequestParam("type") String type) {
        List<String> idList = Arrays.asList(ids.split(","));
        for (String id : idList) {
            PropertyLog propertyLog = propertyLogService.getById(Long.valueOf(id));
            if (propertyLog.getPayStatus().equals("1")) {
                LambdaQueryWrapper<SendSms> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SendSms::getStatus, "1");
                queryWrapper.eq(SendSms::getTemplateType, "2");
                queryWrapper.eq(SendSms::getCommunityId, propertyLog.getCommunityId());
                SendSms sendSms = sendSmsService.getOne(queryWrapper);
                SendSetting sendSetting = sendSettingService.getById(sendSms.getSendSettingId());
                //短信消息
                SendMsgRequest sendMsgRequest = new SendMsgRequest();
                sendMsgRequest.setAccessKeyId(sendSetting.getAccessKeyId());
                sendMsgRequest.setAccessKeySecret(sendSetting.getAccessKeySecret());
                String name = "";
                String phone = "";

                Long ownerId = roomService.getRoomIdOwnerId(propertyLog.getRoomId());
                //亲爱的${业主名称}业主，您好，感谢这一年对我们物业人员工作上的支持和配合，希望各业主能积极在${缴费日期}前将管理费用${金额}元交于物业管理处，物业费是小区公共秩序维护、环境卫生公共区域维修，以及配电、公共照明等设备设施正常运转的根本保障。所以希望各业主能积极配合，谢谢!
                //业主
                Long accountId = ownerService.getByOwnerIdAccountId(ownerId, propertyLog.getCommunityId());
                Account account = accountManageService.getByAccountId(accountId);
                if (account != null) {
                    name = account.getAccountName();
                    phone = account.getLoginName();
                }
                Date nextDate = DateUtils.getNextDate(5);
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                if (StringUtils.isNotEmpty(name)) {
                    if (type.equals("1")) {
                        //短信信息
                        sendMsgRequest.setSignName(sendSetting.getSignName());
                        sendMsgRequest.setPhoneNumber(phone);
                        sendMsgRequest.setTemplateCode(sendSms.getTemplateCode());
                        String templateParam = "{\"name\":\"" + name + "\",\"date\":\"" + nextDate + "\",\"money\":\"" + propertyLog.getPaidAmount() + "\"}";
                        sendMsgRequest.setTemplateParam(templateParam);

                        try {
                            //发送缴费通知短信
                            String msg = SendSmsUtils.sendMsg(sendMsgRequest);
                            JSONObject jsBody = new JSONObject(msg);
                            String code = jsBody.get("code").toString();
                            if (StringUtils.equals("OK", code)) {
                                //发送成功
                                propertyLog.setSendStatus("1");
                                propertyLog.setSendErrorMsg("");
                            } else {

                                //失败
                                String message = jsBody.get("message").toString();
                                propertyLog.setSendStatus("2");
                                propertyLog.setSendErrorMsg(message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            propertyLog.setSendStatus("2");
                            //失败
                            propertyLog.setSendErrorMsg(e.getMessage());
                        }


                    } else {
                        List<SysDictData> dictCache = DictUtils.getDictCache("notice_template");
                        //站内消息

                        NoticeProperty noticeProperty = new NoticeProperty();
                        noticeProperty.setCommunityId(sendSms.getCommunityId());
                        noticeProperty.setAccountId(accountId);
                        noticeProperty.setPropertyLogId(propertyLog.getLogId());
                        noticeProperty.setNoticeTitle("物业费缴费通知");
                        String remark = dictCache.stream().filter(sysDictData -> sysDictData.getDictValue().equals("2")).collect(Collectors.toList()).get(0).getRemark();
                        remark = remark.replace("${name}", name);
                        remark = remark.replace("${date}", sim.format(nextDate));
                        remark = remark.replace("${money}", String.valueOf(propertyLog.getPaidAmount()));
                        noticeProperty.setContent(remark);
                        noticePropertyService.save(noticeProperty);
                        propertyLog.setSendStatus("1");
                        propertyLog.setSendErrorMsg("");
                    }
                    propertyLog.setSendType(type);
                    propertyLogService.updateById(propertyLog);
                }
            }
        }
        return AjaxResult.success();
    }

    /**
     * @param roomId
     * @return
     */
    @ApiOperation("根据房屋id查询物业费")
    @GetMapping("getByRoomIdAmount")
    public AjaxResult getByRoomIdAmount(@RequestParam("roomId") Long roomId, @RequestParam("isUnpaidPropertyFee") String isUnpaidPropertyFee) {

        return AjaxResult.success(propertyLogService.getByRoomIdAmount(roomId, isUnpaidPropertyFee));
    }

    /**
     * @param discountAmount
     * @param paidAmount
     * @param logId
     * @return
     */
    @ApiOperation("修改金额")
    @GetMapping("updateByLogIdAmount")
    public AjaxResult updateByLogIdAmount(
            @RequestParam("discountAmount") BigDecimal discountAmount,
            @RequestParam("paidAmount") BigDecimal paidAmount,
            @RequestParam("logId") Long logId) {
        propertyLogService.updateByLogIdAmount(discountAmount, paidAmount, logId);
        return AjaxResult.success();
    }
}
