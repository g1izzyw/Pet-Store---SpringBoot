package com.petstore.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.petstore.entity.Tag;
import com.petstore.interfaces.IStorage;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TagDAOServiceTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private IStorage<Tag> repository;
	
	private Tag tagBig;
	private Tag tagSmall;
	private Tag tagCute;
	
	@Before
	public void executedBeforeEach() {
		tagBig = new Tag();
		tagBig.setName("Big");
		
		tagSmall = new Tag();
		tagSmall.setName("Small");
		
		tagCute = new Tag();
		tagCute.setName("Cute");
		
		entityManager.persist(tagBig);
		entityManager.persist(tagSmall);
		entityManager.persist(tagCute);
		entityManager.flush();
	}
	
	@Test
	public void returnNoResults() {
		entityManager.remove(tagBig);
		entityManager.remove(tagSmall);
		entityManager.remove(tagCute);
		entityManager.flush();
		
		List<Tag> tags = repository.readAll();
		assertEquals(tags.size(), 0);
	}
 
	@Test
	public void returnBigTagOnly() {
		entityManager.remove(tagSmall);
		entityManager.remove(tagCute);
		entityManager.flush();
		
		List<Tag> tags = repository.readAll();
		assertEquals(tags.size(), 1);
		
		Tag resultTagBig = tags.get(0);
		assertNotNull(resultTagBig.getId());
		assertEquals(resultTagBig.getName(), "Big");
	}
 
	@Test
	public void returnAllThreeTags() {
		List<Tag> tags = repository.readAll();
		assertEquals(tags.size(), 3);
	}
 
}
