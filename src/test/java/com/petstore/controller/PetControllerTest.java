package com.petstore.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
@WebMvcTest(value = PetController.class, secure = false)
public class PetControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IStorage<Pet> petRepository;
	
	@Mock
    private Pet petDog;

	@Mock
	private Category categoryDog;
	
	@Mock
	private Tag tagBig;
	@Mock
	private Tag tagCute;
	
	@Mock
	private PetPicture petPic1;
	@Mock
	private PetPicture petPic2;
	
	@Before
	public void executedBeforeEach() {
		Set<Pet> setTagPets = new HashSet<Pet>();
		setTagPets.add(petDog);
		
		Set<PetPicture> setPicOfPet = new HashSet<PetPicture>();
		setPicOfPet.add(petPic1);
		setPicOfPet.add(petPic2);
		
		Set<Tag> setPetTags = new HashSet<Tag>();
		setPetTags.add(tagCute);
		setPetTags.add(tagBig);
		
		when(tagBig.getName()).thenReturn("Big");
		when(tagCute.getName()).thenReturn("Cute");
		when(tagBig.getPets()).thenReturn(setTagPets);
		when(tagCute.getPets()).thenReturn(setTagPets);
		
		when(categoryDog.getName()).thenReturn("Dog");
		when(categoryDog.getPet()).thenReturn(petDog);

		when(petPic1.getLink()).thenReturn("dog.img");
		when(petPic2.getLink()).thenReturn("cute_dog.img");
		when(petPic1.getPet()).thenReturn(petDog);
		when(petPic2.getPet()).thenReturn(petDog);
		
		when(petDog.getId()).thenReturn(new Long(101));
		when(petDog.getName()).thenReturn("Alet");
		when(petDog.getStatus()).thenReturn(Status.Available);
		when(petDog.getPictures()).thenReturn(setPicOfPet);
		when(petDog.getTags()).thenReturn(setPetTags);
		when(petDog.getCategory()).thenReturn(categoryDog);
	}

	@Test
	public void retrieveDetailsForPet() throws Exception {

		when(petRepository.read(new Long(101))).thenReturn(petDog);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pet/101").accept(MediaType.APPLICATION_JSON);
		
		MvcResult mvcresult = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(mvcresult.getResponse());
		
		
		ResultActions result = mockMvc.perform(requestBuilder);
		result.andExpect(status().isOk());
		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.id", is(new Long(101))));
		result.andExpect(jsonPath("$.name", is("Alet")));
		
		result.andExpect(jsonPath("$.tags", hasSize(2)));
		
//		actions.andExpect((jsonPath("$.data.roles", Matchers.containsInAnyOrder("role1", "role2", "role3"))));

		
		result.andExpect(jsonPath("$.status", is(Status.Available)));
		result.andExpect(jsonPath("$.category.name", is("Dog")));
		result.andExpect(jsonPath("$.picture", hasSize(2)));

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
