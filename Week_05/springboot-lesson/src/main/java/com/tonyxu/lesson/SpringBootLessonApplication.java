package com.tonyxu.lesson;

import com.tonyxu.lesson.dao.jpa.LocationRepositoryJpa;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootLessonApplication {


	@Bean
	InitializingBean initLocationData(LocationRepositoryJpa repo){
		return null;
	}



	public static void main(String[] args) {
		SpringApplication.run(SpringBootLessonApplication.class, args);
	}

}
