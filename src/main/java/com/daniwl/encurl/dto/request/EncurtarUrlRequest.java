package com.daniwl.encurl.dto.request;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;

public record EncurtarUrlRequest(
		@NotBlank(message = "A URL não pode estar vazia.")
		@URL(message = "Não é uma URL válida")
		String urlOriginal
) {}
