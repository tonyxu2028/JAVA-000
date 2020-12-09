package com.bank2.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * 
 */
@Data
@TableName("account_info")
public class AccountInfoPo  {

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 户主姓名
	 */
	private String name;
	/**
	 * 银行卡号
	 */
	private Integer cardNumber;
	/**
	 * 帐户密码
	 */
	private String password;
	/**
	 * 帐户余额
	 */
	private Integer balance;

}
