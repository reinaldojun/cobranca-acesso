package com.trusthub.cobranca.configuration.validation.generic;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;

/**
 * Default class that will handle errors in exceptions
 * @author alan.franco
 */
public class TrustHubException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	TrustHubError trustHubError;
	HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

	public TrustHubException(String msg, TrustHubError trustHubError) {
		super(msg);
		this.trustHubError = trustHubError;
	}

	public TrustHubException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, cause);
		this.trustHubError = trustHubError;
	}

	public TrustHubException(String msg, TrustHubError trustHubError, HttpStatus httpStatus) {
		super(msg);
		this.trustHubError = trustHubError;
		this.httpStatus = httpStatus;
	}

	public TrustHubException(String msg, TrustHubError trustHubError, HttpStatus httpStatus, Throwable cause) {
		super(msg, cause);
		this.trustHubError = trustHubError;
		this.httpStatus = httpStatus;
	}

	public TrustHubError getTrustHubError() {
		return trustHubError;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
