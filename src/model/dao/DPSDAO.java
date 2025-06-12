package model.dao;

import metier.persistence.DPS;

import java.sql.*;
import java.util.*;

public class DPSDAO extends DAO<DPS> {

    @Override
    public int create(DPS dps) {
        String query = "INSERT INTO DPS (id, horaireDepart, horaireFin, sportAssocie, codeSite) VALUES (" +
                dps.getId() + ", " + dps.getHoraireDepart() + ", " + dps.getHoraireFin() + ", '" +
                dps.getSportAssocie() + "', " + dps.getCodeSite() + ")";
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(DPS dps) {
        String query = "UPDATE DPS SET horaireDepart=" + dps.getHoraireDepart() +
                ", horaireFin=" + dps.getHoraireFin() +
                ", sportAssocie='" + dps.getSportAssocie() +
                "', codeSite=" + dps.getCodeSite() +
                " WHERE id=" + dps.getId();
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public int delete(DPS dps) {
        String query = "DELETE FROM DPS WHERE id=" + dps.getId();
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<DPS> findAll() {
        List<DPS> liste = new LinkedList<>();
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM DPS")) {
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
        try (Connection connexion = getConnection();
             Statement statement = connexion.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM DPS WHERE id=" + id)) {
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
