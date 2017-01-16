package com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.interfaces.IStorable;

public class Category implements IStorable {

	@JsonProperty("id") private Integer id;
	@JsonProperty("name") private String name;
	
	public Category() {
		super();
	}
	
	public Category(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
