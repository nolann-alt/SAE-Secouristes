package metier.graphe.model.dao;

import metier.persistence.DPS;

import java.sql.*;
import java.util.*;

public class DPSDAO extends DAO<DPS> {

    @Override
    public int create(DPS dps) {
        String query = "INSERT INTO DPS (label, date, heure_debut, heure_fin, sportAssocie, codeSite, lieu, description, couleur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, dps.getLabel());
            ps.setDate(2, dps.getDate());
            ps.setTime(3, dps.getHeureDebut());
            ps.setTime(4, dps.getHeureFin());
            ps.setString(5, dps.getSportAssocie());
            ps.setInt(6, dps.getCodeSite());
            ps.setString(7, dps.getLieu());
            ps.setString(8, dps.getDescription());
            ps.setString(9, dps.getCouleur());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(DPS dps) {
        String query = "UPDATE DPS SET label = ?, date = ?, heure_debut = ?, heure_fin = ?, sportAssocie = ?, codeSite = ?, lieu = ?, description = ?, couleur = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, dps.getLabel());
            ps.setDate(2, dps.getDate());
            ps.setTime(3, dps.getHeureDebut());
            ps.setTime(4, dps.getHeureFin());
            ps.setString(5, dps.getSportAssocie());
            ps.setInt(6, dps.getCodeSite());
            ps.setString(7, dps.getLieu());
            ps.setString(8, dps.getDescription());
            ps.setString(9, dps.getCouleur());
            ps.setLong(10, dps.getId());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int delete(DPS dps) {
        String query = "DELETE FROM DPS WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, dps.getId());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public DPS findByID(Long id) {
        String query = "SELECT * FROM DPS WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DPS dps = new DPS(
                        rs.getLong("id"),
                        rs.getString("label"),
                        rs.getDate("date"),
                        rs.getTime("heure_debut"),
                        rs.getTime("heure_fin"),
                        rs.getString("sportAssocie"),
                        rs.getInt("codeSite"),
                        rs.getString("lieu"),
                        rs.getString("description")
                );
                dps.setCouleur(rs.getString("couleur"));
                return dps;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DPS> findAll() {
        List<DPS> list = new ArrayList<>();
        String query = "SELECT * FROM DPS";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DPS dps = new DPS(
                        rs.getLong("id"),
                        rs.getString("label"),
                        rs.getDate("date"),
                        rs.getTime("heure_debut"),
                        rs.getTime("heure_fin"),
                        rs.getString("sportAssocie"),
                        rs.getInt("codeSite"),
                        rs.getString("lieu"),
                        rs.getString("description")
                );
                dps.setCouleur(rs.getString("couleur"));
                list.add(dps);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
