package metier.graphe.model.dao;

import metier.persistence.Disponibilite;
import metier.persistence.Journee;

import java.sql.*;
import java.util.*;

public class DispoDAO extends DAO<Disponibilite> {

    @Override
    public int create(Disponibilite d) {
        String query = "INSERT INTO Disponibilite (idSecouriste, jour, mois, annee) VALUES (" +
                d.getIdSecouriste() + ", " +
                d.getDateDispo().getJour() + ", " +
                d.getDateDispo().getMois() + ", " +
                d.getDateDispo().getAnnee() + ")";
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Disponibilite d) {
        return -1;
    }

    @Override
    public int delete(Disponibilite d) {
        String query = "DELETE FROM Disponibilite WHERE idSecouriste=" + d.getIdSecouriste() +
                " AND jour=" + d.getDateDispo().getJour() +
                " AND mois=" + d.getDateDispo().getMois() +
                " AND annee=" + d.getDateDispo().getAnnee();
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Disponibilite> findAll() {
        List<Disponibilite> liste = new LinkedList<>();
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM Disponibilite")) {
            while (rs.next()) {
                Journee j = new Journee(
                        rs.getInt("jour"),
                        rs.getInt("mois"),
                        rs.getInt("annee")
                );
                liste.add(new Disponibilite(rs.getInt("idSecouriste"), j));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return liste;
    }

    @Override
    public Disponibilite findByID(Long id) {
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM Disponibilite WHERE idSecouriste=" + id)) {
            if (rs.next()) {
                Journee j = new Journee(
                        rs.getInt("jour"),
                        rs.getInt("mois"),
                        rs.getInt("annee")
                );
                return new Disponibilite(rs.getInt("idSecouriste"), j);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Disponibilite> findAllBySecouriste(int idSec) {
        List<Disponibilite> liste = new LinkedList<>();
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM Disponibilite WHERE idSecouriste=" + idSec)) {
            while (rs.next()) {
                Journee j = new Journee(
                        rs.getInt("jour"),
                        rs.getInt("mois"),
                        rs.getInt("annee")
                );
                liste.add(new Disponibilite(rs.getInt("idSecouriste"), j));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return liste;
    }
}
