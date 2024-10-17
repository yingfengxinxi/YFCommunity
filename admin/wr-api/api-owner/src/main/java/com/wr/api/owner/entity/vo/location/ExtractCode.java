package com.wr.api.owner.entity.vo.location;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wr.common.core.utils.sql.SqlUtil;
import com.wr.common.core.web.page.PageDomain;
import com.wr.common.core.web.page.TableSupport;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类
 * @Author: lvzy
 * @Date: 2022-12-03 10:01:43
 * @Desc:工具类
 */
public class ExtractCode {

    /**
     * 生成工单编号   前缀_年月日_6位随机数
     * @param str 前缀 如: xj
     * @return 如: xj-20221031-310324
     */
    public String sixInspectNo(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = dateFormat.format(new Date());
        int ranDom = (int) ((Math.random() * 9 + 1) * 100000);
        return str + "-" + format + "-" + ranDom;
    }


    /**
     * 小区合同编号
     * @param str 前缀 如: cj
     * @return 如: cj-20221212141946-29021
     */
    public String communityNo(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = dateFormat.format(new Date());
        int ranDom = (int) ((Math.random() * 9 + 1) * 10000);
        return str + "-" + format + "-" + ranDom;
    }

    /**
     * 分页条件(防止SQL注入)
     * @param pageNum  页数
     * @param pageSize 每页条数
     */
    public Page<Object> pageListAntiSql(Integer pageNum, Integer pageSize) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        return PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }
}
