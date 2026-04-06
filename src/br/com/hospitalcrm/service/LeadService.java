package br.com.hospitalcrm.service;

import br.com.hospitalcrm.dao.LeadDAO;
import br.com.hospitalcrm.exception.CadastroDuplicadoException;
import br.com.hospitalcrm.model.Lead;

import java.util.Arrays;
import java.util.List;

public class LeadService {

    private LeadDAO dao = new LeadDAO();

    private static final List<String> STATUS_VALIDOS =
            Arrays.asList("FRIO", "MORNO", "QUENTE");

    public void cadastrar(Lead l) throws CadastroDuplicadoException {

        if (l.getNome() == null || l.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do lead é obrigatório.");
        }

        if (l.getStatus() == null || !STATUS_VALIDOS.contains(l.getStatus())) {
            throw new IllegalArgumentException(
                    "Status inválido. Use: FRIO, MORNO ou QUENTE.");
        }

        if (l.getEmail() != null && !l.getEmail().trim().isEmpty()) {
            if (dao.existePorEmail(l.getEmail())) {
                throw new CadastroDuplicadoException(
                        "Já existe um lead cadastrado com o e-mail: " + l.getEmail());
            }
        }

        dao.salvar(l);
    }

    public List<Lead> listar() {
        return dao.listar();
    }
}