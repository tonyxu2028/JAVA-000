1）写代码实现Spring Bean的装配，方式越多越好（XML、Annotation都可以）,提交到Github。

针对这个题目，详见spring-lesson

三种加载方式为：

<1> XML通过ID方式加载， MAIN通过arg[0]传入 TYPE_INIT_BY_ID方式执行

<2> XML通过NAME方式加载，MAIN通过arg[0]传入TYPE_INIT_BY_NAME方式执行

<3> ANNOTATION注解方式，MAIN通过arg[0]传入TYPE_INIT_BY_ANNOTATION方式执行

2) 给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。

针对这个题目，详见springboot-starter

<1> pom.xml 中自定义的一个spring boot的start pom

<2> Student的属性通过@ConfigurationProperties(prefix = "student")自动转配，内容在application.properties中配置

<3> 同理Student，Klass的自动加载方式与其一致,@ConfigurationProperties(prefix = "klass"),集合内容在application.properties中配置

<4> School为配置类，@Configuration // 标记当前类是配置类，@EnableConfigurationProperties({Student.class,Klass.class}) // 使用java类作为配置文件，并进行自动加载。

<5> spring.factories进行对School的自动加载配置

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.tonyxu.springboot_starter.autoconf.School
```

 以上就是全部的start pom案例，当写完后，使用maven的install安装到本地仓库后，在创建好springboot之后，添加上依赖，就可以很方便的使用了。 

3）研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
3.1）使用 JDBC 原生接口，实现数据库的增删改查操作。
3.2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
3.3）配置 Hikari 连接池，改进上述操作。提交代码到 Github。

针对这个题目，详见springboot-lesson，目前是用JPA实现的Hikari 连接池，H2的数据库

<1> 通过application.yml配置了 h2和hikari的连接池

<2> LocationContraller的init,save,update,delete,findAll实现了Contraller层的，初始化，增加，修改，删除，查询

<3> LocationService在LocationContraller中自动加载，处理service层的事宜，在initLocations，saveLocation，deleteLocation，updateLocation上均定义了事务，@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW),所以事务的处理是在service层实现的。

<4> LocationRepositoryJpa在Dao层处理，继承了JpaRepository

<5> 页面通过对init,save,update,delete,findAll的调用，可以完成对h2中Location初始化，增加，修改，删除，查询功能