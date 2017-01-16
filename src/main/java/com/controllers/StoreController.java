package com.controllers;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.PetDAO_JSON;
import com.dto.Pet;
import com.interfaces.IStorage;

@RestController
public class StoreController {

	private final AtomicInteger petIdCounter;
	private final IStorage<Pet> petDAO;
	
	public StoreController() {
		petDAO = new PetDAO_JSON();
		
		List<Pet> petsInStore = petDAO.readAll();
		
		int biggestId = -1;
		for (Pet pet : petsInStore) {
			if (pet.getId() > biggestId) {
				biggestId = pet.getId();
			}
		}
		
		petIdCounter = new AtomicInteger(biggestId + 1);
	}
	
	@RequestMapping(value = "/pet/{petId}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Pet> getPetById(@PathVariable("petId") int petId) {
		Pet petWithId = petDAO.read(petId);
		if (petWithId != null) {
			return new ResponseEntity<Pet>(petWithId, HttpStatus.OK);
		} else {
			return new ResponseEntity<Pet>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/pet/{petId}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity deletePetById(@PathVariable("petId") int petId) {
		boolean wasPetDeleted = petDAO.delete(petId);
		if (wasPetDeleted) {
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/pet", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Pet> addPetToStore(@RequestBody Pet petToCreate) {	
		petToCreate.setId(petIdCounter.incrementAndGet());
		
		Pet newPet = petDAO.create(petToCreate);
		if (newPet != null) {
			return new ResponseEntity<Pet>(newPet, HttpStatus.OK);
		} else {
			return new ResponseEntity<Pet>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		
	}
	
}
