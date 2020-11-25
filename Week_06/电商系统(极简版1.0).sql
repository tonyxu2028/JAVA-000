/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/11/24 16:25:49                          */
/*==============================================================*/

alter table TB_ORDER
   drop primary key;

drop table if exists TB_ORDER;

alter table TB_ORDER_COMMODITY
   drop primary key;

drop table if exists TB_ORDER_COMMODITY;



alter table TB_COMMODITY
   drop primary key;

drop table if exists TB_COMMODITY;

alter table TB_COMMODITY_DISCOUNT
   drop primary key;

drop table if exists TB_COMMODITY_DISCOUNT;



alter table TB_GROUP_RATE
   drop primary key;

drop table if exists TB_GROUP_RATE;

alter table TB_RATE_RULE
   drop primary key;

drop table if exists TB_RATE_RULE;

alter table TB_BILLING_FLOW
   drop primary key;

drop table if exists TB_BILLING_FLOW;



alter table TB_MEMBER
   drop primary key;

drop table if exists TB_MEMBER;



alter table TB_SNAPSHOT
   drop primary key;

drop table if exists TB_SNAPSHOT;



/*==============================================================*/
/* Table: TB_ORDER                                              */
/*==============================================================*/
create table TB_ORDER
(
   ORDER_ID             BIGINT not null,
   ORDER_CODE           VARCHAR(50) not null,
   ORDER_NAME           VARCHAR(100) not null,
   ORDER_TYPE           CHAR(2) not null comment '10-线上支付
            20-线下支付',
   ORDER_MESSAGE        VARCHAR(200) not null,
   ORDER_PARENT_CODE    VARCHAR(50) not null,
   ORDER_SPLIT_STATUS   CHAR(2) not null comment '10-未拆分
            20-已拆分',
   ORDER_STATUS         CHAR(2) not null comment '10-处理中
            20-完成
            30-异常
            40-取消',
   ORDER_NODE_FLAG      CHAR(2) not null comment '10-非叶子节点
            20-叶子节点',
   MEMBER_CODE          VARCHAR(50) not null,
   STATUS               CHAR(2) not null comment '10-有效
            20-无效',
   CREATE_TIME          timestamp not null,
   UPDATE_TIME          timestamp not null,
   DATA_VERSION         int(11) not null
);

alter table TB_ORDER
   add primary key (ORDER_ID);

/*==============================================================*/
/* Table: TB_ORDER_COMMODITY                                    */
/*==============================================================*/
create table TB_ORDER_COMMODITY
(
   ORDER_COMMODITY_ID   BIGINT not null,
   ORDER_COMMODITY_CODE VARCHAR(50) not null,
   ORDER_CODE           VARCHAR(50) not null,
   COMMODITY_CODE       VARCHAR(50) not null,
   COMMODITY_NUMBER     INTEGER not null,
   COMMODITY_AMOUNT_SUM DECIMAL(17,6) not null,
   STATUS               CHAR(2) not null comment '10-有效
            20-无效',
   CREATE_TIME          timestamp not null,
   UPDATE_TIME          timestamp not null,
   DATA_VERSION         int(11) not null
);

alter table TB_ORDER_COMMODITY
   add primary key (ORDER_COMMODITY_ID);



/*==============================================================*/
/* Table: TB_COMMODITY                                          */
/*==============================================================*/
create table TB_COMMODITY
(
   COMMODITY_ID         BIGINT not null,
   COMMODITY_CODE       VARCHAR(50) not null,
   COMMODITY_NAME       VARCHAR(100) not null,
   COMMODITY_AMOUNT     DECIMAL(17,6) not null,
   STATUS               CHAR(2) not null comment '10-有效
            20-无效',
   CREATE_TIME          timestamp not null,
   UPDATE_TIME          timestamp not null,
   DATA_VERSION         int(11) not null
);

alter table TB_COMMODITY
   add primary key (COMMODITY_ID);

/*==============================================================*/
/* Table: TB_COMMODITY_DISCOUNT                                 */
/*==============================================================*/
create table TB_COMMODITY_DISCOUNT
(
   COMMODITY_DISCOUNT_ID BIGINT not null,
   COMMODITY_DISCOUNT_CODE VARCHAR(50) not null,
   COMMODITY_CODE       VARCHAR(50) not null,
   GROUP_RATE_CODE      VARCHAR(50) not null,
   STATUS               CHAR(2) not null comment '10-有效
            20-无效',
   CREATE_TIME          timestamp not null,
   UPDATE_TIME          timestamp not null,
   DATA_VERSION         int(11) not null
);

alter table TB_COMMODITY_DISCOUNT
   add primary key (COMMODITY_DISCOUNT_ID);



/*==============================================================*/
/* Table: TB_GROUP_RATE                                         */
/*==============================================================*/
create table TB_GROUP_RATE
(
   GROUP_RATE_ID        BIGINT not null,
   GROUP_RATE_CODE      VARCHAR(50) not null,
   GROUP_RATE_NAME      VARCHAR(100) not null,
   STATUS               CHAR(2) not null comment '10-有效
            20-无效',
   CREATE_TIME          timestamp not null,
   UPDATE_TIME          timestamp not null,
   DATA_VERSION         int(11) not null
);

alter table TB_GROUP_RATE
   add primary key (GROUP_RATE_ID);

/*==============================================================*/
/* Table: TB_RATE_RULE                                          */
/*==============================================================*/
create table TB_RATE_RULE
(
   RATE_RULE_ID         BIGINT not null,
   RATE_RULE_CODE       VARCHAR(50) not null,
   RATE_RULE_NAME       VARCHAR(100) not null,
   RATE_RULE_TYPE       CHAR(2) not null comment '10-折扣金额
            20-折扣率',
   GROUP_RATE_CODE      VARCHAR(50) not null,
   STEP_LINE_AMOUNT     DECIMAL(17,6) not null,
   STEP_DOWN_AMOUNT     DECIMAL(17,6) not null,
   DISCOUNT_AMOUNT      DECIMAL(17,6),
   DISCOUNT_RATE        NUMERIC(8,4),
   STATUS               CHAR(2) not null comment '10-有效
            20-无效',
   CREATE_TIME          timestamp not null,
   UPDATE_TIME          timestamp not null,
   DATA_VERSION         int(11) not null
);

alter table TB_RATE_RULE
   add primary key (RATE_RULE_ID);

/*==============================================================*/
/* Table: TB_BILLING_FLOW                                       */
/*==============================================================*/
create table TB_BILLING_FLOW
(
   BILLING_FLOW_ID      BIGINT not null,
   BILLING_FLOW_CODE    VARCHAR(50) not null,
   ORDER_CODE           VARCHAR(50) not null,
   ORDER_COMMODITY_CODE VARCHAR(50) not null,
   COMMODITY_CODE       VARCHAR(50) not null,
   GROUP_RATE_CODE      VARCHAR(50) not null,
   SNAPSHOT_CODE        VARCHAR(50) not null,
   STATUS               CHAR(2) not null comment '10-有效
            20-无效',
   CREATE_TIME          timestamp not null,
   UPDATE_TIME          timestamp not null,
   DATA_VERSION         int(11) not null
);

alter table TB_BILLING_FLOW
   add primary key (BILLING_FLOW_ID);



/*==============================================================*/
/* Table: TB_MEMBER                                             */
/*==============================================================*/
create table TB_MEMBER
(
   MEMBER_ID            BIGINT not null,
   MEMBER_CODE          VARCHAR(50) not null,
   MEMBER_NAME          VARCHAR(100) not null,
   MEMBER_TYPE          CHAR(2) not null comment '10-私人
            20-企业',
   MEMBER_IDENTITY_CODE VARCHAR(50),
   MEMBER_ORGANIZATION_NUMBER VARCHAR(50),
   STATUS               CHAR(2) not null comment '10-有效
            20-无效',
   CREATE_TIME          timestamp not null,
   UPDATE_TIME          timestamp not null,
   DATA_VERSION         int(11) not null
);

alter table TB_MEMBER
   add primary key (MEMBER_ID);



/*==============================================================*/
/* Table: TB_SNAPSHOT                                           */
/*==============================================================*/
create table TB_SNAPSHOT
(
   SNAPSHOT_ID          BIGINT not null,
   SNAPSHOT_CODE        VARCHAR(50) not null,
   SNAPSHOT_NAME        VARCHAR(100) not null,
   SNAPSHOT_TYPE        CHAR(4) not null comment '1001-计费类型',
   SNAPSHOT_TYPE_CONTENT BLOB(2M) not null,
   STATUS               CHAR(2) not null comment '10-有效
            20-无效',
   CREATE_TIME          timestamp not null,
   UPDATE_TIME          timestamp not null,
   DATA_VERSION         int(11) not null
);

alter table TB_SNAPSHOT
   add primary key (SNAPSHOT_ID);

