package com.daniwl.encurl.core;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder implements Encoder{
	
	private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final int BASE = 62;

	@Override
	public String codificar(Long input) {
		// se o ID for zero, já devolve o primeiro caractere ("0")
        if (input == 0) 
            return String.valueOf(CHARACTERS.charAt(0));
        
        StringBuilder sb = new StringBuilder();
        
        while (input > 0) {
            int remainder = (int) (input % BASE);
            sb.append(CHARACTERS.charAt(remainder));
            input = input / BASE;
        }

        return sb.reverse().toString();
	}
	
	@Override
	public Long decodificar(String input) {
		long result = 0;
        for (int i = 0; i < input.length(); i++) {
            
            // 1. Encontrar o valor numérico do caractere no seu alfabeto
            int valorDoCaractere = CHARACTERS.indexOf(input.charAt(i));
            // 2. Multiplica o que já temos pela base e soma o novo valor
            // deslocando a potência conforme avançamos na string
            result = result * BASE + valorDoCaractere;
        }

        return result;
	}

}
