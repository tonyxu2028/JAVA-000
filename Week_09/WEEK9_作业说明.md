1（必做）改造自定义RPC的程序，提交到github：
1）尝试将服务端写死查找接口实现类变成泛型和反射
2）尝试将客户端动态代理改成AOP
3）尝试使用Netty+HTTP作为client端传输方式

代码详见示例代码：simple-rpc-framework

由于本周在忙太太怀孕事宜，故没有时间将示例代码上的东西在当前的RPC程序上进行修订（已经通读过对应代码，能够满足上述1，2，3的要求，不好意思）

思路：跟进上述代码示例可以改造自定义RPC满足上述三点要求。

示例代码说明：

```
RPC框架对外提供的服务接口  RpcAccessPoint
public interface RpcAccessPoint extends Closeable{
    /**
     * 客户端获取远程服务的引用
     * @param uri 远程服务地址
     * @param serviceClass 服务的接口类的Class
     * @param <T> 服务接口的类型
     * @return 远程服务引用
     */
    <T> T getRemoteService(URI uri, Class<T> serviceClass);

    /**
     * 服务端注册服务的实现实例
     * @param service 实现实例
     * @param serviceClass 服务的接口类的Class
     * @param <T> 服务接口的类型
     * @return 服务地址
     */
    <T> URI addServiceProvider(T service, Class<T> serviceClass);

    /**
     * 获取注册中心的引用
     * @param nameServiceUri 注册中心URI
     * @return 注册中心引用
     */
    default NameService getNameService(URI nameServiceUri) {
        Collection<NameService> nameServices = ServiceSupport.loadAll(NameService.class);
        for (NameService nameService : nameServices) {
            if(nameService.supportedSchemes().contains(nameServiceUri.getScheme())) {
                nameService.connect(nameServiceUri);
                return nameService;
            }
        }
        return null;
    }

    /**
     * 服务端启动RPC框架，监听接口，开始提供远程服务。
     * @return 服务实例，用于程序停止的时候安全关闭服务。
     */
    Closeable startServer() throws Exception;
}

```

```
RPC框架对外提供的服务接口Netty的实现类 NettyRpcAccessPoint
public class NettyRpcAccessPoint implements RpcAccessPoint {
    private final String host = "localhost";
    private final int port = 9999;
    private final URI uri = URI.create("rpc://" + host + ":" + port);
    private TransportServer server = null;
    private TransportClient client = ServiceSupport.load(TransportClient.class);
    private final Map<URI, Transport> clientMap = new ConcurrentHashMap<>();
    private final StubFactory stubFactory = ServiceSupport.load(StubFactory.class);
    private final ServiceProviderRegistry serviceProviderRegistry = ServiceSupport.load(ServiceProviderRegistry.class);

    @Override
    public <T> T getRemoteService(URI uri, Class<T> serviceClass) {
        Transport transport = clientMap.computeIfAbsent(uri, this::createTransport);
        return stubFactory.createStub(transport, serviceClass);
    }

    private Transport createTransport(URI uri) {
        try {
            return client.createTransport(new InetSocketAddress(uri.getHost(), uri.getPort()),30000L);
        } catch (InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public synchronized <T> URI addServiceProvider(T service, Class<T> serviceClass) {
        serviceProviderRegistry.addServiceProvider(serviceClass, service);
        return uri;
    }

    @Override
    public synchronized Closeable startServer() throws Exception {
        if (null == server) {
            server = ServiceSupport.load(TransportServer.class);
            server.start(RequestHandlerRegistry.getInstance(), port);

        }
        return () -> {
            if(null != server) {
                server.stop();
            }
        };
    }

    @Override
    public void close() {
        if(null != server) {
            server.stop();
        }
        client.close();
    }
}
```



2（必做）结合dubbo+hmily，实现一个TCC外汇交易处理，代码提交到github：用户A的美元账户和人民币账户都在A库，使用1美元兑换7人民币；用户B的美元账户和人民币账户都在B库，使用7人民币兑换1美元；设计账户表，冻结资产表，实现上述两个本地事务的分布式事务。

本周思路说明：

在上周作业 springcloud-tcc上进行了修订，在对应的账户表中加入了币种和冻结金额。

 try场景说明：

​	用户A的账户表的美元币种户头，假设账户余额原为10美元&冻结金额为0，1美元为冻结金额，9美元为当前账户余额；

​    用户B的账户表的人民币种户头，假设账户余额原为77人民币&冻结金额为0，7人民币为冻结金额，70人民币为当前账 户余额

confirm场景说明：

​	用户A的账户表的美元币种户头，冻结金额清零，美元账户余额为9美元，人民币账户余额追加7人民币。

​	用户B的账户表的人民币种户头，冻结金额清零，人民币账户余额为70人民币，美元账户追加1美元。

cancel场景说明：

​     用户A的账户表的美元币种户头，冻结金额清零，美元账户余额恢复为10美元

​	用户B的账户表的人民币种户头，冻结金额清零，人民币账户余额恢复为77人民币。

上周思路说明如下：（代码详见: springcloud-tcc）

（1)  本实例通过Hmily实现TCC分布式事务，模拟两个账户的转账交易过程。两个账户分别在不同的银行(Tonyxu在bank1、Leoxu在bank2)，bank1、bank2是两个微服务。交易过程是，Tonyxu给Leoxu转账指定金额。上述交易步骤，要么一起成功，要么一起失败，必须是一个整体性的事务。 

（2)  数据库准备：

 (2.1) hmily数据库 

```
CREATE DATABASE `hmily` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
```

 (2.2) 创建bank1库，并导入以下表结构和数据(包含Tonyxu账户) 

```
CREATE DATABASE `bank1` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;CREATE TABLE `account_info` (  `id` int(11) NOT NULL AUTO_INCREMENT,  `name` varchar(255) DEFAULT NULL COMMENT '户主姓名',  `card_number` int(11) DEFAULT NULL COMMENT '银行卡号',  `password` varchar(255) DEFAULT NULL COMMENT '帐户密码',  `balance` int(11) DEFAULT NULL COMMENT '帐户余额',  PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;insert into account_info(name,card_number,password,balance) values('Tonyxu',1,'123456',10000)
```

 (2.3) 创建bank2库，并导入以下表结构和数据(包含Leoxu账户) 

```
CREATE DATABASE `bank2` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;CREATE TABLE `account_info` (  `id` int(11) NOT NULL AUTO_INCREMENT,  `name` varchar(255) DEFAULT NULL COMMENT '户主姓名',  `card_number` int(11) DEFAULT NULL COMMENT '银行卡号',  `password` varchar(255) DEFAULT NULL COMMENT '帐户密码',  `balance` int(11) DEFAULT NULL COMMENT '帐户余额',  PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;insert into account_info(name,card_number,password,balance) values('Leoxu',1,'123456',0)
```

 (2.4) bank1、bank2 创建共同表 

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