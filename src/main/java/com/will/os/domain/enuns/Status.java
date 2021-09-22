package com.will.os.domain.enuns;

public enum Status {
	
	ABERTO(0, "ABERTO"), ANDAMENTO(1, "ANDAMENTO"), ENCERRADO(2, "ENCERRADO");

	private Integer cod;
	private String descricao;

	private Status(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Status toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		for(Status valor : Status.values()) {
			if(cod.equals(valor.getCod())) {
				return valor;
			}
		}
		
		throw new IllegalArgumentException("Status inválido!" + cod);
	}

}
