package com.petstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import com.petstore.entity.Category;
import com.petstore.entity.Pet;
import com.petstore.entity.PetPicture;
import com.petstore.entity.Tag;

@SpringBootApplication
@EntityScan(basePackageClasses = {Pet.class, Category.class, Tag.class, PetPicture.class})
@ComponentScan("com.petstore.*")
@EnableAutoConfiguration
public class PetstoreApplication  {
	
	public static void main(String[] args) {
		SpringApplication.run(PetstoreApplication.class, args);
	}
	
}
