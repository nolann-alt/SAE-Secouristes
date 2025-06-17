package metier.graphe.model.dao;

import metier.persistence.Journee;

import java.sql.*;
import java.util.*;

public class JourneeDAO extends DAO<Journee> {

    @Override
    public int create(Journee j) {
        String query = "INSERT INTO Journee (jour, mois, annee) VALUES (?, ?, ?)";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setInt(1, j.getJour());
            ps.setInt(2, j.getMois());
            ps.setInt(3, j.getAnnee());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Journee j) {
        // ATTENTION : mise à jour sur la même clé → inefficace si pas de modif sur un champ clé
        String query = "UPDATE Journee SET jour = ?, mois = ?, annee = ? WHERE jour = ? AND mois = ? AND annee = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setInt(1, j.getJour());
            ps.setInt(2, j.getMois());
            ps.setInt(3, j.getAnnee());
            ps.setInt(4, j.getJour());
            ps.setInt(5, j.getMois());
            ps.setInt(6, j.getAnnee());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int delete(Journee j) {
        String query = "DELETE FROM Journee WHERE jour = ? AND mois = ? AND annee = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setInt(1, j.getJour());
            ps.setInt(2, j.getMois());
            ps.setInt(3, j.getAnnee());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Journee> findAll() {
        List<Journee> liste = new LinkedList<>();
        String query = "SELECT * FROM Journee";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(new Journee(
                        rs.getInt("jour"),
                        rs.getInt("mois"),
                        rs.getInt("annee")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return liste;
    }

    @Override
    public Journee findByID(Long id) {
        // Méthode inapplicable sans identifiant unique, retourne null
        return null;
    }

    public Journee findByDate(int jour, int mois, int annee) {
        String query = "SELECT * FROM Journee WHERE jour = ? AND mois = ? AND annee = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setInt(1, jour);
            ps.setInt(2, mois);
            ps.setInt(3, annee);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Journee(
                            rs.getInt("jour"),
                            rs.getInt("mois"),
                            rs.getInt("annee")
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
