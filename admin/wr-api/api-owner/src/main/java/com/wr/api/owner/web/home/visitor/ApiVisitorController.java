package com.wr.api.owner.web.home.visitor;


import com.wr.api.owner.entity.home.visitor.ApiHouse;
import com.wr.api.owner.entity.home.visitor.OwnerHouse;
import com.wr.api.owner.entity.home.visitor.vo.ApiLiaisonVo;
import com.wr.api.owner.entity.home.visitor.vo.OwnerHouseVo;
import com.wr.api.owner.entity.home.visitor.vo.RegisterVisitorVO;
import com.wr.api.owner.service.home.visitor.ApiVisitorService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: lusu
 * @Date: 2022/10/15 11:46
 * @Desc: 访客控制层
 */
@RestController
@RequestMapping("/v2/apiVisitor")
public class ApiVisitorController extends BaseController {

    @Autowired
    private ApiVisitorService apiVisitorService;


    /**
     * 获取业主访客列表
     *
     * @param apiLiaisonVo
     * @return
     * @throws ParseException
     */
    @GetMapping("/list")
    @AuthRequired
    public TableDataInfo list(ApiLiaisonVo apiLiaisonVo) throws ParseException {
        startPage();
        //调用接口获取内容
        List<ApiLiaisonVo> list = apiVisitorService.getList(apiLiaisonVo);
        return getDataTable(list);
    }


    /**
     * 访客详情
     *
     * @param visitorId
     * @return
     */
    @GetMapping(value = "/{visitorId}")
    @AuthRequired
    public AjaxResult getVisitorById(@PathVariable Long visitorId) {
        ApiLiaisonVo notice = apiVisitorService.getVisitorById(visitorId);
        return AjaxResult.success(notice);
    }


    /**
     * 通过业主ownerId，以及名下房产房产
     *
     * @param ownerId
     * @return
     */
    @GetMapping(value = "/ownerInfo/{ownerId}")
    @AuthRequired
    public AjaxResult ownerInfo(@PathVariable Long ownerId) {
        AjaxResult ajaxResult = new AjaxResult();
        List<OwnerHouseVo> notice = apiVisitorService.ownerInfo(ownerId);

        //取出业主的房产信息
        List<OwnerHouse> ownerHouseList = notice.get( 0 ).getOwnerHouseList();
        //存放ApiHouse对象，最终异步发送
        ArrayList<ApiHouse> addressList = new ArrayList<ApiHouse>();
        for (OwnerHouse ownerHouse : ownerHouseList) {
            //将小区+楼宇+单元+房号拼接为一个字符串
            StringBuffer addressBuffer = new StringBuffer();
            addressBuffer.append(ownerHouse.getCommunityName());
            addressBuffer.append(ownerHouse.getBuildingName());
            addressBuffer.append(ownerHouse.getUnitName());
            addressBuffer.append(ownerHouse.getRoomName());

            //创建ApiHouse实体类，并赋值，存放addressList中
            ApiHouse apiHouse = new ApiHouse();
            apiHouse.setAddress(addressBuffer.toString());
            apiHouse.setRoomId(ownerHouse.getRoomId());
            apiHouse.setCommunityId(ownerHouse.getCommunityId());
            addressList.add(apiHouse);
        }

        //发送
        ajaxResult.put("ownerAddress", addressList);
        ajaxResult.put("data", notice);
        return ajaxResult;
    }


    /**
     * 新增访客信息（业主给访客申请二维码！！）
     *
     * @param
     * @return
     */
    @PostMapping("/add")
    @AuthRequired
    @Log(title = "新增", businessType = BusinessType.INSERT)
    public AjaxResult add(@RequestBody ApiLiaisonVo apiLiaisonVo) {
        Integer inte = apiVisitorService.add(apiLiaisonVo);
        return inte > 0 ? AjaxResult.success("新增成功") : AjaxResult.error("新增失败");
    }


    /**
     * 驳回 ~  通过  状态修改
     *
     * @param apiLiaisonVo
     * @return
     */
    @AuthRequired
    @PostMapping("/updateStatus")
    @Log(title = "修改", businessType = BusinessType.UPDATE)
    public AjaxResult updateStatus(@RequestBody ApiLiaisonVo apiLiaisonVo) {
        return toAjax(apiVisitorService.updateStatus(apiLiaisonVo));
    }

    /**
     * 注册访客
     *
     * @param registerVisitor 注册参数
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/12/1 10:44
     */
    @PostMapping("/register")
    @ApiLog(title="业主端-登录", businessType = BusinessType.INSERT)
    public AjaxResult register(@RequestBody RegisterVisitorVO registerVisitor) {
        Assert.notNull(registerVisitor.getCommunityId(), "请选择小区");
        return apiVisitorService.register(registerVisitor);
    }
}
