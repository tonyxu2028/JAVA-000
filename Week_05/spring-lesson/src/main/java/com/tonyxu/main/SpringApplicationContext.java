package com.tonyxu.main;

import com.tonyxu.beans.Lesson;
import com.tonyxu.beans.User;
import com.tonyxu.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static com.tonyxu.constant.ApplicationConstant.TYPE_INIT_BY_ANNOTATION;
import static com.tonyxu.constant.ApplicationConstant.TYPE_INIT_BY_ID;
import static com.tonyxu.constant.ApplicationConstant.TYPE_INIT_BY_NAME;

/**
 * Created on 2020/11/16.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@Configuration
@ComponentScan(basePackages="com.tonyxu.service")
public class SpringApplicationContext {

    public static void main(String[] args){

        if(args[0]==null){
            return;
        }

        String type = args[0];

        // XML_BY_ID
        if(type.equals(TYPE_INIT_BY_ID)){
            createBeanById();
        }

        // XML_BY_NAME
        if(type.equals(TYPE_INIT_BY_NAME)){
            createBeanByName();
        }

        // ANNOTATION
        if(type.equals(TYPE_INIT_BY_ANNOTATION)){
            createBeanByAnnotation();
        }
    }

    /**
     * XML_BY_ID ::: 建BEAN方式
     *
     */
    public static void createBeanById(){
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        User user = (User) context.getBean("user");
        user.init();
    }

    /**
     * XML_BY_NAME ::: 创建BEAN方式
     *
     */
    public static void createBeanByName(){
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        Lesson lesson = (Lesson) context.getBean("lesson");
        System.out.println(lesson.toString());
    }

    /**
     * ANNOTATION ::: 创建BEAN方式
     */
    public static void createBeanByAnnotation(){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringApplicationContext.class);
        UserService userService = (UserService)context.getBean("userService");
        userService.printUserName();
    }

}
