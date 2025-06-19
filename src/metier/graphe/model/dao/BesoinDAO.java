package metier.graphe.model.dao;

import metier.persistence.Besoin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class BesoinDAO extends DAO<Besoin> {

    @Override
    public int create(Besoin besoin) {
        String query = "INSERT INTO Besoin (nombreSecouriste, intituleComp, idDPS) VALUES (?, ?, ?)";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setInt(1, besoin.getNombreSecouriste());
            ps.setString(2, besoin.getIntituleComp());
            ps.setInt(3, (int) besoin.getIdDPS());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Besoin besoin) {
        // Non applicable car on ne modifie pas un besoin existant (clé multiple)
        return -1;
    }

    @Override
    public int delete(Besoin besoin) {
        String query = "DELETE FROM Besoin WHERE intituleComp = ? AND idDPS = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setString(1, besoin.getIntituleComp());
            ps.setInt(2, (int) besoin.getIdDPS());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Besoin> findAll() {
        List<Besoin> liste = new LinkedList<>();
        String query = "SELECT * FROM Besoin";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Besoin b = new Besoin(
                        rs.getInt("nombreSecouriste"),
                        rs.getString("intituleComp"),
                        rs.getInt("idDPS")
                );
                liste.add(b);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return liste;
    }

    @Override
    public Besoin findByID(Long id) {
        // Non applicable car pas d'identifiant unique
        return null;
    }

    /**
     * Récupère tous les besoins pour un DPS donné.
     */
    public List<Besoin> findAllByDPS(int idDPS) {
        List<Besoin> liste = new LinkedList<>();
        String query = "SELECT * FROM Besoin WHERE idDPS = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setInt(1, idDPS);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Besoin b = new Besoin(
                            rs.getInt("nombreSecouriste"),
                            rs.getString("intituleComp"),
                            rs.getInt("idDPS")
                    );
                    liste.add(b);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return liste;
    }

}
