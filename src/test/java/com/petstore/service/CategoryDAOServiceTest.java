package com.petstore.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.petstore.entity.Category;
import com.petstore.interfaces.IStorage;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryDAOServiceTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private IStorage<Category> repository;
	
	private Category categoryDog;
	private Category categoryCat;
	private Category categoryFish;
	
	@Before
	public void executedBeforeEach() {
		categoryDog = new Category();
		categoryDog.setName("Dog");
		
		categoryCat = new Category();
		categoryCat.setName("Cat");
		
		categoryFish = new Category();
		categoryFish.setName("Fish");
		
		entityManager.persist(categoryDog);
		entityManager.persist(categoryCat);
		entityManager.persist(categoryFish);
		entityManager.flush();
	}
	
	@Test
	public void returnNoResults() {
		entityManager.remove(categoryDog);
		entityManager.remove(categoryCat);
		entityManager.remove(categoryFish);
		entityManager.flush();
		
		List<Category> categories = repository.readAll();
		assertEquals(categories.size(), 0);
	}
 
	@Test
	public void returnBigTagOnly() {
		entityManager.remove(categoryCat);
		entityManager.remove(categoryFish);
		entityManager.flush();
		
		List<Category> categories = repository.readAll();
		assertEquals(categories.size(), 1);
		
		Category resultCategoryDog = categories.get(0);
		assertNotNull(resultCategoryDog.getId());
		assertEquals(resultCategoryDog.getName(), "Dog");
	}
 
	@Test
	public void returnAllThreeTags() {
		List<Category> tags = repository.readAll();
		assertEquals(tags.size(), 3);
	}
}
