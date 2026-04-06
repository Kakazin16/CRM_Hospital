package br.com.hospitalcrm.controller;

import br.com.hospitalcrm.model.Agendamento;
import br.com.hospitalcrm.service.AgendamentoService;

import java.util.List;

public class AgendamentoController {

    private AgendamentoService service = new AgendamentoService();

    public void salvar(Agendamento a) {
        service.agendar(a);
    }

    public List<Agendamento> listar() {
        return service.listar();
    }
}