package com.petstore.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petstore.entity.Category;
import com.petstore.interfaces.IStorage;

@Repository
public class CategoryDAOService implements IStorage<Category> {

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
	@Transactional
	public List<Category> readAll() {
		try {
			Query query = entityManager.createNamedQuery("Category.readAll");
			List<Category> allCategories = (List<Category>) query.getResultList();
			return allCategories;
		} catch (IllegalStateException e) {
			return null;
		}
	}

}
