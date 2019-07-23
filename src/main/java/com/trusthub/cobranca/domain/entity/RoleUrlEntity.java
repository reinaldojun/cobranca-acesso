package com.trusthub.cobranca.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Data;

/**
 * Entity - Role_URL
 * @author alan.franco
 */
@Data
@Entity
public class RoleUrlEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer idRole;
	private String method;
	private String url;
	private String modulo;
	private String metodo;
	
}
