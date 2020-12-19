package com.bank1.service;

import com.bank1.client.Bank2Client;
import com.bank1.excepiton.FeignClientException;
import com.bank1.excepiton.TccTryException;
import com.bank1.mapper.AccountInfoMapper;
import com.bank1.mapper.TccMapper;
import com.bank1.po.AccountInfoPo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tony.xu
 */

@Service
@Slf4j
public class AccountInfoService extends ServiceImpl<AccountInfoMapper, AccountInfoPo> {


    @Autowired
    private TccMapper tccMapper;

    @Autowired
    private Bank2Client bank2Client;


    /**
     * @Description:
     * tcc try操作,@Hmily注解是开启tcc try方法，在注解中指定confirm方法和cancel方法，并且参数一样
     * @Author:rosh
     * @date: 2020-12-9
     * @param cardNumber 卡号
     * @param money     金额
     */
    @Transactional
    @Hmily(confirmMethod = "confirm", cancelMethod = "cancel")
    public void updateAccountBalance(String cardNumber, Integer money) {
        //1 获取全局事务ID
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.warn("AccountInfoService invoke updateAccountBalance , transId : {}", transId);
        //2 幂等校验
        if (tccMapper.isExistTry(transId) > 0) {
            return;
        }
        //3 悬挂处理，保证try在cancel执行之前
        if (tccMapper.isExistCancel(transId) > 0) {
            throw new TccTryException("AccountInfoService invoke updateAccountBalance 出现悬挂现象，" +
                    "transId: " + transId);
        }
        //4 扣减金额
        if (baseMapper.subtractBalance(cardNumber, money) <= 0) {
            //扣减失败
            throw new TccTryException("bank1 扣减金额失败,transiId: " + transId);
        }
        //5 更新幂等状态
        tccMapper.addTry(transId);
        Boolean rs = bank2Client.transfer(cardNumber, money);
        log.info("bank2Client远程调用结果:{}", rs);
        //6 转账
        if (Boolean.FALSE.equals(rs)) {
            throw new FeignClientException("AccountInfoService invoke updateAccountBalance " +
                    "failed cause bank2Client invoke transfer failed");
        }

    }

    /**
     * @Description: tcc confirm
     * @Author:rosh
     * @date: 2020-12-9
     * @param cardNumber 卡号
     * @param money 金额
     */
    public void confirm(String cardNumber, Integer money) {
        // Do nothing
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.warn("AccountInfoService invoke confirm , transId : {}", transId);
    }

    /**
     * @Description: tcc cancel
     * @Author:rosh
     * @date: 2020-12-9
     * @param cardNumber 卡号
     * @param money 金额
     */
    @Transactional
    public void cancel(String cardNumber, Integer money) {
        // 1 全局事务ID
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
        //4 增加
        baseMapper.addBalance(cardNumber, money);
        //5 更新幂等
        tccMapper.addCancel(transId);
    }

}