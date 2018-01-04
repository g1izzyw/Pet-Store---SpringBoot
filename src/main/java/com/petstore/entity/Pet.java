package com.petstore.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.petstore.enums.Status;
import com.petstore.interfaces.IStorable;

@Entity
@Table(name = "Pet")
@NamedQueries({
	@NamedQuery(name = "Pet.readAll", query = "SELECT p FROM Pet p")
})
public class Pet implements IStorable, Serializable {

	private static final long serialVersionUID = 1438831856541179960L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pet_id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_id")
	@JsonManagedReference("pet-category")
	private Category category;
	
	@OneToMany(cascade={CascadeType.ALL},mappedBy="pet")
	@JsonManagedReference("pet-picture")
	private Set<PetPicture> pictures;

	@ManyToMany
	@JoinTable(
		name="Pet_Tag",
		joinColumns=@JoinColumn(name="pet_id", referencedColumnName="pet_id"),
		inverseJoinColumns=@JoinColumn(name="tag_id", referencedColumnName="tag_id")
	)
	private Set<Tag> tags;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pet other = (Pet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Pet() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	
	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Set<PetPicture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<PetPicture> pictures) {
		this.pictures = pictures;
	}

}
