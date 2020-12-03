题目1： 按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率

思路如下：试过多种方式，最终感觉数据库内存表的方式效率相对高点。

步骤一 	创建订单表(上期作业设计的表)
步骤二 	创建订单内存表
步骤三 	创建存储过程
步骤四	调用存储过程，插入订单内存表
步骤五	从订单内存表，插入到订单表，（测试过15秒以内）

（详见: 插入100万订单模拟数据.md）



题目2：动态切换数据源版本1.0

思路如下：

（1）整体思路
Spring boot提供了AbstractRoutingDataSource 根据用户定义的规则选择当前的数据源。
它的抽象方法 determineCurrentLookupKey() 决定使用哪个数据源。
AbstractRoutingDataSource的多数据源动态切换的核心逻辑是：在程序运行时，把数据源数据源通过 AbstractRoutingDataSource 动态织入到程序中，灵活的进行数据源切换。

（2）实现逻辑

步骤1 定义DynamicDataSource类继承抽象类AbstractRoutingDataSource，并实现了determineCurrentLookupKey()方法。

步骤2 把配置的多个数据源会放在AbstractRoutingDataSource的 targetDataSources和defaultTargetDataSource中，
然后通过afterPropertiesSet()方法将数据源分别进行复制到resolvedDataSources和resolvedDefaultDataSource中。

步骤3 调用AbstractRoutingDataSource的getConnection()的方法的时候，先调用determineTargetDataSource()方法返回DataSource在进行getConnection()。

（3）具体代码实现详见：spring-boot-dynamic-data-source



题目3： shardingsphere 数据库框架版本2.0

（备注：单库分表和多库分表都支持，当前可以运行为多库多表方式，具体代码实现详见：shardingjdbcdemo）

思路如下：

《1》针对单库分表的方式：

1) 创建数据库goods_db；

2) 在goods_db中创建表goods_1、goods_2；

3) 约定规则，如果添加商品id是偶数把数据加入goods_1，如果是偶数把数据加入goods_2；

4) application.properties配置Sharding-JDBC

\# 配置Sharding-JDBC的分片策略

\# 配置数据源，给数据源起名g1,g2...此处可配置多数据源

spring.shardingsphere.datasource.names=g1

\# 配置允许后面的Bean覆盖前面名称重复的Bean

spring.main.allow-bean-definition-overriding=true

\# 配置数据源具体内容

\# 由于上面配置数据源只有g1因此下面只配置g1.type,g1.driver-class-name,g1.url,g1.username,g1.password

spring.shardingsphere.datasource.g1.type=com.alibaba.druid.pool.DruidDataSource

spring.shardingsphere.datasource.g1.driver-class-name=com.mysql.cj.jdbc.Driver

spring.shardingsphere.datasource.g1.url=jdbc:mysql://localhost:3306/goods_db?characterEncoding=utf-8&useUnicode=true&useSSL=false&serverTimezone=UTC

spring.shardingsphere.datasource.g1.username=root

spring.shardingsphere.datasource.g1.password=123456

\# 配置表的分布，表的策略

spring.shardingsphere.sharding.tables.goods.actual-data-nodes=g1.goods_$->{1..2}

\# 指定goods表 主键gid 生成策略为 SNOWFLAKE

spring.shardingsphere.sharding.tables.goods.key-generator.column=gid

spring.shardingsphere.sharding.tables.goods.key-generator.type=SNOWFLAKE

\# 指定分片策略 约定gid值是偶数添加到goods_1表，如果gid是奇数添加到goods_2表

spring.shardingsphere.sharding.tables.goods.table-strategy.inline.sharding-column=gid

spring.shardingsphere.sharding.tables.goods.table-strategy.inline.algorithm-expression=goods_$->{gid % 2 + 1}

\# 打开sql输出日志

spring.shardingsphere.props.sql.show=true

5)测试代码详见：ShardingjdbcdemoApplicationTests void addGoods() (测试用例gid为奇数，在goods_2中插入了数据，goods_1中无)



《2》针对多库多表的玩法：

1) 创建两个数据库goods_db_1和goods_db_2，每个数据库中均包含两个表goods_1和goods_2，goods_1和goods_2和上述分表的结构一样。

2) 配置application.properties

\# 配置Sharding-JDBC的分片策略

\# 配置数据源，给数据源起名g1,g2...此处可配置多数据源

spring.shardingsphere.datasource.names=g1,g2

\# 配置允许一个实体类映射多张表

spring.main.allow-bean-definition-overriding=true

\# 配置数据源具体内容

\# g1配置g1.type,g1.driver-class-name,g1.url,g1.username,g1.password

spring.shardingsphere.datasource.g1.type=com.alibaba.druid.pool.DruidDataSource

spring.shardingsphere.datasource.g1.driver-class-name=com.mysql.cj.jdbc.Driver

spring.shardingsphere.datasource.g1.url=jdbc:mysql://localhost:3306/goods_db_1?characterEncoding=utf-8&useUnicode=true&useSSL=false&serverTimezone=UTC

spring.shardingsphere.datasource.g1.username=root

spring.shardingsphere.datasource.g1.password=123456

\# 配置数据源具体内容

\# g2配置g2.type,g2.driver-class-name,g2.url,g2.username,g2.password

spring.shardingsphere.datasource.g2.type=com.alibaba.druid.pool.DruidDataSource

spring.shardingsphere.datasource.g2.driver-class-name=com.mysql.cj.jdbc.Driver

spring.shardingsphere.datasource.g2.url=jdbc:mysql://localhost:3306/goods_db_2?characterEncoding=utf-8&useUnicode=true&useSSL=false&serverTimezone=UTC

spring.shardingsphere.datasource.g2.username=root

spring.shardingsphere.datasource.g2.password=123456

\# 配置数据库的分布，表的分布

\# m1:goods_1 goods_2; m2:goods_1,goods_2;

spring.shardingsphere.sharding.tables.goods.actual-data-nodes=g$->{1..2}.goods_$->{1..2}

\# 指定goods表 主键gid 生成策略为 SNOWFLAKE

spring.shardingsphere.sharding.tables.goods.key-generator.column=gid

spring.shardingsphere.sharding.tables.goods.key-generator.type=SNOWFLAKE

\# 指定数据库分片策略 约定user_id值是偶数添加到goods_db_1中，奇数添加到goods_db_2中

spring.shardingsphere.sharding.tables.goods.database-strategy.inline.sharding-column=user_id

spring.shardingsphere.sharding.tables.goods.database-strategy.inline.algorithm-expression=g$->{user_id % 2 + 1}

\# 指定表分片策略 约定gid值是偶数添加到goods_1表，如果gid是奇数添加到goods_2表

spring.shardingsphere.sharding.tables.goods.table-strategy.inline.sharding-column=gid

spring.shardingsphere.sharding.tables.goods.table-strategy.inline.algorithm-expression=goods_$->{gid % 2 + 1}

\# 打开sql输出日志

spring.shardingsphere.props.sql.show=true

3) 编写测试方法 ShardingjdbcdemoApplicationTests void addGoods02() (测试用例：由于我们的user_id设置为100L，为偶数，根据我们的初衷，偶数存入goods_db_1中)
