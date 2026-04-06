package br.com.hospitalcrm.controller;

import br.com.hospitalcrm.exception.CadastroDuplicadoException;
import br.com.hospitalcrm.model.Paciente;
import br.com.hospitalcrm.service.PacienteService;

import java.util.List;

public class PacienteController {

    private PacienteService service = new PacienteService();

    public String salvar(Paciente p) {
        try {
            service.cadastrar(p);
            return "Paciente salvo com sucesso!";
        } catch (CadastroDuplicadoException e) {
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro: " + e.getMessage();
        }
    }

    public String editar(Paciente p) {
        try {
            service.editar(p);
            return "Paciente editado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro: " + e.getMessage();
        }
    }

    public String inativar(Long id) {
        try {
            service.inativar(id);
            return "Paciente inativado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro: " + e.getMessage();
        }
    }

    public Paciente buscar(String cpf) {
        return service.buscarPorCpf(cpf);
    }

    public List<Paciente> listar() {
        return service.listar();
    }
}