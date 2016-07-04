package br.com.doasangue.enums;

public enum StateEnum {

	AC("AC", "Acre"),
	AL("AL", "Alagoas"),
	AM("AM", "Amazonas"),
	AP("AP", "Amapá"),
	BA("BA", "Bahia"),
	CE("CE", "Ceará"),
	DF("DF", "Distrito Federal"),
	ES("ES", "Espírito Santo"),
	GO("GO", "Goiás"),
	MG("MG", "Minas Gerais"),
	PA("PA", "Pará"),
	PE("PE", "Pernambuco"),
	PI("PI", "Piauí"),
	PR("PR", "Paraná"),
	RN("RN", "Rio Grande do Norte"),
	SC("SC", "Santa Catarina"),
	RJ("RJ", "Rio de Janeiro"),
	RO("RO", "Rondônia"),
	RR("RR", "Roraima"),
	RS("RS", "Rio Grande do Sul"),
	MS("MS", "Mato Grosso do Sul"),
	MT("MT", "Mato Grosso"),
	PB("PB", "Paraíba"),
	SE("SE", "Sergipe"),
	SP("SP", "São Paulo"),
	TO("TO", "Tocantins"),
	MA("MA", "Maranhão");
	
	private String initials;
	private String name;
	
	StateEnum(String initials, String name){
		this.initials = initials;
		this.name = name;
	}

	public static StateEnum getByInitials(String initialsParam){
		initialsParam = initialsParam.toUpperCase();
		
		for (StateEnum stateEnum : StateEnum.values()) {
			if(stateEnum.initials.equals(initialsParam)){
				return stateEnum;
			}
		}
		
		return null;
	}
	
	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
