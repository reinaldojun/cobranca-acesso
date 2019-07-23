package com.trusthub.cobranca.application.exceptions;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubRepositoryException;

/**
 *  Classe que representa exception da cobranca acesso Repository
 *  @author alan.franco
 */
public class CobrancaAcessoRepositoryException extends TrustHubRepositoryException {

	private static final long serialVersionUID = 1L;

	public CobrancaAcessoRepositoryException(String msg, TrustHubError trustHubError, HttpStatus httpStatus,
			Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

	public CobrancaAcessoRepositoryException(String msg, TrustHubError trustHubError,
			HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public CobrancaAcessoRepositoryException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public CobrancaAcessoRepositoryException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}

}
