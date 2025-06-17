package metier.graphe.model.dao;

import metier.persistence.DPS;

import java.sql.*;
import java.util.*;

public class DPSDAO extends DAO<DPS> {

    @Override
    public int create(DPS dps) {
        String query = "INSERT INTO DPS (id, horaireDepart, horaireFin, sportAssocie, codeSite) VALUES (?, ?, ?, ?, ?)";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setLong(1, dps.getId());
            ps.setInt(2, dps.getHoraireDepart());
            ps.setInt(3, dps.getHoraireFin());
            ps.setString(4, dps.getSportAssocie());
            ps.setInt(5, dps.getCodeSite());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(DPS dps) {
        String query = "UPDATE DPS SET horaireDepart = ?, horaireFin = ?, sportAssocie = ?, codeSite = ? WHERE id = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setInt(1, dps.getHoraireDepart());
            ps.setInt(2, dps.getHoraireFin());
            ps.setString(3, dps.getSportAssocie());
            ps.setInt(4, dps.getCodeSite());
            ps.setLong(5, dps.getId());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int delete(DPS dps) {
        String query = "DELETE FROM DPS WHERE id = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setLong(1, dps.getId());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<DPS> findAll() {
        List<DPS> liste = new LinkedList<>();
        String query = "SELECT * FROM DPS";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DPS dps = new DPS(
                        rs.getLong("id"),
                        rs.getInt("horaireDepart"),
                        rs.getInt("horaireFin")
                );
                dps.setSportAssocie(rs.getString("sportAssocie"));
                dps.setCodeSite(rs.getInt("codeSite"));
                liste.add(dps);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return liste;
    }

    @Override
    public DPS findByID(Long id) {
        String query = "SELECT * FROM DPS WHERE id = ?";
        try (Connection connexion = getConnection();
             PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DPS dps = new DPS(
                            rs.getLong("id"),
                            rs.getInt("horaireDepart"),
                            rs.getInt("horaireFin")
                    );
                    dps.setSportAssocie(rs.getString("sportAssocie"));
                    dps.setCodeSite(rs.getInt("codeSite"));
                    return dps;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
