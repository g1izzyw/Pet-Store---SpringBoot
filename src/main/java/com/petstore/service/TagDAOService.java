package com.petstore.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.petstore.entity.Tag;
import com.petstore.interfaces.IStorage;

@Repository
public class TagDAOService implements IStorage<Tag> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Tag create(Tag instance) {
		return null;
	}

	@Override
	@Transactional
	public Tag read(Long id) {
		return null;
	}

	@Override
	@Transactional
	public Tag update(Tag instance) {
		return null;
	}

	@Override
	@Transactional
	public boolean delete(Long id) {
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tag> readAll() {
		try {
			Query query = entityManager.createNamedQuery("Tag.readAll");
			List<Tag> allTags = (List<Tag>) query.getResultList();
			return allTags;
		} catch (IllegalStateException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

}
