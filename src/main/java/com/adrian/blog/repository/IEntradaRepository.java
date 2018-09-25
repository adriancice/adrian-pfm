package com.adrian.blog.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.adrian.blog.model.Entrada;

@Repository("entradaRepository")
public interface IEntradaRepository extends PagingAndSortingRepository<Entrada, Integer> {

}
