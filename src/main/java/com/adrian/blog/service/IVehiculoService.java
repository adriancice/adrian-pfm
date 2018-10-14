package com.adrian.blog.service;

import java.util.Collection;

import com.adrian.blog.model.Vehiculo;

public interface IVehiculoService {

	Vehiculo save(Vehiculo vehiculo);

	Vehiculo update(Vehiculo vehiculo);

	Vehiculo delete(Vehiculo vehiculo);

	Collection<Vehiculo> findAll();

}
