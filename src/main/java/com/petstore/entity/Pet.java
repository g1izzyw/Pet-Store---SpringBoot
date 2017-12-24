package com.petstore.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.petstore.enums.Status;
import com.petstore.interfaces.IStorable;

@Entity
@Table(name = "Pet")
@NamedQueries({
	@NamedQuery(name = "Pet.readAll", query = "SELECT p FROM Pet p")
})
public class Pet implements IStorable, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	// @SequenceGenerator(name = "PetSequence", sequenceName = "PET_ID_SEQ",
	// allocationSize = 1, initialValue = 1)
	@Column(name = "pet_id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_id") // dont need to use this is we are using mapsid
//	@MapsId
	private Category category;
	
	// private List<String> photoUrls;

	// private List<Tag> tags;

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

}
