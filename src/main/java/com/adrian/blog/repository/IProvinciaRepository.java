package com.adrian.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrian.blog.model.Provincia;

@Repository("provinciaRepository")
public interface IProvinciaRepository extends JpaRepository<Provincia, Integer> {

}
