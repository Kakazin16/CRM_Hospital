package br.com.hospitalcrm.service;

import br.com.hospitalcrm.dao.MedicoDAO;
import br.com.hospitalcrm.exception.CadastroDuplicadoException;
import br.com.hospitalcrm.model.Medico;

import java.util.List;

public class MedicoService {

    private MedicoDAO dao = new MedicoDAO();

    public void cadastrar(Medico m) throws CadastroDuplicadoException {
        if (m.getNome() == null || m.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do médico é obrigatório.");
        }
        if (m.getCrm() == null || m.getCrm().trim().isEmpty()) {
            throw new IllegalArgumentException("CRM é obrigatório.");
        }
        if (dao.existePorCrm(m.getCrm())) {
            throw new CadastroDuplicadoException(
                    "Já existe um médico cadastrado com o CRM: " + m.getCrm());
        }
        dao.salvar(m);
    }

    public void editar(Medico m) {
        if (m.getId() == null) {
            throw new IllegalArgumentException("ID do médico é obrigatório para edição.");
        }
        if (m.getNome() == null || m.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do médico é obrigatório.");
        }
        if (m.getCrm() == null || m.getCrm().trim().isEmpty()) {
            throw new IllegalArgumentException("CRM é obrigatório.");
        }
        dao.editar(m);
    }

    public void inativar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do médico é obrigatório.");
        }
        dao.inativar(id);
    }

    public List<Medico> listar() {
        return dao.listar();
    }
}