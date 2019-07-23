package com.trusthub.cobranca.application.exceptions;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubBusinessException;

/**
 *  Classe que representa exception da cobranca acesso business
 *  @author alan.franco
 */
public class CobrancaAcessoBusinessException extends TrustHubBusinessException {

	private static final long serialVersionUID = 1L;

	public CobrancaAcessoBusinessException(String msg, TrustHubError trustHubError, HttpStatus httpStatus,
			Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

	public CobrancaAcessoBusinessException(String msg, TrustHubError trustHubError,
			HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public CobrancaAcessoBusinessException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public CobrancaAcessoBusinessException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}
	
}
