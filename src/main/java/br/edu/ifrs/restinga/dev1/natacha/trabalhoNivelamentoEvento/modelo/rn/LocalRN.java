package br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.rn;

import org.springframework.stereotype.Component;

import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.erro.RNInvalida;
import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.entidade.Local;

@Component
public class LocalRN {

    private void valida(Local local) {
                     
        if (local.getNomeLocal() == null || local.getNomeLocal().trim().isEmpty()) {
            throw new RNInvalida("Local obrigatorio!");
        }    
        
    }

    public void validaInsercao(Local local) {
        this.valida(local);
    }

    public void validaAtualizacao(Local local) {
        this.valida(local);
    }
}
