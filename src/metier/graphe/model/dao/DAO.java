package metier.graphe.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Abstract generic DAO class that defines the basic CRUD operations for any entity type {@code T}.
 * This class provides a reusable database connection method and must be extended by concrete DAO classes.
 *
 * @param <T> The type of object the DAO will manage.
 */
public abstract class DAO<T> {

    /** Database URL */
    private static final String URL = "jdbc:mysql://localhost:3306/secours2030";

    /** Database user */
    private static final String USER = "Marin";

    /** Database password */
    private static final String PASSWORD = "rootroot";

    /**
     * Establishes and returns a connection to the database using JDBC.
     *
     * @return A {@link Connection} to the MySQL database.
     * @throws SQLException If the connection fails.
     */
    protected Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Inserts a new record into the database.
     *
     * @param element The object to be inserted.
     * @return An integer status (typically 1 if success, 0 or -1 if failed).
     */
    public abstract int create(T element);

    /**
     * Updates an existing record in the database.
     *
     * @param element The object with updated values.
     * @return An integer status (typically 1 if success, 0 or -1 if failed).
     */
    public abstract int update(T element);

    /**
     * Deletes a record from the database.
     *
     * @param element The object to be deleted.
     * @return An integer status (typically 1 if success, 0 or -1 if failed).
     */
    public abstract int delete(T element);

    /**
     * Finds a record by its ID.
     *
     * @param id The ID of the desired record.
     * @return The object corresponding to the ID, or {@code null} if not found.
     */
    public abstract T findByID(Long id);

    /**
     * Retrieves all records from the table.
     *
     * @return A {@link List} of all records.
     */
    public abstract List<T> findAll();
}
