package com.trusthub.cobranca.domain.enums;

public enum Perfil {

	USUARIO(1, "ROLE_USUARIO"),
	EMPRESA(2, "ROLE_EMPRESA"),
	COBRANCA(3, "ROLE_COBRANCA"),
	COBRANCA_ATENDIMENTO(4, "ROLE_COBRANCA_ATENDIMENTO"),
	COBRANCA_JURIDICO(5, "ROLE_COBRANCA_JURIDICO"),
	RELATORIOS(6, "ROLE_RELATORIOS"),
	DASHBOARD(7, "ROLE_DASHBOARD");
	
	private int cod;
	private String descricao;

	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao () {
		return descricao;
	}

	public static Perfil toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (Perfil x : Perfil.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}