package com.adrian.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrian.blog.model.Modelo;

@Repository("modeloRepository")
public interface IModeloRepository extends JpaRepository<Modelo, Integer> {

	List<Modelo> findByIdMarca(int idMarca);

}
