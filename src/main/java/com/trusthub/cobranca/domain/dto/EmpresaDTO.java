package com.trusthub.cobranca.domain.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO - Empresa
 * @author alan.franco
 */
@Data
public class EmpresaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String cnpj;
	private String nome;
	
}
