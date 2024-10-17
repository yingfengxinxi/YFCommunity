package com.wr.api.estate.service.impl.application.manage;

import com.wr.api.estate.entity.dto.BorrowDTO;
import com.wr.api.estate.entity.vo.application.manage.BorrowVo;
import com.wr.api.estate.entity.vo.application.manage.GoodsVo;
import com.wr.api.estate.mapper.application.manage.GoodsMapper;
import com.wr.api.estate.service.application.manage.GoodsService;
import com.wr.common.core.domain.R;
import com.wr.common.customize.util.BaseUtils;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 19:39:33
 * @Desc: 业务处理层
 */
@Service
public class GoodsServiceImpl extends BaseUtils implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * 物品列表
     * @param goodsVo
     * @return
     */
    @Override
    public List<GoodsVo> getList(GoodsVo goodsVo) {
        return goodsMapper.getList(goodsVo);
    }

    /**
     * 物品详情
     * @param goodsId
     * @return
     */
    @Override
    public GoodsVo getById(Long goodsId) {
        return goodsMapper.getById(goodsId);
    }

    /**
     * 确认归还
     * @param borrowDTO
     * @return
     */
    @Override
    @Transactional
    public int updateGoodsVoFree(BorrowDTO borrowDTO) {
        // 归还借用记录
        goodsMapper.updateBorrowStatus(borrowDTO.getBorrowId());
        // 归还物品
        return goodsMapper.updateGoodsVoFree(borrowDTO.getGoodsId());
    }

    /**
     * 借用历史列表
     * @param goodsId
     * @return
     */
    @Override
    public List<BorrowVo> getBorrowList(Long goodsId) {
        return goodsMapper.getBorrowList(goodsId);
    }

    /**
     * 驳回借用申请
     * @param borrowVo
     * @return
     */
    @Override
    @Transactional
    public int updateBorrowRefuse(BorrowVo borrowVo) {
        return goodsMapper.updateBorrowRefuse(borrowVo);
    }

    /**
     * 同意借用申请
     * @param borrowVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBorrowAgree(BorrowVo borrowVo) {
        int i = 0;
        try {
            GoodsVo goodsVo = goodsMapper.getById(borrowVo.getGoodsId());
            goodsMapper.updateGoodsVoBusy(goodsVo);
            //language=JSON
            String content = "{\"borrowId\": \""+borrowVo.getBorrowId()+"\",\"goodsId\": \""+borrowVo.getGoodsId()+"\"}";
            MultipartFile qrCode = QrCodeUtils.createQrCode(content);
            R<SysFile> uploadResult = remoteFileService.upload(qrCode);
            String url = uploadResult.getData().getUrl();
            log.info("Qrcode=========>>>{}", url);
            borrowVo.setCodeUrl(url);
            i = goodsMapper.updateBorrowAgree(borrowVo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改借用信息失败!");
        }
        return i;
    }

    /**
     * 物品总数
     * @param communityId
     * @return
     */
    @Override
    public int getCount(Long communityId) {
        return goodsMapper.getCount(communityId);
    }

    /**
     * 借用申请列表
     * @param communityId
     * @return
     */
    @Override
    public List<BorrowVo> getApplyList(Long communityId) {
        return goodsMapper.getApplyList(communityId);
    }

    /**
     * 借用申请详情
     * @param borrowVo
     * @return
     */
    @Override
    public BorrowVo getApplyDetail(BorrowVo borrowVo) {
        return goodsMapper.getApplyDetail(borrowVo);
    }

    @Override
    public GoodsVo getDetailById(Long goodsId) {
        return goodsMapper.getDetailById(goodsId);
    }

    @Override
    public List<GoodsVo> getClassifyName(Long estateId) {
        return goodsMapper.getClassifyName(estateId);
    }
}
