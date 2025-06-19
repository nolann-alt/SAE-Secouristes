package metier.graphe.model.dao;

import metier.persistence.Disponibilite;
import metier.persistence.Journee;

import java.sql.*;
import java.util.*;

/**
 * DAO class for managing {@link Disponibilite} records in the database.
 * Handles CRUD operations and additional utility methods for availability logic.
 */
public class DispoDAO extends DAO<Disponibilite> {

    /**
     * DAO class for managing {@link Disponibilite} records in the database.
     * Handles CRUD operations and additional utility methods for availability logic.
     */
    @Override
    public int create(Disponibilite d) {
        String query = "INSERT INTO Disponibilite (idSecouriste, jour, mois, annee) VALUES (?, ?, ?, ?)";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setInt(1, (int) d.getIdSecouriste());
            ps.setInt(2, d.getDateDispo().getJour());
            ps.setInt(3, d.getDateDispo().getMois());
            ps.setInt(4, d.getDateDispo().getAnnee());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Updates an availability. Not supported.
     *
     * @param d The availability to update.
     * @return Always returns -1.
     */
    @Override
    public int update(Disponibilite d) {
        // Non applicable : pas de clé primaire unique autre que tous les champs
        return -1;
    }

    /**
     * Deletes an availability from the database.
     *
     * @param d The availability to delete.
     * @return 1 if the deletion was successful, -1 otherwise.
     */
    @Override
    public int delete(Disponibilite d) {
        String query = "DELETE FROM Disponibilite WHERE idSecouriste = ? AND jour = ? AND mois = ? AND annee = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setInt(1, (int) d.getIdSecouriste());
            ps.setInt(2, d.getDateDispo().getJour());
            ps.setInt(3, d.getDateDispo().getMois());
            ps.setInt(4, d.getDateDispo().getAnnee());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Retrieves all availability records.
     *
     * @return A list of all {@link Disponibilite} entries.
     */
    @Override
    public List<Disponibilite> findAll() {
        List<Disponibilite> liste = new LinkedList<>();
        String query = "SELECT * FROM Disponibilite";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
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

    /**
     * Retrieves a single availability by secouriste ID.
     *
     * @param id The ID of the secouriste.
     * @return The first {@link Disponibilite} found, or null if none.
     */
    @Override
    public Disponibilite findByID(Long id) {
        String query = "SELECT * FROM Disponibilite WHERE idSecouriste = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Journee j = new Journee(
                            rs.getInt("jour"),
                            rs.getInt("mois"),
                            rs.getInt("annee")
                    );
                    return new Disponibilite(rs.getInt("idSecouriste"), j);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all availability entries for a specific secouriste.
     *
     * @param idSec The ID of the secouriste.
     * @return A list of {@link Disponibilite} objects.
     */
    public List<Disponibilite> findAllBySecouriste(int idSec) {
        List<Disponibilite> liste = new LinkedList<>();
        String query = "SELECT * FROM Disponibilite WHERE idSecouriste = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setInt(1, idSec);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Journee j = new Journee(
                            rs.getInt("jour"),
                            rs.getInt("mois"),
                            rs.getInt("annee")
                    );
                    liste.add(new Disponibilite(rs.getInt("idSecouriste"), j));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return liste;
    }

    /**
     * Checks if a secouriste is available on a specific date.
     *
     * @param idSecouriste The ID of the secouriste.
     * @param journee      The date to check.
     * @return {@code true} if available, {@code false} otherwise.
     */
    public boolean isDisponible(int idSecouriste, Journee journee) {
        String query = "SELECT 1 FROM Disponibilite WHERE idSecouriste = ? AND jour = ? AND mois = ? AND annee = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setInt(1, idSecouriste);
            ps.setInt(2, journee.getJour());
            ps.setInt(3, journee.getMois());
            ps.setInt(4, journee.getAnnee());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true s'il y a une ligne => disponible
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
