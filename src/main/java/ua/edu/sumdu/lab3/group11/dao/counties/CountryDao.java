package ua.edu.sumdu.lab3.group11.dao.counties;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.obj.Country;

/**
 *
 * @author Yulia Lukianenko
 */
public interface CountryDao {

    /**
     * Opens new connection
     *
     * @return A connection (session) with a specific database.
     * @throws java.sql.SQLException - if a database access error occurs or this
     * method is called on a closed connection
     */
    public Connection openConnection() throws SQLException;

    /**
     * Close connection
     *
     * @throws java.sql.SQLException - if a database access error occurs or this
     * method is called on a closed connection
     */
    public void closeConnection() throws SQLException;

    /**
     * Returns a list of objects corresponding to all of the records in the
     * database
     *
     * @return - All Countries from databace
     * @throws ua.edu.sumdu.lab3.group11.dao.DBRecordException - if a database
     * access error occurs or this method is called on a closed connection
     */
    public List<Country> getAll() throws DBRecordException;

    /**
     * Returns a list of objects corresponding to all of the records in the
     * database
     *
     * @return - All Parts of world and Countries from databace
     * @throws ua.edu.sumdu.lab3.group11.dao.DBRecordException - if a database
     * access error occurs or this method is called on a closed connection
     */
    public List<Country> getCountryTree() throws DBRecordException;

}
