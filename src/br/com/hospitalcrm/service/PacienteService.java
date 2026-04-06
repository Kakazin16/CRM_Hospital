package br.com.hospitalcrm.service;

import br.com.hospitalcrm.dao.PacienteDAO;
import br.com.hospitalcrm.exception.CadastroDuplicadoException;
import br.com.hospitalcrm.model.Paciente;

import java.util.List;

public class PacienteService {

    private PacienteDAO dao = new PacienteDAO();

    public void cadastrar(Paciente p) throws CadastroDuplicadoException {
        if (p.getCpf() == null || p.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF obrigatório");
        }
        if (dao.existePorCpf(p.getCpf())) {
            throw new CadastroDuplicadoException("CPF já cadastrado");
        }
        p.calcularIMC();
        if (p.getImc() > 999.99) {
            throw new IllegalArgumentException("IMC inválido");
        }
        dao.salvar(p);
    }

    public void editar(Paciente p) {
        if (p.getId() == null) {
            throw new IllegalArgumentException("ID do paciente é obrigatório para edição.");
        }
        if (p.getCpf() == null || p.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF obrigatório.");
        }
        p.calcularIMC();
        dao.editar(p);
    }

    public void inativar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do paciente é obrigatório.");
        }
        dao.inativar(id);
    }

    public Paciente buscarPorCpf(String cpf) {
        return dao.buscarPorCpf(cpf);
    }

    public List<Paciente> listar() {
        return dao.listar();
    }
}