package metier.graphe.model.dao;

import metier.persistence.Sport;

import java.sql.*;
import java.util.*;

public class SportDAO extends DAO<Sport> {

    @Override
    public int create(Sport sport) {
        String query = "INSERT INTO Sport (code, nom) VALUES (?, ?)";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setString(1, sport.getCode());
            ps.setString(2, sport.getNom());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Sport sport) {
        String query = "UPDATE Sport SET nom = ? WHERE code = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setString(1, sport.getNom());
            ps.setString(2, sport.getCode());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int delete(Sport sport) {
        String query = "DELETE FROM Sport WHERE code = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setString(1, sport.getCode());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Sport> findAll() {
        List<Sport> liste = new LinkedList<>();
        String query = "SELECT * FROM Sport";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
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
        return null; // Inapplicable, la clé primaire est de type String
    }

    public Sport findByCode(String code) {
        String query = "SELECT * FROM Sport WHERE code = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Sport(
                            rs.getString("code"),
                            rs.getString("nom")
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
