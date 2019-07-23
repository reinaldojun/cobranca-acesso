package com.trusthub.cobranca.controller.token;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trusthub.cobranca.application.business.token.TokenBusiness;
import com.trusthub.cobranca.security.JWTUtil;
import com.trusthub.cobranca.security.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * API - Controller -  Servicos referentes ao token
 * @author alan.franco
 */
@RestController
@Validated
@CrossOrigin(value = "*")
@Api(value = "API - Cobranca Acesso - Serviços referentes ao token")
@RequestMapping("/trusthub-cobranca-acesso/v1")
public class TokenController {
	
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private UserService userService;
	@Autowired
	private TokenBusiness tokenBusiness;
	
	@ApiOperation(value = "Validar Token e Verificar Autorizacao")
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
	@GetMapping("/validar/token/{urlExecutada}")
	public ResponseEntity<String> validarTokenEVerificaAutorizacao(@PathVariable(value = "urlExecutada", required = true) String urlExecutada){
		return ResponseEntity.ok().body(tokenBusiness.token(urlExecutada));
	}
	
	
	@ApiOperation(value = "Refresh Token")
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
	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		Authentication authentication = userService.authenticated();
		String token = jwtUtil.generateToken(authentication);
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.ok().build();
	}
		
}
