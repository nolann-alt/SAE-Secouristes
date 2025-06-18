package metier.graphe.model.dao;

import metier.persistence.Affectation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AffectationDAO extends DAO<Affectation> {

    @Override
    public int create(Affectation affectation) {
        String sql = "INSERT INTO Affectation (idSecouriste, idDPS) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, affectation.getIdSecouriste());
            ps.setLong(2, affectation.getIdDPS());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delete(Affectation affectation) {
        String sql = "DELETE FROM Affectation WHERE idSecouriste = ? AND idDPS = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, affectation.getIdSecouriste());
            ps.setLong(2, affectation.getIdDPS());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int deleteAllByDPS(long idDPS) {
        String sql = "DELETE FROM Affectation WHERE idDPS = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idDPS);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Affectation affectation) {
        return 0; // Non applicable
    }

    @Override
    public Affectation findByID(Long id) {
        return null; // Non applicable (clé composite)
    }

    @Override
    public List<Affectation> findAll() {
        List<Affectation> list = new ArrayList<>();
        String sql = "SELECT * FROM Affectation";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Affectation(
                        rs.getInt("idSecouriste"),
                        "", // ou null si tu préfères, car intituleComp n’est pas en base
                        rs.getInt("idDPS")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
