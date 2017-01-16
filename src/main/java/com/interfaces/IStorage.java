package com.interfaces;

import java.util.List;

public interface IStorage<T extends IStorable> {

	public T create(T instance);
	public T read(int id);
	public T update(T instance);
	public boolean delete(int id);
	public List<T> readAll();

}
