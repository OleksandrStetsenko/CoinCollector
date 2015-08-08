/**
 * 
 */
package ua.edu.sumdu.lab3.group11.dao.coins;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.obj.Coin;

// TODO: Auto-generated Javadoc
/**
 * The Interface CoinDao.
 *
 * @author vtregub
 */
public interface CoinDao {

    /**
     *  Opens new connection.
     *
     * @return the connection
     * @throws SQLException the SQL exception
     */
    public Connection openConnection() throws SQLException;

    /**
     *  Close connection.
     *
     * @throws SQLException the SQL exception
     */
    public void closeConnection() throws SQLException;

    /**
     *  Creates a new data in the database and the corresponding object.
     *
     * @param coin the coin
     * @return the coin
     * @throws SQLException the SQL exception
     */
    public Coin create(Coin coin)throws  SQLException;

    /**
     *  Returns an object corresponding to a date with the primary key or null.
     *
     * @param key the key
     * @return the coin
     * @throws SQLException the SQL exception
     */
    public Coin read(int key) throws SQLException;

    
    /**
     *  Update object Coin in the database.
     *
     * @param coin the coin
     * @throws DBRecordException the DB record exception
     */
    public void update(Coin coin) throws DBRecordException;
    
    /**
     *  Removes the entry of the object from the database.
     *
     * @param key the key
     * @throws DBRecordException the DB record exception
     */
    public void delete(int key) throws DBRecordException;

    /**
     *  Returns a list of objects corresponding to all of the records in the database.
     *
     * @return the all
     * @throws SQLException the SQL exception
     */
    public List<Coin> getAll() throws SQLException;

}
