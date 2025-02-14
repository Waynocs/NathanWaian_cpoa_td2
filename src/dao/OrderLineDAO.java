package dao;

import model.OrderLine;

/**
 * Interface DAO utilise un type T cette class hérite de DAO, et l'objet de type
 * T se défini ici comme OrderLine
 */
public interface OrderLineDAO extends DAO<OrderLine> {
    /**
     * Finds a order line by its order and product ids
     * 
     * @param order   id of the order
     * @param product id of the product
     * @return the line found, or null for nothing
     * @exception DAOException thrown when bad stuff happened
     */
    OrderLine getById(int order, int product) throws DAOException;

    /**
     * Returns Finds all lines from a single order
     * 
     * @param order order to look for
     * @return all the lines from a single order
     * @exception DAOException thrown when bad stuff happened
     */
    OrderLine[] getAllFromOrder(int order) throws DAOException;

    /**
     * Finds all lines from a single product
     * 
     * @param product product to look for
     * @return all the lines from a single product
     * @exception DAOException thrown when bad stuff happened
     */
    OrderLine[] getAllFromProduct(int product) throws DAOException;

    /**
     * Finds all lines
     * 
     * @return all the lines
     * @exception DAOException thrown when bad stuff happened
     */
    OrderLine[] getAll() throws DAOException;

}
