package com.wr.estate.web.send;

import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.sms.SendMsgRequest;
import com.wr.common.core.utils.sms.SendSmsUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.DictUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.vo.SendSmsRecordVo;
import com.wr.estate.service.center.useraccountmanage.AccountManageService;
import com.wr.estate.service.financial.EstateEarnService;
import com.wr.estate.service.info.NoticeBirthdayService;
import com.wr.estate.service.info.NoticeEstateService;
import com.wr.estate.service.info.NoticeFestivalService;
import com.wr.estate.service.send.SendSettingService;
import com.wr.estate.service.send.SendSmsRecordService;
import com.wr.estate.service.send.SendSmsService;
import com.wr.remote.domain.*;
import com.wr.remote.estate.manage.contract.Account;
import com.wr.system.service.EstateLoginService;
import com.wr.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/2/29 21:00
 */
@RestController
@RequestMapping("sendSmsRecord")
@Api(tags = "发送短信记录")
public class SendSmsRecordController extends BaseController {


    @Autowired
    private SendSmsRecordService sendSmsRecordService;

    @Autowired
    private SendSmsService sendSmsService;

    @Autowired
    private SendSettingService sendSettingService;

    @Autowired
    private AccountManageService accountManageService;

    @Autowired
    private EstateLoginService estateLoginService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoticeEstateService noticeEstateService;

    @Autowired
    private NoticeBirthdayService noticeBirthdayService;

    @Autowired
    private NoticeFestivalService noticeFestivalService;

    /**
     * @param sendSmsRecordVo
     * @return
     */
    @ApiOperation("业主短信分页列表")
    @PostMapping("getSendSmsOwnerList")
    public TableDataInfo getSendSmsOwnerList(@RequestBody SendSmsRecordVo sendSmsRecordVo) {

        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        sendSmsRecordVo.setEstateId(estateId);

        LambdaQueryWrapper<NoticeEstate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NoticeEstate::getEstateId, estateId);
        queryWrapper.eq(NoticeEstate::getReadStatus, "0");
        List<NoticeEstate> list = noticeEstateService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(noticeEstate -> {
                noticeEstate.setReadStatus("1");
                noticeEstate.setUpdateTime(new Date());
                noticeEstateService.updateById(noticeEstate);
            });
        }
        PageUtils.startPage(sendSmsRecordVo.getPageNum(), sendSmsRecordVo.getPageSize());
        return getDataTable(sendSmsRecordService.getSendSmsOwnerList(sendSmsRecordVo));
    }

    /**
     * @param sendSmsRecordVo
     * @return
     */
    @ApiOperation("物业短信分页列表")
    @PostMapping("getSendSmsEstateList")
    public TableDataInfo getSendSmsEstateList(@RequestBody SendSmsRecordVo sendSmsRecordVo) {

        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        sendSmsRecordVo.setEstateId(estateId);

        LambdaQueryWrapper<NoticeEstate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NoticeEstate::getEstateId, estateId);
        queryWrapper.eq(NoticeEstate::getReadStatus, "0");
        List<NoticeEstate> list = noticeEstateService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(noticeEstate -> {
                noticeEstate.setReadStatus("1");
                noticeEstate.setUpdateTime(new Date());
                noticeEstateService.updateById(noticeEstate);
            });
        }
        PageUtils.startPage(sendSmsRecordVo.getPageNum(), sendSmsRecordVo.getPageSize());
        return getDataTable(sendSmsRecordService.getSendSmsEstateList(sendSmsRecordVo));
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
            SendSmsRecord sendSmsRecord = sendSmsRecordService.getById(id);
            String sendSmsId = sendSmsRecord.getSendSmsId();
            SendSms sendSms = sendSmsService.getById(sendSmsId);
            SendSetting sendSetting = sendSettingService.getById(sendSms.getSendSettingId());
            //短信消息
            SendMsgRequest sendMsgRequest = new SendMsgRequest();
            sendMsgRequest.setAccessKeyId(sendSetting.getAccessKeyId());
            sendMsgRequest.setAccessKeySecret(sendSetting.getAccessKeySecret());
            String name = "";
            String estateName = "";

            if (sendSmsRecord.getUserType().equals("1")) {
                //业主
                Account account = accountManageService.getByAccountId(sendSmsRecord.getUserId());
                if (account != null) {
                    name = account.getAccountName();
                    //Estate estate = estateLoginService.selectEstateByCommunityId(account.getCommunityId());
                   // estateName = estate.getEstateName();
                }


            } else {
                //物业
                SysUser user = userService.selectUserById(sendSmsRecord.getUserId());
                if (user != null) {
                    name = user.getNickName();
                }
            }
            if (StringUtils.isNotEmpty(name)) {
                if (type.equals("1")) {
                    //短信信息
                    sendMsgRequest.setSignName(sendSetting.getSignName());
                    sendMsgRequest.setPhoneNumber(sendSmsRecord.getPhone());
                    sendMsgRequest.setTemplateCode(sendSms.getTemplateCode());
                    String templateParam = "";
                    if (sendSms.getTemplateType().equals("1")) {
                        //生日
                        templateParam = "{\"name\":\"" + name + "\"}";
                    } else {
                        //节假日
                        templateParam = "{\"name\":\"" + name + "\",\"festivalName\":\"" + sendSmsRecord.getFestivalName() + "\"}";
                    }
                    sendMsgRequest.setTemplateParam(templateParam);

                    try {
                        String msg = SendSmsUtils.sendMsg(sendMsgRequest);
                        JSONObject jsBody = new JSONObject(msg);
                        String code = jsBody.get("code").toString();
                        if (StringUtils.equals("OK", code)) {
                            //发送成功
                            sendSmsRecord.setStatus("1");
                            sendSmsRecord.setErrorMsg("");
                        } else {
                            sendSmsRecord.setStatus("2");
                            //失败
                            String message = jsBody.get("message").toString();
                            sendSmsRecord.setErrorMsg(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        sendSmsRecord.setStatus("2");
                        //失败
                        sendSmsRecord.setErrorMsg(e.getMessage());
                    }


                } else {
                    List<SysDictData> dictCache = DictUtils.getDictCache("notice_template");
                    //站内消息
                    if (sendSms.getTemplateType().equals("1")) {

                        //生日
                        NoticeBirthday noticeBirthday = new NoticeBirthday();
                        noticeBirthday.setCommunityId(sendSms.getCommunityId());
                        noticeBirthday.setAccountId(sendSmsRecord.getUserId());
                        noticeBirthday.setSendSmsRecordId(sendSmsRecord.getId());
                        noticeBirthday.setNoticeTitle("生日快乐");
                        String remark = dictCache.stream().filter(sysDictData -> sysDictData.getDictValue().equals("0")).collect(Collectors.toList()).get(0).getRemark();
                        remark = remark.replace("${name}", name);
                        noticeBirthday.setContent(remark);
                        noticeBirthdayService.save(noticeBirthday);
                        sendSmsRecord.setStatus("1");
                        sendSmsRecord.setErrorMsg("");
                    } else {
                        //节假日
                        NoticeFestival noticeFestival = new NoticeFestival();
                        noticeFestival.setUserId(sendSmsRecord.getUserId());
                        noticeFestival.setCommunityId(sendSms.getCommunityId());
                        noticeFestival.setUserType(sendSmsRecord.getUserType());
                        noticeFestival.setSendSmsRecordId(sendSmsRecord.getId());
                        noticeFestival.setNoticeTitle(sendSmsRecord.getFestivalName() + "快乐");
                        String remark = dictCache.stream().filter(sysDictData -> sysDictData.getDictValue().equals("1")).collect(Collectors.toList()).get(0).getRemark();
                        remark = remark.replace("${name}", name);
                        remark = remark.replace("${festivalName}", sendSmsRecord.getFestivalName());

                        noticeFestival.setContent(remark);
                        noticeFestivalService.save(noticeFestival);
                        sendSmsRecord.setStatus("1");
                        sendSmsRecord.setErrorMsg("");
                    }
                }
                sendSmsRecord.setType(type);
                sendSmsRecord.setUpdateTime(new Date());
                sendSmsRecordService.updateById(sendSmsRecord);
            }
        }
        return AjaxResult.success();
    }

}
