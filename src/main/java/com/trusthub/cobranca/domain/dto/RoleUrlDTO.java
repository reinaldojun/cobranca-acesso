package com.trusthub.cobranca.domain.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO - Role_URL
 * @author alan.franco
 */
@Data
public class RoleUrlDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer idRole;
	private String method;
	private String url;
	private String modulo;
	private String metodo;
	
}
