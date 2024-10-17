package com.wr.estate.service.impl.manage;

import com.wr.common.core.constant.UserConstants;
import com.wr.estate.mapper.manage.PassageMapper;
import com.wr.estate.service.manage.PassageSerive;
import com.wr.remote.estate.manage.Passage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-13 14:33:22
 * @Desc:
 */
@Service
public class PassageSeriveImpl implements PassageSerive {

    @Autowired
    private PassageMapper passageMapper;
    @Override
    public List<Passage> selectPassageList(Passage passage) {
        return passageMapper.selectPassageList(passage);
    }

    @Override
    public Passage selectPassageById(Long passageId) {
        return passageMapper.selectPassageById(passageId);
    }

    @Override
    @Transactional
    public int deletePassageByIds(Long[] passageIds) {
        return passageMapper.deletePassageByIds(passageIds);
    }

    @Override
    @Transactional
    public int updatePassage(Passage passage) {
        return passageMapper.updatePassage(passage);
    }

    @Override
    @Transactional
    public int insertPassage(Passage passage) {
        return passageMapper.insertPassage(passage);
    }

    @Override
    public String checkPassageNameUnique(Passage passage) {
        int count = passageMapper.checkPassageNameUnique(passage);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
