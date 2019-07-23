package com.trusthub.cobranca.application.business.empresa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.trusthub.cobranca.application.exceptions.CobrancaAcessoBusinessException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.service.empresa.EmpresaService;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.domain.dto.EmpresaDTO;

/**
 * Camada de negocio com regras da empresa
 * @author alan.franco
 */
@Component
public class EmpresaBusiness {

	@Autowired
	private EmpresaService empresaService;
	
	/**
	 * Listar todas empresas cadastradas na base que estao ativas
	 * @return List<EmpresaDTO> - Lista de empresas
	 */
	public List<EmpresaDTO> listarEmpresas() {
		List<EmpresaDTO> listaemDtos = empresaService.listarEmpresas();
		if(listaemDtos.isEmpty()) {
			throw new CobrancaAcessoBusinessException(Mensagens.BUSINESS_NAO_HA_EMPRESA_CADASTRADA, CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, HttpStatus.NO_CONTENT);
		}
		return listaemDtos;
	}

	/**
	 * Cadastrar empresa
	 * @param empresaDTO - Dados da empresa
	 */
	public void cadastrarEmpresa(EmpresaDTO empresaDTO) {
		EmpresaDTO empresaBanco = empresaService.consultarEmpresaPorCnpj(empresaDTO.getCnpj());
		if(empresaBanco.getId() == null) {
			empresaService.cadastrarEmpresa(empresaDTO);
		}else {
			throw new CobrancaAcessoBusinessException(Mensagens.BUSINESS_EMPRESA_JA_CADASTRADA, CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS);
		}
	}
	
	/**
	 * Apagar empresa por id (Inativar)
	 * @param id
	 */
	public void apagarEmpresaPorId(Long id) {
		empresaService.apagarEmpresaPorId(id);
	}
	
	/**
	 * Consultar empresa por Id
	 * @param id
	 * @return
	 */
	public EmpresaDTO consultarEmpresaId(Integer id) {
		return empresaService.consultarEmpresaPorId(id);
	}
	
	
}
