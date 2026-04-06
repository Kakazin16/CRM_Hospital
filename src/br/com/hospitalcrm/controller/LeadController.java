package br.com.hospitalcrm.controller;

import br.com.hospitalcrm.exception.CadastroDuplicadoException;
import br.com.hospitalcrm.model.Lead;
import br.com.hospitalcrm.service.LeadService;

import java.util.List;

public class LeadController {

    private LeadService service = new LeadService();

    public String salvar(Lead l) {
        try {
            service.cadastrar(l);
            return "Lead salvo com sucesso!";
        } catch (CadastroDuplicadoException e) {
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro: " + e.getMessage();
        }
    }

    public List<Lead> listar() {
        return service.listar();
    }
}