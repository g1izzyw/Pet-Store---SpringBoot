package com.petstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import com.petstore.entity.Category;
import com.petstore.entity.Pet;
import com.petstore.entity.Tag;

@SpringBootApplication
//@ComponentScan({"com.petstore.controller","com.petstore.config", "com.petstore.interfaces", "com.petstore.interfaces"})
@EntityScan(basePackageClasses = {Pet.class, Category.class, Tag.class})
@ComponentScan("com.petstore.*")
//@EntityScan("com.petstore.entity")
@EnableAutoConfiguration
public class PetstoreApplication extends SpringBootServletInitializer  {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PetstoreApplication.class);
    }
	
	public static void main(String[] args) {
		new PetstoreApplication().configure(new SpringApplicationBuilder(PetstoreApplication.class)).run(args);
//		SpringApplication.run(PetstoreApplication.class, args);
	}
	
}
