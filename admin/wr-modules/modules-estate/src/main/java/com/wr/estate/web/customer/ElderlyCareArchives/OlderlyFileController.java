package com.wr.estate.web.customer.ElderlyCareArchives;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.HomeEntity;
import com.wr.estate.entity.OldManByTreeEntity;
import com.wr.estate.entity.PeopleEntity;
import com.wr.estate.entity.vo.OlderVo;
import com.wr.estate.service.customer.ElderlyCareArchives.OlderManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/11
 * @Description:
 * @FileName: OldmanFileController
 * @History:
 */

@RestController
@RequestMapping("/oldman/oldfile")
public class OlderlyFileController extends BaseController {

    @Autowired
    OlderManService olderManService;


    @GetMapping("/gettree")
    public AjaxResult tree() {
        List<OldManByTreeEntity> list = olderManService.getTree();
        if (list != null) {
            if (list.size() > 0) {
                return AjaxResult.success(list);
            }
        }
        return AjaxResult.success(null);
    }

    @Log(title = "获取楼字", businessType = BusinessType.OTHER)
    @PostMapping("/getbuilding")
    public AjaxResult getbuilding(@RequestBody HomeEntity homeEntity) {
        List<HomeEntity> list = olderManService.getbuilding(homeEntity.getCommunityId());
        if (list != null) {
            if (list.size() > 0) {
                return AjaxResult.success(list);
            }
        }
        return AjaxResult.success(null);
    }

    @Log(title = "获取单元", businessType = BusinessType.OTHER)
    @PostMapping("/getunit")
    public AjaxResult getunit(@RequestBody HomeEntity homeEntity) {
        List<HomeEntity> list = olderManService.getunit(homeEntity.getBuildingId(), homeEntity.getCommunityId());
        if (list != null) {
            if (list.size() > 0) {
                return AjaxResult.success(list);
            }
        }
        return AjaxResult.success(null);
    }

    @Log(title = "获取房屋", businessType = BusinessType.OTHER)
    @PostMapping("/getroom")
    public AjaxResult getroom(@RequestBody HomeEntity homeEntity) {
        List<HomeEntity> list = olderManService.getroom(homeEntity.getBuildingId(), homeEntity.getCommunityId(), homeEntity.getUnitId());
        if (list != null) {
            if (list.size() > 0) {
                return AjaxResult.success(list);
            }
        }
        return AjaxResult.success(null);
    }

    @Log(title = "老人信息", businessType = BusinessType.OTHER)
    @PostMapping("/getoldman")
    public AjaxResult getoldman(@RequestBody HomeEntity homeEntity) {
        List<PeopleEntity> list = olderManService.getMan(homeEntity.getRoomId());
        if (list != null) {
            if (list.size() > 0) {
                return AjaxResult.success(list);
            }
        }
        return AjaxResult.success(null);
    }

    @Log(title = "新增人档案", businessType = BusinessType.INSERT)
    @RequiresPermissions("oldman:elderlyfile:add")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody OlderVo olderVo) {
        olderVo.setCreateBy(SecurityUtils.getUsername());
        int row = olderManService.addoldfile(olderVo);
        if (row == 1) {
            return AjaxResult.success("添加成功");
        }
        if (row == -123) {
            return AjaxResult.error("该老人已在档案");
        }
        return AjaxResult.error("添加失败" + row);
    }

    /**
     * 老人数据
     * @param olderVo
     * @return: com.wr.common.core.web.page.TableDataInfo
     * @date: 2023/5/25 10:28
     * @author: SJiang
     **/
    @RequiresPermissions("oldman:elderlyfile:list")
    @GetMapping("/list")
    public TableDataInfo list(OlderVo olderVo) {
        startPage();
        List<OlderVo> list = olderManService.getoldman(olderVo);
        return getDataTable(list);
    }

    @GetMapping("/getoldmsg/{olderId}")
    public AjaxResult getoldmsg(@PathVariable("olderId") long olderId) {
        List list = olderManService.oldmsg(olderId);
        if (list != null) {
            return AjaxResult.success(list);
        }
        return AjaxResult.success(null);
    }

    @Log(title = "修改老人状态_审核通过", businessType = BusinessType.INSERT)
    @RequiresPermissions("oldman:elderlyfile:tg")
    @PutMapping("/getoldmsg/auditstatus/{olderId}")
    public AjaxResult auditstatus(@PathVariable("olderId") long olderId) {
        int row = olderManService.tg(SecurityUtils.getUsername(), olderId);
        if (-1 == row) {
            return AjaxResult.error("未选择通过人");
        } else if (row < 0) {
            return AjaxResult.error("修改失败");
        } else {
            return AjaxResult.success("修改成功");
        }
    }

    @Log(title = "修改老人状态_驳回通过", businessType = BusinessType.INSERT)
    @RequiresPermissions("oldman:elderlyfile:bh")
    @PutMapping("/getoldmsg/noauditstatus/{olderId}/{rejectReason}")
    public AjaxResult Noauditstatus(@PathVariable("olderId") long olderId, @PathVariable("rejectReason") String rejectReason) {
        int row = olderManService.bh(SecurityUtils.getUsername(), olderId, rejectReason);
        if (-1 == row) {
            return AjaxResult.error("未选择通过人");
        } else if (row < 0) {
            return AjaxResult.error("修改失败");
        } else {
            return AjaxResult.success("修改成功");
        }
    }

    @RequiresPermissions("oldman:elderlyfile:del")
    @DeleteMapping("/del/{olderId}")
    public AjaxResult del(@PathVariable("olderId") Long[] olderId) {
        int row = olderManService.del(olderId);
        if (row > 0) {
            return AjaxResult.success(row);
        } else {
            return AjaxResult.error("删除失败");
        }
    }


}
