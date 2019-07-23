package com.trusthub.cobranca.security;

public enum UrlLiberadas {

	SWAGGER(1, "swagger"),
	ALTERAR_SENHA(2, "/trusthub-cobranca-acesso/v1/usuario/recuperar/senha/"),
	ERROR(3, "error");
	
	private int cod;
	private String descricao;

	private UrlLiberadas(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao () {
		return descricao;
	}

	public static UrlLiberadas toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (UrlLiberadas x : UrlLiberadas.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}