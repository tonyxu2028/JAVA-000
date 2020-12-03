package com.tonyxu;

import com.tonyxu.datasource.DynamicDataSourceConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author tonyxu
 */
@MapperScan("com.tonyxu.mapper")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Import({DynamicDataSourceConfig.class})
public class SpringBootDynamicDataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDynamicDataSourceApplication.class, args);
    }

}
