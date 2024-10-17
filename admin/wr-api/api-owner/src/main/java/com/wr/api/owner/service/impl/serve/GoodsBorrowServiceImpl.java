package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.serve.ApiBorrow;
import com.wr.api.owner.entity.serve.ApiRemind;
import com.wr.api.owner.entity.vo.serve.ApiGoodsVo;
import com.wr.api.owner.mapper.serve.BorrowMapper;
import com.wr.api.owner.mapper.serve.GoodsMapper;
import com.wr.api.owner.mapper.serve.RemindMapper;
import com.wr.api.owner.service.serve.GoodsBorrowService;
import com.wr.common.core.domain.R;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 物品借用
 * @Author: lvzy
 * @Date: 2022-11-14 13:18:30
 * @Desc: 物品借用
 */
@Service
@AllArgsConstructor
public class GoodsBorrowServiceImpl implements GoodsBorrowService {

    private final GoodsMapper goodsMapper;

    // 借用提醒记录表
    private final RemindMapper remindMapper;

    // 借用申请表
    private final BorrowMapper borrowMapper;

    // 上传服务
    private final RemoteFileService remoteFileService;

    @Override
    public List<ApiGoodsVo> listPage(Long communityId) {
        return goodsMapper.listPage(communityId);
    }

    @Override
    public ApiGoodsVo detail(Long goodsId) {
        return goodsMapper.detail(goodsId);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult borrowByMe(ApiGoodsVo apiGoodsVo) {
        if (apiGoodsVo.getGoodsId() != null) {
            // 1. 改bus_goods状态
//            goodsMapper.updateById(apiGoodsVo.getGoodsId());
            // 2. bus_remind 新增
            ApiRemind apiRemind = new ApiRemind();
            apiRemind.setCommunityId(apiGoodsVo.getCommunityId());
            apiRemind.setAccountId(apiGoodsVo.getAccountId());
            apiRemind.setGoodsId(apiGoodsVo.getGoodsId());
            apiRemind.setCreateBy(apiGoodsVo.getCreateBy());
            remindMapper.insertSelective(apiRemind);
            // 3. bus_borrow 新增
            ApiBorrow apiBorrow = new ApiBorrow();
            apiBorrow.setCommunityId(apiGoodsVo.getCommunityId());
            apiBorrow.setGoodsId(apiGoodsVo.getGoodsId());
            apiBorrow.setAccountId(apiGoodsVo.getAccountId());
            apiBorrow.setReturnTime(apiGoodsVo.getReturnTime());
            borrowMapper.insertSelective(apiBorrow);

            //生成二维码
            String content = "{\"borrowId\": \""+apiBorrow.getBorrowId()+"\",\"goodsId\": \""+apiBorrow.getGoodsId()+"\"}";
            String url = null;
            try {
                MultipartFile multipartFile = QrCodeUtils.createQrCode(content);
                R<SysFile> fileResult = remoteFileService.upload(multipartFile);
                url = fileResult.getData().getUrl();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ApiBorrow borrow = new ApiBorrow();
            borrow.setBorrowId(apiBorrow.getBorrowId());
            borrow.setCodeUrl(url);
            borrowMapper.updateByPrimaryKeySelective(borrow);
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
