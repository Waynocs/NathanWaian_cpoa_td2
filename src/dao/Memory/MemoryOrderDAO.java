package dao.Memory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import model.Order;

/**
 * Class used to manage orders using the MySQLDAOFactory
 */
public class MemoryOrderDAO implements dao.OrderDAO {
    private static int index = 0;

    private class Data {
        public LocalDateTime date;
        public int customer;

        public Data(LocalDateTime date, int customer) {

        }

    }

    private static MemoryOrderDAO instance;

    private Map<Integer, Data> memory;

    private MemoryOrderDAO() {
        memory = new HashMap<Integer, Data>();
    }

    /**
     * Returns the only instance of this class
     * 
     * @return the only instance of this class
     */
    public static MemoryOrderDAO getInstance() {
        return instance == null ? instance = new MemoryOrderDAO() : instance;
    }

    @Override
    public boolean create(Order object) {
        memory.put(index++, new Data(object.getDate(), object.getCustomer()));
        return true;
    }

    @Override
    public boolean update(Order object) {
        if (!memory.keySet().contains(object.getId()))
            return false;
        else {
            memory.put(object.getId(), new Data(object.getDate(), object.getCustomer()));
            return true;
        }
    }

    @Override
    public boolean delete(Order object) {
        if (!memory.keySet().contains(object.getId()))
            return false;
        else {
            memory.remove(object.getId());
            return true;
        }
    }

    @Override
    public Order getById(int id) {
        if (!memory.keySet().contains(id))
            return null;
        else {
            var element = memory.get(id);
            return new Order(id, element.date, element.customer);
        }
    }

    @Override
    public Order[] getAll() {
        var list = new LinkedList<Order>();
        for (var entry : memory.entrySet())
            list.add(new Order(entry.getKey(), entry.getValue().date, entry.getValue().customer));
        return list.toArray(new Order[0]);
    }

}