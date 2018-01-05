package com.petstore.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.petstore.entity.Category;
import com.petstore.interfaces.IStorage;

@Repository
public class CategoryDAOService implements IStorage<Category> {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public Category create(Category instance) {
		return null;
	}

	@Override
	@Transactional
	public Category read(Long id) {
		return null;
	}

	@Override
	@Transactional
	public Category update(Category instance) {
		return null;
	}

	@Override
	@Transactional
	public boolean delete(Long id) {
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> readAll() {
		try {
			Query query = entityManager.createNamedQuery("Category.readAll");
			List<Category> allCategories = (List<Category>) query.getResultList();
			log.trace(String.format("Reading all categories, %d categories found", allCategories.size()));
			return allCategories;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.trace("Failed to read all categories");
		return null;
	}

}
