package metier.graphe.model.dao;

import metier.persistence.Sport;

import java.sql.*;
import java.util.*;

public class SportDAO extends DAO<Sport> {

    @Override
    public int create(Sport sport) {
        String query = "INSERT INTO Sport (nom, niveau_de_risque, competences_requises) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, sport.getNom());
            ps.setString(2, sport.getNiveauDeRisque());
            ps.setString(3, sport.getCompetencesRequises());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Sport sport) {
        String query = "UPDATE Sport SET niveau_de_risque = ?, competences_requises = ? WHERE nom = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, sport.getNiveauDeRisque());
            ps.setString(2, sport.getCompetencesRequises());
            ps.setString(3, sport.getNom());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int delete(Sport sport) {
        String query = "DELETE FROM Sport WHERE nom = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, sport.getNom());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Sport> findAll() {
        List<Sport> list = new ArrayList<>();
        String query = "SELECT * FROM Sport";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Sport(
                        rs.getString("nom"),
                        rs.getString("niveau_de_risque"),
                        rs.getString("competences_requises")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Sport findByID(Long id) {
        return null; // La clé primaire est un String, donc findByID ne s'applique pas ici
    }

    public Sport findByNom(String nom) {
        String query = "SELECT * FROM Sport WHERE nom = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Sport(
                        rs.getString("nom"),
                        rs.getString("niveau_de_risque"),
                        rs.getString("competences_requises")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
