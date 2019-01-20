package com.adrian.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adrian.blog.model.Vehiculo;

@Repository("vehiculoRepository")
public interface IVehiculoRepository extends JpaRepository<Vehiculo, Integer> {

	Collection<Vehiculo> findByMarca(String marca);

	Collection<Vehiculo> findByIdUserOrderByFechaMilisegundosDesc(int id);

	@Query(value = "SELECT * FROM vehiculos WHERE id in(SELECT id_vehiculo FROM favoritos WHERE id_favorito in(SELECT id_favorito FROM favoritos WHERE id_user = :id_user))", nativeQuery = true)
	Optional<List<Vehiculo>> findByUserFavorito(@Param("id_user") int idUser);

	Page<Vehiculo> findAllByOrderByFechaMilisegundosDesc(Pageable pageable);

}
