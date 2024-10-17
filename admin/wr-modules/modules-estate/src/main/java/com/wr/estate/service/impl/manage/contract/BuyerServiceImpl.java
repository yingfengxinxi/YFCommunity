package com.wr.estate.service.impl.manage.contract;


import com.wr.common.core.utils.chinese.ChineseCharToEn;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.CommunityMapper;
import com.wr.estate.mapper.manage.contract.BuyerMapper;
import com.wr.estate.service.manage.contract.BuyerService;
import com.wr.estate.service.manage.contract.MaterialService;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.estate.manage.contract.Estate;
import com.wr.remote.estate.manage.contract.Material;
import com.wr.remote.estate.manage.vo.contract.BuyerVo;
import com.wr.system.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Author: lusu
 * @Date: 2022/10/17 14:46
 * @Desc: 采购合同
 */
@Service
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private BuyerMapper buyerMapper;

    @Autowired
    private MaterialService materialService;

    /**
     * 获取采购列表
     *
     * @param buyerVo
     * @return
     */
    @Override
    public List<BuyerVo> getBuyerList(BuyerVo buyerVo) {
        buyerVo.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return buyerMapper.getBuyerList(buyerVo);
    }

    /**
     * 新增合同、物品信息
     *
     * @param buyerVo
     * @return
     */
    @Override
    @Transactional
    public Integer add(BuyerVo buyerVo) {
        //获取当前的登录人信息
        buyerVo.setCreateBy(SecurityUtils.getUsername());
        //生成采购合同编号
        buyerVo.setContractNo(this.contractNoCreate(buyerVo.getEstateName()));
        //调用采购合同接口，添加合同信息
        Integer add = buyerMapper.add(buyerVo);
        //生成Material实体
        Material materialInstance = getMaterialInstance(buyerVo);
        //在添加采购物品信息
        Integer integer = materialService.addMaterial(materialInstance);
        if (add > 0 && integer > 0) {
            return 1;
        }
        return 0;
    }

    /**
     * 生成Material实体
     *
     * @param buyerVo
     * @return
     */
    public Material getMaterialInstance(BuyerVo buyerVo) {
        Material material = new Material();
        material.setMaterialName(buyerVo.getMaterialName());
        material.setUnitPrice(buyerVo.getUnitPrice());
        material.setSpecification(buyerVo.getSpecification());
        material.setMaterialNum(buyerVo.getMaterialNum());
        material.setTotalAmount(buyerVo.getTotalAmount());
        material.setActuallyAmount(buyerVo.getActuallyAmount());
        material.setCreateBy(buyerVo.getCreateBy());
        material.setProcurementId(buyerVo.getProcurementId());
        return material;
    }


    @Override
    public List<Estate> getEstateList() {
        return buyerMapper.getEstateList(SecurityUtils.getLoginUser().getSysUser().getEstateId());
    }

    @Override
    @Transactional
    public int deleteBuyerByIds(Long[] procurementId) {
        return buyerMapper.deleteBuyerByIds(procurementId);
    }

    @Override
    public BuyerVo getBuyerById(Long procurementId) {
        return buyerMapper.getBuyerById(procurementId);
    }

    @Override
    public List<Staff> getStaffList(Long estateId) {
        List<Staff> staffList = buyerMapper.getStaffList(estateId);
        //过滤一下null值，然后再返回
        List<Staff> collect = staffList.stream().filter(str -> str!=null).collect(Collectors.toList());
        return collect;
    }


    /**
     * 物业首字母全拼+年、月、日、时（时精确到分）+五位随机数据
     * @param contractNo
     * @return
     */
    public String contractNoCreate(String contractNo){
        //物业名称转换为首字母
        String allFirstLetter = ChineseCharToEn.getAllFirstLetter(contractNo);
        //讲当前时间年、月、日、时、分 获取后，转换为字符串
        String date=null;
        try {
            Date totay = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            date = sdf.format(totay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //随机5个数据
        String str = "";
        Random rand = new Random();
        char[] letters=new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
                'R','S','T','U','V','W','X','Y','Z',
                '0','1','2','3','4','5','6','7','8','9'};
        int index;
        boolean[] flags = new boolean[letters.length];//默认为false
        for(int i=0;i<5;i++){
            do{
                index = rand.nextInt(letters.length);
            }while(flags[index]==true);
            char c = letters[index];
            str += c;
            flags[index]=true;
        }


        return allFirstLetter+"-"+date+"-"+str;
    }



}
