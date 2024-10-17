package com.wr.govern.service.impl.voluntary;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.CommunityMapper;
import com.wr.govern.mapper.voluntary.VoluntaryMapper;
import com.wr.remote.govern.voluntary.Voluntary;
import com.wr.govern.service.voluntary.VoluntaryService;
import com.wr.remote.domain.SysUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: lvzy
 * @Date: 2022-10-25 10:13:41
 * @Desc:
 */
@Service
public class VoluntaryServiceImpl implements VoluntaryService {

    @Resource
    private VoluntaryMapper voluntaryMapper;

    private SysUser sysUser;

    private Long estateId;//物业id

    @Resource
    private CommunityMapper communityMapper;
    @Override
    public List<LinkedHashMap<String, Object>> searchList(Map<String, Object> params) {
        sysUser = SecurityUtils.getLoginUser().getSysUser();
        estateId = communityMapper.getCurrentEstateId(sysUser.getDeptId());
        params.put("id",estateId);
        List<LinkedHashMap<String, Object>> list = voluntaryMapper.searchList(params);
        list.forEach(e->{
                e.put("communityIds",voluntaryMapper.queryByVoluntaryId(Long.valueOf(e.get("voluntary_id")+"")));
        });
        return list;
    }

    @Override
    public int saveVoluntary(Map<String, Object> params) {

        Voluntary v = info(params);
        v.setEstateId(estateId);

        voluntaryMapper.saveVoluntary(v);//新增活动

        return voluntaryMapper.saveVoluntaryRang(
                new HashMap<String,Object>(){
                    {
                        put("voluntary_id",v.getVoluntaryId());
                        put("communityIds",params.get("communityIds"));
                    }
                }
        );//新增活动关联小区
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeVoluntary(Long id) {

        voluntaryMapper.removeVoluntaryRang(id);// 删除 关联小区
        voluntaryMapper.removeVoluntaryUser(id);//删除 关联人员
        return voluntaryMapper.removeVoluntary(id);//删除志愿者活动
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateVoluntary(Map<String, Object> params) {

        Voluntary v = info(params);
        v.setVoluntaryId(Long.valueOf(params.get("voluntary_id")+""));
        voluntaryMapper.removeVoluntaryRang(v.getVoluntaryId());//删除 old 关联小区
        voluntaryMapper.updateVoluntary(v);//修改活动

        return voluntaryMapper.saveVoluntaryRang(
                new HashMap<String,Object>(){
                    {
                        put("voluntary_id",v.getVoluntaryId());
                        put("communityIds",params.get("communityIds"));
                    }
                }
        );//新增活动关联小区
    }

    @Override
    public List<LinkedHashMap<String, Object>> searchListUser(Map<String, Object> params) {
        return voluntaryMapper.searchListUser(params);
    }

    private Voluntary info(Map<String, Object> params){

        Voluntary v = new Voluntary();
        v.setActTitle(String.valueOf(params.get("act_title")));
        v.setCoverUrl(String.valueOf(params.get("cover_url")));
        v.setStartTime(String.valueOf(params.get("start_time")));
        v.setEndTime(String.valueOf(params.get("end_time")));
        v.setNeedNum(Long.valueOf(params.get("need_num")+""));
        v.setContent(String.valueOf(params.get("content")));
        v.setCommunityId(Long.valueOf(params.get("community_id")+""));
        v.setUpdateBy(sysUser.getUserName());

        return v;
    }
}
