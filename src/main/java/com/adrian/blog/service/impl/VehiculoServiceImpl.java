package com.adrian.blog.service.impl;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

		// seteamos los precios, años y km minimo/maximo si el input es vacio
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

		// buscamos la palabra clave en los campos de:
		// marca-modelo-descripcion-combustible-provincia-color
		String palabra = filtro.getPalabra();
		cleanString(palabra);
		System.out.println("Cuantas veces?" + palabra);
		String[] palabras = palabra.toLowerCase().split(" ");
		for (Vehiculo v : vehiculoRepository.findAll()) {
			// concatenamos todos los atributos del vehiculos en un String
			String c = v.getMarca().concat(" " + v.getModelo()).concat(" " + v.getColor()).concat(" " + v.getCombustible()).concat(" " + v.getTipoCambio())
					.concat(" " + v.getProvincia()).concat(" " + v.getDescripcion()).concat(" " + v.getAnio());
			System.err.println("Coche: : " + c);
			// comprobamos si el string que concatenamos mas arriba contiene todos los
			// string del array de 'palabras'
			if (Stream.of(palabras).allMatch(c.toLowerCase()::contains)) {
				vehiculos.add(v);
			}

		}

		// filtramos por: precio-año-kilometros
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

	public static String cleanString(String texto) {
		texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
		return texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	}

	@Override
	public Collection<Vehiculo> findAllOrderByPrecio() {
		Collection<Vehiculo> vehiculos = new ArrayList<>();
		for (Vehiculo v : vehiculoRepository.findAll(new Sort(Sort.Direction.ASC, "precio"))) {
			vehiculos.add(v);
		}
		return vehiculos;
	}

	@Override
	public Collection<Vehiculo> findAllOrderBykm() {
		Collection<Vehiculo> vehiculos = new ArrayList<>();
		for (Vehiculo v : vehiculoRepository.findAll(new Sort(Sort.Direction.ASC, "kilometros"))) {
			vehiculos.add(v);
		}
		return vehiculos;
	}

	@Override
	public void deleteByIdUser(int id) {
		for (Vehiculo v : vehiculoRepository.findAll()) {
			if (v.getIdUser() == id) {
				vehiculoRepository.delete(v);
			}
		}

	}

	@Override
	public List<Vehiculo> findVehiculosByUserFavorito(int idUser) {

		Optional<List<Vehiculo>> searchVehiculo = vehiculoRepository.findByUserFavorito(idUser);
		if (searchVehiculo.isPresent()) {
			return searchVehiculo.get();
		}
		return null;
	}

	@Override
	public Page<Vehiculo> findAll(Pageable pageable) {
		return vehiculoRepository.findAll(pageable);
	}

}
