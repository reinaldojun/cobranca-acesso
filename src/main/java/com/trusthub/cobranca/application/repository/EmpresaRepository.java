package com.trusthub.cobranca.application.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoRepositoryException;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.repository.GenericJdbcTemplate;
import com.trusthub.cobranca.domain.entity.EmpresaEntity;


/**
 * Responsavel por operacoes de acesso a banco de dados referente a empresa (COB.EMPRESA)
 * @author alan.franco
 */
@Repository
public class EmpresaRepository {
	
	@Autowired
	private GenericJdbcTemplate genericJdbcTemplate;
	
	/**
	 * Lista todas empresas
	 * @return List<EmpresaEntity>
	 */
	public List<EmpresaEntity> listarEmpresas() {
		List<EmpresaEntity> listaEmpresaEntity = new ArrayList<>();
		try {
			listaEmpresaEntity = genericJdbcTemplate.queryForSQLName("listar_empresas", BeanPropertyRowMapper.newInstance(EmpresaEntity.class));
		}catch (Exception e) {
			throw new CobrancaAcessoRepositoryException(new StringBuilder(Mensagens.REPOSITORY_LISTAR_EMPRESAS)
					.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY, e);
		}
		return listaEmpresaEntity;
	}
	
	/**
	 * Consultar empresa pelo Id
	 * @param idEmpresa - Id da Empresa
	 * @return
	 */
	public EmpresaEntity consultarEmpresaPorId(Integer idEmpresa) {
		List<EmpresaEntity> listaEmpresaEntity = null;
		EmpresaEntity empresaEntity = new EmpresaEntity();
		try {
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("id", idEmpresa);
			listaEmpresaEntity = genericJdbcTemplate.queryForSQLName("consultar_empresa_por_id", parametro, BeanPropertyRowMapper.newInstance(EmpresaEntity.class));
			if(!listaEmpresaEntity.isEmpty()){
				empresaEntity = listaEmpresaEntity.get(0);
			}
		}catch (Exception e) {
			throw new CobrancaAcessoRepositoryException(new StringBuilder(Mensagens.REPOSITORY__CONSULTAR_EMPRESA_POR_ID)
					.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY, e);
		}
		return empresaEntity;
	}
	
	/**
	 * Consultar empresa pelo CNPJ
	 * @param cnpj
	 * @return EmpresaEntity
	 */
	public EmpresaEntity consultarEmpresaPorCnpj(String cnpj) {
		List<EmpresaEntity> listaEmpresaEntity = null;
		EmpresaEntity empresaEntity = new EmpresaEntity();
		try {
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("cnpj", cnpj);
			listaEmpresaEntity = genericJdbcTemplate.queryForSQLName("consultar_empresa_por_cnpj", parametro, BeanPropertyRowMapper.newInstance(EmpresaEntity.class));
			if(!listaEmpresaEntity.isEmpty()){
				empresaEntity = listaEmpresaEntity.get(0);
			}
		}catch (Exception e) {
			throw new CobrancaAcessoRepositoryException(new StringBuilder(Mensagens.REPOSITORY__CONSULTAR_EMPRESA_POR_CNPJ)
					.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY, e);
		}
		return empresaEntity;
	}
	
	/**
	 * Cadastrar Empresas 
	 * @param empresaEntity
	 * @return
	 */
	public Long cadastrarEmpresa(EmpresaEntity empresaEntity) {
		Long id = 0L;
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("cnpj", empresaEntity.getCnpj());
			parametro.addValue("nome", empresaEntity.getNome());
			int returnSql = genericJdbcTemplate.updateForSQLName("cadastrar_empresa", parametro, keyHolder);
			if (returnSql != 1 && keyHolder.getKey() == null) {
				throw new CobrancaAcessoRepositoryException(Mensagens.REPOSITORY_ERRO_CADASTRAR_EMPRESA, CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY);
			}else {
				id = keyHolder.getKey().longValue();
			}
		}catch (Exception e) {
			throw new CobrancaAcessoRepositoryException(new StringBuilder(Mensagens.REPOSITORY_CADASTRAR_EMPRESA)
					.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY, e);
		}	
		return id;
	}
	
	/**
	 * Apagar empreda por id
	 * @param id
	 */
	public void apagarEmpresaPorId(Long id) {
		try {
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("id", id);
			genericJdbcTemplate.updateForSQLName("apagar_cadastro_empresa", parametro);
		}catch (Exception e) {
			throw new CobrancaAcessoRepositoryException(new StringBuilder(Mensagens.REPOSITORY_APAGAR_EMPRESA_ID)
					.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY, e);
		}	
	}
	

}
