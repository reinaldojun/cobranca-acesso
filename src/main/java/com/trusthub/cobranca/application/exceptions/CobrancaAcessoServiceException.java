package com.trusthub.cobranca.application.exceptions;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubServiceException;

/**
 *  Classe que representa exception da cobranca acesso service
 *  @author alan.franco
 */
public class CobrancaAcessoServiceException extends TrustHubServiceException {

	private static final long serialVersionUID = 1L;

	public CobrancaAcessoServiceException(String msg, TrustHubError trustHubError, HttpStatus httpStatus,
			Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

	public CobrancaAcessoServiceException(String msg, TrustHubError trustHubError,
			HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public CobrancaAcessoServiceException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public CobrancaAcessoServiceException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}

}
