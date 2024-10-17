package com.wr.govern.web.autonomy.statute;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.service.autonomy.statute.StatuteService;
import com.wr.remote.domain.SysFile;
import com.wr.remote.domain.SysRole;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.manage.vo.contract.LeaseVo;
import com.wr.remote.govern.autonomy.statute.Statute;
import com.wr.remote.govern.autonomy.statute.vo.StatuteVo;
import com.wr.remote.system.RemoteFileService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: luSu
 * @Date: 2022/10/25 14:46
 * @Desc: 自治规约controller
 */
@RestController
@RequestMapping("/statute")
public class StatuteController extends BaseController {


    @Autowired
    private StatuteService statuteService;

    @Autowired
    private RemoteFileService remoteFileService;


    /**
     * 查询自治规约列表
     * @param statute
     * @return
     * @throws ParseException
     */
    @GetMapping("/list")
    //@RequiresPermissions("autonomy:statute:list")
    public TableDataInfo list(Statute statute) throws ParseException {
        startPage();
        //调用接口获取内容
        List<Statute> list = statuteService.getList(statute);
        return getDataTable(list);
    }


    /**
     * 新增自治规约
     * @param statute
     * @return
     */
    @PostMapping
    @RequiresPermissions("autonomy:statute:add")
    @Log(title = "新增规约", businessType = BusinessType.INSERT)
    public AjaxResult addStatute(@Validated @RequestBody Statute statute) {
        //通过vo类接收新增信息， 统一使用lease的接口， 随后在lease实现类中讲vo类中的字段一一放置到具体的实现类中
        Integer inte = statuteService.addStatute(statute);
        return inte > 0 ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }



    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("/uploadPicture")
    public AjaxResult uploadPicture(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            System.out.println("开始上传图片！");
            //MultipartFile temp = ThumbnailsUtils.compressMultipart(filePath,file,1280,1080);
            R<SysFile> fileResult = remoteFileService.upload(file);
            if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData())) {
                return AjaxResult.error("文件服务异常，请联系管理员");
            }
            if(R.FAIL == fileResult.getCode()){
                return AjaxResult.error(fileResult.getMsg());
            }
            String url = fileResult.getData().getUrl();
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgPath", url);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }


    /**
     *根据id获取当前详情
     * @param statuteId
     * @return
     */
    @GetMapping(value = { "/", "/{statuteId}" })
    public AjaxResult getInfo(@PathVariable(value = "statuteId", required = false) Long statuteId) {
        AjaxResult ajax = AjaxResult.success();

        //1.不是空时，发送ajax
        if (StringUtils.isNotNull(statuteId))
        {
            //2.根据bus_statute_id查询bus_statute的记录
            Statute statute = statuteService.getStatuteById(statuteId);
            //3.根据statute_id查询bus_statute_rang的详情
            List<Long>  ids =  statuteService.selectStatuteCommunityById(statuteId);

            ajax.put(AjaxResult.DATA_TAG, statute);
            ajax.put("commIds",ids );
        }

        return ajax;
    }


    /**
     * 修改
     * @param statute
     * @return
     */
    @RequiresPermissions("autonomy:statute:edit")
    @Log(title = "修改规约", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateStatute(@Validated @RequestBody Statute statute){
       int i= statuteService.updateStatute(statute);
        return toAjax(i);
    }


    /**
     * 删除当前制约
     * @param statuteId
     * @return
     */
    @RequiresPermissions("autonomy:statute:delete")
    @Log(title = "删除规约", businessType = BusinessType.DELETE)
    @DeleteMapping("/{statuteId}")
    public AjaxResult delStatute(@PathVariable Long[] statuteId){
        return toAjax(statuteService.delStatute(statuteId));
    }



}
