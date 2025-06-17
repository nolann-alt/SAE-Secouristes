package metier.graphe.model.dao;

import metier.persistence.Competences;
import metier.persistence.Possede;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PossedeDAO extends DAO<Possede> {

    @Override
    public int create(Possede possede) {
        String query = "INSERT INTO Possede (idSecouriste, intitule) VALUES (?, ?)";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setLong(1, possede.getSecouriste().getId());
            ps.setString(2, possede.getIntitule());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int deleteAllBySecouriste(long idSecouriste) {
        String query = "DELETE FROM Possede WHERE idSecouriste = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setLong(1, idSecouriste);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public List<Competences> findCompetencesBySecouriste(long secouristeId) {
        List<Competences> competences = new ArrayList<>();
        String query = "SELECT intitule FROM Possede WHERE idSecouriste = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setLong(1, secouristeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    competences.add(new Competences(rs.getString("intitule")));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return competences;
    }

    @Override
    public int update(Possede possede) {
        return -1; // Pas d'update logique pour une table d'association simple
    }

    @Override
    public int delete(Possede possede) {
        String query = "DELETE FROM Possede WHERE idSecouriste = ? AND intitule = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setLong(1, possede.getIdSecouriste());
            ps.setString(2, possede.getIntitule());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public Possede findByID(Long id) {
        return null;
    }

    @Override
    public List<Possede> findAll() {
        return null;
    }
}