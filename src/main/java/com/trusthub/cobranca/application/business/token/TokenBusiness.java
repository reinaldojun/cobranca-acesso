package com.trusthub.cobranca.application.business.token;

import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.trusthub.cobranca.application.business.perfil.PerfilBusiness;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoBusinessException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoRepositoryException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoServiceException;
import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.security.UserSS;
import com.trusthub.cobranca.security.UserService;

/**
 * Classe que faz algumas regras do token
 * @author alan.franco
 */
@Component
public class TokenBusiness {
	
	@Autowired
	private UserService userService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private PerfilBusiness perfilBusiness;
	@Value("${token.dashboar.interno}")
	private String tokenDashBoardInterno;
	
	
	/**
	 * Método que primeiro irá validar o token e depois se tiver valido ira verificar se esta autorizado
	 * @param urlExecutada
	 * @return
	 */
	public String token(String urlExecutada) {
		
		//LIBERA O DASHBORD INTERNO
		if(this.liberaDashBoardInterno()) {
			return Constantes.AUTORIZADO;
		}
			
		String token = this.verificarTokenValido();
		if(token.equalsIgnoreCase(Constantes.TOKEN_VALIDO)) {
			if(this.tokenAutorizado(urlExecutada)) {
				token = Constantes.AUTORIZADO;
			}else {
				token = Constantes.NAO_AUTORIZADO;
			}
		}
		return token;
	}
	
	/**
	 * Libera o acesso quando for o dashboardInterno
	 * @return
	 */
	private Boolean liberaDashBoardInterno() {
		Boolean autorizacao = false;
		String tokenAuthorization = request.getHeader(Constantes.AUTHORIZATION);
		if(tokenAuthorization.equals(Constantes.BEARER + Constantes.ESPACO_BRANCO + tokenDashBoardInterno)) {
			autorizacao = true;
		}
		return autorizacao;
	}
	
	
	
	/**
	 * Verifica se o token está valido.
	 * @return
	 */
	public String verificarTokenValido() {
		String tokenValido = Constantes.TOKEN_VALIDO;
		Authentication auth = userService.authenticated();
		try {
			UserSS user = (UserSS)auth.getPrincipal();
			if(user == null) {
				tokenValido = Constantes.TOKEN_INVALIDO;
			}
		} catch (Exception e) {
			tokenValido = Constantes.TOKEN_INVALIDO;
		}
		return tokenValido;
	}
	
	/**
	 * Verifica se o token esta autorizado de acordo com o perfil
	 * @param urlExecutada
	 * @return
	 */
	public Boolean tokenAutorizado(String urlExecutada) {
		Boolean tokenAutorizado = false;
		try {
			String authorization = request.getHeader(Constantes.AUTHORIZATION);
			String decodedUrlExecutada = new String(Base64.getDecoder().decode(urlExecutada));
			String perfilEsperadoPelaUrl = perfilBusiness.perfilEsperadoPelaUrl(decodedUrlExecutada);
			List<String> perfisDoUsuario = perfilBusiness.getPerfisDoUsuario(authorization);
			tokenAutorizado = perfilBusiness.usarioTemPerfil(perfisDoUsuario, perfilEsperadoPelaUrl);
		}catch (CobrancaAcessoRepositoryException | CobrancaAcessoServiceException | CobrancaAcessoBusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_TOKEN_AUTORIZADO)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
		return tokenAutorizado;
	}
	
}
