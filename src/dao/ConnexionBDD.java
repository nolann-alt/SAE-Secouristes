package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBDD {
    private static Connection instance;

    private ConnexionBDD() {}

    public static Connection getInstance() {
        if (instance == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/secours2030";
                String user = "root";
                String password = "rootroot";
                instance = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.err.println("Erreur de connexion : " + e.getMessage());
            }
        }
        return instance;
    }
}

