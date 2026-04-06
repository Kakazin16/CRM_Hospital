package br.com.hospitalcrm.dao;

import br.com.hospitalcrm.model.Medico;
import br.com.hospitalcrm.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    public void salvar(Medico m) {
        String sql = "INSERT INTO MEDICO (NOME, CRM, TELEFONE, EMAIL) VALUES (?,?,?,?)";
        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNome());
            ps.setString(2, m.getCrm());
            ps.setString(3, m.getTelefone());
            ps.setString(4, m.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editar(Medico m) {
        String sql = "UPDATE MEDICO SET NOME=?, CRM=?, TELEFONE=?, EMAIL=? WHERE ID=?";
        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNome());
            ps.setString(2, m.getCrm());
            ps.setString(3, m.getTelefone());
            ps.setString(4, m.getEmail());
            ps.setLong(5, m.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar médico", e);
        }
    }

    public List<Medico> listar() {
        List<Medico> lista = new ArrayList<>();
        String sql = "SELECT * FROM MEDICO WHERE ATIVO = 'S'";
        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Medico m = new Medico();
                m.setId(rs.getLong("ID"));
                m.setNome(rs.getString("NOME"));
                m.setCrm(rs.getString("CRM"));
                m.setTelefone(rs.getString("TELEFONE"));
                m.setEmail(rs.getString("EMAIL"));
                lista.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public boolean existePorCrm(String crm) {
        String sql = "SELECT 1 FROM MEDICO WHERE CRM = ?";
        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, crm);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public void inativar(Long id) {
        String sql = "UPDATE MEDICO SET ATIVO = 'N' WHERE ID = ?";
        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}