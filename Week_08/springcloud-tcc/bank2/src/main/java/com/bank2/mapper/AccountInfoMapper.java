package com.bank2.mapper;

import com.bank2.po.AccountInfoPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 *
 * @author Tony.xu
 */
@Mapper
public interface AccountInfoMapper extends BaseMapper<AccountInfoPo> {


    @Update("update account_info set balance=balance + #{money} where card_number=#{cardNumber} ")
    int addBalance(@Param("cardNumber") String cardNumber, @Param("money") Integer money);

    @Update("update account_info set balance=balance - #{money} where balance>#{money} and card_number=#{cardNumber} ")
    int subtractBalance(@Param("cardNumber") String cardNumber, @Param("money") Integer money);

}
