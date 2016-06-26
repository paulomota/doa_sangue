package br.com.doasangue.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private List<String> msgErrors = new ArrayList<String>();

	public ValidationException(String msg) {
		super(msg);
		msgErrors.add(msg);
	}

	public ValidationException(List<String> msg) {
		System.out.println("\nErros de validacao: ");
		
		for (String string : msg) {
			System.out.println(string);
		}
		
		System.out.println("\n");
		msgErrors.addAll(msg);
	}

	public List<String> getMsgErrors() {
		return msgErrors;
	}

}
