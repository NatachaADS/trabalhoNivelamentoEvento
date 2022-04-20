package br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.entidade.Convidado;
import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.entidade.Evento;
import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.entidade.Local;

@Component
public class EventoDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    LocalDAO localDAO;
    
    @Autowired
    EventoConvidadoDAO eventoConvidadoDAO;

    public Evento inserir(Evento evento) {
        Integer idLocal = null;
        if (evento.getLocal() != null) {
            Local local = localDAO.inserir(evento.getLocal());
            idLocal = local.getId();
        }

        String sql = "INSERT INTO evento(nomeEvento, local_id) VALUES(?,?)";
        jdbcTemplate.update(sql, evento.getNomeEvento(), idLocal);
        // recupera o ID do elemento inserido 
        int id = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()", Integer.class);
        //    jdbcTemplate.
        evento.setId(id);

        eventoConvidadoDAO.inserir(evento.getId(), evento.getConvidados());
        
        return evento;
    }

    public Evento recuperar(int id) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(
                "SELECT * FROM evento WHERE id=?", id);
        if (rowSet.next()) {
            Evento evento = criaEvento(rowSet);
            return evento;
        } else {
            return null;
        }
    }

    private Evento criaEvento(SqlRowSet rowSet) throws DataAccessException, InvalidResultSetAccessException {
        Evento evento = new Evento();
        evento.setId(rowSet.getInt("id"));
        evento.setNomeEvento(rowSet.getString("nomeEvento"));

        Local local = null;
        int local_id = rowSet.getInt("local_id");
        if (local_id != 0) {
            local = localDAO.recuperar(local_id);
        }

        evento.setLocal(local);

        Convidado[] convidados = eventoConvidadoDAO.recuperar(evento.getId());
        evento.setConvidados(convidados);

        return evento;
    }

    public List<Evento> listar() {
        ArrayList<Evento> eventos = new ArrayList<>();
        SqlRowSet rowSet
                = jdbcTemplate.queryForRowSet(
                        "SELECT * FROM evento");
        while (rowSet.next()) {
            Evento evento = criaEvento(rowSet);
            eventos.add(evento);
        }
        return eventos;
    }

    public Evento atualizar(int id, Evento evento) {
        Evento eventoAntiga = this.recuperar(id);

        Integer localId = null;

        if (eventoAntiga.getLocal() == null) {
            if (evento.getLocal() != null) {
                Local localBanco = localDAO.inserir(evento.getLocal());
                localId = localBanco.getId();
            }
        } else {
            if (evento.getLocal() == null) {
                localDAO.excluir(
                        eventoAntiga.getLocal().getId());
            } else {
                localDAO.atualizar(
                        eventoAntiga.getLocal().getId(),
                        evento.getLocal());
                localId = eventoAntiga.getLocal().getId();
            }
        }

        jdbcTemplate.update(
                "UPDATE evento SET nomeEvento=?, local_id=? WHERE  id=?",
                evento.getNomeEvento(),
                localId,
                id
        );
        
        eventoConvidadoDAO.atualizar(evento.getId(), evento.getConvidados());

        return evento;
    }

    public void excluir(int id) {
        Evento evento = recuperar(id);
        if (evento.getLocal() != null) {
            localDAO.excluir(evento.getLocal().getId());
        }

        jdbcTemplate.update(
                "DELETE FROM evento WHERE id=?",
                id
        );
    }
    
}
