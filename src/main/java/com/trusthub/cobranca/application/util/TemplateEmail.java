package com.trusthub.cobranca.application.util;

import java.io.StringWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class TemplateEmail {
	
	private TemplateEmail() {}
	
	/**
	 * Alteração de senha
	 * @param nome
	 * @param senha
	 * @return
	 */
	public static String alterarSenhaBase64(String email, String nome, String senha) {
		Map<String, Object> scope = new HashMap<>();
		scope.put("email", email);
		scope.put("nome", nome);
		scope.put("senha", senha);
  	    return Base64.getEncoder().encodeToString(getBody(scope, "template-email-cobranca/template_email_cobranca_recuperar_senha.html").getBytes());
	}
	
	/**
	 * Cadastrar senha
	 * @param email
	 * @param nome
	 * @param senha
	 * @return
	 */
	public static String cadastrarSenhaBase64(String email, String nome, String senha) {
		Map<String, Object> scope = new HashMap<>();
		scope.put("email", email);
		scope.put("nome", nome);
		scope.put("senha", senha);
  	    return Base64.getEncoder().encodeToString(getBody(scope, "template-email-cobranca/template_email_cobranca_cadastrar_senha.html").getBytes());
	}
	
	
	/**
	 * Gerar String através do html com os parametros e template
	 * @param scope
	 * @param template
	 * @return
	 */
	private static String getBody(Map<String, Object> scope, String template) {
		String templateRenderizado = "";
		StringWriter stringWriter = new StringWriter();
		MustacheFactory mf = new DefaultMustacheFactory();
		Mustache mustache = mf.compile(template);
		templateRenderizado = mustache.execute(stringWriter, scope).toString();
		stringWriter.flush();
		return templateRenderizado;
	}

}
