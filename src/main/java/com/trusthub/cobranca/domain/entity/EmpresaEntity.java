package com.trusthub.cobranca.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Data;

/**
 * Entity - Empresa
 * @author alan.franco
 */

@Data
@Entity
public class EmpresaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String cnpj;
	private String nome;
	
}
