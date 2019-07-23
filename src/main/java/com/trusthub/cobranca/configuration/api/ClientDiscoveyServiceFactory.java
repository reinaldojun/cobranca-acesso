package com.trusthub.cobranca.configuration.api;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.trusthub.cobranca.application.util.Constantes;

/**
 * Classe responsável por realizar o load balance e chamada de servicos  
 * @author alan.franco
 */
@Component
public class ClientDiscoveyServiceFactory extends Integracao {
	
	private static final Logger log = LoggerFactory.getLogger(ClientDiscoveyServiceFactory.class);

	@Autowired
	private LoadBalancerClient loadBalancer;

	public URI getProfileUri(String serviceId) {
		ServiceInstance instance = loadBalancer.choose(serviceId);
		URI uri = null;
		if (instance != null) {
			uri = UriComponentsBuilder.fromHttpUrl((instance.isSecure() ? Constantes.HTTPS : Constantes.HTTP) + instance.getHost() + ":" + instance.getPort()).build().toUri();
		}else {
			if(serviceId.contains(Constantes.HTTP) || serviceId.contains(Constantes.HTTPS)) {
				uri = UriComponentsBuilder.fromHttpUrl(serviceId).build().toUri();
			}
		}
		return uri;
	}

	public String formatFinalUrl(String url, String endPoint) {
		String urlFinal = getProfileUri(url).toString();
		return String.format("%s/%s/%s", urlFinal, url, endPoint);
	}
	
	
	public String getUrlService(String serviceId) {
		URI serviceUri = getProfileUri(serviceId);
		if (serviceUri == null) {
			log.error("Não foi possível encontrar o serviço {}", serviceId);
			return "";
		}
		return serviceUri.toString();
	}
	
	public String url(String integracao, String contexto, String uri) {
		String host = getUrlService(integracao);
		return host.concat(contexto).concat(uri);
	}

	
}
