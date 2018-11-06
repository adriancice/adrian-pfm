package com.adrian.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "provincias")
public class Provincia {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_provincia", unique = true)
	private int idProvincia;

	private String provincia;

}
