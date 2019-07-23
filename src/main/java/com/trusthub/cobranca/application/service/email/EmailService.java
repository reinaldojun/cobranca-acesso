package com.trusthub.cobranca.application.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.trusthub.cobranca.application.exceptions.CobrancaAcessoEmailException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoServiceException;
import com.trusthub.cobranca.application.util.ApiConstantes;
import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.api.ClientDiscoveyServiceFactory;
import com.trusthub.cobranca.domain.dto.email.EmailDTO;
import com.trusthub.cobranca.security.CobrancaAcessoSecurityUtil;


/**
 * Service que acessa servicos email 
 * @author alan.franco
 */
@Service
public class EmailService {
	
	@Autowired
	protected RestTemplate restTemplate;
	@Autowired
	private ClientDiscoveyServiceFactory discovey;
	
	public Boolean enviarEmail(EmailDTO emailDTO) {
		ResponseEntity<EmailDTO> response = null;
		Boolean isEnviado = null;
		try {
			String uri = new StringBuilder(ApiConstantes.API_BAR).append(ApiConstantes.ENVIAR_EMAIL_GENERICO_TEMPLATE_BASE64).toString();
			response = restTemplate.exchange(discovey.url(discovey.TRUSTHUB_COMUM_EMAIL, discovey.CONTEXTO_TRUSTHUB_COMUM_EMAIL, uri), 
							HttpMethod.POST, CobrancaAcessoSecurityUtil.atribuirBodyAndHeader(emailDTO, null),
							EmailDTO.class);
			isEnviado = this.validarRespostaEnviarEmail(response);
		}catch (CobrancaAcessoServiceException ei) {
			throw ei;
		}catch (HttpClientErrorException ei) {
			throw new CobrancaAcessoEmailException(new StringBuilder(Mensagens.SERVICE_ENVIAR_EMAIL)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaAcessoError.ERROR_COBRANCA_TRUSTHUB_COMUM_EMAIL, HttpStatus.FORBIDDEN);
		}catch (HttpServerErrorException ei) {
			throw new CobrancaAcessoEmailException(new StringBuilder(Mensagens.SERVICE_ENVIAR_EMAIL)
					.append(Constantes.ERRO_COMPONENTE_EXTERNO).append(ei.getResponseBodyAsString().replace(Constantes.TIMESTAMP, Constantes.TIME)).toString(), 
						CobrancaAcessoError.ERROR_COBRANCA_TRUSTHUB_COMUM_EMAIL);
		}catch (Exception e) {
			throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_ENVIAR_EMAIL)
				.append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE);
		}
		return isEnviado;
	}
	
		
	public Boolean validarRespostaEnviarEmail(ResponseEntity<EmailDTO> response) {
		Boolean status = null;
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value()) {
				throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_VALIDAR_RESPOSTA_ENVIAR_EMAIL)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), 
							CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE);
			}else if (response.getBody() != null) {
				status = response.getBody().getEnviado();
			}
		}catch (Exception e) {
			 throw new CobrancaAcessoServiceException(new StringBuilder(Mensagens.SERVICE_VALIDAR_RESPOSTA_ENVIAR_EMAIL)
					 .append(e.getMessage()).toString(), CobrancaAcessoError.ERROR_COBRANCA_ACESSO_SERVICE);
		}				
		return status;
	}

}
