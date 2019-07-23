package com.trusthub.cobranca.application.service.usuario;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoRepositoryException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoServiceException;
import com.trusthub.cobranca.application.repository.UsuariosRepository;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.domain.dto.UsuarioDTO;
import com.trusthub.cobranca.domain.entity.UsuarioEntity;


/**
 * Service que acessa servicos do Usuario 
 * @author alan.franco
 */
@Service
public class UsuariosService {
	
	@Autowired
	UsuariosRepository usuarioRepository;
	@Autowired
	private BCryptPasswordEncoder pe;
	
	
	/**
	 * Consulta dados para o login
	 * @param usuario
	 * @param senha
	 * @return
	 */
	public UsuarioDTO login(String usuario, String senha) {
		return toDTO(usuarioRepository.login(usuario, senha));
	}
	
	/**
	 * Cadastrar Usuario
	 * @param usuarioDTO
	 * @return Long
	 */
	public Long cadastrarUsuario(UsuarioDTO usuarioDTO) {
		try {
			return usuarioRepository.cadastrarUsuario(toEntity(usuarioDTO));
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_CADASTRAR_USUARIO)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
	}
	
	/**
	 * Alterar dados do Usuario
	 * @param usuarioDTO
	 */
	public void alterarDadosUsuario(UsuarioDTO usuarioDTO) {
		try {
			usuarioRepository.alterarDadosUsuario(toEntity(usuarioDTO));
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_ALTERAR_USUARIO)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
	}
	
	/**
	 * Listar Usuarios
	 * @return
	 */
	public List<UsuarioDTO> listarUsuarios() {
		List<UsuarioDTO> listaUsuarioDTO = new ArrayList<>();
		try {
				usuarioRepository.listarUsuarios().forEach(entity -> {
					listaUsuarioDTO.add(toDTO(entity));
				});
			return listaUsuarioDTO;
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_LISTAR_USUARIOS)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
	}
	
	/**
	 * Consultar Usuarios por email
	 * @param email
	 * @return
	 */
	public UsuarioDTO consultarUsuarioPorEmail(String email) {
		try {
			return toDTO(usuarioRepository.consultarUsuarioPorEmail(email));
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_CONSULTAR_USUARIO_EMAIL)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
	}
	
	/**
	 * Consultar Usuarios por id
	 * @param id
	 * @return
	 */
	public UsuarioDTO consultarUsuarioPorId(Long id) {
		try {
			return toDTO(usuarioRepository.consultarUsuarioPorId(id));
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_CONSULTAR_USUARIO_ID)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
	}
	
	
	/**
	 * Apagar usuarios por id
	 * @param id
	 */
	public void apagarUsuarioPorId(Long id) {
		try {
			usuarioRepository.apagarUsuarioPorId(id);
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_APAGAR_USUARIO_ID)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
		
	}
	
	/**
	 * Alterar Senha
	 * @param id
	 * @param senha
	 */
	public void alterarSenha(Long id, String senha) {
		try {
			usuarioRepository.alterarSenha(id, pe.encode(senha));
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_APAGAR_USUARIO_ID)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
		
	}
	
	/**
	 * Converte Entity para DTO
	 * @param entity
	 * @return UsuarioDTO
	 */
	public UsuarioDTO toDTO(UsuarioEntity entity){
		UsuarioDTO dto = new UsuarioDTO();
		BeanUtils.copyProperties(entity, dto); 
		dto.setSenha(null);
		return dto ;
	}
	
	/**
	 * Converte DTO para Entity 
	 * @param dto 
	 * @return UsuarioEntity 
	 */
	public UsuarioEntity toEntity(UsuarioDTO dto){
		UsuarioEntity entity = new UsuarioEntity();
		BeanUtils.copyProperties(dto, entity);
		if(dto.getSenha() != null) {
			entity.setSenha(pe.encode(dto.getSenha()));
		}
		return entity;
	}

}
