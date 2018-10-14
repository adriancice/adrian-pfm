package com.adrian.blog.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.adrian.blog.model.Vehiculo;

public interface IVehiculoRepository extends PagingAndSortingRepository<Vehiculo, Integer>{

}
