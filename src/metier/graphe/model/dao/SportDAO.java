package metier.graphe.model.dao;

import metier.persistence.Sport;

import java.sql.*;
import java.util.*;

public class SportDAO extends DAO<Sport> {

    @Override
    public int create(Sport sport) {
        String query = "INSERT INTO Sport (code, nom) VALUES ('" + sport.getCode() + "', '" + sport.getNom() + "')";
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Sport sport) {
        String query = "UPDATE Sport SET nom='" + sport.getNom() + "' WHERE code='" + sport.getCode() + "'";
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int delete(Sport sport) {
        String query = "DELETE FROM Sport WHERE code='" + sport.getCode() + "'";
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Sport> findAll() {
        List<Sport> liste = new LinkedList<>();
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM Sport")) {
            while (rs.next()) {
                liste.add(new Sport(
                        rs.getString("code"),
                        rs.getString("nom")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return liste;
    }

    @Override
    public Sport findByID(Long id) {
        // Ici, le code est une chaîne, donc on ne peut pas utiliser Long.
        // On retourne null ou on change la signature si besoin.
        return null;
    }

    // BONUS : méthode utile spécifique
    public Sport findByCode(String code) {
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM Sport WHERE code='" + code + "'")) {
            if (rs.next()) {
                return new Sport(
                        rs.getString("code"),
                        rs.getString("nom")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
