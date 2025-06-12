package metier.graphe.model.dao;

import metier.persistence.Journee;

import java.sql.*;
import java.util.*;

public class JourneeDAO extends DAO<Journee> {

    @Override
    public int create(Journee j) {
        String query = "INSERT INTO Journee (jour, mois, annee) VALUES (" +
                j.getJour() + ", " + j.getMois() + ", " + j.getAnnee() + ")";
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Journee j) {
        String query = "UPDATE Journee SET jour=" + j.getJour() +
                ", mois=" + j.getMois() +
                ", annee=" + j.getAnnee() +
                " WHERE jour=" + j.getJour() + " AND mois=" + j.getMois() + " AND annee=" + j.getAnnee();
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int delete(Journee j) {
        String query = "DELETE FROM Journee WHERE jour=" + j.getJour() +
                " AND mois=" + j.getMois() +
                " AND annee=" + j.getAnnee();
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Journee> findAll() {
        List<Journee> liste = new LinkedList<>();
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM Journee")) {
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
        return null;
    }

    public Journee findByDate(int jour, int mois, int annee) {
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM Journee WHERE jour=" + jour + " AND mois=" + mois + " AND annee=" + annee)) {
            if (rs.next()) {
                return new Journee(
                        rs.getInt("jour"),
                        rs.getInt("mois"),
                        rs.getInt("annee")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
