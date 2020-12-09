package com.bank1.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TccMapper {

    @Select("select count(1) from local_try_log where tx_no = #{txNo}")
    int isExistTry(String transId);

    @Select("select count(1) from local_confirm_log where tx_no = #{txNo}")
    int isExistConfirm(String transId);

    @Select("select count(1) from local_cancel_log where tx_no = #{txNo}")
    int isExistCancel(String transId);

    @Insert("insert into local_try_log values(#{txNo},now());")
    int addTry(String localTradeNo);

    @Insert("insert into local_cancel_log values(#{txNo},now());")
    int addCancel(String localTradeNo);


}
