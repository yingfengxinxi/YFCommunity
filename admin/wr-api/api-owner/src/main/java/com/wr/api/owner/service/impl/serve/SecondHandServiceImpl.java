package com.wr.api.owner.service.impl.serve;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.owner.entity.dto.SecondHandDTO;
import com.wr.api.owner.entity.vo.serve.SecondHandVo;
import com.wr.api.owner.mapper.serve.SecondHandMapper;
import com.wr.api.owner.service.serve.SecondHandService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.customer.secondhand.SecondHand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-11 15:13:40
 * @Desc:
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SecondHandServiceImpl extends ServiceImpl<SecondHandMapper, SecondHand> implements SecondHandService {

    private final SecondHandMapper secondHandMapper;


    /**
     * 二手交易列表
     * @param secondHandVo
     * @return
     */
    @Override
    public List<SecondHandDTO> getSecondHandList(SecondHandVo secondHandVo) {

        List<SecondHandDTO> allList = secondHandMapper.getAllList(secondHandVo);
        //VO和DTO的转换
//        List<SecondHandDTO> list = Objects.requireNonNull(BeanUtil.copyToList(allList,SecondHandDTO.class));
        return allList;
    }

    /**
     * 添加二手交易
     * @param secondHandVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult addSecondHand(SecondHandVo secondHandVo) {
        //将Base64解码
//        String encode = Base64Util.encode(secondHandVo.getThumbnail());
//        System.out.println(encode);
//        base64StrToImage(secondHandVo.getThumbnail(),"D:\\002.png");
        //发布时间
        secondHandVo.setPublishTime(new Date());
        //向二手交易添加数据
        int row = secondHandMapper.insertSecond(secondHandVo);
        if(row>0){
            return AjaxResult.success("添加二手交易成功");
        }else {
            return AjaxResult.error("添加二手交易失败");
        }
    }

    /**
     * 查询二手交易详情
     * @param handId
     * @return
     */
    @Override
    public SecondHandVo SecondHandInfo(Long handId) {
        SecondHandVo secondHandVo = secondHandMapper.getInfo(handId);
        return secondHandVo;
    }

    /**
     * 下架
     *
     * @param handId id
     * @return int
     * @author yx
     * @date 2023/1/13 13:15
     */
    @Override
    public int outStack(Long handId) {
        return secondHandMapper.outStack(handId, "2");
    }

    /**
     * 已出售
     *
     * @param handId id
     * @return int
     * @author yx
     * @date 2023/1/13 13:24
     */
    @Override
    public int sell(Long handId) {
        return secondHandMapper.outStack(handId, "1");
    }

    //    public static boolean base64StrToImage(String imgStr, String path) {
//        if (imgStr == null) {
//            return false;
//        }
//        BASE64Decoder decoder = new BASE64Decoder();
//        try {
//            byte[] bytes = decoder.decodeBuffer(imgStr.replace("data:image/png;base64,", ""));
//            // 解密
//            // 处理数据
//            for (int i = 0; i < bytes.length; ++i) {
//                if (bytes[i] < 0) {
//                    bytes[i] += 256;
//                }
//            }
//            //文件夹不存在则自动创建
//            File tempFile = new File(path);
//            if (!tempFile.getParentFile().exists()) {
//                tempFile.getParentFile().mkdirs();
//            }
//            OutputStream out = new FileOutputStream(tempFile);
//            out.write(bytes);
//            out.flush();
//            out.close();
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }


}
