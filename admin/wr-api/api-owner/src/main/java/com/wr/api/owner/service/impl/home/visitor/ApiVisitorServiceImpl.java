package com.wr.api.owner.service.impl.home.visitor;

import com.wr.api.owner.entity.home.visitor.ApiAccount;
import com.wr.api.owner.entity.home.visitor.ApiVisitor;
import com.wr.api.owner.entity.home.visitor.vo.ApiLiaisonVo;
import com.wr.api.owner.entity.home.visitor.vo.OwnerHouseVo;
import com.wr.api.owner.entity.home.visitor.vo.RegisterVisitorVO;
import com.wr.api.owner.mapper.LoginMapper;
import com.wr.api.owner.mapper.home.visitor.ApiVisitorMapper;
import com.wr.api.owner.service.home.visitor.ApiVisitorService;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.DictUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.estate.customer.decorate.Account;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/15 11:46
 * @Desc: 我的访客
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiVisitorServiceImpl implements ApiVisitorService {
    // @Value("${owner.defaultMan}")
    //private String defaultMan;

    // @Value("${owner.defaultGirl}")
    // private String defaultGirl;

    @Resource
    private ApiVisitorMapper apiVisitorMapper;

    @Resource
    private LoginMapper loginMapper;

    @Autowired
    private RedisService redisService;

    /**
     * 获取社区救助列表
     *
     * @param apiLiaisonVo
     * @return
     * @throws ParseException
     */
    @Override
    public List<ApiLiaisonVo> getList(ApiLiaisonVo apiLiaisonVo) {
        return apiVisitorMapper.getList(apiLiaisonVo);
    }

    @Override
    public ApiLiaisonVo getVisitorById(Long visitorId) {
        return apiVisitorMapper.getVisitorById(visitorId);
    }


    /**
     * 新增访客
     *
     * @param apiLiaisonVo
     * @return
     */
    @Override
    @Transactional
    public Integer add(ApiLiaisonVo apiLiaisonVo) {
        //临时变量
        int i = 0;
        //判断：拜访人和被访人是否是同一个人，通过身份证判断
        //查询业主
//        if (!(apiLiaisonVo.getOwnerId() == null)) {
//            //比较当前新增访客是否是业主本人，如果是则字节返回error
//            OwnerHouseVo vo = ownerInfo( apiLiaisonVo.getOwnerId() );
//            if (apiLiaisonVo.getCardNo().equals( vo.getCardNo() )) {
//                return i;
//            }
//        }

        //判断当前新增操作人员：是业主还是访客
        if (apiLiaisonVo.getAccountType().equals("0")) {
            //业主为访客创建通信证,无需审核，直接通过！添加两张表格！（业主为访客新增通行二维码！）
            apiLiaisonVo.setLiaisonType("1");
            apiLiaisonVo.setLiaisonStatus("1");
            apiLiaisonVo.setPassTime(new Date());
            this.addLiaison(apiLiaisonVo);
            this.addVisitor(apiLiaisonVo);
            i = 1;
        } else if (apiLiaisonVo.getAccountType().equals("2")) {
            //访客为自己创建， 需被访问业主进行审核！！添加两张表格
            apiLiaisonVo.setLiaisonType("0");
            apiLiaisonVo.setLiaisonStatus("0");
            this.addVisitor(apiLiaisonVo);
            this.addLiaison(apiLiaisonVo);
            i = 1;
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false)
    public int updateStatus(ApiLiaisonVo apiLiaisonVo) {
        return apiVisitorMapper.updateStatus(apiLiaisonVo);
    }


    /**
     * 数据新增到bus_liaison通信证表格
     */
    @Transactional(readOnly = false)
    public void addLiaison(ApiLiaisonVo apiLiaisonVo) {
        apiVisitorMapper.addLiaison(apiLiaisonVo);
    }


    /**
     * 数据新增到bus_visitor访客档案表格
     */
    @Transactional(readOnly = false)
    public void addVisitor(ApiLiaisonVo apiLiaisonVo) {
        //新增访客档案，用该实体类
        ApiVisitor apiVisitor = new ApiVisitor();
        //通过拜访者手机号，查询信息，可能他不是用户，如果未注册用户，则小区id和账户id就不进行存储！
        ApiAccount apiAccount = visitorInfo(apiLiaisonVo.getUserPhone());
        if (apiAccount != null) {
            apiVisitor.setAccountId(apiAccount.getAccountId());
        }
        apiVisitor.setCommunityId(apiLiaisonVo.getCommunityId());
        //为实体类设置参数
        apiVisitor.setVisitorName(apiLiaisonVo.getUserName());
        apiVisitor.setVisitorPhone(apiLiaisonVo.getUserPhone());
        apiVisitor.setCardType(apiLiaisonVo.getCardType());
        apiVisitor.setCardNo(apiLiaisonVo.getCardNo());
        //判断当前新增者
        if (apiLiaisonVo.getLiaisonType().equals("1")) {
            apiVisitor.setCreateBy(apiLiaisonVo.getOwnerName());
        } else {
            apiVisitor.setCreateBy(apiLiaisonVo.getUserName());
        }
        //开始新增
        apiVisitorMapper.addVisitor(apiVisitor);
    }


    /**
     * 查询业主信息
     *
     * @param ownerId
     * @return
     */
    @Override
    public List<OwnerHouseVo> ownerInfo(Long ownerId) {
        return apiVisitorMapper.ownerInfo(ownerId);
    }

    /**
     * 查询访客信息——根据手机号码
     *
     * @return
     */
    public ApiAccount visitorInfo(String loginName) {
        return apiVisitorMapper.visitorInfo(loginName);
    }

    /**
     * 注册访客
     *
     * @param registerVo 注册参数
     * @author yx
     * @date 2022/12/1 10:23
     */
    @Override
    @Transactional
    public AjaxResult register(RegisterVisitorVO registerVo) {
        Assert.notNull(registerVo.getPhone(), "手机号码不能为空");
        Account phoneAccount = loginMapper.selectAccountByLoginName(registerVo.getPhone());
        if (null != phoneAccount) {
            return AjaxResult.error("该手机号已注册");
        }
        Assert.notNull(registerVo.getName(), "姓名不能为空");
        Assert.notNull(registerVo.getIdCard(), "身份证号不能为空");
        Long accountId = loginMapper.selectVisitorAccountByCardNo(registerVo.getIdCard());
        if (null != accountId) {
            return AjaxResult.error("该身份证号已注册");
        }
        Assert.notNull(registerVo.getPassword(), "密码不能为空");
        // 添加账号
        Account account = new Account();
        account.setCommunityId(registerVo.getCommunityId());
        account.setLoginName(registerVo.getPhone());
        account.setPassword(SecurityUtils.encryptPassword(registerVo.getPassword()));
        account.setAccountName(registerVo.getName());
        account.setAccountNick(registerVo.getName());
        String avatar = redisService.getCacheObject(CacheConstants.SYS_CONFIG_KEY + "ownerAvatarMain");
        account.setAvatar(avatar);
        if (StringUtils.equals(registerVo.getCardType(), "I")) {
            Integer sex = Integer.parseInt(registerVo.getIdCard().substring(16, 17));
            Integer sexNew = sex % 2 == 0 ? 0 : 1;
            if (sexNew == 0) {

                avatar = redisService.getCacheObject(CacheConstants.SYS_CONFIG_KEY + "ownerAvatarFemale");
                account.setAvatar(avatar);
            }
        }

        loginMapper.insertAccount(account);
        // 添加访客信息
        ApiVisitor apiVisitor = new ApiVisitor();
        apiVisitor.setCommunityId(registerVo.getCommunityId());
        apiVisitor.setAccountId(account.getAccountId());
        apiVisitor.setVisitorName(registerVo.getName());
        apiVisitor.setVisitorPhone(registerVo.getPhone());
        apiVisitor.setCardType(registerVo.getCardType());
        apiVisitor.setCardNo(registerVo.getIdCard());
        apiVisitor.setVisitorNum(0);
        apiVisitor.setDelFlag("0");
        apiVisitorMapper.addVisitor(apiVisitor);
        return AjaxResult.success();
    }
}
