package com.trusthub.cobranca.application.business.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trusthub.cobranca.application.service.role.RoleUrlService;
import com.trusthub.cobranca.domain.dto.RoleUrlDTO;

/**
 *  
 * @author alan.franco
 */
@Component
public class RoleUrlBusiness {

	@Autowired
	private RoleUrlService roleUrlService;
	
	/**
	 * Consultar Roles
	 * @param idRole
	 * @return
	 */
	public List<RoleUrlDTO> consultarRoles(Integer idRole) {
		return roleUrlService.consultarRoleUrl(idRole);
	}
	
}
