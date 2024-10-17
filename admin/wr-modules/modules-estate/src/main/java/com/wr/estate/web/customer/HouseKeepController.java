package com.wr.estate.web;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.HouseKeepService;
import com.wr.remote.estate.housekeep.vo.HouseKeepDetailVO;
import com.wr.remote.estate.housekeep.vo.HouseKeepListVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 荀彧
 * @description 管家管理Controller
 * @date 2022-10-13
 */
@RestController
@RequestMapping("/housekeep")
public class HouseKeepController extends BaseController {

    @Resource
    private HouseKeepService houseKeepService;


    /**
     * 分页查询
     *
     * @param houseKeepListVO
     * @return
     */
    @PreAuthorize("@ss.hasPermi('bussiness:housekeep:list')")
    @GetMapping("/getList")
    public TableDataInfo getList(HouseKeepListVO houseKeepListVO) {
        startPage();
        List<HouseKeepListVO> list = houseKeepService.pageList(houseKeepListVO);
        return getDataTable(list);
    }

    /**
     * 删除
     *
     * @param majordomoId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('bussiness:housekeep:remove')")
    @PostMapping("/del/{majordomoId}")
    public AjaxResult del(@PathVariable("majordomoId") Long majordomoId) {
        if (null == majordomoId) {
            return AjaxResult.error("系统未知错误");
        }
        try {
            if (houseKeepService.del(majordomoId) > 0) {
                return AjaxResult.success("删除成功");
            }
            return AjaxResult.error("删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("系统未知错误");
        }
    }

    /**
     * 岗位下拉框
     *
     * @return
     */
    @GetMapping("/postSelect")
    public AjaxResult postSelect() {
        return AjaxResult.success(houseKeepService.postSelect());
    }

    /**
     * 修改带回数据
     *
     * @param majordomoId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('bussiness:housekeep:query')")
    @PostMapping("/updateDetail/{majordomoId}")
    public AjaxResult updateDetail(@PathVariable("majordomoId") Long majordomoId) {
        if (null == majordomoId) {
            return AjaxResult.error("未知错误，请联系管理员");
        }
        return AjaxResult.success(houseKeepService.queryDetail(majordomoId));
    }

    /**
     * 修改
     *
     * @param majordomoId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('bussiness:housekeep:edit')")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody HouseKeepListVO houseKeepListVO) {
        if (null == houseKeepListVO) {
            return AjaxResult.error("未知错误，请联系管理员");
        }
        String unique = houseKeepService.checkBuildUnique(houseKeepListVO);
        if (UserConstants.NOT_UNIQUE.equals(unique)){
            return AjaxResult.error("修改失败，当前小区楼栋已存在管家，请换个试试");
        }
        Integer num = houseKeepService.updateKeepList(houseKeepListVO);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    @GetMapping(value = "/build/{communityId}")
    public List<HouseKeepListVO> selectBuild(@PathVariable("communityId") Long communityId) {
        return houseKeepService.selectBuild(communityId);
    }


    @GetMapping(value = "/staff")
    public List<HouseKeepListVO> selectStaff(HouseKeepListVO houseKeepListVO) {
        return houseKeepService.selectStaff(houseKeepListVO);
    }

    @GetMapping(value = "/phone/{staffId}")
    public HouseKeepListVO selectPhone(@PathVariable("staffId") Long staffId) {
        return houseKeepService.selectPhone(staffId);
    }


    @PreAuthorize("@ss.hasPermi('bussiness:housekeep:add')")
    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody HouseKeepListVO houseKeepListVO) {
        String operName = SecurityUtils.getUsername();
        houseKeepListVO.setCreateBy(operName);
        if (null == houseKeepListVO) {
            return AjaxResult.error("未知错误，请联系管理员");
        }
        String unique = houseKeepService.checkBuildUnique(houseKeepListVO);
        if (UserConstants.NOT_UNIQUE.equals(unique)){
            return AjaxResult.error("添加失败，当前小区楼栋已存在管家，请换个试试");
        }
        int row = houseKeepService.insertBuild(houseKeepListVO);
        return row > 0 ? AjaxResult.success() : AjaxResult.error();
    }

}
