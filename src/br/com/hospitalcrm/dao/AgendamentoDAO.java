package br.com.hospitalcrm.dao;

import br.com.hospitalcrm.model.Agendamento;
import br.com.hospitalcrm.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    public void salvar(Agendamento a) {
        String sql = "INSERT INTO AGENDAMENTO (PACIENTE_ID, MEDICO_ID, DATA_HORA, STATUS) VALUES (?,?,?,?)";

        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, a.getPacienteId());
            ps.setLong(2, a.getMedicoId());
            ps.setTimestamp(3, Timestamp.valueOf(a.getDataHora()));
            ps.setString(4, a.getStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Agendamento> listar() {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM AGENDAMENTO";

        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Agendamento a = new Agendamento();
                a.setId(rs.getLong("ID"));
                a.setPacienteId(rs.getLong("PACIENTE_ID"));
                a.setMedicoId(rs.getLong("MEDICO_ID"));
                a.setDataHora(rs.getTimestamp("DATA_HORA").toLocalDateTime());
                a.setStatus(rs.getString("STATUS"));

                lista.add(a);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public void atualizarStatus(Agendamento a) {
        String sql = "UPDATE AGENDAMENTO SET STATUS = ? WHERE ID = ?";
        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getStatus());
            ps.setLong(2, a.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}