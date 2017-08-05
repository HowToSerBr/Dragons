package org.dragons.devarka.utils;

public enum Status {

	DESCONHECIDO,
	EM_JOGO,
	AGUARDANDO,
	REINICIANDO;
	
	public static Status status = Status.DESCONHECIDO;
	
	public static Status getStatus() {
		return status;
	}
	
	public static void setStatus(Status st) {
		status = st;
	}
	
}
