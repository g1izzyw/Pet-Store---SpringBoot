package com.petstore.service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.petstore.entity.Pet;
import com.petstore.entity.PetPicture;
import com.petstore.entity.Tag;
import com.petstore.interfaces.IStorage;

@Repository
public class PetDAOService implements IStorage<Pet> {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Pet create(Pet instance) {
		try {
			entityManager.persist(instance);
			for (PetPicture picture : instance.getPictures()) {
				picture.setPet(instance);
			}
			log.trace(String.format("Create pet with name: %s", instance.getName()));
			return instance;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (TransactionRequiredException e) {
			e.printStackTrace();
		} catch (EntityExistsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.trace(String.format("Failed to create pet with name: %s", instance.getName()));
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Pet read(Long id) {
		try {
			Pet pet = entityManager.find(Pet.class, id);
			log.trace(String.format("Read pet with id: %d", id));
			return pet;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.trace(String.format("Failed to read pet with id: %d", id));
		return null;
	}

	@Override
	@Transactional
	public Pet update(Pet instance) {
		try {
			entityManager.merge(instance);
			log.trace(String.format("Update pet with id: %d", instance.getId()));
			return instance;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (TransactionRequiredException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.trace(String.format(String.format("Failed to update pet with id:", instance.getId())));
		return null;
	}

	@Override
	@Transactional
	public boolean delete(Long id) {
		Pet pet = read(id);
		if (pet != null) {
			try {
				entityManager.remove(pet);
				for (Tag tag : pet.getTags()) {
					tag.getPets().remove(pet);
				}
				log.trace(String.format("Remove pet with id: %d", id));;
				return true;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (TransactionRequiredException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			log.trace(String.format(String.format("Failed to remove pet with id:", id)));
			return false;
		}
		log.trace(String.format(String.format("Could not read (to remove) pet with id: %d", id)));
		return false;
	}

	@Override
	@Transactional
	public List<Pet> readAll() {
		return null;
	}

}
