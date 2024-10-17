package com.wr.estate.web.customer.CommunityHealthCare;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.MedacalOlderEntity;
import com.wr.estate.entity.MedicalEntity;
import com.wr.estate.entity.vo.ExaminerExcelEntity;
import com.wr.estate.service.customer.CommunityHealthCare.MedicalEntityService;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 医疗检测通告Controller
 *
 * @author bajie
 * @date 2022-10-13
 */
@RestController
@RequestMapping("/medical/medical")
public class MedicalEntityController extends BaseController
{
    @Autowired
    private MedicalEntityService medicalEntityService;
    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * 查询医疗检测通告列表
     */
    @PreAuthorize("@ss.hasPermi('medical:medical:list')")
    @GetMapping("/list")
    public TableDataInfo list(MedicalEntity medicalEntity)
    {
        startPage();
        List<MedicalEntity> list = medicalEntityService.selectMedicalEntityList(medicalEntity);
        return getDataTable(list);
    }

    /**
     * 导出医疗检测通告列表
     */
    @Log(title = "医疗检测通告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MedicalEntity medicalEntity)
    {
        System.out.println(medicalEntity.getMedicalId());
        MedacalOlderEntity medacalOlder = new MedacalOlderEntity();
        medacalOlder.setMedicalId(medicalEntity.getMedicalId());
        List<MedacalOlderEntity> list = medicalEntityService.lookoldere(medacalOlder);
        ExcelUtil<MedacalOlderEntity> util = new ExcelUtil<MedacalOlderEntity>(MedacalOlderEntity.class);
        util.exportExcel(response, list, "医疗检测通告体检人员数据数据");
    }

    @Log(title = "医疗检测通告", businessType = BusinessType.EXPORT)
    @PostMapping("/export1")
    public void export1(HttpServletResponse response, MedicalEntity medicalEntity)
    {
        System.out.println(medicalEntity.getMedicalId());
        List<MedicalEntity> list = medicalEntityService.selectMedicalEntityList(medicalEntity);
        ExcelUtil<MedicalEntity> util = new ExcelUtil<MedicalEntity>(MedicalEntity.class);
        util.exportExcel(response, list, "医疗检测通告数据");
    }

    /**
     * 获取医疗检测通告详细信息
     */
    @PreAuthorize("@ss.hasPermi('medical:medical:query')")
    @GetMapping(value = "/{medicalId}")
    public AjaxResult getInfo(@PathVariable("medicalId") Long medicalId)
    {
        return AjaxResult.success(medicalEntityService.selectMedicalEntityByMedicalId(medicalId));
    }

    /**
     * 新增医疗检测通告
     */
    @PreAuthorize("@ss.hasPermi('medical:medical:add')")
    @Log(title = "医疗检测通告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MedicalEntity medicalEntity)
    {
        int row = medicalEntityService.insertMedicalEntity(medicalEntity);
        if (row == -5){
            return AjaxResult.error("活动名称重复,请重新输入");
        }
        return toAjax(row);
    }

    /**
     * 修改医疗检测通告
     */
    @PreAuthorize("@ss.hasPermi('medical:medical:edit')")
    @Log(title = "医疗检测通告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MedicalEntity medicalEntity)
    {
        return toAjax(medicalEntityService.updateMedicalEntity(medicalEntity));
    }

    /**
     * 删除医疗检测通告
     */
    @PreAuthorize("@ss.hasPermi('medical:medical:remove')")
    @Log(title = "医疗检测通告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{medicalIds}")
    public AjaxResult remove(@PathVariable Long[] medicalIds)
    {
        return toAjax(medicalEntityService.deleteMedicalEntityByMedicalIds(medicalIds));
    }

    /**
     * 查看医疗详情
     */
    @PostMapping("/getMedicalList/")
    public AjaxResult examinerMedical(@RequestBody  MedacalOlderEntity medacalOlderEntity)
    {
        if (medacalOlderEntity.getMedicalId() != null){
            List<MedacalOlderEntity> list = medicalEntityService.lookoldere(medacalOlderEntity);
            return AjaxResult.success(list);
        }
        return  null;
    }





    @Log(title = "下载模板", businessType = BusinessType.OTHER)
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<ExaminerExcelEntity> util = new ExcelUtil<ExaminerExcelEntity>(ExaminerExcelEntity.class);
        util.importTemplateExcel(response, "体检人员导入模板");
    }

    @Log(title = "导入", businessType = BusinessType.OTHER)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<ExaminerExcelEntity> util = new ExcelUtil<ExaminerExcelEntity>(ExaminerExcelEntity.class);
        List<ExaminerExcelEntity> userList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = medicalEntityService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @Log(title = "检测报告导入", businessType = BusinessType.OTHER)
    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file, long examinerId) throws Exception
    {
        System.out.println(examinerId);
        if (!file.isEmpty()) {
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
            System.out.println(url);
            int addpath = medicalEntityService.addpath(url, examinerId);
            if (addpath >0){
                return AjaxResult.error("上传文件成功");
            }
            return AjaxResult.error("上传文件成功,数据库插入失败");
        }
        return AjaxResult.error("上传文件异常，请联系管理员");
    }

    @Log(title = "查看医疗详情1", businessType = BusinessType.OTHER)
    @PostMapping("/lookoldere1/{examinerId}")
    public AjaxResult lookoldere1(@PathVariable("examinerId")long examinerId)
    {
        System.out.println(examinerId);
        MedacalOlderEntity medacalOlderEntity = medicalEntityService.lookoldere1(examinerId);
        return AjaxResult.success(medacalOlderEntity);
    }

}
