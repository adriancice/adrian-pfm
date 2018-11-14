package com.adrian.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrian.blog.model.Anio;

@Repository("anioRepository")
public interface IAnioRepository extends JpaRepository<Anio, Short> {

}
