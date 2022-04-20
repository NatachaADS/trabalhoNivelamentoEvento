package br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.entidade.Convidado;

@Component
public class EventoConvidadoDAO {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Convidado inserir(int eventoId, Convidado convidado) {
        String sql = "INSERT INTO evento_convidado(evento_id, nomeConvidado) VALUES(?,?)";
        jdbcTemplate.update(sql,
                eventoId,
                convidado.getNomeConvidado());
        int id = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()", Integer.class);
        convidado.setId(id);
        return convidado;
    }

    public Convidado[] inserir(int eventoId, Convidado convidados[]) {
        if (convidados == null) {
            return null;
        }
        Convidado[] convidadosBanco = new Convidado[convidados.length];
        for (int i = 0; i < convidados.length; i++) {
            convidadosBanco[i] = this.inserir(eventoId, convidados[i]);
        }
        return convidadosBanco;
    }

    private Convidado criaConvidado (SqlRowSet rowSet) {
        Convidado convidado = new Convidado();
        convidado.setId(rowSet.getInt("id"));
        convidado.setNomeConvidado(rowSet.getString("nomeConvidado"));
        return convidado;
    }

    public Convidado[] recuperar(int festaId) {
        ArrayList<Convidado> convidados = new ArrayList<>();
        SqlRowSet rowSet
                = jdbcTemplate.queryForRowSet(
                        "SELECT * FROM evento_convidado WHERE evento_id=?", festaId);
        while (rowSet.next()) {
            Convidado convidado = criaConvidado(rowSet);
            convidados.add(convidado);
        }
        return convidados.toArray(new Convidado[0]);
    }

    private Convidado atualizar(Convidado convidado) {
        jdbcTemplate.update(
                "UPDATE evento_convidado SET nomeConvidado=? WHERE  id=?",
                convidado.getNomeConvidado(),
                convidado.getId()
        );
        return convidado;
    }

    public Convidado[] atualizar(int eventoId, Convidado[] convidadosNovos) {
        ArrayList<Integer> ids = new ArrayList<>();
        Convidado[] convidadoAntigo = recuperar(eventoId);
        if (convidadosNovos != null) {
            for (int i = 0; i < convidadosNovos.length; i++) {
                if (convidadosNovos[i].getId() != 0) {
                    convidadosNovos[i] = this.atualizar(convidadosNovos[i]);
                } else {
                    convidadosNovos[i] = this.inserir(eventoId, convidadosNovos[i]);
                }
                ids.add(convidadosNovos[i].getId());
            }
        }

        for (Convidado convidado : convidadoAntigo) {
            if (!ids.contains(convidado.getId())) {
                jdbcTemplate.update("DELETE FROM evento_convidado WHERE id=?", convidado.getId());
            }
        }

        return convidadosNovos;
    }
}
