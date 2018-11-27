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
@Table(name = "modelos")
public class Modelo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_modelo", unique = true)
	private int idModelo;

	@Column(name = "id_marca")
	private int idMarca;

	private String modelo;
}
