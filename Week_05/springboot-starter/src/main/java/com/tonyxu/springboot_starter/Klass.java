package com.tonyxu.springboot_starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
@ConfigurationProperties(prefix = "klass")
@Data
public class Klass { 
    
    List<Student> students;
    
    public void dong(){
        System.out.println(this.getStudents());
    }
    
}
