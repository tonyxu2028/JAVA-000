package com.tonyxu.springboot_starter;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
@ConfigurationProperties(prefix = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student implements Serializable {
    
    private int id;
    private String name;
    
    public void init(){
        System.out.println("hello...........");
    }
    
    public Student create(){
        return new Student(101,"KK101");
    }
}
