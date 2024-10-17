package com.wr.api.owner.mapper.home.rescue;

import com.wr.api.owner.entity.home.rescue.ApiRescueUser;
import com.wr.api.owner.entity.home.rescue.vo.ApiRescueVo;
import org.apache.ibatis.annotations.Mapper;

import java.text.ParseException;
import java.util.List;


/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 社区公告
 */
@Mapper
public interface ApiRescueMapper {

    /**
     * 获取社区救助列表
     * @param rescueVo
     * @return
     * @throws ParseException
     */
    List<ApiRescueVo> getList(ApiRescueVo rescueVo);

    /**
     * 获取社区救助详情
     * @param rescueId
     * @return
     */
    ApiRescueVo selectOneById(Long rescueId);

    /**
     * 根据求助id查询该求助的救助者信息
     * @param rescueId
     * @return
     */
    List<ApiRescueUser> getRescueUserById(Long rescueId);

    /**
     * 用户点击参与救助，讲用户信息新增关联表
     * @param apiRescueUser
     * @return
     */
    Integer add(ApiRescueUser apiRescueUser);

    /**
     * 查询志愿者是否已经参与了社区救助
     * @author yx
     * @date 2023/1/17 10:56
     * @param apiRescueUser
     * @return int
     */
    int countByUser(ApiRescueUser apiRescueUser);

    /**
     * 获取社区救助列表-别人发布，我已参加
     * @param rescueVo
     * @return
     * @throws ParseException
     */
    List<ApiRescueVo> getJoinRescueList(ApiRescueVo rescueVo);
}
