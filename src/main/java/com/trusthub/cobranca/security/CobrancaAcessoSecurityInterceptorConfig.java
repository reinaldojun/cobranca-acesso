package com.trusthub.cobranca.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Adiciona o CobrancaIntegracaoSecurityInterceptor no registry
 * Caso nao queira ligar a seguranca nesse modulo e soh  comentar a linha ( registry.addInterceptor(cobrancaIntegracaoInterceptor); )
 * @author alan.franco
 */
@Component
public class CobrancaAcessoSecurityInterceptorConfig implements WebMvcConfigurer {
	
	@Autowired
	CobrancaAcessoSecurityInterceptor cobrancaIntegracaoInterceptor;
	
	@Value("${app.cobranca.acesso.cobranca.seguraca.ligada}")
	public Boolean segurancaLigada ;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if(segurancaLigada) {
			registry.addInterceptor(cobrancaIntegracaoInterceptor);
		}
	}
}
