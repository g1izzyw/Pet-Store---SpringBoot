package com.petstore.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.petstore.entity.Pet;
import com.petstore.interfaces.IStorage;

@RestController
//@RequestMapping("/pet")
public class PetController {

	@Autowired
    DataSource dataSource;

    @Autowired
//    PetRepository petRepository;
	IStorage<Pet> petRepository;
    
    @RequestMapping(value = "/pet/{petId}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Pet> getPetById(@PathVariable("petId") Long petId) {
//		Pet petWithId = petRepository.findOne(petId);
    	Pet petWithId = petRepository.read(petId);
		if (petWithId != null) {
			return new ResponseEntity<Pet>(petWithId, HttpStatus.OK);
		} else {
			return new ResponseEntity<Pet>(HttpStatus.NOT_FOUND);
		}
	}
    
    @RequestMapping(value = "/pet/{petId}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity deletePetById(@PathVariable("petId") Long petId) {
		//boolean wasPetDeleted = petRepository.delete(petId);
    	try {
    		petRepository.delete(petId);
    		return new ResponseEntity(HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} finally {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
    
    @RequestMapping(value = "/pet", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Pet> addPetToStore(@RequestBody Pet petToCreate) {	
//		petToCreate.setId(petIdCounter.incrementAndGet());
		
//		Pet newPet = petRepository.save(petToCreate);
    	Pet newPet = petRepository.create(petToCreate);
		if (newPet != null) {
			return new ResponseEntity<Pet>(newPet, HttpStatus.OK);
		} else {
			return new ResponseEntity<Pet>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		
	}
}
