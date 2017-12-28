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

import com.petstore.entity.Tag;
import com.petstore.interfaces.IStorage;

@RestController
@RequestMapping("/tag")
public class TagController {

	@Autowired
	DataSource dataSource;

	@Autowired
	IStorage<Tag> tagRepository;

	@GetMapping("")
	public @ResponseBody ResponseEntity<Set<Tag>> getAllTags() {
		Set<Tag> allTagsSet = new HashSet<Tag>();
		List<Tag> allTagsList = tagRepository.readAll();

		if (allTagsList != null) {
			for (Tag t : allTagsList) {
				allTagsSet.add(t);
			}
			return new ResponseEntity<Set<Tag>>(allTagsSet, HttpStatus.OK);
		} else {
			return new ResponseEntity<Set<Tag>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// @RequestMapping(value = "", method = Req)

}
