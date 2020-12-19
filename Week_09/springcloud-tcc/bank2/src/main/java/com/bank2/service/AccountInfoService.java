package com.bank2.service;


import com.bank2.excepiton.TccTryException;
import com.bank2.mapper.AccountInfoMapper;
import com.bank2.mapper.TccMapper;
import com.bank2.po.AccountInfoPo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountInfoService extends ServiceImpl<AccountInfoMapper, AccountInfoPo> {

    @Autowired
    private TccMapper tccMapper;

    /**
     * @Description: tcc try操作
     * @Author:rosh
     * @date: 2020-10-13
     * @param cardNumber 卡号
     * @param money      金额
     */
    @Hmily(confirmMethod = "confirm", cancelMethod = "cancel")
    @Transactional
    public void addAccountBalance(String cardNumber, Integer money) {
        //1 获取分布式ID
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.warn("AccountInfoService invoke addAccountBalance , transId : {}", transId);

        if (money.equals(250)){
            throw new RuntimeException("不能转账250");
        }

        //2 幂等
        if (tccMapper.isExistTry(transId) > 0) {
            return;
        }
        //3 悬挂，防止已经执行过cancel
        if (tccMapper.isExistCancel(transId) > 0) {
            throw new TccTryException("AccountInfoService invoke addAccountBalance 出现悬挂现象，transId: " + transId);
        }
        //4 增加金额
        baseMapper.addBalance(cardNumber, money);
        //5 更新幂等状态
        tccMapper.addTry(transId);
    }


    /**
     * @Description: tcc confirm操作
     * @Author:rosh
     * @date: 2020-10-13
     * @param cardNumber 卡号
     * @param money 金额
     */
    public void confirm(String cardNumber, Integer money) {
        //获取全局事务ID
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.warn("AccountInfoService invoke confirm , transId : {}", transId);
    }

    /**
     * @Description: tcc cancel操作
     * @Author:rosh
     * @date: 2020-10-13
     * @param cardNumber 卡号
     * @param money 金额
     */
    @Transactional
    public void cancel(String cardNumber, Integer money) {
        //1 获取全局事务ID
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.warn("AccountInfoService invoke cancel , transId : {}", transId);
        // 2 幂等
        if (tccMapper.isExistCancel(transId) > 0) {
            return;
        }
        //3 防止空回滚,确保已经try已经执行
        if (tccMapper.isExistTry(transId) <= 0) {
            return;
        }
        //4 减少
        baseMapper.subtractBalance(cardNumber, money);
        //5 更新幂等
        tccMapper.addCancel(transId);
    }


}