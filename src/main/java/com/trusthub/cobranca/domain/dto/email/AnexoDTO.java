package com.trusthub.cobranca.domain.dto.email;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO - Email
 * @author alan.franco
 */
@Data
public class AnexoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String extensaoArquivo;
	private String codeBase64;
	
	
}
