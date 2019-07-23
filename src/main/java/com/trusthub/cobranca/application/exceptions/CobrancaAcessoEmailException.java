package com.trusthub.cobranca.application.exceptions;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubExternalIntegrationException;

/**
 *  Classe que representa exception da cobranca acesso (com api trusthub-comum-email)
 *  @author alan.franco
 */
public class CobrancaAcessoEmailException extends TrustHubExternalIntegrationException {

	private static final long serialVersionUID = 1L;

	public CobrancaAcessoEmailException(String msg, TrustHubError trustHubError, HttpStatus httpStatus,
			Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

	public CobrancaAcessoEmailException(String msg, TrustHubError trustHubError,
			HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public CobrancaAcessoEmailException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public CobrancaAcessoEmailException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}

}
