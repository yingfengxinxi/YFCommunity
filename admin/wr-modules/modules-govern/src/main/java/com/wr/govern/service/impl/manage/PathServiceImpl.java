package com.wr.govern.service.impl.manage;

import com.wr.common.core.utils.DateUtils;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.TableUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.CommunityMapper;
import com.wr.govern.mapper.TableMapper;
import com.wr.govern.mapper.manage.PathMapper;
import com.wr.govern.service.manage.PathService;
import com.wr.remote.domain.Community;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.manage.contract.Owner;
import com.wr.remote.estate.manage.contract.Tenant;
import com.wr.remote.estate.manage.vo.contract.OwnerVo;
import com.wr.remote.govern.manage.VehicleLog;
import com.wr.remote.govern.manage.vo.UserDetailLogVO;
import com.wr.remote.govern.manage.vo.UserLogVO;
import com.wr.remote.govern.manage.vo.VehicleDetailLogVO;
import com.wr.remote.govern.manage.vo.VehicleLogVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.wr.common.core.utils.PageUtils.startPage;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-31 16:41:12
 * @Desc: 轨迹管理业务层
 */
@Service
public class PathServiceImpl implements PathService {

    //用户轨迹业主
    private final static String TYPE_OF_OWNER = "1";

    //车辆轨迹租客
    private final static String TYPE_OF_VEHICLE_TENTANT = "1";

    //车辆轨迹游客
    private final static String TYPE_OF_VISITIOR = "2";

    @Autowired
    private PathMapper pathMapper;

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private CommunityMapper communityMapper;

    @Override
    public Map<String, Object> selectUserLogList(UserLogVO userLogVO) {
        //获取当前登录人的所在小区
        List<Community> communities = communityMapper.selectCommunityList(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        Long communityId = 1L;
        String tablePrefix = null;
        if (communities != null) {
            communityId = communities.get(0).getCommunityId();
        }
        if (userLogVO.getQueryTime() == null && userLogVO.getCommunityId() == null) {
            //检查表存不存在
            tablePrefix = TableUtils.getTableName(TableConstants.PREFIX_USER_LOG, communityId, new Date());
        } else if (userLogVO.getQueryTime() == null) {
            tablePrefix = TableUtils.getTableName(TableConstants.PREFIX_USER_LOG, userLogVO.getCommunityId(), new Date());
        } else if (userLogVO.getCommunityId() == null) {
            tablePrefix = TableUtils.getTableName(TableConstants.PREFIX_USER_LOG, communityId, userLogVO.getQueryTime());
        } else {
            tablePrefix = TableUtils.getTableName(TableConstants.PREFIX_USER_LOG, userLogVO.getCommunityId(), userLogVO.getQueryTime());
        }
        Integer result = tableMapper.existTable(tablePrefix);
        if (result == null || result==0) {
            return null;
        }

        String tableName = TableConstants.LOG_DB_NAME + "." + tablePrefix;
        //查询表名
        startPage();
        List<UserLogVO> userLogVOList = pathMapper.selectUserLogList(userLogVO, tableName, userLogVO.getQueryTime());
        List<UserLogVO> newUserLogVOList = new ArrayList<>();
        for (UserLogVO logVO : userLogVOList) {
            UserLogVO userinfo;
            String queryName = null;
            //根据名称过滤
            if (userLogVO.getUserName() != null) {
                queryName = userLogVO.getUserName();
            }
            if (TYPE_OF_OWNER.equals(logVO.getUserType())) {
                //获取业主信息
                userinfo = pathMapper.selectOwnerInfo(logVO.getIdCard(), queryName);
            } else {
                //获取租客信息
                userinfo = pathMapper.selectTenantInfo(logVO.getIdCard(), queryName);
            }
            if (userinfo == null) {
                newUserLogVOList.add(logVO);
                continue;
            }
            logVO.setAvatar(userinfo.getAvatar());
            logVO.setGender(userinfo.getGender());
            // 年龄
            String substring = logVO.getIdCard().substring(6, 10);
            Calendar cal = Calendar.getInstance();
            int newYear = cal.get(Calendar.YEAR);
            logVO.setAge(newYear - Integer.parseInt(substring));
            newUserLogVOList.add(logVO);
        }
        if (newUserLogVOList.size() == 0) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tablePrefix);
        map.put("resultList", newUserLogVOList);
        return map;
    }

    @Override
    public UserDetailLogVO getUserLogDetail(String idCard, String currentTableName, String logId) {

        UserDetailLogVO userDetailLogVO = new UserDetailLogVO();

        //表名
        currentTableName = TableConstants.LOG_DB_NAME + "." + currentTableName;

        //查询用户类型
        UserLogVO userLogVO = pathMapper.getUserLogInfo(currentTableName, logId);
        userDetailLogVO.setCommunityName(userLogVO.getCommunityName());
        if (TYPE_OF_OWNER.equals(userLogVO.getUserType())) {
            userLogVO = pathMapper.selectOwnerInfo(idCard, null);
        } else {
            userLogVO = pathMapper.selectTenantInfo(idCard, null);
        }
        if (userLogVO == null) {
            return null;
        }
        userDetailLogVO.setUserName(userLogVO.getUserName());
        userDetailLogVO.setAvatar(userLogVO.getAvatar());
        userDetailLogVO.setAge(userLogVO.getAge());
        userDetailLogVO.setIdCard(idCard);
        userDetailLogVO.setCardType(userLogVO.getCardType());
        userDetailLogVO.setPhoneNumber(userLogVO.getPhoneNumber());
        userDetailLogVO.setGender(userLogVO.getGender());

        //获取当天的出入记录
        List<UserLogVO> userLogVOList = pathMapper.getUserLogDetail(idCard, currentTableName);
        userDetailLogVO.setUserLogVOs(userLogVOList);
        return userDetailLogVO;
    }

    @Override
    public Map<String, Object> selectVehicleLogList(VehicleLogVO vehicleLog) {
        //获取当前登录人的所在小区
        List<Community> communities = communityMapper.selectCommunityList(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        Long communityId = 1L;
        String tablePrefix = null;
        if (communities != null) {
            communityId = communities.get(0).getCommunityId();
        }
        if (vehicleLog.getQueryTime() == null && vehicleLog.getCommunityId() == null) {
            //检查表存不存在
            tablePrefix = TableUtils.getTableName(TableConstants.PREFIX_VEHICLE_LOG, communityId, new Date());
        } else if (vehicleLog.getQueryTime() == null) {
            tablePrefix = TableUtils.getTableName(TableConstants.PREFIX_VEHICLE_LOG, vehicleLog.getCommunityId(), new Date());
        } else if (vehicleLog.getCommunityId() == null) {
            tablePrefix = TableUtils.getTableName(TableConstants.PREFIX_VEHICLE_LOG, communityId, vehicleLog.getQueryTime());
        } else {
            tablePrefix = TableUtils.getTableName(TableConstants.PREFIX_VEHICLE_LOG, vehicleLog.getCommunityId(), vehicleLog.getQueryTime());
        }
        Integer result = tableMapper.existTable(tablePrefix);
        if (result == null || result==0) {
            return null;
        }
        String tableName = TableConstants.LOG_DB_NAME + "." + tablePrefix;
        //查询表 根据车牌号 时间 查询
        startPage();
        List<VehicleLogVO> vehicleLogVOList = pathMapper.selectVehicleList(tableName, vehicleLog.getPlateNo(), vehicleLog.getQueryTime());
        if (vehicleLogVOList.size() == 0) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("resultList", vehicleLogVOList);
        map.put("tableName", tablePrefix);
        return map;
    }

    @Override
    public VehicleDetailLogVO getVehicleLogDetail(String plateNo, String currentTableName) {
        //返回对象
        VehicleDetailLogVO vehicleDetailLogVO = new VehicleDetailLogVO();

        //查询表名
        currentTableName = TableConstants.LOG_DB_NAME + "." + currentTableName;
        //获取车辆记录列表
        List<VehicleLogVO> vehicleLogVOList = pathMapper.getVehicleDetail(currentTableName, plateNo);
        //页面数据
        VehicleLogVO vehicleLogVO = vehicleLogVOList.get(0);
        vehicleDetailLogVO.setVehicleLogVOs(vehicleLogVOList);
        //获取用户信息
        UserLogVO userLogVO;
        //游客可能没有信息
        if (TYPE_OF_VISITIOR.equals(vehicleLogVO.getUserType())) {
            if ("".equals(vehicleLogVO.getIdCardNo())) {
                return vehicleDetailLogVO;
            }
            //获取用户信息 查询业主
            userLogVO = pathMapper.selectOwnerInfo(vehicleLogVO.getIdCardNo(), null);
            if (userLogVO == null) {
                //业主不存在 查询租客
                userLogVO = pathMapper.selectTenantInfo(vehicleLogVO.getIdCardNo(), null);
            }
            //业主和租客都没有 返回数据
            if (userLogVO == null) {
                return vehicleDetailLogVO;
            }
        } else if (TYPE_OF_VEHICLE_TENTANT.equals(vehicleLogVO.getUserType())) {
            //获取用户信息 查询租客
            userLogVO = pathMapper.selectTenantInfo(vehicleLogVO.getIdCardNo(), null);
            if (userLogVO == null) {
                return vehicleDetailLogVO;
            }
        } else {
            //获取用户信息 查询业主
            userLogVO = pathMapper.selectOwnerInfo(vehicleLogVO.getIdCardNo(), null);
            if (userLogVO == null) {
                return vehicleDetailLogVO;
            }
        }
        vehicleDetailLogVO.setUserName(vehicleLogVO.getUserName());
        vehicleDetailLogVO.setPlateNo(vehicleLogVO.getPlateNo());
        vehicleDetailLogVO.setCommunityName(vehicleLogVO.getCommunityName());
        vehicleDetailLogVO.setIdCardNo(vehicleLogVO.getIdCardNo());
        vehicleDetailLogVO.setAvatar(userLogVO.getAvatar());
        vehicleDetailLogVO.setAge(userLogVO.getAge());
        vehicleDetailLogVO.setIdCardNo(vehicleLogVO.getIdCardNo());
        vehicleDetailLogVO.setCardType(userLogVO.getCardType());
        vehicleDetailLogVO.setPhoneNumber(userLogVO.getPhoneNumber());
        vehicleDetailLogVO.setGender(userLogVO.getGender());
        return vehicleDetailLogVO;
    }


}
