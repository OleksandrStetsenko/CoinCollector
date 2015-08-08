package ua.edu.sumdu.lab3.group11.dao.counties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.obj.Country;
import ua.edu.sumdu.lab3.group11.Settings;

import javax.sql.DataSource;

/**
 *
 * @author Yulia Lukianenko
 */
public class OracleCountryDao implements CountryDao {

    private Connection connection;
    private DataSource ds;
    private static Logger log = Logger.getLogger(OracleCountryDao.class.getName());

    public OracleCountryDao(DataSource ds) {
        this.ds = ds;
    }

    /**
     * Opens new connection
     *
     * @return A connection (session) with a specific database.
     * @throws java.sql.SQLException - if a database access error occurs or this
     * method is called on a closed connection
     */
    @Override
    public Connection openConnection() throws SQLException {
        connection = ds.getConnection();
        return connection;
    }

    /**
     * Close connection
     *
     * @throws java.sql.SQLException - if a database access error occurs or this
     * method is called on a closed connection
     */
    @Override
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Returns a list of objects corresponding to all of the records in the
     * database
     *
     * @return - All Countries from databace
     * @throws ua.edu.sumdu.lab3.group11.dao.DBRecordException - if a database
     * access error occurs or this method is called on a closed connection
     */
    @Override
    public List<Country> getAll() throws DBRecordException {

        List<Country> listCountrys = new ArrayList<Country>();
        PreparedStatement prst = null;
        ResultSet rs = null;
        try {
            prst = connection.prepareStatement(Settings.GET_ALL_COUNTRIES);
            rs = prst.executeQuery();
            while (rs.next()) {
                Country country = new Country();
                country.setCountryId(rs.getInt("object_id"));
                country.setCountryParentID(rs.getInt("parent_id"));
                country.setCountryName(rs.getString("name"));
                listCountrys.add(country);
            }
        } catch (SQLException ex) {
            log.error(ex);
            throw new DBRecordException(ex);
        } finally {
            try {
                if (prst != null) {
                    prst.close();
                }
            } catch (SQLException ex) {
                log.error("Can not close statement", ex);
                throw new DBRecordException(ex);
            }
            log.info("Statement has be closed.");
        }
        return listCountrys;
    }

    /**
     * Returns a list of objects corresponding to all of the records in the
     * database
     *
     * @return - All Parts of world and Countries from databace
     * @throws ua.edu.sumdu.lab3.group11.dao.DBRecordException - if a database
     * access error occurs or this method is called on a closed connection
     */
    @Override
    public List<Country> getCountryTree() throws DBRecordException {
        System.out.println(" Init Country tree .");
        List<Country> listCountrys = new ArrayList<Country>();
        PreparedStatement prst = null;
        ResultSet rs = null;
        try {
            prst = connection.prepareStatement(Settings.GET_HIERAR_ALL_COUNTRIES);
            rs = prst.executeQuery();
            log.info("exequte Query - list<Country>.");

            while (rs.next()) {

                if (rs.getInt("level") >= 3) {
                    continue;
                }
                Country country = new Country();
                country.setCountryId(rs.getInt("object_id"));
                country.setCountryName(rs.getString("name"));
                if (rs.getInt("level") == 1) {
                    country.setCountryType(Country.CountryType.partOfWorld);
                } else if (rs.getInt("level") == 2) {
                    country.setCountryType(Country.CountryType.country);
                }
                listCountrys.add(country);
            }
        } catch (SQLException ex) {
            log.error(ex);
            throw new DBRecordException(ex);
        } finally {
            try {
                if (prst != null) {
                    prst.close();
                }
            } catch (SQLException ex) {
                log.error("Can not close statement", ex);
                throw new DBRecordException(ex);
            }
            log.info("Statement has be closed.");
        }
        return listCountrys;
    }

}
