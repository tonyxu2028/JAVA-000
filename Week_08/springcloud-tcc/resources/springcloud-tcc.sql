############################### hmily ###############################
CREATE DATABASE `hmily` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;



############################### bank1 ###############################
CREATE DATABASE `bank1` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `account_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '户主姓名',
  `card_number` int(11) DEFAULT NULL COMMENT '银行卡号',
  `password` varchar(255) DEFAULT NULL COMMENT '帐户密码',
  `balance` int(11) DEFAULT NULL COMMENT '帐户余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

insert into account_info(name,card_number,password,balance) values('Tonyxu',1,'123456',10000)



############################### bank2 ###############################
CREATE DATABASE `bank2` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `account_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '户主姓名',
  `card_number` int(11) DEFAULT NULL COMMENT '银行卡号',
  `password` varchar(255) DEFAULT NULL COMMENT '帐户密码',
  `balance` int(11) DEFAULT NULL COMMENT '帐户余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


insert into account_info(name,card_number,password,balance) values('Leoxu',1,'123456',0)



############################### bank1、bank2 创建共同表 ##################
 CREATE TABLE `local_try_log` (
`tx_no` varchar(64) NOT NULL COMMENT '事务id',
`create_time` datetime DEFAULT NULL,
PRIMARY KEY (`tx_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `local_confirm_log`(
 `tx_no` varchar(64) NOT NULL COMMENT '事务id',
 `create_time` datetime DEFAULT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `local_cancel_log`(
`tx_no` varchar(64) NOT NULL COMMENT '事务id',
`create_time` datetime DEFAULT NULL,
PRIMARY KEY (`tx_no`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


