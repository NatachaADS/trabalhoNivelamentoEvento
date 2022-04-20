package br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.entidade.Local;

@Component
public class LocalDAO {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Local inserir(Local local) {

        String sql = "INSERT INTO local(nomeLocal) VALUES(?)";
        jdbcTemplate.update(sql,
                local.getNomeLocal());
        // recupera o ID do elemento inserido 
        int id = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()", Integer.class);
        local.setId(id);
        return local;
    }

    private Local criaLocal(SqlRowSet rowSet) {
        Local local = new Local();
        local.setId(rowSet.getInt("id"));
        local.setNomeLocal(rowSet.getString("Local"));
        return local;
    }

    public Local recuperar(int id) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(
                "SELECT * FROM local WHERE id=?", id);
        if (rowSet.next()) {
            Local local = criaLocal(rowSet);
            return local;
        } else {
            return null;
        }
    }

    public List<Local> listar() {
        ArrayList<Local> locais = new ArrayList<>();
        SqlRowSet rowSet
                = jdbcTemplate.queryForRowSet(
                        "SELECT * FROM local");
        while (rowSet.next()) {
            Local local = criaLocal(rowSet);
            locais.add(local);
        }
        return locais;
    }

    public Local atualizar(int id, Local local) {
        jdbcTemplate.update(
                "UPDATE local SET nomeLocal=? WHERE  id=?",
                local.getNomeLocal(),
                id
        );
        return local;
    }

    public void excluir(int id) {
        jdbcTemplate.update(
                "DELETE FROM local WHERE id=?",
                id
        );
    }
}
