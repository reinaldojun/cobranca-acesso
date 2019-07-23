package com.trusthub.cobranca.application.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoServiceException;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.repository.GenericJdbcTemplate;
import com.trusthub.cobranca.domain.entity.RoleUrlEntity;

/**
 * Classe que irá acessar a tabela (COB.ROLE_URL)
 * @author alan.franco
 */
@Repository
public class RoleUrlRepository {
	
	@Autowired
	private GenericJdbcTemplate genericJdbcTemplate;
	
	/**
	 * Listar as Urls de acordo com a role específica.
	 * @param idRole
	 * @return
	 */
	public List<RoleUrlEntity> consultarRoleUrl(Integer idRole) {
		List<RoleUrlEntity> listaRoleUrlEntity = new ArrayList<>();
		try {
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("idRole", idRole);
			listaRoleUrlEntity = genericJdbcTemplate.queryForSQLName("consultar_roles_url", parametro, BeanPropertyRowMapper.newInstance(RoleUrlEntity.class));
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.REPOSITORY_CONSULTAR_ROLES_URL)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
		return listaRoleUrlEntity;
	}
	

}
