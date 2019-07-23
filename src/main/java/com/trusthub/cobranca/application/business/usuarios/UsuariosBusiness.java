package com.trusthub.cobranca.application.business.usuarios;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import com.trusthub.cobranca.application.exceptions.CobrancaAcessoBusinessException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoEmailException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoRepositoryException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoServiceException;
import com.trusthub.cobranca.application.service.email.EmailService;
import com.trusthub.cobranca.application.service.empresa.EmpresaService;
import com.trusthub.cobranca.application.service.perfil.PerfilService;
import com.trusthub.cobranca.application.service.usuario.UsuariosService;
import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.application.util.TemplateEmail;
import com.trusthub.cobranca.domain.dto.PerfilDTO;
import com.trusthub.cobranca.domain.dto.UsuarioDTO;
import com.trusthub.cobranca.domain.dto.email.EmailDTO;
import com.trusthub.cobranca.domain.enums.Perfil;
import com.trusthub.cobranca.security.JWTUtil;

import io.jsonwebtoken.Claims;

/**
 * Classe que contem as regras de usuario
 * @author alan.franco
 */
@Component
public class UsuariosBusiness {
	
	private static final Logger log = LoggerFactory.getLogger(UsuariosBusiness.class);

	@Autowired
	private UsuariosService usuarioService;
	@Autowired
	private PerfilService perfilService;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private JWTUtil jWTUtil;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private EmailService emailService;
	
	
	/**
	 * Retorna os dados do usuário logado
	 * @param email
	 * @param senha
	 * @return
	 */
	public UsuarioDTO usuarioLogado() {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		try {
			String token = request.getHeader(Constantes.AUTHORIZATION);
			Claims claims = jWTUtil.getClaims(token.substring(7));
			Long id = Long.parseLong(claims.getId());
			usuarioDTO =  this.consultarUsuarioPorId(id);
			usuarioDTO.setExpiration(claims.getExpiration() != null ? claims.getExpiration().getTime() : 0L);
			usuarioDTO.setEmpresaDTO(empresaService.consultarEmpresaPorId(usuarioDTO.getIdEmpresa()));
		}catch (CobrancaAcessoRepositoryException | CobrancaAcessoServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_USUARIO_LOGADO)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
		return usuarioDTO;
	}
	
	/**
	 * Cadastrar usuario
	 * @param usuarioDTO
	 */
	public void cadastrarUsuario(UsuarioDTO usuarioDTO) {
		try {
			UsuarioDTO usuarioCadastrado = usuarioService.consultarUsuarioPorEmail(usuarioDTO.getEmail());
			if(usuarioCadastrado.getId() == null) {
				usuarioDTO.setSenha(this.gerarSenha());
				Long idUsuario = usuarioService.cadastrarUsuario(usuarioDTO);
				Set<Perfil> perfis = usuarioDTO.getPerfis();
				perfis.forEach(perfil -> {
					PerfilDTO perfilDTO = new PerfilDTO(perfil.getCod(), idUsuario, perfil.toString());
					perfilService.cadastrarPerfil(perfilDTO);
				});
				this.envioDeEmail(Constantes.EMAIL_CADASTRO, usuarioDTO, Constantes.CADASTRAR);
			}else {
				throw new CobrancaAcessoBusinessException(Mensagens.BUSINESS_USUARIO_JA_CADASTRADO, CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS);
			}
		}catch (CobrancaAcessoRepositoryException | CobrancaAcessoServiceException | CobrancaAcessoEmailException e) {
			throw e;
		} catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_CADASTRAR_USUARIO)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
	}
	
	/**
	 * Envio de email
	 * @param assunto
	 * @param usuarioDTO
	 */
	private void envioDeEmail(String assunto,  UsuarioDTO usuarioDTO, String tipoEmail) {
		try {
			EmailDTO emailDTO = new EmailDTO();
			emailDTO.setAssunto(assunto);
			List<String> listaDestinatarios = new ArrayList<>();
			listaDestinatarios.add(usuarioDTO.getEmail());
			emailDTO.setDestinatarios(listaDestinatarios);
			if(tipoEmail.equals(Constantes.CADASTRAR)) {
				emailDTO.setBodyBase64(TemplateEmail.cadastrarSenhaBase64(usuarioDTO.getEmail(), usuarioDTO.getNome(), usuarioDTO.getSenha()));	
			}else if(tipoEmail.equals(Constantes.ALTERAR)) {
				emailDTO.setBodyBase64(TemplateEmail.alterarSenhaBase64(usuarioDTO.getEmail(), usuarioDTO.getNome(), usuarioDTO.getSenha()));
			}
			emailService.enviarEmail(emailDTO);
		} catch (CobrancaAcessoServiceException | HttpClientErrorException | CobrancaAcessoEmailException  e) {
			//Caso dê erro no acesso a API de Envio de email TRUSTHUB_COMUM_EMAIL, SOMENTE LOGA O ERRO E CONTINUA O PROCESSO.
			log.error(new StringBuilder(Constantes.ERRO_GERAR_SENHA_ACESSO)
					.append(usuarioDTO.getEmail()).append(Constantes.ESPACO_BRANCO)
						.append(Constantes.ERRO_COMPONENTE_EXTERNO + e.getMessage()).toString());
		}
		
	}
	
	
	/**
	 * Alterar dados do usuario
	 * @param id
	 * @param usuarioDTO
	 */
	public void alterarDadosUsuario(Long id, UsuarioDTO usuarioDTO) {
		try {
			if(id != null) {
				//Apagar os perfis
				PerfilDTO idUsuarioDTO = new PerfilDTO();
				idUsuarioDTO.setIdUsuario(id);
				perfilService.apagarPerfil(idUsuarioDTO);
				//altera os dados do usuario
	            usuarioService.alterarDadosUsuario(usuarioDTO);
				//Cadastra os novos perfis selecionados
	            Set<Perfil> perfis = usuarioDTO.getPerfis();
				perfis.forEach(perfil -> {
					PerfilDTO perfilDTO = new PerfilDTO(perfil.getCod(), id, perfil.toString());
					perfilService.cadastrarPerfil(perfilDTO);
				});
			}
		}catch (CobrancaAcessoRepositoryException | CobrancaAcessoServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_CADASTRAR_USUARIO)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
	}
	
	/**
	 * Listar Usuarios
	 * @return
	 */
	public List<UsuarioDTO> listarUsuarios() {
		List<UsuarioDTO> listaUsuariosDTO = new ArrayList<>();
		try {
			listaUsuariosDTO = usuarioService.listarUsuarios();
			listaUsuariosDTO.forEach(usuarioDTO -> {
				usuarioDTO.setEmpresaDTO(empresaService.consultarEmpresaPorId(usuarioDTO.getIdEmpresa()));
			});
			
		}catch (CobrancaAcessoRepositoryException | CobrancaAcessoServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_LISTAR_USUARIOS)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
		return listaUsuariosDTO;
	}
	
	/**
	 * Consultar usuario por email
	 * @param email
	 * @return
	 */
	public UsuarioDTO consultarUsuarioPorEmail(String email) {
		UsuarioDTO usuarioDTO = null;
		try {
			usuarioDTO = usuarioService.consultarUsuarioPorEmail(email);
			if(usuarioDTO.getId() == null) {
				throw new CobrancaAcessoBusinessException(Mensagens.BUSINESS_USUARIO_NAO_ENCONTRADO, CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, HttpStatus.NO_CONTENT);
			}
		}catch (CobrancaAcessoRepositoryException | CobrancaAcessoServiceException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_CONSULTAR_USUARIO_EMAIL)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
		return usuarioDTO;
	}
	
	/**
	 * Consultar usuario por id
	 * @param id
	 * @return
	 */
	public UsuarioDTO consultarUsuarioPorId(Long id) {
		UsuarioDTO usuarioDTO = null;
		try {
			//Consulta usuarios
			usuarioDTO = usuarioService.consultarUsuarioPorId(id);
			if(usuarioDTO.getId() != null) {
				usuarioDTO.setAcessos(this.getPerfisDoUsuario(usuarioDTO.getId()));
			}else{
				throw new CobrancaAcessoBusinessException(Mensagens.BUSINESS_USUARIO_NAO_ENCONTRADO, CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, HttpStatus.NO_CONTENT);
			}
		}catch (CobrancaAcessoRepositoryException | CobrancaAcessoServiceException | CobrancaAcessoBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_CONSULTAR_USUARIO_ID)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
		return usuarioDTO;
	}
	
	
	/**
	 * Busca perfis do usario
	 * @param id
	 * @return
	 */
	private Set<Integer> getPerfisDoUsuario(Long id){
		Set<Integer> perfis = new HashSet<>();
		try {
			PerfilDTO perfilDTO = new PerfilDTO();
			perfilDTO.setIdUsuario(id);
			List<PerfilDTO> listaPerfilDTO = perfilService.consultarPerfil(perfilDTO);
			listaPerfilDTO.forEach(dto -> {
				perfis.add(dto.getId());
			});
			
		}catch (CobrancaAcessoRepositoryException | CobrancaAcessoServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_CONSULTAR_PERFILS_USUARIO)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
		return perfis;
	}
	
	/**
	 * Apagar usuarios por id
	 * @param id
	 */
	public void apagarUsuarioPorId(Long id) {
		try {
			UsuarioDTO usuarioDTO = this.consultarUsuarioPorId(id);
			if(usuarioDTO != null) {
				usuarioService.apagarUsuarioPorId(id);
				PerfilDTO perfilDTO = new PerfilDTO();
				perfilDTO.setIdUsuario(usuarioDTO.getId());
				perfilService.apagarPerfil(perfilDTO);
			}
		}catch (CobrancaAcessoRepositoryException | CobrancaAcessoServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_APAGAR_USUARIO_POR_ID)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
	}
	
	
	
	/**
	 * Valida se o Id do token é o mesmo que está cadastrado na base de dados
	 * @param authorization
	 * @return
	 */
	public Boolean validaIdBaseIdToken(String authorization ) {
		Boolean idValido = false;
		try {
			Claims claims = jWTUtil.getClaims(authorization.substring(7));
		    UsuarioDTO usuarioDTO = consultarUsuarioPorEmail(claims.getSubject());
			if(usuarioDTO.getId() == Long.parseLong(claims.getId())) {
				idValido = true;
			}
		} catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_ID_BASE_ID_TOKEN)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
		return idValido;
	}
	
	/**
	 * Consulta o email, se existir gera uma nova senha, envio um novo email e 
	 * altera a senha na base codificada.
	 * @param email
	 * @return
	 */
	public void recuperarSenha(String email) {
		UsuarioDTO usuarioDTO = null;
		try {
			usuarioDTO = usuarioService.consultarUsuarioPorEmail(email);
			if(usuarioDTO.getId() == null) {
				throw new CobrancaAcessoBusinessException(Mensagens.BUSINESS_USUARIO_NAO_ENCONTRADO, CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, HttpStatus.BAD_REQUEST);
			}
			usuarioDTO.setSenha(this.gerarSenha());
			this.envioDeEmail(Constantes.EMAIL_NOVA_SENHA_GERADA, usuarioDTO, Constantes.ALTERAR);
			usuarioService.alterarSenha(usuarioDTO.getId(), usuarioDTO.getSenha());
		}catch (CobrancaAcessoRepositoryException | CobrancaAcessoServiceException | CobrancaAcessoBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_RECUPERAR_SENHA)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
	}
	
	/**
	 * Gerar Senha Randomica
	 * @return
	 */
	public String gerarSenha() {
		UUID uuid = UUID.randomUUID();	
		String myRandom = uuid.toString();
		return myRandom.substring(0,6);
	}
	
	/**
	 * Alterar Senha
	 * @param senha
	 * @return
	 */
	public void alterarSenha(String senha) {
		UsuarioDTO usuarioDTO = null;
		try {
			usuarioDTO = usuarioLogado();
			usuarioDTO.setSenha(senha);
			usuarioService.alterarSenha(usuarioDTO.getId(), usuarioDTO.getSenha());
		}catch (CobrancaAcessoRepositoryException | CobrancaAcessoServiceException | CobrancaAcessoBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_ALTERAR_SENHA)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
	}
	

}
