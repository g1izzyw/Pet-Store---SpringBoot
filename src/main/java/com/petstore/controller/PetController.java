package com.petstore.controller;

import javax.sql.DataSource;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.petstore.entity.Pet;
import com.petstore.exceptions.PetNotFoundException;
import com.petstore.interfaces.IStorage;

@RestController
@RequestMapping("/pet")
public class PetController {

	@Autowired
	DataSource dataSource;

	@Autowired
	IStorage<Pet> petRepository;

	@GetMapping(value = "/{petId}")
	public @ResponseBody ResponseEntity<Pet> getPetById(@PathVariable("petId") Long petId) throws PetNotFoundException, TypeMismatchException {
		Pet petWithId = petRepository.read(petId);
		if (petWithId != null) {
			return new ResponseEntity<Pet>(petWithId, HttpStatus.OK);
		} else {
			throw new PetNotFoundException(petId);
		}
	}

	@DeleteMapping(value = "/{petId}")
	public ResponseEntity<Void> deletePetById(@PathVariable("petId") Long petId) throws PetNotFoundException, TypeMismatchException {
		if (petRepository.delete(petId)) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			throw new PetNotFoundException(petId);
		}
	}

	@PostMapping(value = "")
	public ResponseEntity<Void> addPetToStore(@RequestBody Pet petToCreate, UriComponentsBuilder ucBuilder) {
		Pet newPet = petRepository.create(petToCreate);
		if (newPet != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/pet/{id}").buildAndExpand(newPet.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} else {
			//TODO put in gobal exceptions
			return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
		}

	}
}
