package com.trusthub.cobranca.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trusthub.cobranca.application.business.role.RoleBusiness;

/**
 * Classe que irá carregar os perfis com urls cadastradas na base de dados na inicialização da aplicação 
 * @author alan.franco
 */
@Configuration
public class CarregarPerfilUrlBanco {
	
	@Autowired
	private RoleBusiness roleBusiness;
	
	@Bean
	public Map<String, String> getMapRoles(){
		return roleBusiness.getMapRoles();
	}

}
