package com.petstore.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstore.entity.Category;
import com.petstore.entity.Pet;
import com.petstore.entity.PetPicture;
import com.petstore.entity.Tag;
import com.petstore.enums.Status;
import com.petstore.interfaces.IStorage;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private IStorage<Pet> petRepository;
	
    private PetPicture petPic1;
	private PetPicture petPic2;
	private Set<PetPicture> setPicOfPet;
	
	private List<String> pictureLinks;
	
	private Category categoryDog;
	
	private Tag tagCute;
	private Tag tagSmall;
	private Set<Tag> setPetTags;
	
	@Before
	public void executedBeforeEach() {
		petPic1 = new PetPicture();
		petPic2 = new PetPicture();
		tagCute = new Tag();
		tagSmall = new Tag();
		categoryDog = new Category();
		
		setPetTags = new HashSet<Tag>();
		setPicOfPet = new HashSet<PetPicture>();
		pictureLinks = new ArrayList<String>();

		petPic1.setId(new Long(1));
		petPic1.setLink("dogImg1.img");
		petPic2.setId(new Long(2));
		petPic2.setLink("dogImg2.img");
		setPicOfPet.add(petPic1);
		setPicOfPet.add(petPic2);
		
		pictureLinks.add("dogImg1.img");
		pictureLinks.add("dogImg2.img");
		
		categoryDog.setId(new Long(1));
		categoryDog.setName("Dog");
		
		tagCute.setId(new Long(1));
		tagCute.setName("Cute");
		tagSmall.setId(new Long(2));
		tagSmall.setName("Small");
		setPetTags.add(tagCute);
		setPetTags.add(tagSmall);
	}

	@Test
	public void requestGetRetrievePetDetailsForValidInput() throws Exception {
		Pet dog = new Pet();
		dog.setId(new Long(11));
		dog.setName("Alet");
		dog.setPictures(setPicOfPet);
		dog.setStatus(Status.Available);
		dog.setCategory(categoryDog);
		dog.setTags(setPetTags);
		
		when(petRepository.read(new Long(11))).thenReturn(dog);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pet/11");
		ResultActions result = mockMvc.perform(requestBuilder);
		result.andExpect(status().isOk());
		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.id", is(11)));
		result.andExpect(jsonPath("$.name", is("Alet")));
		
		result.andExpect(jsonPath("$.tags", hasSize(2)));
		result.andExpect(jsonPath("$.tags[0].name", Matchers.isOneOf("Cute", "Small")));

		result.andExpect(jsonPath("$.status", is("Available")));
		result.andExpect(jsonPath("$.category.name", is("Dog")));
		
		result.andExpect(jsonPath("$.pictureLinks", hasSize(2)));
		result.andExpect(jsonPath("$.pictureLinks", Matchers.containsInAnyOrder("dogImg1.img", "dogImg2.img")));
	
		result.andExpect(jsonPath("$.setPicOfPet").doesNotExist());
	}
	
    @Test
    public void requestGetReturnPetNotFoundForValidInput() throws Exception {
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pet/1");
    	ResultActions result = mockMvc.perform(requestBuilder);
    	result.andExpect(status().isNotFound());
    }
    
    @Test
    public void requestGetReturnBadRequestForInvalidInput() throws Exception {
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pet/invlidInput");
    	ResultActions result = mockMvc.perform(requestBuilder);
    	result.andExpect(status().isBadRequest());
    }

    @Test
    public void requestDeletePetReturnsOkForValidInput() throws Exception {
    	when(petRepository.delete(new Long(11))).thenReturn(true);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/pet/11");
		ResultActions result = mockMvc.perform(requestBuilder);
		result.andExpect(status().isOk());
    }

    @Test
    public void requestDeletePetReturnsPetNotFoundForValidInput() throws Exception {
    	when(petRepository.delete(new Long(11))).thenReturn(false);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/pet/1");
		ResultActions result = mockMvc.perform(requestBuilder);
		result.andExpect(status().isNotFound());
    }
    
    @Test
    public void requestDeletePetReturnsBadRequestForInvalidInput() throws Exception {
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/pet/invlidInput");
    	ResultActions result = mockMvc.perform(requestBuilder);
    	result.andExpect(status().isBadRequest());
    }
    
    @Test
    public void requestPostCreatePetReturnsOkForValidInput() throws Exception {
    	Pet dog = new Pet();
		dog.setId(new Long(11));
		dog.setName("Alet");
		dog.setPictureLinks(pictureLinks);
		dog.setStatus(Status.Available);
		dog.setCategory(categoryDog);
		dog.setTags(setPetTags);
		
		when(petRepository.create(dog)).thenReturn(dog);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/pet").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dog));
		ResultActions resultAction = mockMvc.perform(requestBuilder);
		resultAction.andExpect(status().isCreated());
		
		MvcResult resultMvc = mockMvc.perform(requestBuilder).andReturn();
		String locationValue = resultMvc.getResponse().getHeader("location");
		assertEquals(locationValue, "http://localhost/pet/11");
    }
    
    @Test
    public void requestPostCreatePetReturnsBadRequestForInvalidInput() throws Exception {
    	Pet dogWithNoName = new Pet();
		dogWithNoName.setId(new Long(11));
		dogWithNoName.setPictureLinks(pictureLinks);
		dogWithNoName.setStatus(Status.Available);
		dogWithNoName.setCategory(categoryDog);
		dogWithNoName.setTags(setPetTags);
		
    	RequestBuilder requestBuilderOne = MockMvcRequestBuilders.post("/pet").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dogWithNoName));
    	ResultActions resultOne = mockMvc.perform(requestBuilderOne);
    	resultOne.andExpect(status().isMethodNotAllowed());
    	
    	Pet dogWithNoPics = new Pet();
    	dogWithNoPics.setId(new Long(11));
    	dogWithNoPics.setName("Alet");
    	dogWithNoPics.setStatus(Status.Available);
    	dogWithNoPics.setCategory(categoryDog);
    	dogWithNoPics.setTags(setPetTags);
    	
    	RequestBuilder requestBuilderTwo = MockMvcRequestBuilders.post("/pet").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dogWithNoPics));
    	ResultActions resultTwo = mockMvc.perform(requestBuilderTwo);
    	resultTwo.andExpect(status().isMethodNotAllowed());
    }
    
}
