package com.wr.estate.web.financial;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.estate.service.financial.EstateEarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author: Wen Chou
 * @Date: 2022-10-12 14:46:07
 * @Desc:
 */

@RestController
@RequestMapping("/estateEarn")
public class EstateEarnContorller extends BaseController {

    @Autowired
    private EstateEarnService estateEarnService;

    /**
     * 停车费
     *
     * @param
     * @return
     */
    @GetMapping("/list")
//    @RequiresPermissions("business:activitetype:list")
    public AjaxResult list(Long communityId) {
        return estateEarnService.parkingIncomeStatistics(communityId);
    }

    /**
     * 场馆收益
     *
     * @param
     * @return
     */
    @GetMapping("/threeCash")
    public AjaxResult estateCash(Long communityId) {
        return estateEarnService.incomeStatistic(communityId);
    }

    /**
     * 同比计算
     *
     * @param
     * @return
     */
    String[] rate(BigDecimal[] thisyear, BigDecimal[] lastyear) {
        String[] rate = new String[12];
        AjaxResult ajaxResult = new AjaxResult();
        for (int i = 0; i < thisyear.length; i++) {
            if (null == lastyear[i] || lastyear[i].equals(BigDecimal.ZERO)) {
                rate[i] = "1";
            } else {
                BigDecimal divide = BigDecimal.valueOf(0.00);
                if (thisyear[i].doubleValue() != 0) {
                    divide = thisyear[i].divide(lastyear[i], 2, BigDecimal.ROUND_HALF_UP);
                }
                rate[i] = String.valueOf(divide);
            }

        }
        ajaxResult.put("rate", rate);
        return rate;
    }

    /**
     * 支出列表
     *
     * @param
     * @return
     */
    @GetMapping("/buylist")
//    @RequiresPermissions("business:activitetype:list")
    public AjaxResult buylist(Long communityId) {
        return estateEarnService.expendStatistic(communityId);
    }

    /**
     * 收益详情
     *
     * @param
     * @return
     */
    @GetMapping("/detile")
    @PreAuthorize("@ss.hasPermi('estate:estateEarn:detail')")
    public AjaxResult detile(Long communityId) {
        return estateEarnService.incomeDetail(communityId);
    }

    /**
     * 支出详情
     *
     * @param
     * @return
     */
    @GetMapping("/buydetile")
//    @RequiresPermissions("business:activitetype:list")
    public AjaxResult buydetile(Long communityId) {
        return estateEarnService.expendDetail(communityId);
    }

    /**
     * 计算同比率
     *
     * @param
     * @return
     */
    BigDecimal rate(BigDecimal now, BigDecimal last) {
        //自定义个实体类
        BigDecimal mal = new BigDecimal("0");
        BigDecimal add = now.add(last);
        if (null == add || add.equals(BigDecimal.ZERO) || mal.compareTo(add) == 0) {
            BigDecimal divide = new BigDecimal("0");
            return divide;
        } else {
            BigDecimal divide = now.divide(add, 2, RoundingMode.HALF_UP);
            return divide;
        }
    }

    /**
     * 计算总的
     *
     * @param
     * @return
     */

    BigDecimal addcount(BigDecimal[] data) {
        BigDecimal count = new BigDecimal(0);
        for (int i = 0; i < data.length; i++) {
            if (null == data[i]) {
                data[i] = BigDecimal.valueOf(0);
            }
            count = count.add(data[i]);
        }
        return count;
    }
}
