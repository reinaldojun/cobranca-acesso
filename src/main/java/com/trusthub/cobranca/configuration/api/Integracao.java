package com.trusthub.cobranca.configuration.api;

import org.springframework.beans.factory.annotation.Value;

/**
 * Interface que fica o contexto e uri das aplicacoes acessadas 
 * @author alan.franco
 */
public class Integracao {
	
	@Value("${app.cobranca.acesso.trusthub.comum.email.contexto}")
	public String CONTEXTO_TRUSTHUB_COMUM_EMAIL;
	
	@Value("${app.cobranca.acesso.trusthub.comum.email.uri}")
	public String TRUSTHUB_COMUM_EMAIL;

}
