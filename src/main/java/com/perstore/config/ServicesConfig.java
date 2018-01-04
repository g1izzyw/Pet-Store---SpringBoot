package com.perstore.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.petstore.entity.Category;
import com.petstore.entity.Pet;
import com.petstore.entity.Tag;
import com.petstore.interfaces.IStorage;
import com.petstore.service.CategoryDAOService;
import com.petstore.service.PetDAOService;
import com.petstore.service.TagDAOService;

@Component
@Configuration
public class ServicesConfig {

	@Bean
	@ConditionalOnProperty(name = "storage.type", havingValue = "memory", matchIfMissing = true)
	public IStorage<Pet> petJPAPersistenceService() {
		return new PetDAOService();
	}

	@Bean
	@ConditionalOnProperty(name = "storage.type", havingValue = "database", matchIfMissing = false)
	public IStorage<Pet> petJSONPersistenceService() {
		return null;
	}

	@Bean
	@ConditionalOnProperty(name = "storage.type", havingValue = "memory", matchIfMissing = true)
	public IStorage<Tag> tagJPAPersistenceService() {
		return new TagDAOService();
	}

	@Bean
	@ConditionalOnProperty(name = "storage.type", havingValue = "memory", matchIfMissing = true)
	public IStorage<Category> categoryJPAPersistenceService() {
		return new CategoryDAOService();
	}
	
//	@Bean
//	public Jackson2ObjectMapperBuilder configureObjectMapper() {
//	    return new Jackson2ObjectMapperBuilder().modulesToInstall(Hibernate4Module.class);
//	}
}
