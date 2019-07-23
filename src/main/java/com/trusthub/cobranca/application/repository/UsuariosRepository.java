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
import com.trusthub.cobranca.domain.entity.UsuarioEntity;


/**
 * Classe que irá realizar operações de CRUD na tabela (COB.USUARIO)
 * @author alan.franco
 */
@Repository
public class UsuariosRepository {
	
	@Autowired
	private GenericJdbcTemplate genericJdbcTemplate;
	
	/**
	 * Cadastrar Usuario
	 * @param usuarioEntity
	 * @return
	 */
	public Long cadastrarUsuario(UsuarioEntity usuarioEntity) {
		Long id = 0L;
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("nome", usuarioEntity.getNome());
			parametro.addValue("email", usuarioEntity.getEmail());
			parametro.addValue("senha",usuarioEntity.getSenha());
			parametro.addValue("idEmpresa", usuarioEntity.getIdEmpresa());
			int returnSql = genericJdbcTemplate.updateForSQLName("cadastrar_usuario", parametro, keyHolder);
			if (returnSql != 1 && keyHolder.getKey() == null) {
				throw new CobrancaAcessoRepositoryException(Mensagens.REPOSITORY_ERRO_CADASTRAR_USUARIO , CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY);
			}else {
				id = keyHolder.getKey().longValue();
			}
		}catch (Exception e) {
			throw new CobrancaAcessoRepositoryException(new StringBuilder(Mensagens.REPOSITORY_CADASTRAR_USUARIO)
					.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY, e);
		}	
		return id;
	}
	
	/**
	 * Alterar dados do Usuário
	 * @param usuarioEntity (Poderá ser alterado nome, id da empresa)
	 */
	public void alterarDadosUsuario(UsuarioEntity usuarioEntity) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("nome", usuarioEntity.getNome());
			parametro.addValue("idEmpresa", usuarioEntity.getIdEmpresa());
			parametro.addValue("id", usuarioEntity.getId());
			genericJdbcTemplate.updateForSQLName("alterar_usuario", parametro, keyHolder);
		}catch (Exception e) {
			throw new CobrancaAcessoRepositoryException(new StringBuilder(Mensagens.REPOSITORY_ALTERAR_USUARIO)
					.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY, e);
		}	
	}
	
	/**
	 * Listar Usuários
	 * @return List<UsuarioEntity> - Lista com os dados do usuário
	 */
	public List<UsuarioEntity> listarUsuarios() {
		List<UsuarioEntity> retorno = new ArrayList<>();
		try {
			retorno = genericJdbcTemplate.queryForSQLName("listar_usuarios", BeanPropertyRowMapper.newInstance(UsuarioEntity.class));
		}catch (Exception e) {
			throw new CobrancaAcessoRepositoryException(new StringBuilder(Mensagens.REPOSITORY_LISTAR_USUARIOS)
					.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY, e);
		}
		return retorno;
	}
	
	/**
	 * Consultar Usuario por email
	 * @param email
	 * @return UsuarioEntity
	 */
	public UsuarioEntity consultarUsuarioPorEmail(String email) {
		List<UsuarioEntity> listaUsuarioEntity = null;
		UsuarioEntity usuarioEntity = new UsuarioEntity();
		try {
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("email", email);
			listaUsuarioEntity = genericJdbcTemplate.queryForSQLName("consultar_usuario_email", parametro, BeanPropertyRowMapper.newInstance(UsuarioEntity.class));
			if(!listaUsuarioEntity.isEmpty()){
				usuarioEntity = listaUsuarioEntity.get(0);
			}
		}catch (Exception e) {
			throw new CobrancaAcessoRepositoryException(new StringBuilder(Mensagens.REPOSITORY_CONSULTAR_USUARIO_EMAIL)
					.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY, e);
		}	
		return usuarioEntity;
	}
	
	/**
	 * Consultar Usuario por Id
	 * @param id
	 * @return UsuarioEntity
	 */
	public UsuarioEntity consultarUsuarioPorId(Long id) {
		List<UsuarioEntity> listaUsuarioEntity = null;
		UsuarioEntity usuarioEntity = new UsuarioEntity();
		try {
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("id", id);
			listaUsuarioEntity = genericJdbcTemplate.queryForSQLName("consultar_usuario_id", parametro, BeanPropertyRowMapper.newInstance(UsuarioEntity.class));
			if(!listaUsuarioEntity.isEmpty()){
				usuarioEntity = listaUsuarioEntity.get(0);
			}
		}catch (Exception e) {
			throw new CobrancaAcessoRepositoryException(new StringBuilder(Mensagens.REPOSITORY_CONSULTAR_USUARIO_ID)
					.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY, e);
		}	
		return usuarioEntity;
	}
	
	/**
	 * Apagar Usuario por id
	 * @param id
	 */
	public void apagarUsuarioPorId(Long id) {
		try {
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("id", id);
			genericJdbcTemplate.updateForSQLName("apagar_cadastro_usuario", parametro);
		}catch (Exception e) {
			throw new CobrancaAcessoRepositoryException(new StringBuilder(Mensagens.REPOSITORY_APAGAR_USUARIO_ID)
					.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY, e);
		}	
	}
	
	/**
	 * Busca dados para realizar login 
	 * @param email
	 * @param senha
	 * @return
	 */
	public UsuarioEntity login(String email, String senha){
		UsuarioEntity usuarioEntity = new UsuarioEntity();
		try {
			List<UsuarioEntity> listaUsuarios = this.listarUsuarios();
			for (UsuarioEntity user : listaUsuarios) {
				if(user.getEmail().equalsIgnoreCase(email)) {
					usuarioEntity = user;
				}
			}
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoRepositoryException(new StringBuilder(Mensagens.REPOSITORY_LOGIN)
					.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY, e);
		}
		return usuarioEntity;
	}
	
	/**
	 * Alterar Senha
	 * @param senha
	 */
	public void alterarSenha(Long id, String senha) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("senha", senha);
			parametro.addValue("id", id);
			genericJdbcTemplate.updateForSQLName("alterar_senha", parametro, keyHolder);
		}catch (Exception e) {
			throw new CobrancaAcessoRepositoryException(new StringBuilder(Mensagens.REPOSITORY_ALTERAR_SENHA)
					.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_REPOSITORY, e);
		}	
	}

}
