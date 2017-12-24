package com.petstore.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petstore.entity.Category;
import com.petstore.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
	List<Pet> findByName(String name);

    List<Pet> findByCategory(Category category);

	// custom query example and return a stream
//    @Query("select p from Pet p where p.name = :email")
//    Stream<Customer> findByEmailReturnStream(@Param("email") String email);
}
