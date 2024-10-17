package com.wr.estate.service.customer.goods.impl;

import com.wr.common.core.domain.R;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.customize.util.BaseUtils;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.customer.goods.BorrowMapper;
import com.wr.estate.mapper.customer.goods.GoodsMapper;
import com.wr.estate.service.customer.goods.BorrowService;
import com.wr.remote.domain.SysFile;
import com.wr.remote.estate.customer.goods.vo.BorrowVo;
import com.wr.remote.estate.customer.goods.vo.GoodsVo;
import com.wr.remote.system.RemoteFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-12 16:27:25
 * @Desc: 借用申请表服务实现类
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class BorrowServiceImpl extends BaseUtils implements BorrowService {

    @Autowired
    private BorrowMapper borrowMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * 查询Borrow列表
     *
     * @param borrowVo 筛选条件
     * @return 查询结果
     */
    @Override
    public List<BorrowVo> selectBorrowList(BorrowVo borrowVo){
        return borrowMapper.selectBorrowList(borrowVo);
    }

    /**
     * 根据id查询单条数据
     *
     * @param borrowId 主键
     * @return 实例对象
     */
    @Override
    public GoodsVo getBorrowById(Long borrowId){
        GoodsVo goodsInfo = borrowMapper.getBorrowById(borrowId);
        int borrowCount = borrowMapper.getBorrowCount(goodsInfo.getGoodsId(), goodsInfo.getAccountId());
        goodsInfo.setBorrowCount(borrowCount);
        return goodsInfo;
    }

    /**
     * 修改数据
     *
     * @param borrowVo 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(readOnly = false)
    public int updateBorrow(BorrowVo borrowVo){

        if ("2".equals(borrowVo.getAuditStatus())){
            GoodsVo goodsVo = new GoodsVo();
            goodsVo.setBorrowStatus("1");
            goodsVo.setGoodsId(borrowVo.getGoodsId());
            goodsMapper.updateGoods(goodsVo);
            // 通过申请生成二维码
            String content = "{\"borrowId\": \""+borrowVo.getBorrowId()+"\",\"goodsId\": \""+borrowVo.getGoodsId()+"\"}";
            try {
                MultipartFile qrCode = QrCodeUtils.createQrCode(content);
                R<SysFile> uploadResult = remoteFileService.upload(qrCode);
                String url = uploadResult.getData().getUrl();
                log.info("Qrcode=========>>>{}",url);
                borrowVo.setCodeUrl(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        borrowVo.setAuditTime(DateUtils.getNowDate());
        borrowVo.setAuditBy(SecurityUtils.getUsername());
        return borrowMapper.updateBorrow(borrowVo);
    }


    /**
     * 借用天数
     *
     * @param goodsId
     * @return
     */
    @Override
    public BorrowVo getBorrowDay(Long goodsId) {
        return borrowMapper.getBorrowDay(goodsId);
    }

    /**
     * 历史借用次数
     *
     * @param goodsId
     * @return
     */
    @Override
    public int getBorrowCount(Long goodsId) {
        return borrowMapper.getBorrowCount(goodsId,null);
    }


}
