package  com.wr.estate.mapper.work.cleaningMangement;


import com.wr.estate.entity.vo.CleanVo;
import com.wr.estate.entity.vo.Executor;
import com.wr.estate.entity.vo.OrderAssistVo;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.work.cleaningManagement.Clean;
import com.wr.remote.work.cleaningManagement.CleanExample;
import com.wr.remote.work.distribution.Distribution;
import com.wr.remote.work.inspect.OrderAudit;
import com.wr.remote.work.inspect.OrderRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CleanMapper {
    int countByExample(CleanExample example);

    int deleteByExample(CleanExample example);

    int deleteByPrimaryKey(Long cleanId);

    int insert(Clean record);

    int insertSelective(Clean record);

    List<CleanVo> selectByExample(CleanExample example);

    int selectNumber(CleanExample example);

    Clean selectByPrimaryKeySingle(Long cleanId);

    Clean selectByPrimaryKeyOne(Long cleanId);

    CleanVo selectByPrimaryKey(Long cleanId);

    Staff selectByPrimaryexecuteId(Long executeId);

    int updateByExampleSelective(@Param("record") Clean record, @Param("example") CleanExample example);

    int updateByExample(@Param("record") Clean record, @Param("example") CleanExample example);

    int updateByPrimaryKeySelective(Clean record);

    int updateByPrimaryKey(Clean record);

    List<Staff> getSatffNameList(@Param("communityId") Long communityId);

    Staff getSatffByIdStaffId(@Param("staffId") Long staffId);

    List<OrderAssistVo>  selectExecutorList(OrderAudit orderAudit);

    Executor getByIdStaffId(@Param("staffId") Long staffId);

    List<OrderRecord> selcetOrderRecordList(@Param("orderId") Long orderId);

    /**
     * 保洁工单分配方式
     *
     * @return {@code Distribution}
     */
    Distribution getClearOrderDistribution(Long communityId);
}
