package com.trusthub.cobranca.application.service.perfil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoRepositoryException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoServiceException;
import com.trusthub.cobranca.application.repository.PerfilRepository;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.domain.dto.PerfilDTO;
import com.trusthub.cobranca.domain.entity.PerfilEntity;


/**
 * Service que acessa servicos do perfil
 * @author alan.franco
 */
@Service
public class PerfilService {
	
	@Autowired
	PerfilRepository perfilRepository;
	
	/**
	 * Cadastrar perfil
	 * @param perfilDTO
	 */
	public void cadastrarPerfil(PerfilDTO perfilDTO) {
		try {
			perfilRepository.cadastrarPerfil(toEntity(perfilDTO));
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_CADASTRAR_PERFIL)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
	}
	
	/**
	 * Apaga o perfil
	 * @param perfilDTO
	 */
	public void apagarPerfil(PerfilDTO perfilDTO) {
		try {
			perfilRepository.apagarPerfil(toEntity(perfilDTO));
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_APAGAR_PERFIL)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
	}
	
	/**
	 * Consulta perfil pelo idUsuario
	 * @param idUsuario
	 * @return
	 */
	public List<PerfilDTO> consultarPerfil(Long idUsuario) {
		PerfilDTO perfilDTO = new PerfilDTO();
		perfilDTO.setIdUsuario(idUsuario);
		return this.consultarPerfil(perfilDTO);
	}
	
	/**
	 * Consultar Perfil
	 * @param perfilDTO
	 * @return
	 */
	public List<PerfilDTO> consultarPerfil(PerfilDTO perfilDTO) {
		List<PerfilDTO> listaPerfilDTO = new ArrayList<>();
		try {
			List<PerfilEntity> listaPerfilEntity = perfilRepository.consultarPerfil(toEntity(perfilDTO));
			listaPerfilEntity.forEach(perfilEntity -> {
				listaPerfilDTO.add(toDTO(perfilEntity));
			});
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_CONSULTAR_PERFIL)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
		return listaPerfilDTO;
	}
	
	/**
	 * Converte Entity para DTO
	 * @param entity
	 * @return PerfilDTO
	 */
	public PerfilDTO toDTO(PerfilEntity entity){
		PerfilDTO dto = new PerfilDTO();
		BeanUtils.copyProperties(entity, dto); 
		return dto ;
	}
	
	/**
	 * Converte DTO para Entity 
	 * @param dto 
	 * @return PerfilEntity 
	 */
	public PerfilEntity toEntity(PerfilDTO dto){
		PerfilEntity entity = new PerfilEntity();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}
	

}
