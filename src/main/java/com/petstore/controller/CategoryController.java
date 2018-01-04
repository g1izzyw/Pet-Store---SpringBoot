package com.petstore.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.petstore.entity.Category;
import com.petstore.exceptions.FailedToReadAllCategoriesException;
import com.petstore.exceptions.FailedToReadAllException;
import com.petstore.interfaces.IStorage;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	DataSource dataSource;

	@Autowired
//	@Qualifier("SomethingImpl")
	IStorage<Category> categoryRepository;

	@GetMapping("")
	public @ResponseBody ResponseEntity<Set<Category>> getAllTags() throws FailedToReadAllException {
		Set<Category> allCategoriesSet = new HashSet<Category>();
		List<Category> allCategoriesList = categoryRepository.readAll();

		if (allCategoriesList != null) {
			for (Category c : allCategoriesList) {
				allCategoriesSet.add(c);
			}
			return new ResponseEntity<Set<Category>>(allCategoriesSet, HttpStatus.OK);
		} else {
			throw new FailedToReadAllCategoriesException();
		}
	}
}
