package com.petstore.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.petstore.entity.Category;
import com.petstore.entity.Pet;
import com.petstore.entity.PetPicture;
import com.petstore.entity.Tag;
import com.petstore.enums.Status;
import com.petstore.interfaces.IStorage;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = PetstoreApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
//@WebMvcTest(value = PetController.class, secure = false)
@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IStorage<Pet> petRepository;
	
//	@Mock
//    private Pet petDog;
//
//	@Mock
//	private Category categoryDog;
//	
//	@Mock
//	private Tag tagBig;
//	@Mock
//	private Tag tagCute;
//	
//	@Mock
//	private PetPicture petPic1;
//	@Mock
//	private PetPicture petPic2;
	
	@Before
	public void executedBeforeEach() {
//		Set<Pet> setTagPets = new HashSet<Pet>();
//		setTagPets.add(petDog);
//		
//		Set<PetPicture> setPicOfPet = new HashSet<PetPicture>();
//		setPicOfPet.add(petPic1);
//		setPicOfPet.add(petPic2);
//		
//		Set<Tag> setPetTags = new HashSet<Tag>();
//		setPetTags.add(tagCute);
//		setPetTags.add(tagBig);
//		
//		when(tagBig.getName()).thenReturn("Big");
//		when(tagCute.getName()).thenReturn("Cute");
//		when(tagBig.getPets()).thenReturn(setTagPets);
//		when(tagCute.getPets()).thenReturn(setTagPets);
//		
//		when(categoryDog.getName()).thenReturn("Dog");
//		when(categoryDog.getPet()).thenReturn(petDog);
//
//		when(petPic1.getLink()).thenReturn("dog.img");
//		when(petPic2.getLink()).thenReturn("cute_dog.img");
//		when(petPic1.getPet()).thenReturn(petDog);
//		when(petPic2.getPet()).thenReturn(petDog);
//		
//		when(petDog.getId()).thenReturn(new Long(101));
//		when(petDog.getName()).thenReturn("Alet");
//		when(petDog.getStatus()).thenReturn(Status.Available);
//		when(petDog.getPictures()).thenReturn(setPicOfPet);
//		when(petDog.getTags()).thenReturn(setPetTags);
//		when(petDog.getCategory()).thenReturn(categoryDog);
	}

	@Test
	public void retrieveDetailsForPet() throws Exception {
		PetPicture petPic1 = new PetPicture();
		PetPicture petPic2 = new PetPicture();
		
		petPic1.setId(new Long(1));
		petPic1.setLink("dogImg1.img");
		
		petPic2.setId(new Long(2));
		petPic2.setLink("dogImg2.img");
		
		Set<PetPicture> setPicOfPet = new HashSet<PetPicture>();
		setPicOfPet.add(petPic1);
		setPicOfPet.add(petPic2);
		
		Category categoryDog = new Category();
		categoryDog.setId(new Long(1));
		categoryDog.setName("Dog");
		
		Tag tagCute = new Tag();
		Tag tagSmall = new Tag();
		
		tagCute.setId(new Long(1));
		tagCute.setName("Cute");
		
		tagSmall.setId(new Long(2));
		tagSmall.setName("Small");
		
		Set<Tag> setPetTags = new HashSet<Tag>();
		setPetTags.add(tagCute);
		setPetTags.add(tagSmall);
		
		Pet dog = new Pet();
		dog.setId(new Long(11));
		dog.setName("Alet");
		dog.setPictures(setPicOfPet);
		dog.setStatus(Status.Available);
		dog.setCategory(categoryDog);
		dog.setTags(setPetTags);
		
		when(petRepository.read(new Long(11))).thenReturn(dog);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pet/11");
		
		MvcResult mvcresult = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(mvcresult.getResponse());
		
		
		ResultActions result = mockMvc.perform(requestBuilder);
		result.andExpect(status().isOk());
		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.id", is(11)));
		result.andExpect(jsonPath("$.name", is("Alet")));
		
		result.andExpect(jsonPath("$.tags", hasSize(2)));
		result.andExpect(jsonPath("$.tags.roles", Matchers.containsInAnyOrder("role1", "role2", "role3")));

		
		result.andExpect(jsonPath("$.status", is("Available")));
		result.andExpect(jsonPath("$.category.name", is("Dog")));
		result.andExpect(jsonPath("$.pictures", hasSize(2)));

//		mvc.perform(get("/api/employees")
//			      .contentType(MediaType.APPLICATION_JSON))
//			      .andExpect(status().isOk())
//			      .andExpect(content()
//			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//			      .andExpect(jsonPath("$[0].name", is("bob")));
		
		
//		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
    @Test
    public void findCarShouldReturnCar() throws Exception {

//        when(findCar.findCar("Honda")).thenReturn(Optional.of(new Car("Honda")));
//
//        mvc.perform(get("/cars/Honda").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//                .andExpect(content().json("{ 'name': 'Honda' }"));
    }

    @Test
    public void findCarShouldReturn404WhenNoCarIsFound() throws Exception {

//    	when(findCar.findCar(anyString())).thenReturn(Optional.empty());
//
//        mvc.perform(get("/cars/Unknown").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

}
