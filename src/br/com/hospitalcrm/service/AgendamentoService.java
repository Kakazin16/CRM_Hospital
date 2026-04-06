package br.com.hospitalcrm.service;

import br.com.hospitalcrm.dao.AgendamentoDAO;
import br.com.hospitalcrm.model.Agendamento;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class AgendamentoService {

    private AgendamentoDAO dao = new AgendamentoDAO();

    private static final List<String> STATUS_VALIDOS =
            Arrays.asList("AGENDADO", "CANCELADO", "REALIZADO",
                    "ATENDIDO", "FALTA", "ABANDONO", "REAGENDADO");

    public void agendar(Agendamento a) {

        if (a.getPacienteId() == null || a.getMedicoId() == null) {
            throw new IllegalArgumentException("Paciente e médico são obrigatórios.");
        }

        if (a.getDataHora() == null) {
            throw new IllegalArgumentException("Data e hora são obrigatórios.");
        }

        if (a.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível agendar para uma data/hora no passado.");
        }

        if (a.getStatus() == null || !STATUS_VALIDOS.contains(a.getStatus())) {
            throw new IllegalArgumentException(
                    "Status inválido. Use: AGENDADO, CANCELADO, REALIZADO, ATENDIDO, FALTA, ABANDONO ou REAGENDADO.");
        }

        dao.salvar(a);
    }

    public void cancelar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do agendamento é obrigatório.");
        }
        Agendamento a = new Agendamento();
        a.setId(id);
        a.setStatus("CANCELADO");
        dao.atualizarStatus(a);
    }

    public void atualizarStatus(Long id, String novoStatus) {
        if (id == null) {
            throw new IllegalArgumentException("ID do agendamento é obrigatório.");
        }
        if (novoStatus == null || !STATUS_VALIDOS.contains(novoStatus)) {
            throw new IllegalArgumentException(
                    "Status inválido. Use: AGENDADO, CANCELADO, REALIZADO, ATENDIDO, FALTA, ABANDONO ou REAGENDADO.");
        }
        Agendamento a = new Agendamento();
        a.setId(id);
        a.setStatus(novoStatus);
        dao.atualizarStatus(a);
    }

    public List<Agendamento> listar() {
        return dao.listar();
    }
}