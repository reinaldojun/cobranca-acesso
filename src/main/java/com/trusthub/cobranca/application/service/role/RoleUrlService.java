package com.trusthub.cobranca.application.service.role;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoRepositoryException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoServiceException;
import com.trusthub.cobranca.application.repository.RoleUrlRepository;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.domain.dto.RoleUrlDTO;
import com.trusthub.cobranca.domain.entity.RoleUrlEntity;


/**
 * Service que acessa servicos de role url
 * @author alan.franco
 */
@Service
public class RoleUrlService {
	
	@Autowired
	RoleUrlRepository roleUrlRepository;
	
	/**
	 * Consulta as role_url
	 * @param idRole
	 * @return
	 */
	public List<RoleUrlDTO> consultarRoleUrl(Integer idRole) {
		List<RoleUrlDTO> listaRoleUrlDTO = new ArrayList<>();
		try {
			List<RoleUrlEntity> listaRoleUrlEntity = roleUrlRepository.consultarRoleUrl(idRole);
			listaRoleUrlEntity.forEach(roleUrlEntity -> {
				listaRoleUrlDTO.add(toDTO(roleUrlEntity));
			});
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_CONSULTAR_ROLE_URL)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
		return listaRoleUrlDTO;
	}
	
	/**
	 * Converte Entity para DTO
	 * @param entity
	 * @return RoleUrlDTO
	 */
	public RoleUrlDTO toDTO(RoleUrlEntity entity){
		RoleUrlDTO dto = new RoleUrlDTO();
		BeanUtils.copyProperties(entity, dto); 
		return dto ;
	}
	
	/**
	 * Converte DTO para Entity 
	 * @param dto 
	 * @return RoleUrlEntity 
	 */
	public RoleUrlEntity toEntity(RoleUrlDTO dto){
		RoleUrlEntity entity = new RoleUrlEntity();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}
	

}
