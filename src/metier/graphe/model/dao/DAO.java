package metier.graphe.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public abstract class DAO<T> {

    private static final String URL = "jdbc:mysql://localhost:3306/secours2030";
    private static final String USER = "Marin";
    private static final String PASSWORD = "rootroot";

    protected Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public abstract int create(T element);
    public abstract int update(T element);
    public abstract int delete(T element);
    public abstract T findByID(Long id);
    public abstract List<T> findAll();
}
