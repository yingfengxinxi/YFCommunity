package com.wr.api.owner.service.impl.home.location;

import com.wr.api.owner.entity.dto.ApiComplaintDTO;
import com.wr.api.owner.entity.dto.ApiHomeOrderDTO;
import com.wr.api.owner.entity.home.location.ApiHomeOrder;
import com.wr.api.owner.entity.home.location.ApiOrderImage;
import com.wr.api.owner.entity.vo.location.ApiHomeOrderVo;
import com.wr.api.owner.mapper.home.location.ApiComplaintMapper;
import com.wr.api.owner.mapper.home.location.ApiHomeOrderMapper;
import com.wr.api.owner.mapper.home.location.ApiOrderImageMapper;
import com.wr.api.owner.service.home.location.ApiHomeOrderService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.OrderConstants;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.util.WorkOrderUtil;
import com.wr.remote.work.distribution.Distribution;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 居家维修工单表
 * @Author: lvzy
 * @Date: 2022-12-01 13:41:30
 * @Desc:居家维修工单表
 */
@Service
@AllArgsConstructor
public class ApiHomeOrderServiceImpl implements ApiHomeOrderService {

    private final ApiHomeOrderMapper homeOrderMapper;

    // 工单图片
    private final ApiOrderImageMapper orderImageMapper;

    // 表扬投诉
    private final ApiComplaintMapper complaintMapper;

    @Override
    public List<ApiHomeOrderVo> listPage(ApiHomeOrderVo apiHomeOrderVo) {
        return homeOrderMapper.listPage(apiHomeOrderVo);
    }

    @Override
    public AjaxResult detail(Long orderId) {
        return AjaxResult.success(homeOrderMapper.detail(orderId));
    }

    @Override
    public AjaxResult save(ApiHomeOrderDTO orderDTO) {
        // 获取工单分配方式
        Distribution allotByType = homeOrderMapper.getAllotByType(orderDTO.getCommunityId(), OrderConstants.HOME_ORDER);
        if (ObjectUtils.isEmpty(allotByType)){
            AjaxResult.error("操作失败，请联系管理员！");
        }
        Long staffId = 0L;
        String allotStatus = "0";
        if (!ObjectUtils.isEmpty(allotByType) && "0".equals(allotByType.getAllotWay())) {
            // 获取工单空闲人员
            Staff staff = getStaffsId(allotByType.getAllotId(), orderDTO.getCommunityId());
            if (ObjectUtils.isNotEmpty(staff)) {
                staffId = staff.getStaffId();
                allotStatus = "1";
            }
        }
        orderDTO.setStaffId(staffId);
        orderDTO.setAllotStatus(allotStatus);
        List<ApiOrderImage> imageList = new ArrayList<>();
        orderDTO.setOrderNo(WorkOrderUtil.getOrderNo("JJ"));
        orderDTO.setAllotWay(allotByType.getAllotWay());

        homeOrderMapper.insertSelective(orderDTO);
        orderDTO.getImageList().forEach(arr -> {
            ApiOrderImage apiOrderImage = new ApiOrderImage();
            apiOrderImage.setImageUrl(arr);
            apiOrderImage.setTableId(orderDTO.getOrderId());
            apiOrderImage.setImageType("0");
            imageList.add(apiOrderImage);
        });
        // 批量新增图片
        orderImageMapper.insertList(imageList);
        return AjaxResult.success();
    }

    public Staff getStaffsId(Long allotId, Long communityId) {
        List<Staff> staffList = homeOrderMapper.listLeisureStaffs(allotId, communityId);
        if (StringUtils.isEmpty(staffList)) {
            return null;
        } else {
            Random random = new Random();
            //随机获取一个员工
            int i = random.nextInt(staffList.size());
            Staff staff = staffList.get(i);
            if (StringUtils.isNull(staff)) {
                return null;
            }
            return staff;
        }

    }

    @Override
    public AjaxResult selectRepair(Long estateId) {
        return AjaxResult.success(homeOrderMapper.selectRepairList(estateId));
    }

    @Override
    public AjaxResult comment(ApiComplaintDTO complaintDTO) {
        // 修改
        ApiHomeOrder apiHomeOrder = new ApiHomeOrder();
        apiHomeOrder.setOrderId(complaintDTO.getOrderId());
        apiHomeOrder.setAppraiseStatus("1");
        apiHomeOrder.setAppraiseStar(complaintDTO.getAppraiseStar());
        homeOrderMapper.updateByPrimaryKeySelective(apiHomeOrder);
        // 新增表扬投诉
        complaintDTO.setTitle(complaintDTO.getOrderName());
        complaintDTO.setOrderType("1");
        complaintMapper.insertSelective(complaintDTO);
        return AjaxResult.success();
    }
}
