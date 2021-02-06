​							***\*JAVA训练营毕业总结\****

***\*毕业项目：\****

<1>对技术的关键点的思考和经验认识：

（备注：针对以下知识点JVM,NIO,并发编程,Spring和ORM等框架,MySql数据库和SQL,分库分表,RPC和微服务,分布式缓存，分布式消息队列）

（1）JVM知识点

总结如下：

加强了对字节码的了解，更加明白了源码和字节码之间的关系，以及字节码的定义。

对java类加载器类的生命周期过程（加载—》验证—》准备—》	解析—》初始化—》使用—》卸载）有了一定了解。明白了类的加载时机，以及三类加载器（启动类加载器，扩展加载器，应用加载器），加载器的特点双亲委托，负责依赖，缓存加载，以及如何使用ClassLoader加载。

对JVM内存模型增进了了解，明确了线程栈和堆内存，以及JVM内存整体结构，并且了解JVM的启动参数和如何配置。

学习了JVM相关工具了解如何通过工具对JVM进行观测和调优。

深入了解了JVM的GC，针对串行GC,并行GC,CMS GC,G1 GC的原理&配置&使用场景，以及对于的优缺点和之后的如何选型均有深入理解，同时也更加清晰了GC的演进路径和GC的日志解读与分析&JVM线程堆栈数据分析。

最后了解JVM面试汇总。

 



知识图谱如下：

![img](file:///C:\Users\tonyxu\AppData\Local\Temp\ksohtml20208\wps1.png) 



（2）NIO

总结如下：

了解Socket通信模型（从建立服务端倾听的Socket—》创建连接Socket向服务端发送请求—》等待并接受连接请求—》接收请求后创建连接Socket—》开始通信（流化）—》结束通信关闭Socket）

针对五种IO模型（阻塞IO模型，非阻塞IO模型，I/O复用模型，信号驱动的I/O模型，异步I/O模型）有了初步的了解。

针对网络开发框架Netty有了一定的了解，主要基于异步，事件驱动，基于NIO,其适用于服务端，客户端，协议为TCP/UDP。以及了解了Netty是如何实现高性能，并且实现了API网关。明白了从事件处理机制到Reactor模型（Reactor模型是事件驱动的，有一个或者多个并发输入源，有一个Service Handler和多个EventHandlers，并且ServiceHandle会同步的将输入请求多路复用的分给相应的Event Handler）

了解了Netty的运行原理和关键对象			Bootstrap,EventLoopGroup,EventLoop,

SocketChannel,ChannelInitializer,ChannelPipline,

ChannelHandler。以及Netty的粘包和拆包。



知识图谱如下：

![img](file:///C:\Users\tonyxu\AppData\Local\Temp\ksohtml20208\wps2.png) 

 



（3）并发编程

总结如下：

了解Thread的状态改变操作

Thread.sleep(long millis);

Thread.yield();

thread.join()/thread.join(long millis);

obj.wait();

obj.notify();

Thread的中断和异常处理，以及多线程的安全问题，多线程的并发问题类似于多事务的并发问题，关键点在于lock的应用。

并发相关的性质：

happens-before原则（先行发生原则）

1）程序次序规则：一个线程内，按照代码先后顺序。

2）锁定规则：一个unlock操作先行发生于后面对同一个锁的lock操作。

3）Volatile变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作。

4）传递规则：如果操作A先行发生于操作B，而操作B又先行发生于操作C，则可以得出A先于C。

5）线程启动规则：Thread对象的start()方法先行发生于此线程的每个一个动作。

6）线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生。

7）线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束，Thread.isAlive()的返回值手段检测到线程已经终止执行。

8）对象终结规则：一个对象的初始化完成先行发生于他的finalize()方法的开始。

线程池：

1）Excutor : 执行者 - 顶层接口

2）ExcutorService : 接口API

3）ThreadFactory : 线程工厂

4）Excutors : 工具类

ReadWriteLock管理一组锁，一个读锁，一个写锁。

读锁可以在没有写锁的时候被多个线程同时持有，但是写锁是独占的。所有读写锁的实现必须确保写操作对读操作的内存影响。每次只能有个一个写线程，但是同时可以有多个线程并发的读数据，所以在ReadWriteLock的情况下适用于读多写少的并发情况。

针对集合的并发安全总结:

ArrayList			并发读写不安全；

LinkedLis			使用副本机制改进；

HashMap			并发读写不安全；

LinkedHashMap	 使用分段锁或者CAS

并发需要考虑的维度：粒度，性能，重入，公平，自旋锁（spinlock）。

线程间协作与通信：

\1. 线程间共享：static/实例变量（堆内存）, Lock , synchronized

\2. 线程间协作：Thread#join(), Object#wait/notify/notifyAll,

Future/Callable,CountdownLatch,CyclicBarrier

知识图谱如下：

![img](file:///C:\Users\tonyxu\AppData\Local\Temp\ksohtml20208\wps3.png) 

 

 

（4）Spring和ORM等框架

总结如下：

对Spring Framework 6大模块有一定了解：

\1. Core: Bean/Context/AOP

\2. Testing: Mock/TestContext

\3. DataAccess: Tx/JDBC/ORM

\4. Spring MVC/WebFlux:web

\5. Integration: remoting/JMS/WS

\6. Languages: Kotlin/Groovy

了解SpringAOP : 其实就是Spring中的一个代理层，通过字节码增强来实现所有对象的托管。（备注：在我们编程设计的过程中，需要注意很多问题都可以通过增加层的方式解决）

了解IOC-控制反转：其也称为DI(Dependency Injection)依赖注入，这个是针对对象装配思路的改进。从对象A直接引用和操作对象B，变成对象A里指需要依赖一个接口IB，系统启动和装配阶段，把IB接口的实例对象注入到对象A，因此A就不需要依赖一个IB接口的具体实现，也就是B类。

了解SpringBean的生命周期：构造函数—》依赖注入—》BeanNameAware—》BeanFactoryAware—》ApplicationContextAware—》BeanPostProcessor前置方法—》InitializingBean—》自定义init方法—》BeanPostProcessor后置方法—》使用—》DisposableBean—》自定义destroy方法

了解了SpringBoot的产生原因和定位，由于框架需要让开发变的简单&让配置变简单&让运行变的简单，基于上述的诉求，那么确定约定大于配置的想法。

SpringBoot简化的手段如下：

​	1.  Spring本身技术的成熟与完善，各方面第三方组件的成熟集成。

​	2.  Spring团队在去Web容器化等方面的努力。

​	3.  基于maven与pom的Java生态体系，整合pom模板成为可能。

​	4.  避免大量maven导入和各种版本的冲突。

​	综上所述，Springboot的定位是Spring的一套快速配置脚手架，关注于自动配置，配置驱动，其包含自动配置注解（SpringBootApplication核心启动入口），条件化自动配置（运行时灵活组装，避免冲突），SpringBootStarter。

​	ORM知识点，针对主流框架Hibernate与MyBatis的比较如下：

​		MyBatis优点：原生SQL（XML语法），直观，对DBA友好

​		MyBatis缺点：繁琐，可以用Mybtis-generator,MyBatis-Plus之类的插件。

​		Hibernate优点：简单场景不用写SQL（HQL，Cretiria, SQL）

​		Hibernate缺点：对DBA不友好

​	了解了Spring的事务管理：

​		Spring声明式事务配置参考：

​		事务的传播性：

​		@Transactional(propagation=Propagation.REQUIRED)

​		事务的隔离级别：

​		@Transactional(isolation = Isolation.READ_UNCOMMITTED)

​		读取未提交数据（会出现脏数据，不可重复读）基本不使用。

​		只读：

​		@Transactional(readOnly=true)

​		该属性用于设置当前事务是否为只读事务，设置为true表示只读，false则表示可读写，默认值为false。

​		事务的超时性：

@Transactional(timeout=30)

回滚：

指定单一异常类：	@Transactional(rollbackFor=RuntimeException.Class)

指定多个异常类：

@Transactional(rollbackFor={RuntimeException.Class,Exception.Class})

 



知识图谱如下：

![img](file:///C:\Users\tonyxu\AppData\Local\Temp\ksohtml20208\wps4.png) 



（5）MySql数据库和SQL

总结如下：

了解数据库设计范式：

第一范式（1NF）：关系R属于第一范式，当且仅当R中的每一个属性A的值域只包含原子项。

第二范式（2NF）：在满足1NF的基础上，消除非主属性对码的部分函数依赖。

第三范式（3NF）：在满足2NF的基础上，消除非主属性对码的传递函数依赖。

BC范式（BCNF）：在满足3NF的基础上，消除主属性对码的部分和传递函数依赖。

第四范式（4NF）：消除非平凡的多值依赖。

第五范式（5NF）：消除一些不合适的连接依赖。

 

了解MySQL相关知识点：

MySQL存储：

独占模式：日志组文件，表结构文件，独占表空间文件，字符集和排序规则文件，binlog二进制日志文件（记录主数据库服务器的DDL和DML操作），二进制日志索引文件。

共享模式：innodb_file_per_table=1

 

MySQL简化执行流程：

客户端—》MySQL服务器—》查询缓存—》解析器—》解析树—》预处理器—》

解析树—》查询优化器—》执行计划—》查询执行引擎—》结果

 

MySQL详细执行流程：

更新记录—》连接器—》分析器—》优化器—》执行器—》写undo log存储回滚段指针和事务ID—》记录所在的目标页存在于内存中—》找到数据判断数据冲突与否更新内存&找到数据更新内存—》写入redo log—》写binlog—》提交事务—》刷redo log盘处于commit-prepare阶段—》刷binlog处于commit-commit阶段

 

MySQL对SQL执行顺序：

from—》on—》join—》where—》group by—》having+聚合函数—》select—》order by—》limit

 

MySQL事务

事务可靠性模型ACID：

Atomicity : 原子性，一次事务中的操作要么全部成功，要么全部失败。

Consistency : 一致性，跨表，跨行，跨事务，数据库始终保持一致状态。

Isolation : 隔离性，可见性，保护事务不会相互干扰，包含4种隔离级别。

Durability : 持久性，事务提交成功后，不会丢数据。

InnoDB:

双写缓冲区，故障恢复，操作系统，fsync()，磁盘存储，缓存，UPS, 网络，备份策略......



 

知识图谱如下：

![img](file:///C:\Users\tonyxu\AppData\Local\Temp\ksohtml20208\wps5.png) 

![img](file:///C:\Users\tonyxu\AppData\Local\Temp\ksohtml20208\wps6.png) 

（6）分库分表

总结如下：

了解MySQL的高可用：

1）主从手动切换：如果主节点挂掉，将某个从改成主。用LVS+Keepalived实现多个节点的探活+请求路由。

 

2）MHA（Master High Availability）目前在MySQL高可用方面是一个相对成熟的解决方案，它由日本DeNA公司的youshimaton开发，是一套优秀的作为MySQL高可用性环境下故障切换和主从提升的高可用软件。

 

3）MGR：如果主节点挂掉，将自动选择某个从改成主；无需人工干预，基于组复制，保证数据一致性。

MGR特点：

<1>高一致性：基于分布式Paxos协议实现组复制，保证数据一致性。

<2>高容错性：自动检测机制，只要不是大多数节点都宕机就可以继续工作，内置防脑裂保护机制。

<3>高扩展性：节点的增加与移除会自动更新组成员信息，新节点加入后，自动从其他节点同步增量数据，直到与其他节点数据一致；

<4>高灵活性：提供单主模式和多主模式，单主模式在主库宕机后能够自动选择主，所有写入都在主节点上进行，多主模式支持多节点写入。

 

4）MySQL Cluster : MySQL InnoDB Cluster 是一个高可用的框架，它由下面这个几个组件构成：

<1>  MySQL Group Replication : 提供DB的扩展，自动故障转移。

<2>  MySQL Router : 轻量级中间件，提供应用程序连续目标的故障转移。

<3>  MySQL Shell : 新的MySQL客户端，多种接口模式。可以设置群组复制及Router。

5）Orchestrator : 如果主节点挂掉，将某个从改成主。一款MySQL高可用和复制拓扑管理工具，支持复制拓扑结构的调整，自动故障转移和手动主从切换等。后端数据库用MySQL或者SQLite存储元数据，并提供Web界面展示MySQL复制的拓扑关系及状态，通过Web可更改MySQL实例的复制关系和部分配置信息，同时也提供命令行和API接口,方便运维管理。

特点：

（1）自动发现MySQL的复制拓扑，并且在Web上展示；

（2）重构复制关系，可以在web进行拖图来进行复制关系变更。

（3）检测主异常，并可以自动或手动恢复，通过Hooks进行自定义脚本；

（4）支持命令行和Web界面管理复制。

​	

​	了解分库分表相关的扩展立方体：

​		x轴 : 通过clone整个系统复制，集群。

​		y轴 : 通过解耦不同功能复制，业务拆分。

​		z轴 : 通过拆分不同数据扩展，数据分片。

 

​	

​		

 

在数据库的扩展方面：

全部数据—》数据复制—》主从数据，备份与高可用。

业务分类数据—》垂直分库分表—》分布式服务化，微服务

任意数据—》水平分库分表—》分布式结构，任意扩容

 

针对数据库垂直拆分的优缺点：

​	优点：

\1. 单库(单表)变小，便于管理和维护

​	2. 对性能和容量有提示作用

​	3. 改造后，系统和数据复杂度降低。

​	4. 可以作为微服务改造的基础。

​	缺点：

​	1. 库变多，管理变复杂。

​	2. 对业务系统有较强的侵入性。

​	3. 改造过程复杂，容易出故障。

​	4. 拆分到一定程度就无法继续拆分。

​		垂直拆分的做法：

​		1. 梳理清楚拆分范围和影响范围。

​		2. 检查评估和重新影响到的服务。

​		3. 准备新的数据库集群复制数据。

​		4. 修改系统配置并发布新版上线。

 

​		数据库水平拆分：

水平拆分就是直接对数据进行分片，有分库和分表两个具体方式，但是都只是降低单个节点数据量，但不改变数据本身的结构。这样对业务系统本身的代码逻辑来说，就不需要做特别大的改动，甚至可以基于一些中间件做到透明。

 

​		数据库水平拆分的优缺点：

​		优点：

​		1. 解决容量问题。

​		2. 比垂直拆分对系统影响小。

​		3. 部分提升性能和稳定性。

​		缺点：

​		1. 集群规模大，管理复杂。

​		2. 复杂SQL支持问题（业务侵入性，性能）

​		3. 数据迁移问题

​		4. 一致性问题

 

​		数据库中间件ShardingSphere:

​		Apache ShardingSphere是一套开源的分布式数据库中间件解决方案组成的生态圈，它由JDBC，Proxy和Sidecar(规划中)这3款相互独立，却又能够混合部署配合使用的产品组成。它们均提供标准化的数据分片，分布式事务和数据库治理功能，可适用于JAVA同构，异构语言，云原生等各种多样化的应用场景。

 

分布式事务：

分布式条件下，多个节点操作的整体事务一致性。

特别是在微服务场景下，业务A和业务B关联，事务A成功，事务B失败，由于跨系统，就会导致不被感知。此时从整体看，数据是不一致的。

了解如何实现分布式下的一致性：

\1. 强一致性： XA

\2. 弱一致性：TCC

(1) 不用事务，业务侧补偿冲正。

(2) 所谓的柔性事务，使用一套事务框架保证最终一致的事务。

XA的事务：（两阶段提交事务）

xa_start : 负责开启或者恢复一个事务分支

xa_end : 负责取消当前线程与事务分支的关联

xa_prepare : 询问RM是否准备好提交事务分支

xa_commit : 通知RM提交事务分支

xa_rollback : 通知RM回滚事务分支

xa_recover : 需要恢复的XA事务

 

BASE柔性事务：

TCC 通过手动补偿处理。

AT 通过自动补偿处理。

 

BASE柔性事务TCC

TCC模式即将每个服务业务操作分为两个阶段：

1）第一个阶段检查并预留相关资源。

2）第二个阶段根据所有服务业务的Try状态来操作，如果都成功，则进行Confirm操作，如果任意一个Try发生错误，则全部Cancel。

 

TCC使用要求就是业务接口都必须实现三段逻辑：

\1. 准备操作Try：完成所有业务检查，预留必须的业务资源。

\2. 确定操作Confirm : 真正执行的业务逻辑，不做任何业务检查，只使用Try阶段预留的业务资源。因此，只要Try操作成功，Confirm必须能成功。另外，Confirm操作需满足幂等性，保证一笔分布式事务能且只能成功一次。

\3. 取消操作Cancel ：释放Try阶段预留的业务资源。同样的，Cancel操作也需要满足幂等性。

 

 

​		

 



 

（7）PRC和微服务

总结如下：

了解RPC概念：

RPC是远程过程调用（Remote Procedure Call）的缩写形式。

简单的说，就是像本地方法一样调用远程方法。

 

PRC原理：

核心是代理机制。

\1. 本地代理存根：Stub

\2. 本地序列化反序列化

\3. 网络通信

\4. 远程序列化反序列化

\5. 远程服务存根：Skeleton

\6. 调用实际业务服务

\7. 原路返回服务结果

\8. 返回给本地调用方

（备注：注意处理异常）

​		

​		设计一个RPC框架

​		共享POJO实体类&接口定义—》选择动态代理或者AOP—》序列化&反序列化—》网络传输TCP/SSL&HTTP/HTTPS—》查找实现类

 

​		从RPC走向服务化

​		客户端—》注册中心—》服务端

​		客户端：

​		服务发现—》调用模块（负载均衡&容错&透明）—》RPC协议(序列化&协议编码&网络传输)

​		服务端：

​		服务暴露—》处理程序—》线程池—》RPC协议（反序列化&协议解码&网络传输）

​		

分布式框架DUBBO

Apache Dubbo是一款高性能，轻量级的开源Java服务框架

六大核心能力：面向接口代理的高性能RPC调用&智能容错和负载均衡&服务自动注册和发现&高度可扩展能力&运行期流量调度&可视化的服务治理与运维。

Dubbo的主要功能：

基础功能：RPC调用&多协议（序列化，传输，RPC）&服务注册发现&配置，元数据管理&框架分层设计，可任意组装和扩展。

扩展功能：集群（负载均衡）&治理，路由&控制台，管理与监控

Dubbo的整体架构：

\1. config配置层：对外配置接口，以ServiceConfig, RefercenceConfig为中心，可以直接初始化配置类，也可以通过spring解析配置生成配置类。

\2. proxy服务代理层：服务接口透明代理，生成服务的客户端Stub和服务器端Skeleton,以ServiceProxy为中心，扩展接口为ProxyFactory。

\3. registry注册中心层：封装服务地址的注册与发现，以服务URL为中心，扩展接口为RegistryFactory,Registry,RegistryService

\4. cluster路由层：封装多个提供者的路由及负载均衡，并桥接注册中心，以Invoker为中心，扩展接口为Cluster,Directory,Router,LoadBalance

\5. monitor监控层：RPC调用次数和调用时间监控，以Statistics为中心，扩展接口为MonitorFactory, Monitor, MonitorService

\6. protocol远程调用层：封装RPC调用，以Invocation,Result为中心，扩展接口为Protocol,Invoker,Exporter。

\7. exchange信息交换层：封装请求响应模式，同步转异步，以Request,Response为中心，扩展接口为Exchange,ExchangeChannel,ExchangeClient,ExchangeServer。

\8. transport网络传输层：抽象main和netty为统一接口，以Message为中心，扩展接口为Channel，Transporter,Client,Server,Codec。

\9. serialize数据序列化层：可复用的一些工具，扩展接口为Serialization,ObjectInput,ObjectOutput,ThreadPool。

 

RPC与分布式服务化的区别：

RPC：技术概念

》以RPC来讲，我们前面的自定义RPC功能已经差不多了。

》可以再考虑一下性能优化，使用spring-boot等封装易用性。

​	  分布式服务化：服务是业务语义，偏向于业务于系统的集成。

​	  》以分布式服务化框架的角度来看，我们还差前面的这些非功能性需求能力。

​	  》具体使用时，另外一个重点是如何设计分布式的业务服务。

 

分布式服务化配置&注册&元数据中心

配置中心（ConfigCenter）: 管理系统需要的配置参数信息。

注册中心（RegistryCenter）: 管理系统的服务注册，提供发现和协调能力。

元数据中心（MetadataCenter）: 管理各个节点使用的元数据信息。

 

相同点：都需要保存和读取数据/状态，变更通知。

不同点：配置是全局非业务参数，注册中心是运行期临时状态，元数据是业务模型。

服务的负载均衡：（Service LoadBalance）

跟Nginx的负载均衡一样。

多个不同策略，原理不同，目的基本一致（尽量均匀）

\1. Random (带权重)

\2. RoundRobin(轮询)

\3. LeastActive(快的多给)

\4. ConsistentHashLoadBalance(同样参数请求到一个提供者)

服务流控：

\1. 限流（内部线程数，外部调用数OR数据量）

\2. 服务降级（去掉不必要的业务逻辑，只保留核心逻辑）

\3. 过载保护（系统短时间不提供新的业务处理服务，积压处理完后在恢复输入请求）

 

微服务架构阶段：

准备阶段：调研—》分析—》规划—》组织

实施阶段：拆分—》部署—》治理—》改进

微服务架构关注维度：

遗留系统改造&自动化管理&恰当粒度拆分&分布式事务&扩展立方体&完善监控体系

微服务的演化过程：

\1. 功能剥离，数据解耦

\2. 自然演进，逐步拆分

\3. 小步快跑，快速迭代

\4. 灰度发布，谨慎试错

\5. 提质量线，还技术债

 

Spring-Cloud (Feign/Ribbon)

Feign的核心功能就是，作为HTTP Client访问REST服务接口。

优势在于：

\1. 全都基于注解，简单方便。

\2. 跟XXTemplate一样，内置了简单操作，OOP。

\3. 跟其他组件，ribbon, hytrix联合使用。

 

Ribbon是用于云环境的一个客户端内部通信（IPC）库。

特性：

\1. 负载均衡。

\2. 容错。

\3. 多协议支持（HTTP，TCP，UDP），特别是异步和反应式下

\4. 缓存和批处理

 



 

知识图谱如下：

![img](file:///C:\Users\tonyxu\AppData\Local\Temp\ksohtml20208\wps7.png) 



（8）分布式缓存

总结如下：

缓存的本质：系统各个处理速度不匹配，导致利用空间换时间。

缓存是提升系统性能的一个简单有效的办法。

 

了解了缓存的加载时机：

\1. 启动全量加载 ==》全局有效，使用简单

\2. 懒加载

同步使用加载==》先看缓存是否有数据，没有的话从数据库读取；读取的数据，先放到内存，然后返回给调用方。

延迟异步加载==》从缓存获取数据，不管是否为空直接返回==》策略异步）如果为空，则发起一个异步加载的线程，负责加载数据==》策略解耦）异步线程负责维护缓存的数据，定期或者根据条件触发更新

​		需要明确，对于数据一致性，性能，成本的综合衡量，是引入缓存的必须指标。

​		

​		缓存不当容易引发的问题：

​		1. 系统预热导致启动慢

​		2. 系统内存资源耗尽

 

​		本地缓存Spring Cache

​		1. 基于注解和AOP，使用非常方便。

​		2. 可以配置Condition和SPEL,非常灵活。

​		3. 需要注意：绕过Spring的话，注解无效。

 

​		远程缓存Redis/Memcached缓存中间件

​		Remote Dictionary Server(Redis)是一个由Salvatore Sanfilippo写的key-value存储系统。

Redies的使用场景：

1）业务数据缓存

2）业务数据处理

3）全局一致计数

4）高效统计计数

5）发布订阅与Stream

6）分布式锁

 

Spring与Redis的结合

核心是RedisTemplate(可以配置基于Jedis,Lettuce,Redisson)

使用方法类似MongoDBTemplate,JDBCTemplate或者JPA封装了基本redis命令操作。

 

Redis高级功能：Redis事务，Redis管道技术，Redis数据备份与恢复--RDB-frm,Redis数据备份与恢复--AOP-binlog

 

了解Redis使用的经验：

\1. 性能：

1） 线程数鱼连接数。

2） 监控系统读写比和缓存命中率

\2. 容量：

1）做好容量评估，合理使用缓存资源。

\3. 资源管理和分配：

1）尽量每个业务集群单独使用自己的Redis，不混用。

2）控制Redis资源的申请与使用，规范环境和Key的管理

3）监控CPU 100%,优化高延迟的操作。

 

了解Redis的集群和高可用

1）Redis主从复制：从单机到多节点 类似MYSQL主从

2）Redis Sentinel 主从切换： 走向高可用-MHA

3）Redis Cluster: 走向分片 全自己分库分表

4）Java中配置使用Redis Sentinel

5) Java中配置使用Redis Cluster

 



知识图谱如下：

![img](file:///C:\Users\tonyxu\AppData\Local\Temp\ksohtml20208\wps8.png) 



（9）分布式消息队列

总结如下：

了解了系统间通信方式：

基于文件，基于共享内存，基于IPC，基于SOCKET，基于数据库，基于PRC

各个模式的缺点：

文件：明显不方便，不及时。

SOCKET：使用麻烦，多数情况下不如RPC。

数据库：不实时，但是经常有人拿数据库来模拟消息队列。

RPC：调用关系复杂，同步处理，压力大的时候无法缓冲。

 

MQ的作用

异步通信，系统解耦，削峰填谷，可靠通信

 

消息处理的保障：

At most once  至多一次

At least once 	至少一次

Exactly once	 精确一次

 

消息处理的事务性：

-通过确定机制实现事务性。

-可以被事务管理器管理，甚至可以支持XA。

 

Kafka是一个分布式的，基于发布/订阅的消息系统。

Broker:kafka集群包含一个或者多个服务器，这种服务器被称为broker。

Topic:每条发布到Kafka集群的消息都有一个类别，这个类别被称为Topic。

Partition：Partition是物理上的概念，每个Topic包含一个或多个Partition。

Producer:Partition是物理上的概念，每个Topic包含一个或多个Partition。

Consumer：消息消费者，向Kafka broker读取消息的客户端。

Consumer Group：每个Consumer属于一个特定的ConsumerGroup(可为每个Consumer指定group name)若不指定group name则属于默认的group。

 

生产者的执行步骤：客户端实现序列化，分区，压缩操作。

生产者的确认模式：

ack=0: 只发送不管有没有写入到broker。

ack=1: 写入到leader就认为成功。

ack=-1/all:写入到最小的复本数则认为成功。

生产者特性-同步发送&异步发送&顺序保证&消息可靠性传递。	

 

消费者-Consumer Group

消费者特性-Offset同步提交&异步提交&自动提交&SEEKS

 

RocketMQ与KAFKA的关系：

从本质上说，两者没有本质区别。

1）纯JAVA开发，用不用ZK

2）支持延迟投递，消息追溯

3）多个队列使用一个日志文件，所以不存着KAFKA过多TOPIC问题



知识图谱如下：

![img](file:///C:\Users\tonyxu\AppData\Local\Temp\ksohtml20208\wps9.png) 



***\*毕业总结：\****

综上所述，经过三个月的学习，针对JVM,NIO,并发编程,Spring和ORM等框架,MySql数据库和SQL,分库分表,RPC和微服务,分布式缓存，分布式消息队列的知识点有了系统性的了解，并且很好的整合之前的知识点，并且通过实践的练习和反馈，确实起到了进阶的作用，非常感谢老师和各位助教的付出。