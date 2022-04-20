package br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.erro;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RNInvalida extends RuntimeException{
    public RNInvalida(String erro) {
        super(erro);
    }
}