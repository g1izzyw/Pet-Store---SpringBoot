package com.petstore.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.petstore.entity.Pet;
import com.petstore.interfaces.IStorage;

@Repository
public class PetDAOService implements IStorage<Pet> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Pet create(Pet instance) {
		entityManager.persist(instance);
		return instance;
	}

	@Override
	@Transactional
	public Pet read(Long id) {
		return entityManager.find(Pet.class, id);
		// return null;
	}

	@Override
	@Transactional
	public Pet update(Pet instance) {
		entityManager.merge(instance);
		return instance;

		// Pet artcl = read(instance.getId());
		// artcl.setTitle(instance.getTitle());
		// artcl.setCategory(instance.getCategory());
		// entityManager.flush();
	}

	@Override
	@Transactional
	public boolean delete(Long id) {
		Pet pet = read(id);
		if (pet != null) {
			entityManager.remove(pet);
		}

		// entityManager.remove(read(id));
		return true;
	}

	@Override
	@Transactional
	public List<Pet> readAll() {
		return null;
	}

}
