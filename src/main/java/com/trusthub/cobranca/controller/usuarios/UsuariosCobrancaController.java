package com.trusthub.cobranca.controller.usuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trusthub.cobranca.application.business.usuarios.UsuariosBusiness;
import com.trusthub.cobranca.domain.dto.UsuarioDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * API - Controller -  Servicos referentes ao usuário
 * @author alan.franco
 */
@RestController
@Validated
@CrossOrigin(value = "*")
@Api(value = "API - Cobranca Acesso - Serviços referentes a acessos")
@RequestMapping("/trusthub-cobranca-acesso/v1")
public class UsuariosCobrancaController {
	
	@Autowired
	private UsuariosBusiness usuarioBusiness;
	
	@ApiOperation(value = "Usuário Logado")
	@ApiResponses(
			value= {
					@ApiResponse(code = 200 , message = "Sucessfull"),
					@ApiResponse(code = 401 , message = "Unauthorized"),
					@ApiResponse(code = 403 , message = "Access denied"),
					@ApiResponse(code = 500, message = "Erro Internal Server Error: Contact your support \n"
														+ "Mensagem de erro:  { timestamp, errorCode, errorDescription, message, path  }  \n"							
														+ "  - errorCode: 1000 - Erro na api (Cobrança Acesso) na camada de Business \n"
														+ "  - errorCode: 2000 - Erro na api (Cobrança Acesso) na camada de Service \n"
														+ "  - errorCode: 3000 - Erro na api (Cobrança Acesso) na camada de Acesso a dados \n"
							)
			}
	)
	@GetMapping("/usuarioLogado")
	public ResponseEntity<UsuarioDTO> usuarioLogado(){
		UsuarioDTO loginDTO = usuarioBusiness.usuarioLogado();
		return ResponseEntity.ok().body(loginDTO);
	}
	
	
	
	@ApiOperation(value = "Cadastrar Usuario")
	@ApiResponses(
			value= {
					@ApiResponse(code = 200 , message = "Sucessfull"),
					@ApiResponse(code = 401 , message = "Unauthorized"),
					@ApiResponse(code = 403 , message = "Access denied"),
					@ApiResponse(code = 500, message = "Erro Internal Server Error: Contact your support \n"
														+ "Mensagem de erro:  { timestamp, errorCode, errorDescription, message, path  }  \n"							
														+ "  - errorCode: 1000 - Erro na api (Cobrança Acesso) na camada de Business \n"
														+ "  - errorCode: 2000 - Erro na api (Cobrança Acesso) na camada de Service \n"
														+ "  - errorCode: 3000 - Erro na api (Cobrança Acesso) na camada de Acesso a dados \n"
							)
			}
	)
	@PostMapping(value = "/usuario")
	public ResponseEntity<Void> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		usuarioBusiness.cadastrarUsuario(usuarioDTO);
		return ResponseEntity.ok().build();
	}
	
	
	@ApiOperation(value = "Alterar dados do Usuário")
	@ApiResponses(
			value= {
					@ApiResponse(code = 200 , message = "Sucessfull"),
					@ApiResponse(code = 401 , message = "Unauthorized"),
					@ApiResponse(code = 403 , message = "Access denied"),
					@ApiResponse(code = 500, message = "Erro Internal Server Error: Contact your support \n"
														+ "Mensagem de erro:  { timestamp, errorCode, errorDescription, message, path  }  \n"							
														+ "  - errorCode: 1000 - Erro na api (Cobrança Acesso) na camada de Business \n"
														+ "  - errorCode: 2000 - Erro na api (Cobrança Acesso) na camada de Service \n"
														+ "  - errorCode: 3000 - Erro na api (Cobrança Acesso) na camada de Acesso a dados \n"
							)
			}
	)
	@PutMapping(value = "/usuario/{id}")
	public ResponseEntity<Void> alterarDadosUsuario(@PathVariable(value = "id", required = true) Long id, @RequestBody UsuarioDTO usuarioDTO) {
		usuarioBusiness.alterarDadosUsuario(id, usuarioDTO);
		return ResponseEntity.ok().build();
	}
	
	
	@ApiOperation(value = "Listar Usuarios")
	@ApiResponses(
			value= {
					@ApiResponse(code = 200 , message = "Sucessfull"),
					@ApiResponse(code = 401 , message = "Unauthorized"),
					@ApiResponse(code = 403 , message = "Access denied"),
					@ApiResponse(code = 500, message = "Erro Internal Server Error: Contact your support \n"
														+ "Mensagem de erro:  { timestamp, errorCode, errorDescription, message, path  }  \n"							
														+ "  - errorCode: 1000 - Erro na api (Cobrança Acesso) na camada de Business \n"
														+ "  - errorCode: 2000 - Erro na api (Cobrança Acesso) na camada de Service \n"
														+ "  - errorCode: 3000 - Erro na api (Cobrança Acesso) na camada de Acesso a dados \n"
							)
			}
	)
	@GetMapping(value = "/usuarios")
	public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
		List<UsuarioDTO> listaUsuarioDTO = usuarioBusiness.listarUsuarios();
		return ResponseEntity.ok().body(listaUsuarioDTO);
	}
	
	@ApiOperation(value = "Consultar Usuário Por Email")
	@ApiResponses(
			value= {
					@ApiResponse(code = 200 , message = "Sucessfull"),
					@ApiResponse(code = 401 , message = "Unauthorized"),
					@ApiResponse(code = 403 , message = "Access denied"),
					@ApiResponse(code = 500, message = "Erro Internal Server Error: Contact your support \n"
														+ "Mensagem de erro:  { timestamp, errorCode, errorDescription, message, path  }  \n"							
														+ "  - errorCode: 1000 - Erro na api (Cobrança Acesso) na camada de Business \n"
														+ "  - errorCode: 2000 - Erro na api (Cobrança Acesso) na camada de Service \n"
														+ "  - errorCode: 3000 - Erro na api (Cobrança Acesso) na camada de Acesso a dados \n"
							)
			}
	)
	@GetMapping(value = "/usuario/email/{email}")
	public ResponseEntity<UsuarioDTO> consultarUsuarioPorEmail(@PathVariable(value = "email", required = true) String email) {
		UsuarioDTO usuarioDTO = usuarioBusiness.consultarUsuarioPorEmail(email);
		return ResponseEntity.ok().body(usuarioDTO);
	}
	
	
	@ApiOperation(value = "Consultar Usuário Por Id")
	@ApiResponses(
			value= {
					@ApiResponse(code = 200 , message = "Sucessfull"),
					@ApiResponse(code = 401 , message = "Unauthorized"),
					@ApiResponse(code = 403 , message = "Access denied"),
					@ApiResponse(code = 500, message = "Erro Internal Server Error: Contact your support \n"
														+ "Mensagem de erro:  { timestamp, errorCode, errorDescription, message, path  }  \n"							
														+ "  - errorCode: 1000 - Erro na api (Cobrança Acesso) na camada de Business \n"
														+ "  - errorCode: 2000 - Erro na api (Cobrança Acesso) na camada de Service \n"
														+ "  - errorCode: 3000 - Erro na api (Cobrança Acesso) na camada de Acesso a dados \n"
							)
			}
	)
	@GetMapping(value = "/usuario/{id}")
	public ResponseEntity<UsuarioDTO> consultarUsuarioPorId(@PathVariable(value = "id", required = true) Long id) {
		UsuarioDTO usuarioDTO = usuarioBusiness.consultarUsuarioPorId(id);
		return ResponseEntity.ok().body(usuarioDTO);
	}
	
	@ApiOperation(value = "Apagar Usuário por Id")
	@ApiResponses(
			value= {
					@ApiResponse(code = 200 , message = "Sucessfull"),
					@ApiResponse(code = 401 , message = "Unauthorized"),
					@ApiResponse(code = 403 , message = "Access denied"),
					@ApiResponse(code = 500, message = "Erro Internal Server Error: Contact your support \n"
														+ "Mensagem de erro:  { timestamp, errorCode, errorDescription, message, path  }  \n"							
														+ "  - errorCode: 1000 - Erro na api (Cobrança Acesso) na camada de Business \n"
														+ "  - errorCode: 2000 - Erro na api (Cobrança Acesso) na camada de Service \n"
														+ "  - errorCode: 3000 - Erro na api (Cobrança Acesso) na camada de Acesso a dados \n"
							)
			}
	)
	@DeleteMapping(value = "/usuario/{id}")
	public ResponseEntity<Void> apagarUsuarioPorId(@PathVariable(value = "id", required = true) Long id) {
		usuarioBusiness.apagarUsuarioPorId(id);
		return ResponseEntity.ok().build();
	}
	
	
	@ApiOperation(value = "Recuperar senha para email")
	@ApiResponses(
			value= {
					@ApiResponse(code = 200 , message = "Sucessfull"),
					@ApiResponse(code = 401 , message = "Unauthorized"),
					@ApiResponse(code = 403 , message = "Access denied"),
					@ApiResponse(code = 500, message = "Erro Internal Server Error: Contact your support \n"
														+ "Mensagem de erro:  { timestamp, errorCode, errorDescription, message, path  }  \n"							
														+ "  - errorCode: 1000 - Erro na api (Cobrança Acesso) na camada de Business \n"
														+ "  - errorCode: 2000 - Erro na api (Cobrança Acesso) na camada de Service \n"
														+ "  - errorCode: 3000 - Erro na api (Cobrança Acesso) na camada de Acesso a dados \n"
							)
			}
	)
	@GetMapping(value = "/usuario/recuperar/senha/{email}")
	public ResponseEntity<UsuarioDTO> recuperarSenha(@PathVariable(value = "email", required = true) String email) {
		usuarioBusiness.recuperarSenha(email);
		return ResponseEntity.ok().build();
	}
	
	
	@ApiOperation(value = "Alterar senha do usuário")
	@ApiResponses(
			value= {
					@ApiResponse(code = 200 , message = "Sucessfull"),
					@ApiResponse(code = 401 , message = "Unauthorized"),
					@ApiResponse(code = 403 , message = "Access denied"),
					@ApiResponse(code = 500, message = "Erro Internal Server Error: Contact your support \n"
														+ "Mensagem de erro:  { timestamp, errorCode, errorDescription, message, path  }  \n"							
														+ "  - errorCode: 1000 - Erro na api (Cobrança Acesso) na camada de Business \n"
														+ "  - errorCode: 2000 - Erro na api (Cobrança Acesso) na camada de Service \n"
														+ "  - errorCode: 3000 - Erro na api (Cobrança Acesso) na camada de Acesso a dados \n"
							)
			}
	)
	@PutMapping(value = "/usuario/alterar/senha/{senha}")
	public ResponseEntity<UsuarioDTO> recadastrarSenha(@PathVariable(value = "senha", required = true) String senha) {
		usuarioBusiness.alterarSenha(senha);
		return ResponseEntity.ok().build();
	}
	
		
}
