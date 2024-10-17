package com.wr.estate.web.manage.contract;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.estate.service.manage.contract.MaterialService;
import com.wr.remote.estate.manage.contract.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: lusu
 * @Date: 2022/10/18 14:46
 * @Desc: 采购表（物品相关）
 */
@RestController
@RequestMapping("/material")
public class MaterialController extends BaseController {

    @Autowired
    private MaterialService materialService;
    /**
     * 新增采购信息
     * @param material
     * @return
     */
    @PostMapping
    public AjaxResult add(Material material) {
        //通过vo类接收新增信息， 统一使用lease的接口， 随后在lease实现类中讲vo类中的字段一一放置到具体的实现类中
        Integer inte= materialService.addMaterial(material);
        return inte > 0 ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }


}
