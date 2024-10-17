package com.wr.api.owner.mapper.home.pay;

import com.wr.api.owner.entity.dto.LiveDTO;
import com.wr.api.owner.entity.vo.home.PropertyFeeVO;
import com.wr.api.owner.entity.vo.home.PropertyLogVO;
import com.wr.api.owner.entity.vo.pay.PropertyLogPageVO;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.PropertyLog;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.boot.actuate.integration.IntegrationGraphEndpoint;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: RainCity
 * @Date: 2022-11-17 09:50:25
 * @Desc: 生活缴费数据层
 */
public interface LiveMapper {
    /**
     * 根据小区id查询物业费
     * @param communityId 小区id
     * @return com.wr.api.owner.entity.vo.home.PropertyFeeVO
     */
    PropertyFeeVO getPropertyFee(Long communityId);

    /**
     * 根据房间id查询面积
     * @param roomId 房间id
     * @return java.math.BigDecimal
     */
    BigDecimal getRoomAreaByRoomId(Long roomId);

    /**
     * 根据id查询物业名称
     * @param estateId 物业id
     * @return java.lang.String
     */
    String getEstateNameById(Long estateId);

    /**
     * 根据楼栋id查询楼栋
     * @param buildingId 楼栋id
     * @return java.lang.String
     */
    String getBuildNameById(Long buildingId);

    /**
     * 根据单元id查询楼栋
     * @param unitId 单元id
     * @return java.lang.String
     */
    String getUnitNameById(Long unitId);

    /**
     * 根据房间id查询楼栋
     * @param roomId 房间id
     * @return java.lang.String
     */
    String getRoomNameById(Long roomId);

    /**
     * 根据房间id查询物业缴费记录
     * @param roomId 房间id
     * @return java.util.List<com.wr.api.owner.entity.vo.home.PropertyLog>
     */
    List<PropertyLogVO> selectPropertyLogByRoom(Long roomId);

    /**
     * 添加物业缴费记录
     * @param propertyLog 数据
     * @return java.lang.Integer
     */
    Integer insertPropertyLog(PropertyLog propertyLog);

    /**
     * @Author SUNk
     * @Description 查询物业费详情
     * @Date 14:43 2024/3/30
     * @Param [logId]
     * @return com.wr.common.core.web.domain.AjaxResult
     **/
    PropertyLog findDetilProperty(Long logId);

        Integer updateStatusPropertyByNo(Map map);

        /**
        * @Author SUNk
        * @Description 根据业主ID查询房间信息
        * @Date 10:56 2024/4/1
        * @Param [ownerId]
        * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
        **/
        List<Map<String,Object>> findListHouseByOwnId(@Param( "ownerId" ) Long ownerId);

        /**
        * @Author SUNk
        * @Description 查询物业费列表详情
        * @Date 11:06 2024/4/1
        * @Param [liveDTO]
        * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
        **/
        List<PropertyLogPageVO> findListPropertyLog(LiveDTO liveDTO);

        /**
        * @Author SUNk
        * @Description 查询物业费金额
        * @Date 17:46 2024/6/12
        * @Param [orderNo]
        * @return java.lang.String
        **/
        String findFeeByOrderNo(@Param( "orderNo" ) String orderNo);

        /*
        * @Author SUNk
        * @Description 更新订单号
        * @Date 17:47 2024/6/12
        * @Param [map]
        * @return java.lang.Integer
        **/
        Integer updateOrderNoByNo(Map map);
}
