package com.wr.estate.service.impl.work.parkmatter;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.work.parkmatter.ParkRepairMapper;
import com.wr.estate.service.work.parkmatter.ParkRepairService;
import com.wr.remote.domain.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 15:04:24
 * @Desc:
 */
@Service
public class ParkRepairServiceImpl implements ParkRepairService {

    @Resource
    private ParkRepairMapper parkRepairMapper;

    @Override
    public List<LinkedHashMap<String, Object>> search(Map<String, Object> params) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        params.put("estate_id",sysUser.getEstateId());

        return parkRepairMapper.search(params);
    }

    @Override
    public AjaxResult save(Map<String, Object> params) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        params.put("estate_id",sysUser.getEstateId());
        params.put("person",sysUser.getUserName());
        int count = parkRepairMapper.repairNameCount(sysUser.getEstateId(), (String) params.get("repair_name"), -1L);
        if (count>0){
            return AjaxResult.error("报事类型已存在!!");
        }
        return AjaxResult.success(parkRepairMapper.save(params));
    }

    @Override
    public AjaxResult update(Map<String, Object> params) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        params.put("person",sysUser.getUserName());
        int count = parkRepairMapper.repairNameCount(sysUser.getEstateId(), (String) params.get("repair_name"), Long.valueOf(params.get("repair_id")+""));
        if (count>0){
            return AjaxResult.error("报事类型已存在!!");
        }

        return AjaxResult.success(parkRepairMapper.update(params));
    }

    @Override
    public int removes(Long[] ids) {

        return parkRepairMapper.removes(ids);
    }
}
