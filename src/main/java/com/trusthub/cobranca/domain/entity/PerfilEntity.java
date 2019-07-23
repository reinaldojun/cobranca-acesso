package com.trusthub.cobranca.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - Perfil
 * @author alan.franco
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PerfilEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Long idUsuario;
	private String nome;
	
}
