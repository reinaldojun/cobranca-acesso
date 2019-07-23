package com.trusthub.cobranca.application.exceptions;

import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Classe que representa o codigo e descricao das camadas ou integracoes
 * @author alan.franco
 */
@Getter
@AllArgsConstructor
public enum CobrancaAcessoError implements TrustHubError {

	ERROR_COBRANCA_ACESSO_BUSINESS(1000, "ERRO NA API (COBRANCA ACESSO) NA CAMADA BUSINESS"), 
	ERROR_COBRANCA_ACESSO_SERVICE(2000, "ERRO NA API (COBRANCA ACESSO) NA CAMADA SERVICE"),
	ERROR_COBRANCA_ACESSO_REPOSITORY(3000, "ERRO NA API (COBRANCA ACESSO) NA CAMADA DE ACESSO A DADOS"),
	ERROR_COBRANCA_SECURITY(6, "ERRO NA API (COBRANCA ACESSO) ERRO - COBRANCA ACESSO"),
	ERROR_COBRANCA_TRUSTHUB_COMUM_EMAIL(7, "ERRO NA API (COBRANCA ACESSO) ERRO - NA INTEGRACAO COM API (TRUSTHUB_COMUM_EMAIL)");
	
	 
	Integer errorCode;
	String errorDescription;

	@Override
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
