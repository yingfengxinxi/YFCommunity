package com.wr.estate.web.manage.contract;

import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.manage.contract.BuyerService;
import com.wr.estate.service.manage.contract.ServeService;
import com.wr.remote.estate.manage.contract.Estate;
import com.wr.remote.estate.manage.vo.contract.ServerContractVO;
import com.wr.remote.domain.SysFile;
import com.wr.remote.domain.SysUser;
import com.wr.remote.system.RemoteFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: LuZhiShen
 * @Date: 2022/10/17 15:53
 * @Desc: 服务合同控制层
 */
@RestController
@RequestMapping("/serve")
public class ServeController extends BaseController {

    @Autowired
    private ServeService serveService;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private RemoteFileService remoteFileService;


    /**
     * 查询服务合同列表
     *
     * @param serverContractVO
     * @return
     */
    @RequiresPermissions("manage:serve:list")
    @GetMapping("/list")
    public TableDataInfo list(ServerContractVO serverContractVO) throws ParseException {
        startPage();
        List<ServerContractVO> list = serveService.getServeList(serverContractVO);
        return getDataTable(list);
    }

    /**
     * 查询详情
     *
     * @param
     * @return
     */
    @RequiresPermissions("manage:serve:list")
    @GetMapping(value = "/getDetailInfo/{serverId}")
    public AjaxResult getDetailInfo(@PathVariable Long serverId) {
        if (serverId == null) {
            return AjaxResult.error();
        }
        ServerContractVO serverContractVO = serveService.getDetailInfo(serverId);
        return AjaxResult.success(serverContractVO);
    }

    /**
     * 获取物业下拉框内容
     *
     * @param
     * @return
     */
    @RequiresPermissions("manage:serve:list")
    @GetMapping(value = "/getServeSelect")
    public AjaxResult getServeSelect() {
        List<Estate> list = buyerService.getEstateList();
        return AjaxResult.success(list);
    }

    /**
     * 获取签约人列表以及物业信息
     *
     * @param
     * @return
     */
    @RequiresPermissions("manage:serve:list")
    @GetMapping(value = "/getUserInfo/{estateId}")
    public AjaxResult getUserInfo(@PathVariable Long estateId) {
        List<SysUser> sysUserList = serveService.getUserInfoByEstateId(estateId);
        Map<String,Object> result=new HashMap<>();
        result.put("userList",sysUserList);
        result.put("estateInfo",serveService.getEstateDetail(estateId));
        return AjaxResult.success(result);
    }

    /**
     * 新增服务合同
     *
     * @param
     * @return
     */
    @Log(title = "新增服务合同", businessType = BusinessType.INSERT)
    @RequiresPermissions("manage:serve:add")
    @PostMapping(value = "/addServe")
    public AjaxResult addServe(@RequestBody ServerContractVO serverContractVO) {
        Integer result = serveService.checkServerContractVO(serverContractVO.getContractNo());
        if (result > 0) {
            return AjaxResult.error("添加失败 合同编号已存在");
        }
        serverContractVO.setCreateBy(SecurityUtils.getUsername());
        return toAjax(serveService.addServe(serverContractVO));
    }

    /**
     * 删除服务
     *
     * @return
     */
    @RequiresPermissions("manage:serve:remove")
    @GetMapping(value = "/delServe/{serverId}")
    public AjaxResult delServe(@PathVariable Long[] serverId) {
        if (serverId.length == 0) {
            return AjaxResult.error();
        }
        return toAjax(serveService.delServe(serverId));
    }

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @Log(title = "服务合同图片上传", businessType = BusinessType.INSERT)
    @RequiresPermissions("manage:serve:upload")
    @PostMapping("/uploadPicture")
    public AjaxResult uploadPicture(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            //MultipartFile temp = ThumbnailsUtils.compressMultipart(filePath,file,1280,1080);
            R<SysFile> fileResult = remoteFileService.upload(file);
            if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData())) {
                return AjaxResult.error("文件服务异常，请联系管理员");
            }
            if (R.FAIL == fileResult.getCode()) {
                return AjaxResult.error(fileResult.getMsg());
            }
            String url = fileResult.getData().getUrl();
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgPath", url);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }
}
