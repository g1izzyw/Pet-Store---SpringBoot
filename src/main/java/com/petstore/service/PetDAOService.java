package com.petstore.service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.petstore.entity.Pet;
import com.petstore.entity.PetPicture;
import com.petstore.entity.Tag;
import com.petstore.interfaces.IStorage;

@Repository
public class PetDAOService implements IStorage<Pet> {

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
			return instance;
		} catch (IllegalArgumentException e) {
			return null;
		} catch (TransactionRequiredException e) {
			return null;
		} catch (EntityExistsException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Pet read(Long id) {
		try {
			return entityManager.find(Pet.class, id);
		} catch (IllegalArgumentException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public Pet update(Pet instance) {
		try {
			entityManager.merge(instance);
			return instance;
		} catch (IllegalArgumentException e) {
			return null;
		} catch (TransactionRequiredException e) {
			return null;
		} catch (Exception e) {
			return null;
		}

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
			try {
				entityManager.remove(pet);
				for (Tag tag : pet.getTags()) { // remove from join table
					tag.getPets().remove(pet);
				}
				
				return true;
			} catch (IllegalArgumentException e) {
				return false;
			} catch (TransactionRequiredException e) {
				return false;
			} catch (Exception e) {
				return false;
			}
		}
		// entityManager.remove(read(id));
		return false;
	}

	@Override
	@Transactional
	public List<Pet> readAll() {
		return null;
	}

}
