package ua.edu.sumdu.lab3.group11.dao.users;

import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.obj.Coin;
import ua.edu.sumdu.lab3.group11.obj.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    /** Opens new connection */
    public Connection openConnection() throws SQLException;

    /** Close connection */
    public void closeConnection() throws SQLException;

    /** Creates a new data in the database and the corresponding object */
    public User create(User user) throws DBRecordException;

    /** Returns an object corresponding to a date with the primary key or null */
    public User read(int key) throws DBRecordException;

    /** Returns an object corresponding to a date with the name */
    public User read(String name) throws DBRecordException;

    /** Saves the state of the object User in the database */
    public void update(User user) throws DBRecordException;

    /** Removes the entry of the object from the database */
    public void delete(User user) throws DBRecordException;

    /** Returns a list of objects corresponding to all of the records in the database */
    public List<User> getAll() throws DBRecordException;

    /** Adds coin to user collection */
    public void addCoinToUserCollection(User user, int coinID) throws DBRecordException;

    /** Returns user coin collection */
    public List<Coin> getUserCollection(User user) throws DBRecordException;

    /** Removes coin from collection */
    public void removeCoinFromCollection(User user, int coinID) throws DBRecordException;

    /** Returns true if the specified user already has a coin with the given coinID */
    public boolean isCoinExist(User user, int coinID) throws DBRecordException;

}
