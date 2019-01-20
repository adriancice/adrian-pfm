package com.adrian.blog.service;

import java.util.Collection;

import com.adrian.blog.model.Marca;

public interface IMarcaService {

	Collection<Marca> findAll();

	Marca findByIdMarca(int id);

	Marca findByMarca(String marca);

	int findIdByMarca(String marca);

}
