package com.trusthub.cobranca.domain.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.trusthub.cobranca.domain.enums.Perfil;

import lombok.Data;

/**
 * DTO - LOGIN
 * @author alan.franco
 */
@Data
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long expiration;
	private Integer idEmpresa;
	private String nome;
	private String email;
	private String senha;
	private EmpresaDTO empresaDTO;
	
	private Set<Integer> perfis = new HashSet<>();
	private Set<Integer> acessos = new HashSet<>();
	
	public UsuarioDTO() {
	}
	
	public Set<Perfil> getPerfis(){
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}
	
	
	
	
	
	
}
