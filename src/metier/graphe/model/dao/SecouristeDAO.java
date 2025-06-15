package metier.graphe.model.dao;

import metier.persistence.Secouriste;

import java.sql.*;
import java.util.*;

public class SecouristeDAO extends DAO<Secouriste> {

    @Override
    public int create(Secouriste s) {
        String query = "INSERT INTO Secouriste (nom, prenom, email, motDePasse) VALUES ('" +
                s.getNom() + "', '" + s.getPrenom() + "', '" +
                s.getEmail() + "', '" + s.getMotDePasse() + "')";
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Secouriste s) {
        String query = "UPDATE Secouriste SET nom='" + s.getNom() +
                "', prenom='" + s.getPrenom() +
                "', email='" + s.getEmail() +
                "', telephone='" + s.getTelephone() +
                "' WHERE id=" + s.getId();
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }


    @Override
    public int delete(Secouriste s) {
        String query = "DELETE FROM Secouriste WHERE id=" + s.getId();
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Secouriste> findAll() {
        List<Secouriste> liste = new LinkedList<>();
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM Secouriste")) {
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
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM Secouriste WHERE id=" + id)) {
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Secouriste findByEmail(String email) {
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM Secouriste WHERE email='" + email + "'")) {
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
