package com.trusthub.cobranca.configuration.validation.generic.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import com.google.gson.Gson;

/**
 * Class that will return errors in the default
 * @author alan.franco
 */
public class StandardError implements Serializable {

	private static final long serialVersionUID = 1L;

	private Timestamp timestamp;
	private Integer errorCode;
	private String errorDescription;
	private String message;
	private String path;
	
	
	public StandardError() {
	}

	public StandardError(Timestamp timestamp, Integer errorCode, String errorDescription, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		this.message = message;
		this.path = path;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public Integer getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorDescription() {
		return errorDescription;
	}
	
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public static StandardError getStandardError(String erro) {
		StandardError standardError = new StandardError();
		if(erro != null) {
			Gson g = new Gson(); 
			standardError  = g.fromJson(erro, StandardError.class);
		}
		return standardError;
	}

}
