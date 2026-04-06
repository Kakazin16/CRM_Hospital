package br.com.hospitalcrm.dao;

import br.com.hospitalcrm.model.Paciente;
import br.com.hospitalcrm.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    private Paciente mapearPaciente(ResultSet rs) throws SQLException {
        Paciente p = new Paciente();
        p.setId(rs.getLong("ID"));
        p.setNome(rs.getString("NOME"));
        p.setCpf(rs.getString("CPF"));
        p.setDataNascimento(rs.getDate("DATA_NASCIMENTO"));
        p.setSexo(rs.getString("SEXO") != null ? rs.getString("SEXO").trim() : null);
        p.setPeso(rs.getDouble("PESO"));
        p.setAltura(rs.getDouble("ALTURA"));
        p.setImc(rs.getDouble("IMC"));
        p.setEmail(rs.getString("EMAIL"));
        p.setTelefone(rs.getString("TELEFONE"));
        p.setPorOndeConheceu(rs.getString("POR_ONDE_CONHECEU"));
        return p;
    }

    public void salvar(Paciente p) {
        String sql = "INSERT INTO PACIENTE (NOME,CPF,DATA_NASCIMENTO,SEXO,PESO,ALTURA,IMC,EMAIL,TELEFONE,POR_ONDE_CONHECEU) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCpf());
            if (p.getDataNascimento() != null) {
                ps.setDate(3, new java.sql.Date(p.getDataNascimento().getTime()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setString(4, p.getSexo());
            ps.setDouble(5, p.getPeso());
            ps.setDouble(6, p.getAltura());
            ps.setDouble(7, p.getImc());
            ps.setString(8, p.getEmail());
            ps.setString(9, p.getTelefone());
            ps.setString(10, p.getPorOndeConheceu());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar paciente", e);
        }
    }

    public void editar(Paciente p) {
        String sql = "UPDATE PACIENTE SET NOME=?, CPF=?, DATA_NASCIMENTO=?, SEXO=?, PESO=?, ALTURA=?, IMC=?, EMAIL=?, TELEFONE=?, POR_ONDE_CONHECEU=? WHERE ID=?";
        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCpf());
            if (p.getDataNascimento() != null) {
                ps.setDate(3, new java.sql.Date(p.getDataNascimento().getTime()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setString(4, p.getSexo());
            ps.setDouble(5, p.getPeso());
            ps.setDouble(6, p.getAltura());
            ps.setDouble(7, p.getImc());
            ps.setString(8, p.getEmail());
            ps.setString(9, p.getTelefone());
            ps.setString(10, p.getPorOndeConheceu());
            ps.setLong(11, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar paciente", e);
        }
    }

    public boolean existePorCpf(String cpf) {
        String sql = "SELECT 1 FROM PACIENTE WHERE CPF = ?";
        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public Paciente buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM PACIENTE WHERE CPF = ?";
        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapearPaciente(rs) : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Paciente> listar() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM PACIENTE WHERE ATIVO = 'S'";
        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearPaciente(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public void inativar(Long id) {
        String sql = "UPDATE PACIENTE SET ATIVO = 'N' WHERE ID = ?";
        try (Connection con = ConnectionDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}