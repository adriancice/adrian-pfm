package com.adrian.blog.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.adrian.blog.model.Vehiculo;
import com.adrian.blog.repository.IVehiculoRepository;
import com.adrian.blog.service.IVehiculoService;

@Service("vehiculoService")
public class VehiculoServiceImpl implements IVehiculoService {

	private IVehiculoRepository vehiculoRepository;

	public VehiculoServiceImpl(IVehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}

	@Override
	public Vehiculo save(Vehiculo vehiculo) {
		return vehiculoRepository.save(vehiculo);
	}

	@Override
	public void delete(Vehiculo vehiculo) {
		vehiculoRepository.delete(vehiculo);
	}

	@Override
	public Collection<Vehiculo> findAll() {
		Collection<Vehiculo> vehiculos = new ArrayList<>();
		for (Vehiculo v : vehiculoRepository.findAll()) {
			vehiculos.add(v);
		}
		return vehiculos;
	}

	@Override
	public Collection<Vehiculo> findByMarca(String marca) {
		Collection<Vehiculo> vehiculos = new ArrayList<>();
		for (Vehiculo v : vehiculoRepository.findByMarca(marca)) {
			vehiculos.add(v);
		}
		return vehiculos;
	}

	@Override
	public Collection<Vehiculo> findByIdUser(int id) {
		return vehiculoRepository.findByIdUser(id);
	}

	@Override
	public Vehiculo findById(int id) {
		return vehiculoRepository.findById(id).orElse(null);
	}

}
