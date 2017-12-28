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
	// PetRepository petRepository;
	IStorage<Pet> petRepository;

	// @RequestMapping(value = "/{petId}", method = RequestMethod.GET)
	@GetMapping(value = "/{petId}")
	public @ResponseBody ResponseEntity<Pet> getPetById(@PathVariable("petId") Long petId) throws PetNotFoundException, TypeMismatchException {
		// Pet petWithId = petRepository.findOne(petId);
		Pet petWithId = petRepository.read(petId);
		if (petWithId != null) {
			return new ResponseEntity<Pet>(petWithId, HttpStatus.OK);
		} else {
			throw new PetNotFoundException(petId);
//			return new ResponseEntity<Pet>(HttpStatus.NOT_FOUND);
		}
	}

	// @RequestMapping(value = "/{petId}", method = RequestMethod.DELETE)
	@DeleteMapping(value = "/{petId}")
	public ResponseEntity<Void> deletePetById(@PathVariable("petId") Long petId) throws PetNotFoundException, TypeMismatchException {
		// boolean wasPetDeleted = petRepository.delete(petId);
		if (petRepository.delete(petId)) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			throw new PetNotFoundException(petId);
//			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	// @RequestMapping(value = "", method = RequestMethod.POST)
	@PostMapping(value = "")
	public ResponseEntity<Void> addPetToStore(@RequestBody Pet petToCreate, UriComponentsBuilder ucBuilder) {
		// petToCreate.setId(petIdCounter.incrementAndGet());

		// Pet newPet = petRepository.save(petToCreate);
		Pet newPet = petRepository.create(petToCreate);
		if (newPet != null) {
			// final URI location =
			// ServletUriComponentsBuilder.fromCurrentServletMapping().path("/pet/{id}").build().expand(newPet.getId()).toUri();
			// final HttpHeaders headers = new HttpHeaders();
			// headers.setLocation(location);
			// final ResponseEntity<Void> entity = new
			// ResponseEntity<Void>(headers, HttpStatus.CREATED);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(newPet.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

			// return new ResponseEntity<Pet>(newPet, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
		}

	}
}
