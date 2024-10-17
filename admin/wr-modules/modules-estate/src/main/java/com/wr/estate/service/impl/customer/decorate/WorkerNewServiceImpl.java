package com.wr.estate.service.impl.customer.decorate;

import com.wr.common.core.domain.R;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.center.useraccountmanage.AccountManageMapper;
import com.wr.estate.mapper.customer.decorate.WorkerNewMapper;
import com.wr.estate.service.customer.decorate.WorkerNewService;
import com.wr.remote.estate.customer.decorate.Account;
import com.wr.remote.estate.customer.decorate.Decorate;
import com.wr.remote.estate.customer.decorate.Liaison;
import com.wr.remote.estate.customer.decorate.Worker;
import com.wr.remote.estate.customer.vo.decorate.WorkerVo;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-10-13 17:09:46
 * @Desc: 新增的装修工
 */
@Service
public class WorkerNewServiceImpl implements WorkerNewService {
    private static final Logger log = LoggerFactory.getLogger(WorkerNewServiceImpl.class);

    @Autowired
    private WorkerNewMapper workerNewMapper;
    @Autowired
    private AccountManageMapper accountManageMapper;
    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * 获取新增的装修工列表
     *
     * @param workerVo
     * @return
     */
    @Override
    public List<WorkerVo> getWorkerNewList(WorkerVo workerVo) {
        return workerNewMapper.getWorkerNewList(workerVo);
    }

    /**
     * 查看新增的装修工详情
     *
     * @param workerId
     * @return
     */
    @Override
    public AjaxResult getWorkerNewDetail(Long workerId) {
        AjaxResult ajaxResult = AjaxResult.success();

        //查询新增的装修工详情
        WorkerVo workerVo = workerNewMapper.getWorkerNewDetail(workerId);

        ajaxResult.put(AjaxResult.DATA_TAG, workerVo);

        return ajaxResult;
    }

    /**
     * 装修工审核通过
     *
     * @param workerIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int workerNewAdopt(Long[] workerIds) {
        try {
            for (Long workerId : workerIds) {
                //根据workerId查询装修工人信息
                Worker worker = workerNewMapper.getWorker(workerId);
                //查询装修信息
                Decorate decorate = workerNewMapper.getDecorateByDecorateId(worker.getDecorateId());
                // 查询账号是否已存在
                Long accountId = accountManageMapper.getAccountIdByLoginName(worker.getWorkerPhone());
                if(null == accountId) {
                    //给每个装修工添加账号
                    Account account = new Account();
                    account.setCommunityId(decorate.getCommunityId());
                    account.setLoginName(worker.getWorkerPhone());
                    account.setPassword(SecurityUtils.encryptPassword("1234abc"));
                    account.setAccountType("3");
                    account.setAccountName(worker.getWorkerName());
                    account.setAccountNick(worker.getWorkerName());
                    account.setDataSource("0");
                    account.setCreateBy(SecurityUtils.getUsername());
                    Integer num = workerNewMapper.insertAccount(account);
                    accountId = account.getAccountId();
                }
                //给装修工设置通行证
                Liaison liaison = new Liaison();

                //生成通行证二维码
                MultipartFile multipartFile = QrCodeUtils.createPassQrCode(worker.getCardNo(), worker.getWorkerName(), worker.getWorkerPhone(), "", 0, decorate.getOwnerName(), decorate.getOwnerPhone(), decorate.getRoomId());
                R<SysFile> fileResult = remoteFileService.upload(multipartFile);
                liaison.setCodeUrl(fileResult.getData().getUrl());

                liaison.setAccountId(accountId);
                liaison.setRoomId(decorate.getRoomId());
                liaison.setOwnerId(decorate.getOwnerId());
                liaison.setUserName(worker.getWorkerName());
                liaison.setUserPhone(worker.getWorkerPhone());
                liaison.setCardNo(worker.getCardNo());
                liaison.setBeginTime(decorate.getPlanStart());
                liaison.setEndTime(decorate.getPlanEnd());
                liaison.setLiaisonType("2");
                liaison.setDecorateId(worker.getDecorateId());
                liaison.setLiaisonStatus("1");
                workerNewMapper.insertLiaison(liaison);

                //将装修工审核通过
                worker.setAccountId(accountId);
                worker.setAuditStatus("2");
                worker.setAuditBy(SecurityUtils.getUsername());
                workerNewMapper.workerAdopt(worker);
            }
        } catch (Exception e) {
            log.error("装修工审核失败:{}",e.getMessage());
            throw new RuntimeException("装修工审核失败");
        }
        return workerIds.length;
    }

    /**
     * 装修工审核驳回
     *
     * @param worker
     * @return
     */
    @Override
    @Transactional
    public int workerNewReject(Worker worker) {

        for (Long workerId : worker.getWorkerIds()) {
            worker.setWorkerId(workerId);
            worker.setAuditStatus("1");
            worker.setAuditBy(SecurityUtils.getUsername());
            //装修工驳回
            workerNewMapper.workerNewReject(worker);
        }

        return worker.getWorkerIds().length;
    }

    /**
     * 装修工审核单个驳回
     *
     * @param worker
     * @return
     */
    @Override
    @Transactional
    public int workerNewRejectOne(Worker worker) {

        worker.setAuditStatus("1");
        worker.setAuditBy(SecurityUtils.getUsername());
        //装修工驳回
        return workerNewMapper.workerNewReject(worker);
    }
}
