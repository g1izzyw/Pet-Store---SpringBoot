package com.petstore.service;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.petstore.entity.Category;
import com.petstore.entity.Pet;
import com.petstore.entity.PetPicture;
import com.petstore.entity.Tag;
import com.petstore.enums.Status;
import com.petstore.interfaces.IStorage;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PetDAOServiceTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private IStorage<Pet> petRepository;
	
	@Autowired
	private IStorage<Category> categoryRepository;
	
	@Autowired
	private IStorage<Tag> tagRepository;
	
	private Category categoryDog;
	
	private Tag tagSmall;
	private Tag tagCute;
	
	@Before
	public void executedBeforeEach() {
		categoryDog = new Category();
		categoryDog.setName("Dog");
		
		tagSmall = new Tag();
		tagSmall.setName("Small");
		tagCute = new Tag();
		tagCute.setName("Cute");
		
		entityManager.persist(categoryDog);
		
		entityManager.persist(tagSmall);
		entityManager.persist(tagCute);
		
		entityManager.flush();
	}
	
	@After
	public void executedAfterEach() {
		entityManager.remove(categoryDog);
		
		entityManager.remove(tagSmall);
		entityManager.remove(tagCute);
		
		entityManager.flush();
	}
	
	private void testDogEntity(Pet pet, String name, String category, Status status, Set<Tag> tags, Set<PetPicture> pictures) {
		assertNotNull(pet);
		Assert.assertEquals(pet.getName(), name);
		if (category == null) {
			assertNull(pet.getCategory());
		} else {
			Assert.assertEquals(pet.getCategory().getName(), category);
		}
		if (status == null) {
			assertNull(pet.getStatus());
		} else {
			assertEquals(pet.getStatus(), status);
		}
		for (PetPicture pic : pictures) {
			assertThat(pet.getPictures(), hasItems(pic));
		}
		for (Tag tag : tags) {
			assertThat(pet.getTags(), hasItems(tag));
		}
	}
	
	private void addPetToEM(Pet pet) {
		entityManager.persist(pet);
		for (PetPicture pic : pet.getPictures()) {
			entityManager.persist(pic);
		}
		entityManager.flush();
		
		if (pet.getCategory() != null) {
			pet.getCategory().setPet(pet);
			entityManager.persist(pet.getCategory());
		}
		
		for (Tag tag : pet.getTags()) {
			Set<Pet> petsForTag = new HashSet<Pet>();
			petsForTag.add(pet);
			tag.setPets(petsForTag);
			entityManager.persist(tag);
		}
		entityManager.flush();
	}
	
	private void removePetFromEM(Pet pet) {
		entityManager.remove(pet);
		for (PetPicture pic : pet.getPictures()) {
			entityManager.remove(pic);
		}
		
		if (pet.getCategory() != null) {
			pet.getCategory().setPet(null);
			entityManager.persist(pet.getCategory());
		}
		
		for (Tag tag : pet.getTags()) {
			tag.setPets(new HashSet<Pet>());
			entityManager.persist(tag);
		}
		
		entityManager.flush();
	}
	
	@Test
	public void returnNoResultsFromRead() {
		Pet dog = petRepository.read(new Long(404));
		assertNull(dog);
	}
	
	@Test
	public void returnPetFromRead() {
		PetPicture dogPic1 = new PetPicture();
		dogPic1.setLink("dog_pic1");
		PetPicture dogPic2 = new PetPicture();
		dogPic2.setLink("dog_pic2");
		
		Set<PetPicture> dogPics = new HashSet<PetPicture>();
		dogPics.add(dogPic1);
		dogPics.add(dogPic2);
		
		Set<Tag> dogTags = new HashSet<Tag>();
		dogTags.add(tagCute);
		dogTags.add(tagSmall);
		
		Pet dog = new Pet();
		dog.setName("Alet");
		dog.setPictures(dogPics);
		dog.setStatus(Status.Available);
		dog.setTags(dogTags);
		dog.setCategory(categoryDog);
		
		addPetToEM(dog);
		
		Long dogId = dog.getId();
		
		Pet readResultDog = petRepository.read(dogId);
		
		testDogEntity(readResultDog, "Alet", categoryDog.getName(), Status.Available, dogTags, dogPics);
		
		removePetFromEM(readResultDog);
	}
	
	@Test 
	public void createNewPetWithPartialInformation() {
		PetPicture dogPic1 = new PetPicture();
		dogPic1.setLink("dog_pic1");
		PetPicture dogPic2 = new PetPicture();
		dogPic2.setLink("dog_pic2");
		
		Set<PetPicture> dogPics = new HashSet<PetPicture>();
		dogPics.add(dogPic1);
		dogPics.add(dogPic2);
		
		Pet dog = new Pet();
		dog.setName("Alet");
		dog.setPictures(dogPics);
		dog.setStatus(null);
		dog.setTags(new HashSet<Tag>());
		dog.setCategory(null);
		
		Pet createdDog = petRepository.create(dog);
		assertNotNull(createdDog);
		
		testDogEntity(createdDog, "Alet", null, null, new HashSet<Tag>(), dogPics);
		
		removePetFromEM(createdDog);
	}
	
	@Test
	public void createNewPetWithFullInformation() {
		PetPicture dogPic1 = new PetPicture();
		dogPic1.setLink("dog_pic1");
		PetPicture dogPic2 = new PetPicture();
		dogPic2.setLink("dog_pic2");
		
		Set<PetPicture> dogPics = new HashSet<PetPicture>();
		dogPics.add(dogPic1);
		dogPics.add(dogPic2);
		
		Set<Tag> dogTags = new HashSet<Tag>();
		dogTags.add(tagCute);
		dogTags.add(tagSmall);
		
		Pet dog = new Pet();
		dog.setName("Alet");
		dog.setPictures(dogPics);
		dog.setStatus(Status.Available);
		dog.setTags(dogTags);
		dog.setCategory(categoryDog);
		
		Pet createdDog = petRepository.create(dog);
		assertNotNull(createdDog);
		
		testDogEntity(createdDog, "Alet", categoryDog.getName(), Status.Available, dogTags, dogPics);
		
		removePetFromEM(createdDog);
	}
	
	@Test
	public void failToRemovePetWhichDoesNotExist() {
		boolean isRemoved = petRepository.delete(new Long(101));
		assertFalse(isRemoved);
	}
	
	@Test
	public void removePetWhichDoesExist() {
		PetPicture dogPic1 = new PetPicture();
		dogPic1.setLink("dog_pic1");
		PetPicture dogPic2 = new PetPicture();
		dogPic2.setLink("dog_pic2");
		
		Set<PetPicture> dogPics = new HashSet<PetPicture>();
		dogPics.add(dogPic1);
		dogPics.add(dogPic2);
		
		Set<Tag> dogTags = new HashSet<Tag>();
		dogTags.add(tagCute);
		dogTags.add(tagSmall);
		
		Pet dog = new Pet();
		dog.setName("Alet");
		dog.setPictures(dogPics);
		dog.setStatus(Status.Available);
		dog.setTags(dogTags);
		dog.setCategory(categoryDog);
		
		addPetToEM(dog);
		
		Long dogId = dog.getId();
		
		boolean isRemoved = petRepository.delete(dogId);
		assertTrue(isRemoved);
		
		List<Category> allCategories = categoryRepository.readAll();
		assertEquals(allCategories.size(), 1);
	
		List<Tag> allTags = tagRepository.readAll();
		assertEquals(allTags.size(), 2);
	}
	
}
