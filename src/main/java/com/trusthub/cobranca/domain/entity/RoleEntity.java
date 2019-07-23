package com.trusthub.cobranca.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Data;

/**
 * Entity - Role
 * @author alan.franco
 */
@Data
@Entity
public class RoleEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String role;
	private String descricao;
	
}
