package com.adrian.blog.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrian.blog.model.Vehiculo;

@Repository("vehiculoRepository")
public interface IVehiculoRepository extends JpaRepository<Vehiculo, Integer> {

	Collection<Vehiculo> findByMarca(String marca);

	Collection<Vehiculo> findByIdUser(int id);
	


}
