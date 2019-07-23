package com.trusthub.cobranca.configuration.validation.generic.layers;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.TrustHubException;
import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;

/**
 * Exception that will occur in the service layer
 * @author alan.franco
 */
public class TrustHubServiceException extends TrustHubException {

	private static final long serialVersionUID = 1L;

	public TrustHubServiceException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}

	public TrustHubServiceException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public TrustHubServiceException(String msg, TrustHubError trustHubError, HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public TrustHubServiceException(String msg, TrustHubError trustHubError, HttpStatus httpStatus, Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

}
