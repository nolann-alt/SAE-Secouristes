package metier.graphe.model.dao;

import metier.persistence.Secouriste;

import java.sql.*;
import java.util.*;

public class SecouristeDAO extends DAO<Secouriste> {

    @Override
    public int create(Secouriste s) {
        String query = "INSERT INTO Secouriste (nom, prenom, email, motDePasse) VALUES (?, ?, ?, ?)";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setString(1, s.getNom());
            ps.setString(2, s.getPrenom());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getMotDePasse());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Secouriste s) {
        String query = "UPDATE Secouriste SET nom = ?, prenom = ?, email = ?, telephone = ? WHERE id = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setString(1, s.getNom());
            ps.setString(2, s.getPrenom());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getTelephone());
            ps.setLong(5, s.getId());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int delete(Secouriste s) {
        String query = "DELETE FROM Secouriste WHERE id = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setLong(1, s.getId());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Secouriste> findAll() {
        List<Secouriste> liste = new LinkedList<>();
        String query = "SELECT * FROM Secouriste";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(new Secouriste(
                        rs.getLong("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motDePasse"),
                        rs.getString("telephone")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return liste;
    }

    @Override
    public Secouriste findByID(Long id) {
        String query = "SELECT * FROM Secouriste WHERE id = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Secouriste(
                            rs.getLong("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("motDePasse"),
                            rs.getString("telephone")
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Secouriste findByEmail(String email) {
        String query = "SELECT * FROM Secouriste WHERE email = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Secouriste(
                            rs.getLong("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("motDePasse"),
                            rs.getString("telephone")
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
