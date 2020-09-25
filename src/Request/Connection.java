package Request;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Object used to manage an SQL connection
 */
public class Connection {

    private static Connection instance;
    private java.sql.Connection connection;

    private Connection() {
        creeConnexion();
    }

    /**
     * Returns the active connection
     * 
     * @return the active connection
     * @throws SQLException
     */
    public static java.sql.Connection getConnection() throws SQLException {
        if (instance == null)
            instance = new Connection();
        if (instance.connection.isClosed())
            instance.creeConnexion();
        return instance.connection;
    }

    private void creeConnexion() {
        // Pour se connecter à un serveur local

        String url = "jdbc:mysql://localhost/serano1u_pullmoche";
        url += "?serverTimezone=Europe/Paris";
        String login = "root";
        String pwd = "";

        try {
            connection = DriverManager.getConnection(url, login, pwd);
        } catch (SQLException sqle) {
            System.out.println("Connexion error" + sqle.getMessage());
        }
    }
}