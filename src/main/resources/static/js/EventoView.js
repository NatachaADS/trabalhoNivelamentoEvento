class EventoView {
    constructor(base) {

        let fieldCadastro = document.createElement("fieldset");
        this.fieldCadastro = fieldCadastro;
        let legendCadastro = document.createElement("legend");
        legendCadastro.innerText = "Cadastro Evento";
        fieldCadastro.appendChild(legendCadastro);

        let labelNomeEvento = document.createElement("label");
        labelNomeEvento.innerText = "Evento:";
        base.appendChild(labelNomeEvento);
        this.inputNomeEvento = document.createElement("input");
        fieldCadastro.appendChild(labelNomeEvento);
        fieldCadastro.appendChild(this.inputNomeEvento);

        fieldCadastro.appendChild(document.createElement("br"));
        fieldCadastro.appendChild(document.createElement("br"));

        let labelNomeLocal = document.createElement("label");
        labelNomeLocal.innerText = "Local:";
        base.appendChild(labelNomeLocal);
        this.inputNomeLocal = document.createElement("input");
        fieldCadastro.appendChild(labelNomeLocal);
        fieldCadastro.appendChild(this.inputNomeLocal);

        fieldCadastro.appendChild(document.createElement("br"));
        fieldCadastro.appendChild(document.createElement("br"));


        //tabela 1
        this.tabela = document.createElement("table");
        const cabecalho1 = this.tabela.insertRow();
               
        const cellConvidado1 = cabecalho1.insertCell();
        cellConvidado1.innerText = "Convidados";
        
        this.tabela.border = "1";
        base.appendChild(this.tabela);
        this.listar();

        fieldCadastro.appendChild(this.tabela);

        fieldCadastro.appendChild(document.createElement("br"));
        fieldCadastro.appendChild(document.createElement("br"));

        let labelNomeConvidado = document.createElement("label");
        labelNomeConvidado.innerText = "Nome:";
        base.appendChild(labelNomeConvidado);
        this.inputNomeConvidado = document.createElement("input");
        fieldCadastro.appendChild(labelNomeConvidado);
        fieldCadastro.appendChild(this.inputNomeConvidado);
/*
        this.convidados = [];
        let labelConvidados = document.createElement("label");
        labelConvidados.innerText = "Nome: ";
        this.divConvidados = document.createElement("ul");
        fieldCadastro.appendChild(document.createElement("br"));
        this.convidado = document.createElement("input");
        this.addConvidado = document.createElement("button");
        this.addConvidado.innerText = "Adicionar";
        this.addConvidado.onclick = () => {
                this.convidados.push(this.convidado.value);
                let itemNome = document.createElement("li");
                itemNome.innerText = this.convidado.value;
                this.divConvidados.appendChild(itemNome);
                this.convidado.value = "";
        };
        fieldCadastro.appendChild(labelConvidados);
        fieldCadastro.appendChild(document.createElement("br"));
        fieldCadastro.appendChild(this.convidado);
        fieldCadastro.appendChild(this.addConvidado);
        fieldCadastro.appendChild(document.createElement("br"));
        fieldCadastro.appendChild(document.createElement("br"));
        
        fieldCadastro.appendChild(this.divConvidados);
       */
      
        fieldCadastro.appendChild(document.createElement("br"));
        fieldCadastro.appendChild(document.createElement("br"));

        let confirmar = document.createElement("button");
        confirmar.innerText = "Confirmar";
        confirmar.onclick = () => {
            let evento = {
                evento: this.inputNomeEvento.value,
                local: this.inputNomeLocal.value,
                convidado: this.inputNomeConvidado.value,
            };
            if (this.idEdicao) {
                evento.id = this.idEdicao;
                this.confirmarEdicao(evento);
            } else {
                this.cadastrar(evento);
            }
        }
        fieldCadastro.appendChild(confirmar);

        let limpar = document.createElement("button");
        this.botaoLimpar = limpar;
        limpar.innerText = "Limpar";
        limpar.onclick = () => {
            this.limpar();
        }
        fieldCadastro.appendChild(limpar);


        base.appendChild(fieldCadastro);

        //tabela
        this.tabela = document.createElement("table");
        const cabecalho = this.tabela.insertRow();

        const cellId = cabecalho.insertCell();
        cellId.innerText = "ID";

        const cellEvento = cabecalho.insertCell();
        cellEvento.innerText = "Evento";

        const cellLocal = cabecalho.insertCell();
        cellLocal.innerText = "Local";

        const cellConvidado = cabecalho.insertCell();
        cellConvidado.innerText = "Convidados";

        this.tabela.border = "1";
        base.appendChild(this.tabela);
        this.listar();
    
    }

    limpar() {
        this.inputNomeEvento.value = "";
        this.inputNomeLocal.value = "";
        this.inputNomeConvidado.value = "";
        if (this.divId) {
            this.divId.remove();
        }
        this.idEdicao = null;
        this.botaoLimpar.innerText = "Limpar";
    }

    confirmarEdicao(evento) {
        let eventoJson = JSON.stringify(evento);
        fetch("/alteraEvento?id=" + evento.id, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: eventoJson
        }).then(
            (resultado) => {
                if (resultado.ok) {
                    this.limpar();
                    this.listar();
                }
                else {
                    resultado.json().then(
                        (erro) => alert(erro.message)
                    )
                }
            }
        );
    }

    cadastrar(evento) {
        let eventoJson = JSON.stringify(evento);
        console.log(evento);
        console.log(eventoJson);
        fetch("/insereEvento", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: eventoJson
        }).then(
            (resultado) => {
                if (resultado.ok) {
                    this.limpar();
                    this.listar();
                }
                else {
                    resultado.json().then(
                        (erro) => alert(erro.message)
                    )
                }
            }
        );


    }
/*
    cadastrarConvidado(idEvento, convidado) {
        let convidadoJson = JSON.stringify(convidado);
        fetch("/adicionaConvidado?idEvento="+idEvento+"?convidado="+convidado, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: convidadoJson
        }).then(
            (resultado) => {
                if (resultado.ok) {
                }
                else {
                    resultado.json().then(
                        (erro) => alert(erro.message)
                    )
                }
            }
        );


    }

*/
    listar() {
        fetch("/mostraEventos").then(
            (resultado) => resultado.json().then(
                (eventos) => this.setEventos(eventos)
            )
        );
    }

    editar(evento) {
        if (this.divId) {
            this.divId.remove();
        }
        this.botaoLimpar.innerText = "Cancelar";
        this.idEdicao = evento.id;
        this.divId = document.createElement("div");
        this.divId.innerText = "id: " + evento.id;
        this.fieldCadastro.insertBefore(this.divId, this.fieldCadastro.childNodes[1]);
        this.inputNomeEvento.value = evento.nome;
        this.inputNomeLocal.value = evento.local;
        this.inputNomeConvidado.value = evento.convidado;
    }

    excluir(id) {
        fetch("/excluiEvento?id=" + id).then(
            () => this.listar()
        );
    }

    setEventos(eventos) {
        this.eventos = eventos;
        while (this.tabela.rows.length > 1)
            this.tabela.rows[1].remove();

        for (let index = 0; index < eventos.length; index++) {
            const evento = eventos[index];

            let linha = this.tabela.insertRow();

            let cellId = linha.insertCell();
            cellId.innerText = evento.id;

            let cellNomeEvento = linha.insertCell();
            cellNomeEvento.innerText = evento.evento;

            let cellNomeLocal = linha.insertCell();
            cellNomeLocal.innerText = evento.local;

            let cellNomeConvidado = linha.insertCell();
            cellNomeConvidado.innerText = evento.convidado;


            let cellApagar = linha.insertCell();
            let botaoExcluir = document.createElement("button");
            botaoExcluir.innerText = "Excluir";
            botaoExcluir.onclick = () => {
                this.excluir(evento.id);
            }
            cellApagar.appendChild(botaoExcluir);

            let cellEditar = linha.insertCell();
            let botaoEditar = document.createElement("button");
            botaoEditar.innerText = "Editar";
            botaoEditar.onclick = () => {
                this.editar(festa);
            }
            cellEditar.appendChild(botaoEditar);

        }
    }

}