package com.adrian.blog.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrian.blog.model.Filtro;
import com.adrian.blog.model.Vehiculo;

public interface IVehiculoService {

	Vehiculo save(Vehiculo vehiculo);

	void delete(Vehiculo vehiculo);

	Collection<Vehiculo> findAll();

	Collection<Vehiculo> findByMarca(String marca);

	Collection<Vehiculo> findByIdUser(int id);

	Vehiculo findById(int id);

	int totalVehiculos(Collection<Vehiculo> vehiculos);

	Collection<Vehiculo> filtrar(Filtro filtro);

	Collection<Vehiculo> findAllOrderByPrecio();

	Collection<Vehiculo> findAllOrderBykm();

	void deleteByIdUser(int id);

	List<Vehiculo> findVehiculosByUserFavorito(int idUser);

}
