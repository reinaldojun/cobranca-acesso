package com.trusthub.cobranca.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * DTO - Role
 * @author alan.franco
 */
@Data
public class RoleDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String role;
	private String descricao;
	private List<RoleUrlDTO> listaRoleUrlDTO = new ArrayList<>();
	
}
