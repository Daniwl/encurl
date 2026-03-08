package com.daniwl.encurl.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.daniwl.encurl.core.Encoder;
import com.daniwl.encurl.dto.response.UrlEncurtadaResponse;
import com.daniwl.encurl.exception.UrlNaoEncontradaException;
import com.daniwl.encurl.model.Link;
import com.daniwl.encurl.repository.LinkRepository;

@Service
public class EncurtarService {
	
	private final Encoder encoder;
	private final LinkRepository linkRepository;
	
	public EncurtarService(Encoder encoder, LinkRepository linkRepository) {
		this.encoder = encoder;
		this.linkRepository = linkRepository;
	}
	
	@Transactional // 2. Segurança total pro Banco de Dados
	@Cacheable(value = "url_criada", key = "#url")
	public UrlEncurtadaResponse encurtarUrl(String url) {
		
		// Passo 1: Gravar URL se não existir e pegar o ID
		Link link = linkRepository.findByUrl(url)
				.orElseGet(() -> linkRepository.save(new Link(url)));		
				
		// Passo 2: Codificar o ID
		String urlCodificada = encoder.codificar(link.getId());
		
		// Passo 3: Montar a URL dinâmica com segurança
		String urlFinal = ServletUriComponentsBuilder
		        .fromCurrentContextPath()     // Pega o domínio base
		        .pathSegment(urlCodificada)   // Adiciona a barra e o hash
		        .toUriString();               // Transforma em texto final
		
		return new UrlEncurtadaResponse(urlFinal);
	}
	
	@Cacheable(value = "urls", key = "#id")
	public String recuperarUrlOriginal(String id) {
		Long idDecodificado = encoder.decodificar(id);
		Link link = linkRepository.findById(idDecodificado)
				.orElseThrow(() -> new UrlNaoEncontradaException("Link não encontrado para o código: " + id));

		return link.getUrl();
	}
}