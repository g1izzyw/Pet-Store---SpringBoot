package com.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.interfaces.IStorable;

public class Pet implements IStorable {

	@JsonProperty("id") private Integer id;
	@JsonProperty("name") private String name;
	@JsonProperty("category") private Category category;
	@JsonProperty("photoUrls") private List<String> photoUrls;
	@JsonProperty("tags") private List<Tag> tags;
	@JsonProperty("status") private String status;
	
	public Pet() {
		super();
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
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public List<String> getPhotoUrls() {
		return photoUrls;
	}
	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
