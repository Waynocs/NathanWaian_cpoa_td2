package dao.mySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.*;

import dao.DAOException;
import dao.OrderDAO;
import model.Order;
import model.OrderLine;
import request.Connection;

/**
 * Class used to manage orders using the MySQLDAOFactory
 */
public class MySQLOrderDAO implements OrderDAO {

    private static MySQLOrderDAO instance;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private MySQLOrderDAO() {
    }

    /**
     * Returns the only instance of this class
     * 
     * @return the only instance of this class
     */
    public static MySQLOrderDAO getInstance() {
        if (instance == null)
            instance = new MySQLOrderDAO();

        return instance;
    }

    @Override
    public Order create(final Order object) {
        try {
            if (MySQLCustomerDAO.getInstance().getById(object.getCustomer()) == null)
                throw new DAOException("No customer with id '" + object.getCustomer() + "' found");
            var statement = Connection.getConnection().prepareStatement(
                    "INSERT INTO `commande` (`date_commande`, `id_client`) VALUES ('"
                            + object.getDate().format(formatter) + "', '" + +object.getCustomer() + "');",
                    Statement.RETURN_GENERATED_KEYS);
            var result = statement.executeUpdate();
            if (result == 0)
                return null;
            else {
                var keys = statement.getGeneratedKeys();
                keys.next();
                return getById((int) keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean update(final Order object) {
        try {
            if (MySQLCustomerDAO.getInstance().getById(object.getCustomer()) == null)
                throw new DAOException("No customer with id '" + object.getCustomer() + "' found");
            var statement = Connection.getConnection()
                    .prepareStatement("UPDATE `commande` SET `date_commande`= '" + object.getDate().format(formatter)
                            + "',`id_client`='" + object.getCustomer() + "' WHERE `id_commande` = " + object.getId());

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean delete(final Order object) {
        try {
            for (OrderLine line : MySQLOrderLineDAO.getInstance().getAllFromOrder(object.getId()))
                MySQLOrderLineDAO.getInstance().delete(line);
            var statement = Connection.getConnection()
                    .prepareStatement("DELETE FROM `commande` WHERE `id_commande` = " + object.getId());

            for (var line : MySQLOrderLineDAO.getInstance().getAllFromOrder(object.getId()))
                MySQLOrderLineDAO.getInstance().delete(line);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Order getById(final int id) {
        try {
            var statement = Connection.getConnection().createStatement();
            var result = statement.executeQuery(
                    "SELECT `id_commande`, `date_commande`, `id_client` FROM `commande` WHERE `id_commande`=" + id);
            return result.next()
                    ? new Order(result.getInt("id_commande"), result.getTimestamp("date_commande").toLocalDateTime(),
                            result.getInt("id_client"))
                    : null;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Order[] getAll() {
        try {
            final var statement = Connection.getConnection().createStatement();
            final ResultSet result = statement
                    .executeQuery("SELECT `id_commande`, `date_commande`, `id_client` FROM `commande`");
            var orderList = new LinkedList<Order>();
            while (result.next())
                orderList.add(new Order(result.getInt("id_commande"),
                        result.getTimestamp("date_commande").toLocalDateTime(), result.getInt("id_client")));
            return orderList.size() > 0 ? orderList.toArray(new Order[0]) : null;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

}
