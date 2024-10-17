package com.wr.govern.web.autonomy.social;

import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.govern.service.autonomy.decide.DecideService;
import com.wr.govern.service.autonomy.social.SocialService;
import com.wr.remote.domain.SysFile;
import com.wr.remote.govern.autonomy.decide.Decide;
import com.wr.remote.govern.autonomy.decide.DecideItem;
import com.wr.remote.govern.autonomy.decide.vo.decideVo;
import com.wr.remote.govern.autonomy.social.Social;
import com.wr.remote.govern.autonomy.statute.Statute;
import com.wr.remote.system.RemoteFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: luSu
 * @Date: 2022/10/26 14:46
 * @Desc: 社交控制层
 */
@RestController
@RequestMapping("/social")
public class SocialController extends BaseController {


    @Autowired
    private SocialService socialService;
    @Autowired
    private RemoteFileService remoteFileService;


    /**
     * 获取信息管理列表
     * @param social
     * @return
     * @throws ParseException
     */
    @GetMapping("/messageList")
    @RequiresPermissions("autonomy:social:list")
    public TableDataInfo messageList(Social social) {
        startPage();
        List<Social> list = socialService.getList(social);
        return getDataTable(list);
    }



    /**
     * 获取系统管理信息列表
     * @param social
     * @return
     * @throws ParseException
     */
    @GetMapping("/systemList")
    @RequiresPermissions("autonomy:social:list")
    public TableDataInfo systemList(Social social) throws ParseException {
        startPage();
        List<Social> list = socialService.systemList(social);
        return getDataTable(list);
    }




    /**
     * 新增系统信息
     * @param social
     * @return
     */
    @PostMapping
    @Log(title = "新增", businessType = BusinessType.INSERT)
    @RequiresPermissions("autonomy:social:add")
    public AjaxResult addSystemSocial(@Validated @RequestBody Social social) {
        //通过vo类接收新增信息， 统一使用lease的接口， 随后在lease实现类中讲vo类中的字段一一放置到具体的实现类中
        Integer inte = socialService.addSystemSocial(social);
        return inte > 0 ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }


    /**
     * 删除社交记录
     * @param socialId
     * @return
     */
    @RequiresPermissions("autonomy:social:delete")
    @Log(title = "删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delMessage/{socialId}")
    public AjaxResult delMessageSocial(@PathVariable Long[] socialId){
        int i = socialService.delMessageSocial(socialId);
        return toAjax(i);
    }


    /**
     * 删除系统社交记录
     * @param id
     * @return
     */
    @RequiresPermissions("autonomy:social:delete")
    @Log(title = "删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delSystem/{id}")
    public AjaxResult delSystemSocial(@PathVariable Long[] id){
        return toAjax(socialService.delSystemSocial(id));
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
     * 修改
     * @param social
     * @return
     */
    @RequiresPermissions("autonomy:social:edit")
    @Log(title = "修改", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateSysSocial(@Validated @RequestBody Social social){
        int i= socialService.updateSysSocial(social);
        return toAjax(i);
    }


    /**
     *根据id查询社交的详情内容
     * @param socialId
     * @return
     */
    @RequiresPermissions("autonomy:social:query")
    @GetMapping(value = { "/", "/{socialId}" })
    public AjaxResult getInfo(@PathVariable(value = "socialId", required = false) Long socialId) {
        AjaxResult ajax = AjaxResult.success();
        //1.不是空时，发送ajax
        if (StringUtils.isNotNull(socialId)){
            //2.查询social表格中信息
            Social social = socialService.getSocialById(socialId);
            //3.查询小区与社交表的关联表
            List<Long>  ids =  socialService.querySocialRangById(socialId);
            ajax.put(AjaxResult.DATA_TAG, social);
            ajax.put("commIds",ids );
        }
        return ajax;
    }

}
