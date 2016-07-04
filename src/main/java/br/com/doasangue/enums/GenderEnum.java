package br.com.doasangue.enums;

public enum GenderEnum {

	F("Feminino"),
	M("Masculino");
	
	private String name;
	
	GenderEnum(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
