package com.adrian.blog.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrian.blog.model.Foto;

@Repository("fotoRepository")
public interface IFotoRepository extends JpaRepository<Foto, Integer> {

	Collection<Foto> findByIdVehiculo(int idVehiculo);

}
