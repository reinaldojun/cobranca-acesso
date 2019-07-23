package com.trusthub.cobranca.application.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoRepositoryException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoServiceException;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.repository.GenericJdbcTemplate;
import com.trusthub.cobranca.domain.entity.PerfilEntity;

/**
 *  Classe que irá acessar a tabela (COB.PERFIL) 
 * @author alan.franco
 */
@Repository
public class PerfilRepository {
	
	@Autowired
	private GenericJdbcTemplate genericJdbcTemplate;
	
	/**
	 * Cadastrar Perfil
	 * @param perfilEntity
	 */
	public void cadastrarPerfil(PerfilEntity perfilEntity) {
		try {
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("idUsuario", perfilEntity.getIdUsuario());
			parametro.addValue("id", perfilEntity.getId());
			parametro.addValue("nome",perfilEntity.getNome());
			genericJdbcTemplate.updateForSQLName("cadastrar_perfil", parametro);
		}catch (Exception e) {
			throw new CobrancaAcessoRepositoryException(new StringBuilder(Mensagens.REPOSITORY_CADASTRAR_PERFIL)
					.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY, e);
		}			
	}
	
	/**
	 * Apagar Perfil
	 * @param perfilEntity
	 */
	public void apagarPerfil(PerfilEntity perfilEntity) {
		try {
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("idUsuario", perfilEntity.getIdUsuario());
			genericJdbcTemplate.updateForSQLName("apagar_perfil", parametro);
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.REPOSITORY_APAGAR_PERFIL)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
	}
	
	/**
	 * Consultar Perfil
	 * @param perfilEntity
	 * @return List<PerfilEntity> - Lista de Perfil
	 */
	public List<PerfilEntity> consultarPerfil(PerfilEntity perfilEntity) {
		List<PerfilEntity> listaPerfilEntity = new ArrayList<>();
		try {
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("idUsuario", perfilEntity.getIdUsuario());
			listaPerfilEntity = genericJdbcTemplate.queryForSQLName("consultar_perfil", parametro, BeanPropertyRowMapper.newInstance(PerfilEntity.class));
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.REPOSITORY_CONSULTAR_PERFIL)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
		return listaPerfilEntity;
	}
	

}
