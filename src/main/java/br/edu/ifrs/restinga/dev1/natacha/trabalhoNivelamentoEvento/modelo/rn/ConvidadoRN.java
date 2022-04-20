package br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.rn;

import org.springframework.stereotype.Component;

import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.erro.RNInvalida;
import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.entidade.Convidado;

@Component
public class ConvidadoRN {

    private void valida(Convidado convidado) {

        if (convidado.getNomeConvidado() == null
                || convidado.getNomeConvidado().trim().isEmpty()) {
            throw new RNInvalida("Convidado obrigatorio!");
        }
    }

    public void validaInsercao(Convidado convidado) {
        this.valida(convidado);
    }

    public void validaAtualizacao(Convidado convidado) {
        this.valida(convidado);
    }
}
