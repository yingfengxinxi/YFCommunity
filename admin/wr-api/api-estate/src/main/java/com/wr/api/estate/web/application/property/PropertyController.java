package com.wr.api.estate.web.application.property;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.api.estate.service.application.property.PropertyService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.estate.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lvzy
 * @Date 2024/1/17 11:09
 * 资产管理
 */
@RestController
@RequestMapping("v2/property")
public class PropertyController extends BaseController {

    @Autowired
    private PropertyService propertyService;

    /**
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public AjaxResult get(@PathVariable("id") String id) {
        LambdaQueryWrapper<Property> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Property::getId, id);
        Property serviceOne = propertyService.getOne(queryWrapper);
        if (serviceOne == null) {
            return AjaxResult.error("当前物品已删除,请联系物业管理人员");
        }
        return AjaxResult.success(serviceOne);
    }
}
