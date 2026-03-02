package com.daniwl.encurl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.daniwl.encurl.dto.response.ResponseErroDto;

@RestControllerAdvice // 1. O "Radar" que escuta erros em todos os Controllers
public class GlobalExceptionHandler {

    // 2. Escuta erros genéricos - Erro 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErroDto> tratarErrosNaoEsperados(Exception e) {
        ResponseErroDto erro = new ResponseErroDto("Erro interno no servidor: " + e.getMessage());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    // 3. Escuta os erros do @Valid  - Erro 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErroDto> tratarErrosDeValidacao(MethodArgumentNotValidException e) {       
        // Pega a mensagem exata que o @NotBlank gerou (ex: "A URL não pode ser vazia")
        String mensagemDeErro = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();       
        ResponseErroDto erro = new ResponseErroDto("Erro de validação: " + mensagemDeErro);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
    
    @ExceptionHandler(UrlNaoEncontradaException.class)
    public ResponseEntity<ResponseErroDto> tratarErrosDeValidacao(UrlNaoEncontradaException e) {        
        ResponseErroDto erro = new ResponseErroDto("Url não encontrada. Erro: "+ e.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }
}