package br.com.hospitalcrm.controller;

import br.com.hospitalcrm.exception.CadastroDuplicadoException;
import br.com.hospitalcrm.model.Medico;
import br.com.hospitalcrm.service.MedicoService;

import java.util.List;

public class MedicoController {

    private MedicoService service = new MedicoService();

    public String salvar(Medico m) {
        try {
            service.cadastrar(m);
            return "Médico salvo com sucesso!";
        } catch (CadastroDuplicadoException e) {
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro: " + e.getMessage();
        }
    }

    public String editar(Medico m) {
        try {
            service.editar(m);
            return "Médico editado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro: " + e.getMessage();
        }
    }

    public String inativar(Long id) {
        try {
            service.inativar(id);
            return "Médico inativado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro: " + e.getMessage();
        }
    }

    public List<Medico> listar() {
        return service.listar();
    }
}