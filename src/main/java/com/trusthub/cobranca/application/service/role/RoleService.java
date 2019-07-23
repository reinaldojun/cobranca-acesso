package com.trusthub.cobranca.application.service.role;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoRepositoryException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoServiceException;
import com.trusthub.cobranca.application.repository.RoleRepository;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.domain.dto.RoleDTO;
import com.trusthub.cobranca.domain.entity.RoleEntity;


/**
 * Service que acessa servicos de role
 * @author alan.franco
 */
@Service
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	/**
	 * Consultar as Roles
	 * @return
	 */
	public List<RoleDTO> consultarRoles() {
		List<RoleDTO> listaRoleDTO = new ArrayList<>();
		try {
			List<RoleEntity> listaRoleEntity = roleRepository.consultarRoles();
			listaRoleEntity.forEach(roleEntity -> {
				listaRoleDTO.add(toDTO(roleEntity));
			});
		}catch (CobrancaAcessoRepositoryException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_CONSULTAR_ROLE)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE, e);
		}
		return listaRoleDTO;
	}
	
	/**
	 * Converte Entity para DTO
	 * @param entity
	 * @return RoleDTO
	 */
	public RoleDTO toDTO(RoleEntity entity){
		RoleDTO dto = new RoleDTO();
		BeanUtils.copyProperties(entity, dto); 
		return dto ;
	}
	
	/**
	 * Converte DTO para Entity 
	 * @param dto 
	 * @return RoleEntity 
	 */
	public RoleEntity toEntity(RoleDTO dto){
		RoleEntity entity = new RoleEntity();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}
	

}
