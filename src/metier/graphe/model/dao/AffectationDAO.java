package metier.graphe.model.dao;

import metier.persistence.Affectation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AffectationDAO extends DAO<Affectation> {

    @Override
    public int create(Affectation affectation) {
        String query = "INSERT INTO Affectation (idSecouriste, idDPS) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, affectation.getIdSecouriste());
            ps.setInt(2, affectation.getIdDPS());

            return ps.executeUpdate(); // 1 si insertion réussie
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // erreur
        }
    }

    @Override
    public int update(Affectation affectation) {
        // Pas pertinent si la table ne contient que deux clés primaires (pas de mise à jour)
        throw new UnsupportedOperationException("Update non supporté pour Affectation.");
    }

    @Override
    public int delete(Affectation affectation) {
        String query = "DELETE FROM Affectation WHERE idSecouriste = ? AND idDPS = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, affectation.getIdSecouriste());
            ps.setInt(2, affectation.getIdDPS());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Affectation findByID(Long id) {
        // Pas applicable : pas de clé primaire simple
        throw new UnsupportedOperationException("findByID non supporté pour Affectation.");
    }

    @Override
    public List<Affectation> findAll() {
        throw new UnsupportedOperationException("findAll non implémenté pour Affectation.");
    }
}
