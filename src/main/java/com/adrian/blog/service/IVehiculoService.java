package com.adrian.blog.service;

import java.util.Collection;

import com.adrian.blog.model.Vehiculo;

public interface IVehiculoService {

	Vehiculo save(Vehiculo vehiculo);

	void delete(Vehiculo vehiculo);

	Collection<Vehiculo> findAll();

	Collection<Vehiculo> findByMarca(String marca);

	Collection<Vehiculo> findByIdUser(int id);

	Vehiculo findById(int id);

}
