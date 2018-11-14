package com.adrian.blog.service.impl;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.blog.model.Filtro;
import com.adrian.blog.model.Vehiculo;
import com.adrian.blog.repository.IVehiculoRepository;
import com.adrian.blog.service.IVehiculoService;

@Service("vehiculoService")
public class VehiculoServiceImpl implements IVehiculoService {

	@Autowired
	private IVehiculoRepository vehiculoRepository;

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

	@Override
	public int totalVehiculos(Collection<Vehiculo> vehiculos) {
		int contador = 0;
		for (Vehiculo v : vehiculos) {
			contador++;
		}
		return contador;
	}

	@Override
	public Collection<Vehiculo> filtrar(Filtro filtro) {
		Collection<Vehiculo> vehiculos = new ArrayList<>();
		int pMin, pMax, anioMin, anioMax, kmMin, kmMax;
		if (filtro.getPrecioMin().isEmpty()) {
			pMin = 0;
		} else {
			pMin = Integer.parseInt(filtro.getPrecioMin());
		}
		if (filtro.getPrecioMax().isEmpty()) {
			pMax = 999999;
		} else {
			pMax = Integer.parseInt(filtro.getPrecioMax());
		}

		if (filtro.getAnioMin().isEmpty()) {
			anioMin = 1950;
		} else {
			anioMin = Integer.parseInt(filtro.getAnioMin());
		}
		if (filtro.getAnioMax().isEmpty()) {
			anioMax = 2999;
		} else {
			anioMax = Integer.parseInt(filtro.getAnioMax());
		}

		if (filtro.getKmMin().isEmpty()) {
			kmMin = 0;
		} else {
			kmMin = Integer.parseInt(filtro.getKmMin());
		}
		if (filtro.getKmMax().isEmpty()) {
			kmMax = 999999;
		} else {
			kmMax = Integer.parseInt(filtro.getKmMax());
		}
		
		//buscamos la palabra clave en los campos de: marca-modelo-descripcion-combustible-provincia-color
		for (Vehiculo v : vehiculoRepository.findAll()) {
			if (v.getMarca().toLowerCase().contains(filtro.getPalabra().toLowerCase())
					|| v.getModelo().toLowerCase().contains(filtro.getPalabra().toLowerCase())
					|| v.getDescripcion().toLowerCase().contains(filtro.getPalabra().toLowerCase())
					|| v.getCombustible().toLowerCase().contains(filtro.getPalabra().toLowerCase())
					|| normalizeString(v.getProvincia()).toLowerCase().contains(normalizeString(filtro.getPalabra()).toLowerCase())
					|| v.getColor().toLowerCase().contains(filtro.getPalabra().toLowerCase())) {
				vehiculos.add(v);
			}
		}
		
		//filtramos por: precio-año-kilometros
		Iterator<Vehiculo> it = vehiculos.iterator();
		try {
			while (it.hasNext()) {
				Vehiculo v = it.next();
				if (v.getPrecio() < pMin || v.getPrecio() > pMax) {
					it.remove();
				}
				if (v.getAnio() < anioMin || v.getAnio() > anioMax) {
					it.remove();
				}
				if (v.getKilometros() < kmMin || v.getKilometros() > kmMax) {
					it.remove();
				}
			}

		} catch (Exception e) {
			System.err.println("Error");
		}

		return vehiculos;
	}

	/**
	 * Metodo para ignorar los accentos de los string
	 * 
	 * @param str
	 * @return str sin accentos
	 */
	public static String normalizeString(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFKD);
		return str.replaceAll("[^a-z,^A-Z,^0-9]", "");
	}

}
