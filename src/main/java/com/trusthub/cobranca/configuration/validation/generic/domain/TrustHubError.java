package com.trusthub.cobranca.configuration.validation.generic.domain;

/**
 * Interface that will have to be implemented to define the code and description of the error
 * @author alan.franco
 */
public interface TrustHubError {

	Integer getErrorCode();

	void setErrorCode(Integer errorCode);

	String getErrorDescription();

	void setErrorDescription(String errorDescription);

}
