package com.adrian.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrian.blog.model.Combustible;

@Repository("combustibleRepository")
public interface ICombustibleRepository extends JpaRepository<Combustible, Integer>{

}
