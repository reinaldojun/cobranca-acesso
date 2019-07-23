package com.trusthub.cobranca.domain.dto.email;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * DTO - Email
 * @author alan.franco
 */
@Data
public class EmailDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String assunto;
	private List<String> destinatarios;
	private List<String> copias;
	private List<String> bcc;
	private String replyTo;
	private String bodyBase64;
	private AnexoDTO anexo;
	private Boolean enviado;
	
	
}
