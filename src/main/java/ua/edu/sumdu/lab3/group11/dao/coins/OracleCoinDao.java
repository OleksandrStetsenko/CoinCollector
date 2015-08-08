package ua.edu.sumdu.lab3.group11.dao.coins;

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

// TODO: Auto-generated Javadoc
/**
 * The Class OracleCoinDao.
 */

/**
 *
 * @author Lukyanenko Yulia
 */


public class OracleCoinDao implements CoinDao {

    /** The log. */
    static Logger log = Logger.getLogger(OracleCoinDao.class.getName());
    
    /** The connection. */
    private Connection connection;
    
    /** The data source. */
    private DataSource ds;

    /**
     * Instantiates a new oracle coin dao.
     *
     * @param ds the ds
     */
    public OracleCoinDao(DataSource ds) {
        this.ds = ds;
    }

    /* (non-Javadoc)
     * @see ua.edu.sumdu.lab3.group11.dao.coins.CoinDao#openConnection()
     */
    public Connection openConnection() throws SQLException {
        connection = ds.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }

    /* (non-Javadoc)
     * @see ua.edu.sumdu.lab3.group11.dao.coins.CoinDao#closeConnection()
     */
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /* (non-Javadoc)
     * @see ua.edu.sumdu.lab3.group11.dao.coins.CoinDao#update(ua.edu.sumdu.lab3.group11.obj.Coin)
     */
    public void update(Coin coin) throws DBRecordException { //update exist coin
        PreparedStatement prst = null;

        try {
            //IMPORTANT! set transaction begin
        	 //connection.setAutoCommit(false);
        	 
            prst = connection.prepareStatement(Settings.UPDATE_OBJECT);
            prst.setString(1, coin.getFullname());
            prst.setInt(2, coin.getCountry()); //parent_id = country
            prst.setInt(3, coin.getCoinID());
            prst.executeUpdate();

            prst = connection.prepareStatement(Settings.UPDATE_PARAM_TEXT);
            prst.setString(1, coin.getName());
            prst.setInt(2, coin.getCoinID());
            prst.setInt(3, Settings.COIN_NAME_ATTR_ID); //6= "name" 
            prst.executeUpdate();

            prst = connection.prepareStatement(Settings.UPDATE_PARAM_TEXT);
            prst.setString(1, coin.getMetall());
            prst.setInt(2, coin.getCoinID());
            prst.setInt(3, Settings.COIN_METALL_ATTR_ID); //7= "metall" 
            prst.executeUpdate();

            prst = connection.prepareStatement(Settings.UPDATE_PARAM_NUMBER);
            prst.setInt(1, coin.getDiameter_mm());
            prst.setInt(2, coin.getCoinID());
            prst.setInt(3, Settings.COIN_DIAMETER_ATTR_ID); //8= "diameter" 
            prst.executeUpdate();

            prst = connection.prepareStatement(Settings.UPDATE_PARAM_NUMBER);
            prst.setInt(1, coin.getValue());
            prst.setInt(2, coin.getCoinID());
            prst.setInt(3, Settings.COIN_VALUE_ATTR_ID); //9= "value" 
            prst.executeUpdate();

            prst = connection.prepareStatement(Settings.UPDATE_PARAM_NUMBER);
            prst.setInt(1, coin.getWeight());
            prst.setInt(2, coin.getCoinID());
            prst.setInt(3, Settings.COIN_WEIGHT_ATTR_ID); //3= "weight" 
            prst.executeUpdate();

            prst = connection.prepareStatement(Settings.UPDATE_PARAM_NUMBER);
            prst.setInt(1, coin.getYear());
            prst.setInt(2, coin.getCoinID());
            prst.setInt(3, Settings.COIN_YEAR_ATTR_ID); //5= "year" 
            prst.executeUpdate();

            prst.close();
            connection.commit();
        } catch (SQLException e) {
            log.error("Failed to update coin:", e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("Failed to rollback update coin with coinID=" + coin.getCoinID(), e1);
            }
        }

    }

    /* (non-Javadoc)
     * @see ua.edu.sumdu.lab3.group11.dao.coins.CoinDao#create(ua.edu.sumdu.lab3.group11.obj.Coin)
     */
    public Coin create(Coin coin) throws SQLException { //write new coin
        String insertSQL = null;
        PreparedStatement prst = null;
        ResultSet rs = null;

    	//IMPORTANT! set transaction begin
        try {
            //connection.setAutoCommit(false);
            prst = connection.prepareStatement(Settings.INSERT_OBJ);
            prst.setInt(1, coin.getCountry());//for parent_id=country code
            prst.setInt(2, Settings.COIN_OBJECT_TYPE_ID);
            prst.setString(3, coin.getFullname());
            log.debug("Trying to insert into Objects");
            prst.executeUpdate();

            //read coinID = object_id from objects
            insertSQL = "select objects_seq.currval from objects";
            prst = connection.prepareStatement(insertSQL);
            log.debug("Trying to " + insertSQL);

            //set coinID into coin
            rs = prst.executeQuery();
            while (rs.next()) {
                coin.setCoinID(rs.getInt("CURRVAL"));
            }

	    	//write params
            log.debug("Start write params");

            prst = connection.prepareStatement(Settings.INSERT_PARAM_NUMBER);
            prst.setInt(1, coin.getCoinID());
            prst.setInt(2, Settings.COIN_WEIGHT_ATTR_ID); //3= weight from attributes
            prst.setInt(3, coin.getWeight());
            prst.executeUpdate();
            log.debug("write weight Ok");

            prst.setInt(1, coin.getCoinID());
            prst.setInt(2, Settings.COIN_YEAR_ATTR_ID); //5= year from attributes
            prst.setInt(3, coin.getYear());
            prst.executeUpdate();
            log.debug("write year Ok");

            prst.setInt(1, coin.getCoinID());
            prst.setInt(2, Settings.COIN_DIAMETER_ATTR_ID); //8= diameter_mm from attributes
            prst.setInt(3, coin.getDiameter_mm());
            prst.executeUpdate();
            log.debug("write diameter_mm Ok");

            prst.setInt(1, coin.getCoinID());
            prst.setInt(2, Settings.COIN_VALUE_ATTR_ID); //9= value from attributes
            prst.setInt(3, coin.getDiameter_mm());
            prst.executeUpdate();
            log.debug("write value Ok");

            prst = connection.prepareStatement(Settings.INSERT_PARAM_TEXT);

            prst.setInt(1, coin.getCoinID());
            prst.setInt(2, Settings.COIN_NAME_ATTR_ID); //6= name from attributes
            prst.setString(3, coin.getName());
            prst.executeUpdate();
            log.debug("write name Ok");

            prst.setInt(1, coin.getCoinID());
            prst.setInt(2, Settings.COIN_METALL_ATTR_ID); //7= metall from attributes
            prst.setString(3, coin.getMetall());
            prst.executeUpdate();
            log.debug("write metall Ok");

            prst.close();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            log.error("can not write coin to database, rollback ", e);
            connection.rollback();
            throw new SQLException(e);
        }
        return coin;
    }

    /* (non-Javadoc)
     * @see ua.edu.sumdu.lab3.group11.dao.coins.CoinDao#read(int)
     */
    public Coin read(int key) throws SQLException {
        return getCoins(" and obj.object_id=" + key).get(0);
    }

    /* (non-Javadoc)
     * @see ua.edu.sumdu.lab3.group11.dao.coins.CoinDao#getAll()
     */
    public List<Coin> getAll() throws SQLException {
        return getCoins(" ");
    }

    /**
     * Gets the coins.
     *
     * @param parameter the parameter
     * @return the coins
     */
    private List<Coin> getCoins(String parameter) { //
        List<Coin> listCoins = new ArrayList<Coin>();
        PreparedStatement prst = null;
        ResultSet rs = null;
        int rows = 0;
        try {
            prst = connection.prepareStatement(
                    Settings.GET_COINS_HEAD
                    + Settings.FROM_OBJ
                    + Settings.GET_COINS_FOOT
                    + Settings.WHERE_OBJ_TYPE_ID
                    + parameter);//Settings.GET_ALL_COINS + parameter);
            log.info("try to get: " +Settings.GET_COINS_HEAD
                    + Settings.FROM_OBJ
                    + Settings.GET_COINS_FOOT+ parameter);
            prst.setInt(1, Settings.COIN_DIAMETER_ATTR_ID);
            prst.setInt(2, Settings.COIN_METALL_ATTR_ID);
            prst.setInt(3, Settings.COIN_NAME_ATTR_ID);
            prst.setInt(4, Settings.COIN_VALUE_ATTR_ID);
            prst.setInt(5, Settings.COIN_YEAR_ATTR_ID);
            prst.setInt(6, Settings.COIN_WEIGHT_ATTR_ID);
            prst.setInt(7, Settings.COIN_OBJECT_TYPE_ID);
            rs = prst.executeQuery();
            log.debug("Query 'getCoins'has been executed with parameter " + parameter);

            while (rs.next()) {
                Coin coin = new Coin();
                rows++;
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

            prst.close();
        } catch (SQLException e) {
            log.error("Get coins FAIL- ", e);

        }

        log.info("SQL rows= " + rows);
        return listCoins;
    }

    /* (non-Javadoc)
     * @see ua.edu.sumdu.lab3.group11.dao.coins.CoinDao#delete(int)
     */
    public void delete(int coinID) throws DBRecordException {
        PreparedStatement prst = null;

        try {
    		//IMPORTANT! set transaction begin
        	// connection.setAutoCommit(false);
            prst = connection.prepareStatement(Settings.DELETE_COIN_PARAMS);
            prst.setInt(1, coinID);
            log.debug("Trying to delete from params coinID=" + coinID);
            prst.executeUpdate();
            log.debug("Sucess!");

            prst = connection.prepareStatement(Settings.DELETE_COIN_OBJECTS);
            prst.setInt(1, coinID);
            log.debug("Trying to delete from objects coinID=" + coinID);
            prst.executeUpdate();
            log.debug("Sucess!");

            connection.commit();	//IMPORTANT! set transaction end

        } catch (SQLException e) {

            try {
                connection.rollback();

            } catch (SQLException e1) {
                log.error("Failed to rollback delete coin with coinID=" + coinID, e1);
            }

            log.error("Failed to delete coin with coinID=" + coinID, e);
        }

    }

}
