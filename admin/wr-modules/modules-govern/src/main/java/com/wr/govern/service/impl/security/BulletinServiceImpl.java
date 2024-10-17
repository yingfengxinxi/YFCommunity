package com.wr.govern.service.impl.security;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.security.BulletInMapper;
import com.wr.govern.service.security.BulletinService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.security.Bulletin;
import com.wr.remote.govern.security.Clue;
import com.wr.remote.govern.security.vo.BulletinVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-24 17:18:46
 * @Desc: 警民互动业务层
 */
@Service
public class BulletinServiceImpl implements BulletinService {

    /**
     * 物业管理员
     */
    private static final String ESTATE_MANAGER = "10";

    @Autowired
    private BulletInMapper bulletInMapper;

    @Override
    public List<Bulletin> selectBulletinList(Bulletin bulletin) {
        bulletin.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return bulletInMapper.selectBulletinList(bulletin);
    }

    @Override
    public Integer insertBulletin(Bulletin bulletin) {
        return bulletInMapper.insertBulletin(bulletin);
    }

    @Override
    public Bulletin selectBulletinById(Long bulletinId) {
        return bulletInMapper.selectBulletinById(bulletinId);
    }

    @Override
    public Integer updateBulletin(Bulletin bulletin) {
        return bulletInMapper.updateBulletin(bulletin);
    }

    @Override
    public Integer deleteBulletinByIds(Long bulletinId) {
        return bulletInMapper.deleteBulletinByIds(bulletinId, SecurityUtils.getUsername());
    }

    @Override
    public Integer updateArchive(Long bulletinId) {
        return bulletInMapper.updateArchive(bulletinId, SecurityUtils.getUsername());
    }

    @Override
    public BulletinVO getArchiveDetail(Long bulletinId) {
        BulletinVO bulletinVO = new BulletinVO();
        Bulletin bulletin = bulletInMapper.selectBulletinById(bulletinId);
        BeanUtils.copyProperties(bulletin, bulletinVO);

        //获取线索表集合
        List<Clue> clueList = bulletInMapper.selectClueListByBulletinId(bulletinId);
        bulletinVO.setClueList(clueList);
        return bulletinVO;
    }

    @Override
    public Boolean getUserType() {
        SysUser sysUser = bulletInMapper.selectUserType(SecurityUtils.getUserId());
        if (sysUser.getUserType().equals(ESTATE_MANAGER)) {
            return true;
        }
        return false;
    }

    @Override
    public Integer checkBulletinExsist(String cardNo) {
        return bulletInMapper.checkBulletinExsist(cardNo);
    }

}
