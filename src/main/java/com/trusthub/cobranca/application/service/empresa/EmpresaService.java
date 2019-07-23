package com.trusthub.cobranca.application.service.empresa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoRepositoryException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoServiceException;
import com.trusthub.cobranca.application.repository.EmpresaRepository;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.domain.dto.EmpresaDTO;
import com.trusthub.cobranca.domain.entity.EmpresaEntity;


/**
 * Service que acessa servicos do empresa 
 * @author alan.franco
 */
@Service
public class EmpresaService {
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	/**
	 * Listar todas empresas
	 * @return List<EmpresaDTO>
	 */
	public List<EmpresaDTO> listarEmpresas() {
		List<EmpresaEntity> listaEmpresaEntity = new ArrayList<>();
		List<EmpresaDTO> listaEmpresaDTO = new ArrayList<>();
		try {
			listaEmpresaEntity = empresaRepository.listarEmpresas();
			listaEmpresaEntity.forEach(empresaEntity -> {
				listaEmpresaDTO.add(toDTO(empresaEntity));
			});
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_LISTAR_EMPRESAS)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
		return listaEmpresaDTO;
	}
	
	/**
	 * Consultar Empresa pelo Id
	 * @param idEmpresa
	 * @return EmpresaDTO
	 */
	public EmpresaDTO consultarEmpresaPorId(Integer idEmpresa) {
		EmpresaDTO empresaDTO = new EmpresaDTO();
		try {
			empresaDTO = toDTO(empresaRepository.consultarEmpresaPorId(idEmpresa));
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_CONSULTAR_EMPRESA_POR_ID)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
		return empresaDTO;
	}
	
	/**
	 * Consultar empresa pelo CNPJ
	 * @param cnpj
	 * @return EmpresaDTO - dados da empresa
	 */
	public EmpresaDTO consultarEmpresaPorCnpj(String cnpj) {
		EmpresaDTO empresaDTO = new EmpresaDTO();
		try {
			empresaDTO = toDTO(empresaRepository.consultarEmpresaPorCnpj(cnpj));
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_CONSULTAR_EMPRESA_POR_CNPJ)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
		return empresaDTO;
	}
	
	/**
	 * Cadastrar Empresa
	 * @param empresaDTO
	 * @return Long
	 */
	public Long cadastrarEmpresa(EmpresaDTO empresaDTO) {
		try {
			return empresaRepository.cadastrarEmpresa(toEntity(empresaDTO));
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_CADASTRAR_EMPRESA)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
	}
	
	/**
	 * Apagar Empresa por id
	 * @param id
	 */
	public void apagarEmpresaPorId(Long id) {
		try {
			empresaRepository.apagarEmpresaPorId(id);
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_APAGAR_EMPRESA_ID)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
		
	}
	
	/**
	 * Converte Entity para DTO
	 * @param entity
	 * @return EmpresaDTO
	 */
	public EmpresaDTO toDTO(EmpresaEntity entity){
		EmpresaDTO dto = new EmpresaDTO();
		BeanUtils.copyProperties(entity, dto); 
		return dto ;
	}
	
	/**
	 * Converte DTO para Entity 
	 * @param dto 
	 * @return EmpresaEntity 
	 */
	public EmpresaEntity toEntity(EmpresaDTO dto){
		EmpresaEntity entity = new EmpresaEntity();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

}
