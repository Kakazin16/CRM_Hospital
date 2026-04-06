package br.com.hospitalcrm.dao;

import br.com.hospitalcrm.model.Lead;
import br.com.hospitalcrm.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeadDAO {

    public void salvar(Lead l) {
        String sql = "INSERT INTO LEAD (NOME, TELEFONE, EMAIL, INTERESSE, ORIGEM, STATUS) VALUES (?,?,?,?,?,?)";

        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, l.getNome());
            ps.setString(2, l.getTelefone());
            ps.setString(3, l.getEmail());
            ps.setString(4, l.getInteresse());
            ps.setString(5, l.getOrigem());
            ps.setString(6, l.getStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Lead> listar() {
        List<Lead> lista = new ArrayList<>();
        String sql = "SELECT * FROM LEAD";

        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Lead l = new Lead();
                l.setId(rs.getLong("ID"));
                l.setNome(rs.getString("NOME"));
                l.setStatus(rs.getString("STATUS"));

                lista.add(l);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public boolean existePorEmail(String email) {
        String sql = "SELECT 1 FROM LEAD WHERE EMAIL = ?";
        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}