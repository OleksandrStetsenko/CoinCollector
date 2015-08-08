package ua.edu.sumdu.lab3.group11.dao.search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.edu.sumdu.lab3.group11.Settings;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.obj.Coin;

/**
 *
 * @author Yulia Lukianenko
 */
public class OracleSearchDao implements SearchDao {

    private Connection connection;
    private DataSource ds;
    private static Logger log = Logger.getLogger(OracleSearchDao.class.getName());
    
    public OracleSearchDao(DataSource ds) {
        this.ds = ds;
    }

    /**
     * Opens new connection
     *
     * @return A connection (session) with a specific database.
     * @throws java.sql.SQLException -if a database access error occurs or this
     * method is called on a closed connection
     */
    @Override
    public Connection openConnection() throws SQLException {
        
        connection = ds.getConnection();
        log.info("Create OracleSearchDao with connection .");
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
            log.info("Close OracleSearchDao connection.");
        }
    }

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
    @Override
    public List<Coin> getCoinsOfSearch(int countryID, int year, String metall, int userID)
            throws DBRecordException {
        
        List<Coin> listCoins = new ArrayList<Coin>();
        PreparedStatement prst;
        ResultSet rs;
        try {
            String conditions = selectConditions(countryID, year, metall);
            log.info(" Conditions = " + conditions);
            if (userID == 0) {
                prst = connection.prepareStatement(
                        Settings.GET_COINS_HEAD
                        + Settings.FROM_OBJ
                        + Settings.GET_COINS_FOOT 
                        + Settings.WHERE_OBJ_TYPE_ID + conditions);
            } else {
                prst = connection.prepareStatement(
                        Settings.GET_COINS_HEAD
                        + Settings.FROM_OBJ
                        + Settings.GET_COINS_FOOT
                        + Settings.WHERE_OBJ_TYPE_ID + conditions
                        + Settings.GET_CONDITIONS_SEARCH_COINS);
                prst.setInt(8, userID);
            }
            prst.setInt(1, Settings.COIN_DIAMETER_ATTR_ID);
            prst.setInt(2, Settings.COIN_METALL_ATTR_ID);
            prst.setInt(3, Settings.COIN_NAME_ATTR_ID);
            prst.setInt(4, Settings.COIN_VALUE_ATTR_ID);
            prst.setInt(5, Settings.COIN_YEAR_ATTR_ID);
            prst.setInt(6, Settings.COIN_WEIGHT_ATTR_ID);
            prst.setInt(7, Settings.COIN_OBJECT_TYPE_ID);
            
            rs = prst.executeQuery();
            log.debug("Query 'getCoinsOfSearch' has been executed  .");

            createListCoins(listCoins, rs);
            prst.close();
        } catch (SQLException e) {
            log.error("!!! read all coins with conditions did not executed " + e);
        }
        return listCoins;
    }

    /**
     * Returns a list of objects corresponding to all of the records in the
     * database
     *
     * @return - All Coin of selecte country from databace
     * @throws ua.edu.sumdu.lab3.group11.dao.DBRecordException - if a database
     * access error occurs or this method is called on a closed connection
     */
    @Override
    public List<Coin> getCoinsOfCountry(int ID, int userID) throws DBRecordException {
        
        List<Coin> listCoins = new ArrayList<Coin>();
        PreparedStatement prst;
        ResultSet rs;
        try {
            int i = 0;
            if (userID == 0) {
                prst = connection.prepareStatement(
                        Settings.HIERAR_QUERY_HEAD
                        + Settings.HIERAR_QUERY_FOOT);
                prst.setInt(1, ID);
                log.info(" CountryID = " + ID);
                prst.setInt(2, Settings.COIN_OBJECT_TYPE_ID);
                i = 2;

            } else {
                prst = connection.prepareStatement(
                        Settings.HIERAR_QUERY_HEAD
                        + Settings.GET_COINS_OF_USER
                        + Settings.HIERAR_QUERY_FOOT);
                prst.setInt(1, ID);
                prst.setInt(3, userID);
                prst.setInt(2, Settings.COIN_OBJECT_TYPE_ID);
                i = 3;
                log.info(" CountryID = " + ID + " of userID = " + userID);
            }
            prst.setInt(++i, Settings.COIN_DIAMETER_ATTR_ID);
            prst.setInt(++i, Settings.COIN_METALL_ATTR_ID);
            prst.setInt(++i, Settings.COIN_NAME_ATTR_ID);
            prst.setInt(++i, Settings.COIN_VALUE_ATTR_ID);
            prst.setInt(++i, Settings.COIN_YEAR_ATTR_ID);
            prst.setInt(++i, Settings.COIN_WEIGHT_ATTR_ID);
            rs = prst.executeQuery();
            log.debug("Query 'getCoinsOfCountry' has been executed  .");
            createListCoins(listCoins, rs);
            prst.close();
        } catch (SQLException e) {
            log.error(" Read all coins with conditions did not executed " + e);
        }
        return listCoins;
    }

    /**
     * Returns a list of distinct years from the database
     *
     * @return - All years of issue coins from databace
     * @throws ua.edu.sumdu.lab3.group11.dao.DBRecordException - if a database
     * access error occurs or this method is called on a closed connection
     */
    @Override
    public List<String> getAllYears() throws DBRecordException {
        
        List<String> listYears = new ArrayList<String>();
        executeSQL(Settings.GET_YEARS, "year", listYears);
        return listYears;
    }

    /**
     * Returns a list of distinct metalls from the database
     *
     * @return - All material(metall) for coins from databace
     * @throws ua.edu.sumdu.lab3.group11.dao.DBRecordException - if a database
     * access error occurs or this method is called on a closed connection
     */
    @Override
    public List<String> getAllMettals() throws DBRecordException {
        
        List<String> listMetalls = new ArrayList<String>();
        executeSQL(Settings.GET_METALS, "metall", listMetalls);
        return listMetalls;
    }

    private void executeSQL(String sql, String parametr, List<String> list) {

        PreparedStatement prst;
        ResultSet rs;
        try {
            prst = connection.prepareStatement(sql);
            if (parametr.equals("year")) {
                prst.setInt(1, Settings.COIN_YEAR_ATTR_ID);
            } else if (parametr.equals("metall")) {
                prst.setInt(1, Settings.COIN_METALL_ATTR_ID);
            }
            rs = prst.executeQuery();
            createListParam(parametr, list, rs);
            prst.close();
        } catch (SQLException e) {
            log.error(sql + " did not executed " + e);
        }
    }

    private void createListCoins(List<Coin> listCoins, ResultSet rs) throws SQLException {
        
        while (rs.next()) {
            Coin coin = new Coin();
            
            coin.setCoinID(rs.getInt("coinID"));
            coin.setDiameter_mm(rs.getInt("diameter_mm"));
            coin.setMetall(rs.getString("metall"));
            coin.setName(rs.getString("name"));
            coin.setFullname(rs.getString("fullname"));
            coin.setValue(rs.getInt("value"));
            coin.setYear(rs.getInt("year"));
            coin.setWeight(rs.getInt("weight"));

            listCoins.add(coin);
        }
        log.debug(" List of Coins is created and full.");
    }

    private void createListParam(String parametr, List<String> list, ResultSet rs) throws SQLException {
        
        while (rs.next()) {
            String element = rs.getString(parametr);
            list.add(element);
        }
    }

    private String selectConditions(int param1, int param2, String param3) {

        StringBuilder builder = new StringBuilder();
        if (param1 != 0) {
            builder.append(" and ")
                    .append(" obj.parent_id =  ")
                    .append(param1);
        }
        if (param2 != 0) {
            builder.append(" and ")
                    .append(" year.NUMBER_VALUE = ")
                    .append(param2);
        }
        if (!param3.equals("")) {
            builder.append(" and ")
                    .append(" metall.text_value =  ")
                    .append("'")
                    .append(param3)
                    .append("'");
        }
        log.debug(" Conditions = " + builder.toString());
        return builder.toString();
    }

}
