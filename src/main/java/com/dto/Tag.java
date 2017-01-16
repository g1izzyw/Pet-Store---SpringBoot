package com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.interfaces.IStorable;

public class Tag implements IStorable {

	@JsonProperty("id") private Integer id;
	@JsonProperty("name") private String name;
	
	public Tag() {
		super();
	}
	
	public Tag(Integer id, String name) {
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
