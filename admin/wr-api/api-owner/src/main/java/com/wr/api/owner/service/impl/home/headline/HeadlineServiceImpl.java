package com.wr.api.owner.service.impl.home.headline;


import com.wr.api.owner.entity.home.headline.Headline;
import com.wr.api.owner.mapper.home.headline.HeadlineMapper;
import com.wr.api.owner.service.home.headline.HeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 社区公告
 */
@Service
public class HeadlineServiceImpl implements HeadlineService {


    @Autowired
    private HeadlineMapper headlineMapper;


    /**
     * 列表
     * @param headline
     * @return
     */
    @Override
    public List<Headline> getList(Headline headline) {
        return headlineMapper.getList(headline);
    }

    /**
     * 根据公告id查详情
     * @param headline
     * @return
     */
    @Override
    public Headline selectOneById(Headline headline) {
        if(headline!=null){
            //增加该头条的阅读数
            this.readNumAdd(headline);
        }
        //查询数据
        return headlineMapper.selectOneById(headline);
    }


    @Transactional
    public void readNumAdd(Headline headline){
        headlineMapper.readNumAdd(headline);
    }



    /**
     * 查询根据发布时间最新的社区公告
     * @return
     */
    @Override
    public Headline selectNews(Headline headline) {
        return headlineMapper.selectNews(headline);
    }
}
