package com.trusthub.cobranca.application.business.perfil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trusthub.cobranca.application.exceptions.CobrancaAcessoBusinessException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoError;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoRepositoryException;
import com.trusthub.cobranca.application.exceptions.CobrancaAcessoServiceException;
import com.trusthub.cobranca.application.service.perfil.PerfilService;
import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.domain.dto.PerfilDTO;
import com.trusthub.cobranca.security.CarregarPerfilUrlBanco;
import com.trusthub.cobranca.security.JWTUtil;

import io.jsonwebtoken.Claims;

/**
 * Classe que contem regras do Perfil
 * @author alan.franco
 */
@Component
public class PerfilBusiness {

	@Autowired
	private PerfilService perfilService;
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private CarregarPerfilUrlBanco carregarPerfilUrlBanco;
	
	/**
	 * Cadastra o perfil
	 * @param perfilDTO
	 */
	public void cadastrarPefil(PerfilDTO perfilDTO) {
		perfilService.cadastrarPerfil(perfilDTO);
	}
	
	/**
	 * Apaga o Perfil
	 * @param perfilDTO
	 */
	public void apagarPefil(PerfilDTO perfilDTO) {
		perfilService.apagarPerfil(perfilDTO);
	}
	
	/**
	 * Consulta lista Perfil
	 * @param perfilDTO
	 * @return
	 */
	public List<PerfilDTO> consultarPefil(PerfilDTO perfilDTO) {
		return perfilService.consultarPerfil(perfilDTO);
	}
	
	/**
	 * Recupera os perfis que estão no token
	 * @param authorization
	 * @return List<String> - Lista de Perfis
	 */
	@SuppressWarnings("unchecked")
	public List<String> getPerfisDoUsuario(String authorization){
		List<String> perfisDoUsuario = new ArrayList<>();
		try {
			Claims claims = jwtUtil.getClaims(authorization.substring(7));
		    perfisDoUsuario = (List<String>)claims.get(Constantes.PERFIS);
		} catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_CAPTURAR_PERFIS_USUARIO)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
		return perfisDoUsuario;
		
	}
	
	/**
	 * Recupera o o nome do perfil de acordo com a url que foi executada
	 * @param urlExecutada
	 * @return String - Perfil
	 */
	public String perfilEsperadoPelaUrl(String urlExecutada) {
		AtomicReference<String>  perfilEsperado = new AtomicReference<>();
		try {
			Map<String, String> mapRoles = carregarPerfilUrlBanco.getMapRoles();
			String[] arrayUrlExecutada = urlExecutada.split(Constantes.BARRA);
	        Integer sizeUrlExecutada = arrayUrlExecutada.length;
	        mapRoles.forEach((chave, valor) -> {
	        	 String[] arrayChave = chave.split(Constantes.BARRA);
	        	 int sizeArrayChave = arrayChave.length;
	        	 if(sizeUrlExecutada == sizeArrayChave) {
	        		 String perfilEncontrado = this.percorrerArrays(arrayUrlExecutada, arrayChave, mapRoles, chave);
	        		 if(perfilEncontrado != null) {
	        			 perfilEsperado.set(perfilEncontrado);
	        		 }
	        	 }
			});
		}catch (CobrancaAcessoRepositoryException | CobrancaAcessoServiceException | CobrancaAcessoBusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_PERFIL_ESPERADO_PELA_URL)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
		return perfilEsperado.get();
	}
	
	/**
	 * Percorre os arrays para encontrar a url executada com o map de urls cadastradas
	 * @param arrayUrlExecutada
	 * @param arrayChave
	 * @param mapRoles
	 * @param chave
	 * @return
	 */
	private String percorrerArrays(String[] arrayUrlExecutada, String[] arrayChave, Map<String, String> mapRoles, String chave) {
		 String perfilEsperado = null;
		 Boolean encontrado = false;
		 for(int i = 0; i < arrayUrlExecutada.length; i++) {
          	if(arrayUrlExecutada[i].equals(arrayChave[i]) || arrayChave[i].contains(Constantes.ABRE_CHAVE) ) {
          		encontrado = true;
          	}else {
          		encontrado = false;
          		break;
          	}
          }
          if(encontrado) {
        	  return mapRoles.get(chave);
          }
          return perfilEsperado;
	}
	
	
	/**
	 * Verifica se o usuario tem o perfil de acordo com o perfil necessario da url
	 * @param perfisDoUsuario
	 * @param perfilEsperadoPelaUrl
	 * @return 
	 */
	public Boolean usarioTemPerfil(List<String> perfisDoUsuario, String perfilEsperadoPelaUrl) {
		AtomicBoolean usuarioTemPerfil = new AtomicBoolean(false);
		try {
		    perfisDoUsuario.forEach(perfilUsuario -> {
		    	if(perfilUsuario.equalsIgnoreCase(perfilEsperadoPelaUrl)) {
		    		usuarioTemPerfil.set(true);
		    	}
		    });
		} catch (Exception e) {
			throw new CobrancaAcessoBusinessException(new StringBuilder(Mensagens.BUSINESS_CAPTURAR_PERFIS_USUARIO)
					.append(e.getMessage()).toString(),  CobrancaAcessoError.ERROR_COBRANCA_ACESSO_BUSINESS, e);
		}
		return usuarioTemPerfil.get();
	}
	
}
