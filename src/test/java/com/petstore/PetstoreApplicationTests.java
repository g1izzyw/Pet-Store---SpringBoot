package com.petstore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.petstore.controller.CategoryController;
import com.petstore.controller.PetController;
import com.petstore.controller.TagController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PetstoreApplicationTests {
	
	@Autowired
    private PetController petController;
	
	@Autowired
	private TagController tagController;
	
	@Autowired
	private CategoryController categoryController;
	
	@Test
    public void contextLoads() throws Exception {
		assertThat(petController).isNotNull();
		assertThat(tagController).isNotNull();
		assertThat(categoryController).isNotNull();
    }
}
