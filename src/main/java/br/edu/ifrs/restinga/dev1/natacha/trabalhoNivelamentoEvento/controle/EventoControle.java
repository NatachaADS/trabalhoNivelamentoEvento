package br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.controle;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.erro.NaoEncontrado;
import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.dao.EventoConvidadoDAO;
import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.dao.EventoDAO;
import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.entidade.Convidado;
import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.entidade.Evento;
import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.rn.ConvidadoRN;
import br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.rn.EventoRN;

@Controller
public class EventoControle {

    @Autowired
    EventoDAO eventoDAO;

    @Autowired
    EventoConvidadoDAO convidadoDAO;

    @Autowired
    EventoRN eventoRN;

    @Autowired
    ConvidadoRN convidadoRN;
    
    @RequestMapping("/recuperaEvento")
    @ResponseBody
    public Evento recuperaEvento(@RequestParam int id) {
        Evento evento = eventoDAO.recuperar(id);
        if (evento == null) {
            NaoEncontrado naoEncontrado = new NaoEncontrado("ID não encontrada");
            throw naoEncontrado;
        }
        return evento;
    }

    @RequestMapping("/excluiEvento")
    @ResponseBody
    public void excluiEvento(@RequestParam int id) {
        eventoDAO.excluir(id);
    }

    @RequestMapping("/alteraEvento")
    @ResponseBody
    public Evento alteraFesta(@RequestParam int id, @RequestBody Evento evento) {
        eventoRN.validaAtualizacao(evento);
        return eventoDAO.atualizar(id, evento);
    }

    @RequestMapping("/adicionaConvidado")
    @ResponseBody
    public Convidado adicionaConvidado(@RequestParam int idEvento, @RequestBody Convidado convidado) {
        convidadoRN.validaInsercao(convidado);
        return convidadoDAO.inserir(idEvento, convidado);
    }

    @RequestMapping("/insereEvento")
    @ResponseBody
    public Evento insereEvento(@RequestBody Evento evento) throws SQLException {
        eventoRN.validaInsercao(evento);
        Evento eventoBanco = eventoDAO.inserir(evento);
        return eventoBanco;
    }

    @RequestMapping("/mostraEventos")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Evento> mostraEvento() {
        return eventoDAO.listar();
    }
}
