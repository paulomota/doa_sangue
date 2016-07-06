package br.com.doasangue.enums;

public enum RoleEnum {

	D("Donor"),
	R("Receiver");
	
	private String name;
	
	RoleEnum(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	
}
