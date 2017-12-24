package com.petstore.interfaces;

import java.util.List;

public interface IStorage<T extends IStorable> {

	public T create(T instance);
	public T read(Long id);
	public T update(T instance);
	public boolean delete(Long id);
	public List<T> readAll();

}
