package ua.edu.sumdu.lab3.group11.dao.search;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.obj.Coin;

/**
 *
 * @author Yulia Lukianenko
 */
public interface SearchDao {

    /**
     * Opens new connection
     *
     * @return A connection (session) with a specific database.
     * @throws java.sql.SQLException -if a database access error occurs or this
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
     * @return - All Coin of selecte country from databace
     * @throws ua.edu.sumdu.lab3.group11.dao.DBRecordException - if a database
     * access error occurs or this method is called on a closed connection
     */
    public List<Coin> getCoinsOfCountry(int ID, int userID) throws DBRecordException;

    /**
     * Returns a list of distinct years from the database
     *
     * @return - All years of issue coins from databace
     * @throws ua.edu.sumdu.lab3.group11.dao.DBRecordException - if a database
     * access error occurs or this method is called on a closed connection
     */
    public List<String> getAllYears() throws DBRecordException;

    /**
     * Returns a list of distinct metalls from the database
     *
     * @return - All material(metall) for coins from databace
     * @throws ua.edu.sumdu.lab3.group11.dao.DBRecordException - if a database
     * access error occurs or this method is called on a closed connection
     */
    public List<String> getAllMettals() throws DBRecordException;

    /**
     * Returns a list of objects corresponding to all of the records in the
     * database
     *
     * @param countryID - Country's ID from databace
     * @param year - Year of issue coins
     * @param metall - Material(metall) for coins
     * @param userID - User's ID from databace
     * @return - All coins selection of the parameters(country, year, metall,
     * user) from databace
     * @throws ua.edu.sumdu.lab3.group11.dao.DBRecordException - if a database
     * access error occurs or this method is called on a closed connection
     */
    public List<Coin> getCoinsOfSearch(int countryID, 
                                       int year, 
                                       String metall, 
                                       int userID) throws DBRecordException;
}
