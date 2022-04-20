package br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.rn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.erro.RNInvalida;
import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.entidade.Evento;

@Component
public class EventoRN {

    @Autowired
    LocalRN localRN;
    //ConvidadoRN convidadoRN;

    private void valida(Evento evento, String modo) {
        if (evento.getNomeEvento() == null
                || evento.getNomeEvento().trim().isEmpty()
                || evento.getNomeEvento().length() < 3) {
            throw new RNInvalida("Nome do Evento deve ter mais de 3 caracteres");
        }

        // if caso aceite nulo
        if (evento.getLocal() != null) {
            if (modo.equals("insercao")) {
                localRN.validaInsercao(evento.getLocal());
            } else {
                localRN.validaAtualizacao(evento.getLocal());
            }
        }

    }

    public void validaInsercao(Evento evento) {
        this.valida(evento, "insercao");
    }

    public void validaAtualizacao(Evento evento) {
        this.valida(evento, "atualizacao");
    }
    
}
