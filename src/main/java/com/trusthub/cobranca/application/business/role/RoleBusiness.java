package com.trusthub.cobranca.application.business.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trusthub.cobranca.application.exceptions.CobrancaAcessoBusinessException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoRepositoryException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoServiceException;
import com.trusthub.cobranca.application.service.role.RoleService;
import com.trusthub.cobranca.application.service.role.RoleUrlService;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.domain.dto.RoleDTO;

/**
 *  
 * @author alan.franco
 */
@Component
public class RoleBusiness {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleUrlService roleUrlService;
	
	public List<RoleDTO> consultarRoles() {
		return roleService.consultarRoles();
	}
	
	/**
	 * Transforma para map as url cadastrada na base com o respectivo acesso (Chave = url, valor = perfil)
	 * @return
	 */
	public Map<String, String> getMapRoles() {
		Map<String, String> mapRole = new HashMap<>();
		try {
			//Busca na base as roles com suas roles_url
			List<RoleDTO> listaRoles = roleService.consultarRoles();
			listaRoles.forEach(role -> {
				role.setListaRoleUrlDTO(roleUrlService.consultarRoleUrl(role.getId()));
			});
			//Atribui as roles e role_url para um map
			listaRoles.forEach(role -> {
				role.getListaRoleUrlDTO().forEach(roleUrl -> {
					mapRole.put(roleUrl.getMethod() + roleUrl.getUrl(), role.getRole());
				});
			});
		}catch (CobrancaAcessoRepositoryException | CobrancaAcessoServiceException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_MAP_ROLES)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
		return mapRole;
	}
	
}
