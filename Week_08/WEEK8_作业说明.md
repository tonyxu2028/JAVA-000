1、（选做）分析前面作业设计的表，是否可以做垂直拆分。

思路说明：（设计详见: 电商系统(极简版1.0).pdm）

1) 之前在设计数据库的时候，采取了集中设计分域处理的方式，划分为订单域，商品域，计费域，会员域，通用域。

2) 从架构角度上来说，已经进行了服务级别的拆分，所以数据库是可以进行垂直拆分的。

3) 每个域或者服务的定位如下：

订单域：针对所有订单交易的处理，支持订单处理，分拆订单等功能。

商品域：针对所有商品的信息处理，包含了商品管理，商品折扣规则管理等功能。

计费域：针对统一计费处理，包含计费规则组管理，实际算费处理等功能。

会员域： 针对电商所有会员信息进行管理。

通用域：针对基础通用功能，目前仅提供的快照功能。

总体说明：

综上所述，目前的设计可以支持一个非常初级的电商平台，而且可以在任何一个服务领域进行扩展，这个根据后续业务的扩展而定。而针对目前的每个服务领域是否需要进行更加深入的技术设计手段，由各个服务领域的性能诉求&自身特点而定，比如会员为高QPS类型的服务（读写分离--->缓存技术--->分库的演进策略），而针对订单这种高TPS的服务（应对高IO的服务设备---》历史数据归档（保证表里为最新一个周期的交易数据，比如3个月）---》分库的演进策略），以此类推，不同特性不同对待。

当然策略也必须根据实际业务情况的场景前提，如果一开始就有业务量很大诉求，那么也不排除优先采用分库分表，分片缓存等技术的可能。

业务功能的扩展，如果已经有对应域的存在，在该域中追加表设计对应服务追加功能，如果没有对应的增加相关域&服务进行扩展，平滑的追加后续功能。





2、（必做）设计对前面的订单表数据进行水平分库分表，拆分2个库，每个库16张表。
并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。

思路说明：（代码详见: shardingjdbcdemo）

1）配置g1,g2两个对应数据库

```
spring.shardingsphere.datasource.names=g1,g2
```

2）每个数据库建立goods的8个表

```
spring.shardingsphere.sharding.tables.goods.actual-data-nodes=g$->{1..2}.goods_$->{1..8}
```

3）指定goods表 主键gid 生成策略为 SNOWFLAKE

```
spring.shardingsphere.sharding.tables.goods.key-generator.column=gidspring.shardingsphere.sharding.tables.goods.key-generator.type=SNOWFLAKE
```

4）分库策略，按照user_id进行分库，user_id%2+1的策略方式

```
spring.shardingsphere.sharding.tables.goods.database-strategy.inline.sharding-column=user_id
```

```
spring.shardingsphere.sharding.tables.goods.database-strategy.inline.algorithm-expression=g$->{user_id % 2 + 1}
```

5）分表策略，按照gid进行分表，gid % 8 + 1的策略方式

```
spring.shardingsphere.sharding.tables.goods.table-strategy.inline.sharding-column=gid
```

```
spring.shardingsphere.sharding.tables.goods.table-strategy.inline.algorithm-expression=goods_$->{gid % 8 + 1}
```





3、（必做）基于hmily TCC或ShardingSphere的Atomikos XA实现一个简单的分布式
事务应用demo（二选一），提交到github。

思路说明：（代码详见: springcloud-tcc）

1)  本实例通过Hmily实现TCC分布式事务，模拟两个账户的转账交易过程。两个账户分别在不同的银行(Tonyxu在bank1、Leoxu在bank2)，bank1、bank2是两个微服务。交易过程是，Tonyxu给Leoxu转账指定金额。上述交易步骤，要么一起成功，要么一起失败，必须是一个整体性的事务。 

2)  数据库准备：

 (1) hmily数据库 

```
CREATE DATABASE `hmily` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
```

 (2) 创建bank1库，并导入以下表结构和数据(包含Tonyxu账户) 

```
CREATE DATABASE `bank1` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;CREATE TABLE `account_info` (  `id` int(11) NOT NULL AUTO_INCREMENT,  `name` varchar(255) DEFAULT NULL COMMENT '户主姓名',  `card_number` int(11) DEFAULT NULL COMMENT '银行卡号',  `password` varchar(255) DEFAULT NULL COMMENT '帐户密码',  `balance` int(11) DEFAULT NULL COMMENT '帐户余额',  PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;insert into account_info(name,card_number,password,balance) values('Tonyxu',1,'123456',10000)
```

 (3) 创建bank2库，并导入以下表结构和数据(包含Leoxu账户) 

```
CREATE DATABASE `bank2` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;CREATE TABLE `account_info` (  `id` int(11) NOT NULL AUTO_INCREMENT,  `name` varchar(255) DEFAULT NULL COMMENT '户主姓名',  `card_number` int(11) DEFAULT NULL COMMENT '银行卡号',  `password` varchar(255) DEFAULT NULL COMMENT '帐户密码',  `balance` int(11) DEFAULT NULL COMMENT '帐户余额',  PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;insert into account_info(name,card_number,password,balance) values('Leoxu',1,'123456',0)
```

 (4) bank1、bank2 创建共同表 

```
 CREATE TABLE `local_try_log` (`tx_no` varchar(64) NOT NULL COMMENT '事务id',`create_time` datetime DEFAULT NULL,PRIMARY KEY (`tx_no`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;CREATE TABLE `local_confirm_log`( `tx_no` varchar(64) NOT NULL COMMENT '事务id', `create_time` datetime DEFAULT NULL)ENGINE=InnoDB DEFAULT CHARSET=utf8;CREATE TABLE `local_cancel_log`(`tx_no` varchar(64) NOT NULL COMMENT '事务id',`create_time` datetime DEFAULT NULL,PRIMARY KEY (`tx_no`))ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

3) SpringCloud 集成Hmily

```
<dependency>    <groupId>org.dromara</groupId>    <artifactId>hmily-spring-boot-starter-springcloud</artifactId>    <version>2.0.6-RELEASE</version></dependency>
```

4) 使用Hmily方法

详见：springcloud-tcc中的 

bank1（AccountInfoService）

bank2 (AccountInfoService)

5) 测试

Tonyxu向Leoxu转账成功，bank1，bank2数据库相关记录全部修改成功commit。

Leoxu向Tonyxu转张失败，bank1，bank2数据库相关记录全部回滚。