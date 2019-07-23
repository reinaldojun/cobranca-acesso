package com.trusthub.cobranca.configuration.validation.generic.layers;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.TrustHubException;
import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;

/**
 * Exception that will occur in the repository layer 
 * @author alan.franco
 */
public class TrustHubRepositoryException extends TrustHubException {

	private static final long serialVersionUID = 1L;

	public TrustHubRepositoryException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}

	public TrustHubRepositoryException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public TrustHubRepositoryException(String msg, TrustHubError trustHubError, HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public TrustHubRepositoryException(String msg, TrustHubError trustHubError, HttpStatus httpStatus, Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

}
