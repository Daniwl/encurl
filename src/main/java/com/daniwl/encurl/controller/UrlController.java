package com.daniwl.encurl.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniwl.encurl.dto.request.EncurtarUrlRequest;
import com.daniwl.encurl.dto.response.UrlEncurtadaResponse;
import com.daniwl.encurl.service.EncurtarService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping
@Tag(name = "Encurtador de URLs", description = "API para encurtar e redirecionar links") 
public class UrlController {
	
	private EncurtarService encurtarService;
	
	public UrlController(EncurtarService encurtarService) {
		this.encurtarService = encurtarService;
	}
	
	@PostMapping("/encurtar")
	@Operation(summary = "Encurtar uma URL", description = "Recebe uma URL longa e devolve um link curto em Base62")
	public ResponseEntity<UrlEncurtadaResponse> encurtarUrl(@Valid @RequestBody EncurtarUrlRequest body) {	
		return ResponseEntity.ok(encurtarService.encurtarUrl(body.urlOriginal()));	
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Redirecionar URL", description = "Recebe o hash Base62 e redireciona o usuário para o site original")
	public ResponseEntity<String> redirecionarUrl(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.FOUND)
				.location(URI.create(encurtarService.recuperarUrlOriginal(id)))
				.build();
	}
}
