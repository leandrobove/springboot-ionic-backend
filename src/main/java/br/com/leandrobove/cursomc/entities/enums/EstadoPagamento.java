package br.com.leandrobove.cursomc.entities.enums;

public enum EstadoPagamento {

	PENDENTE(1, "Pendente"), APROVADO(2, "Aprovado"), CANCELADO(3, "Cancelado");

	private int id;
	private String descricao;

	private EstadoPagamento(int id, String desc) {
		this.id = id;
		this.descricao = desc;
	}

	public int getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public static EstadoPagamento toEnum(Integer id) {
		if (id == null) {
			return null;
		}

		for (EstadoPagamento ep : EstadoPagamento.values()) {
			if (id.equals(ep.getId())) {
				return ep;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + id);

	}

}
