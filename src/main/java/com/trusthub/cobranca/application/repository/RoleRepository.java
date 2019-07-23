package com.trusthub.cobranca.application.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoServiceException;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.repository.GenericJdbcTemplate;
import com.trusthub.cobranca.domain.entity.RoleEntity;


/**
 *  Classe que irá acessar a tabela (COB.ROLE)
 * @author alan.franco
 */
@Repository
public class RoleRepository {
	
	@Autowired
	private GenericJdbcTemplate genericJdbcTemplate;
	
	public List<RoleEntity> consultarRoles() {
		List<RoleEntity> listaRoleEntity = new ArrayList<>();
		try {
			listaRoleEntity = genericJdbcTemplate.queryForSQLName("consultar_roles", BeanPropertyRowMapper.newInstance(RoleEntity.class));
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.REPOSITORY_CONSULTAR_ROLES)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
		return listaRoleEntity;
	}

}
