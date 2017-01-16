package com.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.dto.Pet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.interfaces.IStorage;

public class PetDAO_JSON implements IStorage<Pet> {

	private final String petDataJSONFile = "json_data/pets.json";
	private ClassLoader classLoader = getClass().getClassLoader();
	
	@Override
	public Pet create(Pet instance) {	
		if (instance.getName() == null || instance.getId() == null || instance.getId() < 0) {
			return null;
		}
		
		List<Pet> petList = readAll();
		petList.add(instance);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		try {			
			mapper.writeValue(new File(classLoader.getResource(this.petDataJSONFile).getFile()), petList);
			return instance;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Pet read(int id) {
		List<Pet> petList = readAll();
		
		for (Pet pet : petList) {
			if (pet.getId() == id) {
				return pet;
			}
		}
				
		return null;
	}

	@Override
	public Pet update(Pet instance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		List<Pet> petList = readAll();

		boolean wasPetRemoved = false;
		
		for (Iterator<Pet> iterator = petList.iterator(); iterator.hasNext();) {
		    Pet pet = iterator.next();
		    if (pet.getId() == id) {
		        // Remove the current element from the iterator and the list.
		        iterator.remove();
		        wasPetRemoved = true;
		        break;
		    }
		}
		
		if (wasPetRemoved) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
				
			try {			
				mapper.writeValue(new File(classLoader.getResource(this.petDataJSONFile).getFile()), petList);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public List<Pet> readAll() {
		FileReader petFileReader = null;
		try {
			petFileReader = new FileReader(classLoader.getResource(this.petDataJSONFile).getFile());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JSONParser parser = new JSONParser();
		List<Pet> allPetsInStore = new ArrayList<Pet>();
		
		Object obj = null;
		try {
			obj = parser.parse(petFileReader);
			petFileReader.close();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jsonPetArray = (JSONArray) obj;
		
		Iterator<JSONObject> jsonPetIterator = jsonPetArray.iterator();
		ObjectMapper mapper = new ObjectMapper();
		while (jsonPetIterator.hasNext()) {
			JSONObject jsonPetInfo = jsonPetIterator.next();
			
			Pet petInfo;
			try {
				String a = jsonPetInfo.toJSONString();
				petInfo = mapper.readValue(jsonPetInfo.toString(), Pet.class);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			
			allPetsInStore.add(petInfo);
		}
		
		return allPetsInStore;
	}

}
