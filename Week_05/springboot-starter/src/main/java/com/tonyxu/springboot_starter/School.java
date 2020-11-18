package com.tonyxu.springboot_starter;

import com.tonyxu.springboot_starter.aop.ISchool;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration // 标记当前类是配置类
@EnableConfigurationProperties(Student.class) // 使用java类作为配置文件
@ConditionalOnProperty(prefix = "student", value = "enable", matchIfMissing = true)
@Data
public class School implements ISchool {

    @Autowired
    Klass class1;

    @Autowired
    Student student100;
    
    @Override
    public void ding(){
        System.out.println("Class1 have " +
                this.class1.getStudents().size() +
                " students and one is " + this.student100);
    }
    
}
