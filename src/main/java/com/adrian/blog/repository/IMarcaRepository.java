package com.adrian.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrian.blog.model.Marca;

@Repository("marcaRepository")
public interface IMarcaRepository extends JpaRepository<Marca, Integer> {

	Marca findByIdMarca(int id);

}
