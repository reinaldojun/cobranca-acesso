package com.trusthub.cobranca.controller.empresas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trusthub.cobranca.application.business.empresa.EmpresaBusiness;
import com.trusthub.cobranca.domain.dto.EmpresaDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * API - Controller - com serviços de empresas
 * @author alan.franco
 */
@RestController
@Validated
@CrossOrigin(value = "*")
@Api(value = "API - Cobranca Acesso - Serviços referentes a Empresa")
@RequestMapping("/trusthub-cobranca-acesso/v1")
public class EmpresaCobrancaController {
	
	@Autowired
	private EmpresaBusiness empresaBusiness;
	
	@ApiOperation(value = "Listar Empresas")
	@ApiResponses(
			value= {
					@ApiResponse(code = 200 , message = "Sucessfull"),
					@ApiResponse(code = 204 , message = "No Content"),
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
	@GetMapping(value = "/empresas")
	public ResponseEntity<List<EmpresaDTO>> listarEmpresas() {
		List<EmpresaDTO> listaEmpresasDTO = empresaBusiness.listarEmpresas();
		return ResponseEntity.ok().body(listaEmpresasDTO);
	}
	
	
	@ApiOperation(value = "Cadastrar Empresas")
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
	@PostMapping(value = "/empresa")
	public ResponseEntity<Void> cadastrarEmpresa(@RequestBody EmpresaDTO empresaDTO) {
		empresaBusiness.cadastrarEmpresa(empresaDTO);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "Apagar Empresa Por Id")
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
	@DeleteMapping(value = "/empresa/{id}")
	public ResponseEntity<Void> apagarEmpresaPorId(@PathVariable(value = "id", required = true) Long id) {
		empresaBusiness.apagarEmpresaPorId(id);
		return ResponseEntity.ok().build();
	}
	
		
	
}
