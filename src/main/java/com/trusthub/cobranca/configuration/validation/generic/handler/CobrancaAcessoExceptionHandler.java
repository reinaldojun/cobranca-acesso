package com.trusthub.cobranca.configuration.validation.generic.handler;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.configuration.validation.generic.TrustHubException;
import com.trusthub.cobranca.configuration.validation.generic.domain.StandardError;

/**
 * Generic class that will intercept the error thrown in the access layers or integrations.
 * @author alan.franco
 */
@ControllerAdvice
public class CobrancaAcessoExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(CobrancaAcessoExceptionHandler.class);

	@ExceptionHandler(TrustHubException.class)
	public ResponseEntity<StandardError> trustHubException(TrustHubException e, HttpServletRequest request) {
		StandardError err = new StandardError(new Timestamp(System.currentTimeMillis()), e.getTrustHubError().getErrorCode(), e.getTrustHubError().getErrorDescription(), e.getMessage(), request.getRequestURI());		
		if(e != null && e.getMessage() != null) {
			if(e.getMessage().contains(Constantes.TIMESTAMP)	
					&& (e.getMessage().contains(Constantes.ERRO_CODE) || e.getMessage().contains(Constantes.STATUS)) 
						&& (e.getMessage().contains(Constantes.ERROR_DESCRIPTION) || e.getMessage().contains(Constantes.ERROR)) 
							&& e.getMessage().contains(Constantes.MESSAGE) 
								&& e.getMessage().contains(Constantes.PATH)){
				String erro = e.getMessage().substring(e.getMessage().indexOf(Constantes.ABRE_CHAVE), e.getMessage().lastIndexOf(Constantes.FECHA_CHAVE)+1);
				erro = erro.replaceAll(Constantes.ASPAS_DUPLA + Constantes.STATUS +  Constantes.ASPAS_DUPLA, Constantes.ASPAS_DUPLA + Constantes.ERRO_CODE +  Constantes.ASPAS_DUPLA);
				erro = erro.replaceAll(Constantes.ASPAS_DUPLA + Constantes.ERROR +  Constantes.ASPAS_DUPLA, Constantes.ASPAS_DUPLA + Constantes.ERROR_DESCRIPTION +  Constantes.ASPAS_DUPLA);
				err = StandardError.getStandardError(erro);
				log.error(err.getMessage());
			}
		}
		String msgErro = new StringBuilder("HEADER: ").append(e.getHttpStatus()).append(" BODY: ").append(err.getMessage()).toString();
		log.error(msgErro, e);   
		return ResponseEntity.status(e.getHttpStatus()).body(err);
	}
}
