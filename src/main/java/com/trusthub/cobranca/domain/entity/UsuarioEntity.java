package com.trusthub.cobranca.domain.entity;

import java.io.Serializable;
import javax.persistence.Entity;

import lombok.Data;

/**
 * Entity - Usuario
 * @author alan.franco
 */

@Data
@Entity
public class UsuarioEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private Integer idEmpresa;
	
}
