package metier.graphe.model.dao;

import metier.persistence.Disponibilite;
import metier.persistence.Journee;

import java.sql.*;
import java.util.*;

public class DispoDAO extends DAO<Disponibilite> {

    @Override
    public int create(Disponibilite d) {
        if (exists((int) d.getIdSecouriste(), d.getDateDispo())) {
            // La disponibilité existe déjà, on ne fait rien ou on retourne 0
            return 0;
        }
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

    // Verifie si une disponibilité existe déjà pour un secouriste à une date donnée
    private boolean exists(int idSecouriste, Journee journee) {
        String query = "SELECT 1 FROM Disponibilite WHERE idSecouriste = ? AND jour = ? AND mois = ? AND annee = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setInt(1, idSecouriste);
            ps.setInt(2, journee.getJour());
            ps.setInt(3, journee.getMois());
            ps.setInt(4, journee.getAnnee());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true si trouvé, donc existe déjà
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public int update(Disponibilite d) {
        // Non applicable : pas de clé primaire unique autre que tous les champs
        return -1;
    }

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
     * Vérifie si un secouriste est disponible à une date donnée.
     * @param idSecouriste l'id du secouriste
     * @param journee la date à vérifier
     * @return true si le secouriste est disponible (c’est-à-dire qu’il n’a pas d’indisponibilité à cette date)
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
                return !rs.next(); // Inverse : true s'il n'est PAS dans la table, donc DISPONIBLE
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


}
