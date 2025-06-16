package metier.graphe.model.dao;

import metier.persistence.Competences;
import metier.persistence.Possede;
import metier.persistence.Secouriste;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PossedeDAO extends DAO<Possede> {

    @Override
    public int create(Possede possede) {
        String query = "INSERT INTO Possede (idSecouriste, intitule) VALUES (" +
                possede.getSecouriste().getId() + ", '" + possede.getIntitule() + "')";
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int deleteAllBySecouriste(long idSecouriste) {
        String query = "DELETE FROM Possede WHERE idSecouriste = " + idSecouriste;
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public List<Competences> findCompetencesBySecouriste(long secouristeId) {
        List<Competences> competences = new ArrayList<>();
        String query = "SELECT intitule FROM Possede WHERE idSecouriste = " + secouristeId;

        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                competences.add(new Competences(rs.getString("intitule")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return competences;
    }

    @Override public int update(Possede possede) { return -1; }

    @Override public int delete(Possede possede) {
        String query = "DELETE FROM Possede WHERE idSecouriste = " + possede.getIdSecouriste() +
                " AND intituleComp = '" + possede.getIntitule() + "'";
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override public Possede findByID(Long id) { return null; }

    @Override public List<Possede> findAll() { return null; }
}
