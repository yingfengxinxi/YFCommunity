package com.wr.estate.service.impl.center;

import com.wr.common.core.constant.UserConstants;
import com.wr.estate.mapper.center.PersonLabelMapper;
import com.wr.estate.service.center.PersonLabelService;
import com.wr.remote.estate.center.PersonLabel;
import com.wr.remote.estate.center.vo.PersonLabelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 09:49:21
 * @Desc:
 */
@Service
public class PersonLabelServiceImpl implements PersonLabelService {
    @Autowired
    private PersonLabelMapper personLabelMapper;
    @Override
    public PersonLabelVo selectPersonLabelByLabelId(Long labelId) {
        return personLabelMapper.selectPersonLabelByLabelId(labelId);
    }
    /*@Override
    public PersonLabelVo selectPersonLabelByLabelId(PersonLabelVo personLabelVo) {
        return personLabelMapper.selectPersonLabelByLabelId(personLabelVo);
    }*/

    @Override
    public List<PersonLabelVo> selectPersonLabelList(PersonLabelVo personLabelVo) {
        return personLabelMapper.selectPersonLabelList(personLabelVo);
    }

    @Override
    public List<PersonLabelVo> selectNameList(PersonLabelVo personLabelVo) {
        return personLabelMapper.selectNameList(personLabelVo);
    }

    @Override
    public int insertPersonLabel(PersonLabelVo personLabelVo) {
        return personLabelMapper.insertPersonLabel(personLabelVo);
    }

    @Override
    public int updatePersonLabel(PersonLabel personLabel) {
        return personLabelMapper.updatePersonLabel(personLabel);
    }

    @Override
    public int deletePersonLabelByLabelIds(Long[] labelIds) {
        return personLabelMapper.deletePersonLabelByLabelIds(labelIds);
    }

    @Override
    public String checkUnique(Long id) {
        int count = personLabelMapper.checkUnique(id);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
