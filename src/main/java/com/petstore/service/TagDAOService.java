package com.petstore.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.petstore.entity.Tag;
import com.petstore.interfaces.IStorage;

@Repository
public class TagDAOService implements IStorage<Tag> {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
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
			log.trace(String.format("Reading all tags, %d tags found", allTags.size()));
			return allTags;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.trace("Failed to read all tags");
		return null;
	}

}
