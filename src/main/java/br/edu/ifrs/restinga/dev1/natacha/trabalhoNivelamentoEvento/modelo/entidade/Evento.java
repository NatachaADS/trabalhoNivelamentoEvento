package br.edu.ifrs.restinga.dev1.natacha.trabalhoNivelamentoEvento.modelo.entidade;

public class Evento {

    private int id;
    private String nomeEvento;
    private Local local;

    private Convidado[] convidados;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Convidado[] getConvidados() {
        return convidados;
    }

    public void setConvidados(Convidado[] convidados) {
        this.convidados = convidados;
    }

}
