package com.wr.system.business.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.TreeUtils;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.SysDept;
import com.wr.system.business.domain.EstateVo;
import com.wr.system.business.domain.TreeSelectVo;
import com.wr.system.business.service.EstateService;
import com.wr.system.service.ISysDeptService;
import com.wr.system.service.ISysDictDataService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DIANWEI
 * @date 2022/9/20 10:01
 */
@RestController
@RequestMapping("/estate")
public class EstateController extends BaseController {
    @Autowired
    private EstateService estateService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysDictDataService dictDataService;

    /**
     * 获取物业公司详情列表
     */
    @RequiresPermissions("system:property:list")
    @GetMapping("/list")
    public AjaxResult list(EstateVo estateVo) {
        List<EstateVo> estateVos = estateService.selectEstateVoList(estateVo);
        return AjaxResult.success(estateVos);
    }

    /**
     * 新增部门
     */
    @RequiresPermissions("system:property:add")
    @Log(title = "物业公司管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody EstateVo estateVo) {
        //统一社会信用代码、机构编码、工商注册号、纳税人识别号
        //校验：公司名称
        if (UserConstants.NOT_UNIQUE.equals(estateService.checkEstateVoNameUnique(estateVo))) {
            return AjaxResult.error("新增物业'" + estateVo.getSysDept().getDeptName() + "'失败，物业公司名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(estateService.checkEstateVoCreditCodeUnique(estateVo))) {
            return AjaxResult.error("新增物业'" + estateVo.getSysDept().getDeptName() + "'失败，统一社会信用代码已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(estateService.checkEstateVoDeptCode(estateVo))) {
            return AjaxResult.error("新增物业'" + estateVo.getSysDept().getDeptName() + "'失败，机构编码已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(estateService.checkEstateVoBusinessCode(estateVo))) {
            return AjaxResult.error("新增物业'" + estateVo.getSysDept().getDeptName() + "'失败，工商注册号已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(estateService.checkEstateVoTaxpayerCode(estateVo))) {
            return AjaxResult.error("新增物业'" + estateVo.getSysDept().getDeptName() + "'失败，纳税人识别号已存在");
        }

        //通过校验后..
        estateVo.setCreateBy(SecurityUtils.getUsername());
        return toAjax(estateService.insertEstateVo(estateVo));
    }

    /**
     * 根据编号获取详细信息
     */
    @RequiresPermissions("system:property:query")
    @GetMapping(value = "/{estateId}")
    public AjaxResult getInfo(@PathVariable Long estateId) {
        return AjaxResult.success(estateService.selectEstateVoById(estateId));
    }

    /**
     * 查询物业列表（排除节点）
     */
    @RequiresPermissions("system:property:list")
    @GetMapping("/list/exclude/{estateId}")
    public AjaxResult excludeChild(@PathVariable(value = "estateId", required = false) Long estateId) {
        EstateVo estateVo = new EstateVo();
        SysDept sysDept = new SysDept();
        sysDept.setDeptType("10");
        estateVo.setSysDept(sysDept);
        List<EstateVo> estateVos = estateService.selectEstateVoList(estateVo);
        Iterator<EstateVo> it = estateVos.iterator();
        while (it.hasNext()) {
            EstateVo d = (EstateVo) it.next();
            String[] ancestors = StringUtils.split(d.getAncestors(), ",");
            if (d.getEstateId().intValue() == estateId
                    || ArrayUtils.contains(ancestors, estateId + "")) {
                it.remove();
            }
        }
        return AjaxResult.success(estateVos);
    }

    /**
     * 修改部门
     */
    @RequiresPermissions("system:property:edit")
    @Log(title = "物业管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody EstateVo estateVo) {
        if (UserConstants.NOT_UNIQUE.equals(estateService.checkEstateVoNameUnique(estateVo))) {
            return AjaxResult.error("修改物业'" + estateVo.getSysDept().getDeptName() + "'失败，物业名称已存在");
        } else if (estateVo.getParentId().equals(estateVo.getDeptId())) {
            return AjaxResult.error("修改物业'" + estateVo.getSysDept().getDeptName() + "'失败，上级不能是自己");
        }
        /*else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
                && estateService.selectNormalChildrenDeptById(dept.getDeptId()) > 0) {
            return AjaxResult.error("该物业包含未停用的子物业！");
        }*/
        estateVo.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(estateService.updateEstateVo(estateVo));
    }

    /**
     * 获取子列表
     */
    @GetMapping("/child_list/{deptId}")
    public AjaxResult childList(@PathVariable Long deptId) {
        List<EstateVo> estateVos = estateService.selectChildrenDeptById(deptId);
        return AjaxResult.success(estateVos);
    }

    /**
     * 删除部门
     */
    @RequiresPermissions("system:property:remove")
    @Log(title = "物业管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public AjaxResult remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return AjaxResult.error("存在下级,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return AjaxResult.error("存在用户,不允许删除");
        }
        return toAjax(deptService.deleteDeptById(deptId));
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/tree")
    public AjaxResult treeSelect(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
    }

    /**
     * 获取物业下拉树列表
     */
    @GetMapping("/pro_tree")
    public AjaxResult proTree(EstateVo estateVo) {
        List<EstateVo> vos = estateService.selectEstateVoList(estateVo);
        List<EstateVo> estateVos = TreeUtils.buildLongTree(vos);
        List<TreeSelectVo> treeSelects = estateVos.stream().map(TreeSelectVo::new).collect(Collectors.toList());
        return AjaxResult.success(treeSelects);
    }

    @PutMapping("/changeStatus")
    @RequiresPermissions("system:property:edit")
    @Log(title = "物业权限管理", businessType = BusinessType.UPDATE)
    public AjaxResult changeStatus(@RequestBody EstateVo estateVo) {

        Integer num = estateService.updateStatus(estateVo);

        return toAjax(num);
    }


    @PostMapping("/import")
    @RequiresPermissions("system:property:add")
    @Transactional
    @Log(title = "物业信息导入", businessType = BusinessType.UPDATE)
    public AjaxResult importExcel(MultipartFile file)throws Exception {
        if (ObjectUtil.isEmpty(file)){
            return AjaxResult.error("导入文件不可为空");
        }
        ExcelUtil<EstateVo> util = new ExcelUtil<EstateVo>(EstateVo.class);
        List<EstateVo> estateVos = util.importExcel(file.getInputStream());
        if (CollectionUtil.isEmpty(estateVos)){
            return AjaxResult.error("导入数据为空");
        }
        String ERROR_MSG_EMPTY = "第{}行,{}不可为空";
        List<EstateVo> allcheckList = estateService.getCheckList();
        int i = 1;
        for (EstateVo estateVo : estateVos) {
            i++;
            if (StringUtils.isBlank(estateVo.getCreditCode())) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "统一社会信用代码"));
            }
            if (StringUtils.isBlank(estateVo.getEstateCode())) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "机构编码"));
            }
            if (StringUtils.isBlank(estateVo.getEstateName())) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "集团/公司名称"));
            }
            if (StringUtils.isBlank(estateVo.getHotline())) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "服务热线"));
            }
            if (StringUtils.isBlank(estateVo.getLegal())) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "法人代表"));
            }
            if (null == estateVo.getRegisterCapital()) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "注册资本（万）"));
            }
            if (null == estateVo.getActualCapital()) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "实缴资本（万）"));
            }
            if (StringUtils.isBlank(estateVo.getRegisterStatus())) { // todo 字典
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "登记状态"));
            }
            if (null == estateVo.getFoundDate()) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "成立日期"));
            }
            if (null == estateVo.getApproveDate()) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "核准日期"));
            }
            if (StringUtils.isBlank(estateVo.getBusinessCode())) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "工商注册号"));
            }
            if (StringUtils.isBlank(estateVo.getTaxpayerCode())) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "纳税人识别号"));
            }
            if (StringUtils.isBlank(estateVo.getTaxpayerCode())) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "纳税人识别号"));
            }
            if (null == estateVo.getPeriodBegin()) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "营业期限（开始）"));
            }
            if (null == estateVo.getPeriodEnd()) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "营业期限（结束）"));
            }
            if (StringUtils.isBlank(estateVo.getFirmType())) { // todo 字典
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "企业类型"));
            }
            if (StringUtils.isBlank(estateVo.getAreaName())) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "所属地区"));
            }
            if (StringUtils.isBlank(estateVo.getRegisterOffice())) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "登记机关"));
            }
            if (StringUtils.isBlank(estateVo.getRegisterAddress())) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "注册地址"));
            }
            if (StringUtils.isBlank(estateVo.getBrief())) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "简介"));
            }
            if (StringUtils.isBlank(estateVo.getBusinessScope())) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "经营范围"));
            }
            if (StringUtils.isBlank(estateVo.getStatus())) {
                return AjaxResult.error(StrUtil.format(ERROR_MSG_EMPTY, i, "物业公司状态"));
            }
            //特殊校验
            if (StringUtils.isNotBlank(estateVo.getParentName())) {
                EstateVo parent = estateService.selectEstateVoByName(estateVo.getParentName());
                if (ObjectUtil.isEmpty(parent)){
                    return AjaxResult.error("第" + i + "行上级公司不存在");
                }else {
                    estateVo.setParentId(parent.getEstateId());
                }
            }
            if (StringUtils.isNotBlank(estateVo.getRegisterStatus())) {
                String sysCommonStatus = dictDataService.selectDictValue("register_status", estateVo.getRegisterStatus());
                if (StringUtils.isBlank(sysCommonStatus)){
                    return AjaxResult.error("第" + i + "行登记状态在字典中不存在");
                }else {
                    estateVo.setRegisterStatus(sysCommonStatus);
                }
            }
            if (StringUtils.isNotBlank(estateVo.getFirmType())) {
                String firmType = dictDataService.selectDictValue("firm_type", estateVo.getFirmType());
                if (StringUtils.isBlank(firmType)){
                    return AjaxResult.error("第" + i + "行企业类型在字典中不存在");
                }else {
                    estateVo.setFirmType(firmType);
                }
            }
            if (StringUtils.isNotBlank(estateVo.getStatus())) {
                String estateEstate = dictDataService.selectDictValue("estate_estate", estateVo.getStatus());
                if (StringUtils.isBlank(estateEstate)){
                    return AjaxResult.error("第" + i + "行物业公司状态在字典中不存在");
                }else {
                    estateVo.setStatus(estateEstate);
                }
            }
            // 统一社会信用代码、机构编码、工商注册号、纳税人识别号
            // 校验：公司名称
            if (allcheckList.stream().anyMatch(check ->check.getEstateName().equals(estateVo.getEstateName() ) )){
                return AjaxResult.error("第" + i + "行集团/公司名称已存在");
            }
            if (allcheckList.stream().anyMatch(check ->check.getCreditCode().equals(estateVo.getCreditCode() ) )){
                return AjaxResult.error("第" + i + "行统一社会信用代码已存在");
            }
            if (allcheckList.stream().anyMatch(check ->check.getEstateCode().equals(estateVo.getEstateCode() ) )){
                return AjaxResult.error("第" + i + "行机构编码已存在");
            }
            if (allcheckList.stream().anyMatch(check ->check.getBusinessCode().equals(estateVo.getBusinessCode() ) )){
                return AjaxResult.error("第" + i + "行工商注册号已存在");
            }
            if (allcheckList.stream().anyMatch(check ->check.getTaxpayerCode().equals(estateVo.getTaxpayerCode() ) )){
                return AjaxResult.error("第" + i + "行纳税人识别号已存在");
            }
            allcheckList.add(estateVo);
            // if (UserConstants.NOT_UNIQUE.equals(estateService.checkEstateVoNameUnique(estateVo))) {
            //     return AjaxResult.error("新增物业'" + estateVo.getSysDept().getDeptName() + "'失败，物业公司名称已存在");
            // } else if (UserConstants.NOT_UNIQUE.equals(estateService.checkEstateVoCreditCodeUnique(estateVo))) {
            //     return AjaxResult.error("新增物业'" + estateVo.getSysDept().getDeptName() + "'失败，统一社会信用代码已存在");
            // } else if (UserConstants.NOT_UNIQUE.equals(estateService.checkEstateVoDeptCode(estateVo))) {
            //     return AjaxResult.error("新增物业'" + estateVo.getSysDept().getDeptName() + "'失败，机构编码已存在");
            // } else if (UserConstants.NOT_UNIQUE.equals(estateService.checkEstateVoBusinessCode(estateVo))) {
            //     return AjaxResult.error("新增物业'" + estateVo.getSysDept().getDeptName() + "'失败，工商注册号已存在");
            // } else if (UserConstants.NOT_UNIQUE.equals(estateService.checkEstateVoTaxpayerCode(estateVo))) {
            //     return AjaxResult.error("新增物业'" + estateVo.getSysDept().getDeptName() + "'失败，纳税人识别号已存在");
            // }
            //给estateVo中的 sysDept赋值
            SysDept sysDept = new SysDept();
            sysDept.setDeptName(estateVo.getEstateName());
            sysDept.setStatus(estateVo.getStatus());
            sysDept.setOrderNum(estateVo.getOrderNum());
            estateVo.setSysDept(sysDept);
            //通过校验后..
            estateVo.setCreateBy(SecurityUtils.getUsername());
            toAjax(estateService.insertEstateVo(estateVo));
        }
        return AjaxResult.success("导入成功");
    }

    @PostMapping("/template")
    // @RequiresPermissions("system:property:add")
    public void importExcel(HttpServletResponse response) {
        ExcelUtil<EstateVo> util = new ExcelUtil<EstateVo>(EstateVo.class);
        util.exportExcel(response, null, "物业信息管理导入模板");
    }
}
