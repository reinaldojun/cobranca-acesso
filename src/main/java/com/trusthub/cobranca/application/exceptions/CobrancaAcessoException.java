package com.trusthub.cobranca.application.exceptions;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.TrustHubException;
import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;

/**
 *  Classe que representa exception cobranca acesso
 *  @author alan.franco
 */
public class CobrancaAcessoException extends TrustHubException {

	private static final long serialVersionUID = 1L;

	public CobrancaAcessoException(String msg, TrustHubError trustHubError, HttpStatus httpStatus,
			Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

	public CobrancaAcessoException(String msg, TrustHubError trustHubError, HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public CobrancaAcessoException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public CobrancaAcessoException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}

}
