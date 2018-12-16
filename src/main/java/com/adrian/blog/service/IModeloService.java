package com.adrian.blog.service;

import java.util.List;

import com.adrian.blog.model.Modelo;

public interface IModeloService {

	void save(Modelo modelo);

	List<Modelo> findByIdMarca(int idMarca);

}
