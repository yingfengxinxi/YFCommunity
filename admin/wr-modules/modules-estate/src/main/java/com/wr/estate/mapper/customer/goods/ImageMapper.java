package com.wr.estate.mapper.customer.goods;

import com.wr.remote.estate.Image;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Author: Guan Yu
* @Date: 2022-10-12 16:27:24
* @Desc: 图片数据库访问层
*/
public interface ImageMapper {

    /**
     * 获取图片列表
     * @param imageType
     * @param tableId
     * @return
     */
    List<Image> getImageList(@Param("imageType")String imageType,@Param("tableId")Long tableId);

}

