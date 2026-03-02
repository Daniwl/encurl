package com.daniwl.encurl.exception;

public class UrlNaoEncontradaException extends RuntimeException{

	private static final long serialVersionUID = 5276540573061402361L;

	public UrlNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
}
