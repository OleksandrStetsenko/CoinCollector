package ua.edu.sumdu.lab3.group11.dao.users;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.Settings;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.obj.Coin;
import ua.edu.sumdu.lab3.group11.obj.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class OracleUserDao implements UserDao {

    static Logger log = Logger.getLogger(OracleUserDao.class.getName());
    private Connection connection;
    private DataSource ds;

    /** Creates user DAO object */
    public OracleUserDao(DataSource ds) {
        this.ds = ds;
    }

    /** Opens new connection */
    public Connection openConnection() throws SQLException {
        connection = ds.getConnection();
        return connection;
    }

    /** Close connection */
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /** Creates a new data in the database and the corresponding object */
    public User create(User user) throws DBRecordException {

        User returnedUser = null;

        String sqlCreateUserObjectQuery = Settings.INSERT_OBJ;
        String sqlCreatePassQuery = Settings.INSERT_PARAM_TEXT; //password
        String sqlCreateAdminQuery = Settings.INSERT_PARAM_BOOLEAN; //admin

        PreparedStatement stm_object = null;
        PreparedStatement stm_pass = null;
        PreparedStatement stm_admin = null;
        PreparedStatement stm_LastUser = null;
        try {
            //sets transaction begin
            connection.setAutoCommit(false);

            //add object
            stm_object = connection.prepareStatement(sqlCreateUserObjectQuery);
            stm_object.setNull(1,java.sql.Types.INTEGER );
            stm_object.setInt(2, Settings.USER_OBJECT_TYPE_ID);
            stm_object.setString(3, user.getUsername());
            int countObj = stm_object.executeUpdate();
            if (countObj != 1) {
                throw new DBRecordException("On creating username modify more then 1 record: " + countObj);
            }

            //get last inserted row
            stm_LastUser = connection.prepareStatement("SELECT objects_seq.currval AS maxID FROM dual");
            ResultSet rs = stm_LastUser.executeQuery();
            rs.next();
            int lastUserID = rs.getInt("maxID");
            log.debug("Last inserted user object_id: " + lastUserID);

            //add pass
            stm_pass = connection.prepareStatement(sqlCreatePassQuery);
            stm_pass.setInt(1, lastUserID); //object_id
            stm_pass.setInt(2, Settings.USER_PASSWORD_ATTR_ID); //attr_id
            stm_pass.setString(3, user.getPassword()); //pass
            int countPass = stm_pass.executeUpdate();
            if (countPass != 1) {
                throw new DBRecordException("On creating password modify more then 1 record: " + countPass);
            }

            //add admin
            stm_admin = connection.prepareStatement(sqlCreateAdminQuery);
            stm_admin.setInt(1, lastUserID); //object_id
            stm_admin.setInt(2, Settings.USER_ADMIN_ATTR_ID); //attr_id
            stm_admin.setInt(3, user.isAdmin() ? 1 : 0); //adm
            int countAdm = stm_admin.executeUpdate();
            if (countAdm != 1) {
                throw new DBRecordException("On creating admin modify more then 1 record: " + countAdm);
            }

            returnedUser = read(lastUserID);

            //that is ok - commit
            connection.commit();

        } catch (SQLException e) {
            //rollback
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    log.error("Rollback was failed", e1);
                    throw new DBRecordException(e1);
                }
            }
            log.error(e);
            throw new DBRecordException(e);
        } finally {

            //returns auto commit
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    log.error("Set auto commit was failed");
                    throw new DBRecordException("Can not close statement", e);
                }
            }

            if (stm_object != null) {
                try {
                    stm_object.close();
                } catch (SQLException e) {
                    log.error("Can not close statement", e);
                    throw new DBRecordException("Can not close statement", e);
                }
                log.debug("Statement (obj) has been closed");
            }
            if (stm_pass != null) {
                try {
                    stm_pass.close();
                } catch (SQLException e) {
                    log.error("Can not close statement", e);
                    throw new DBRecordException("Can not close statement", e);
                }
                log.debug("Statement (pass) has been closed");
            }
            if (stm_admin != null) {
                try {
                    stm_admin.close();
                } catch (SQLException e) {
                    log.error("Can not close statement", e);
                    throw new DBRecordException("Can not close statement", e);
                }
                log.debug("Statement (adm) has been closed");
            }
            if (stm_LastUser != null) {
                try {
                    stm_LastUser.close();
                } catch (SQLException e) {
                    log.error("Can not close statement", e);
                    throw new DBRecordException("Can not close statement", e);
                }
                log.debug("Statement (last user) has been closed");
            }
        }

        return returnedUser;
    }

    /** Returns an object corresponding to a date with the primary key or null */
    public User read(int key) throws DBRecordException {

        String sql = Settings.GET_USERS + Settings.USER_BY_PK;

        User user = null;
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, Settings.USER_PASSWORD_ATTR_ID);
            stm.setInt(2, Settings.USER_ADMIN_ATTR_ID);
            stm.setInt(3, Settings.USER_OBJECT_TYPE_ID);
            stm.setInt(4, key);
            ResultSet rs = stm.executeQuery();
            log.debug("Query has been executed");
            if (rs.next()) {
                user = new User(rs.getInt("object_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("is_admin") == 1 ? true : false);
            }
        } catch (SQLException e) {
            throw new DBRecordException("Statement was failed", e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    log.error("Can not close statement", e);
                    throw new DBRecordException("Can not close statement", e);
                }
                log.debug("Statement has been closed");
            }
        }

        log.debug("Read user by PK " + user);

        return user;
    }

    /** Returns an object corresponding to a date with the name */
    public User read(String name) throws DBRecordException {

        String sql = Settings.GET_USERS + Settings.USER_BY_NAME;

        User user = null;
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, Settings.USER_PASSWORD_ATTR_ID);
            stm.setInt(2, Settings.USER_ADMIN_ATTR_ID);
            stm.setInt(3, Settings.USER_OBJECT_TYPE_ID);
            stm.setString(4, name);
            ResultSet rs = stm.executeQuery();
            log.debug("Query has been executed");
            if (rs.next()) {
                user = new User(rs.getInt("object_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("is_admin") == 1 ? true : false);
            }
            if (rs.next()) {
                throw new DBRecordException("More then 1 record is received when getting user by name.");
            }
        } catch (SQLException e) {
            throw new DBRecordException("Statement was failed", e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    log.error("Can not close statement", e);
                    throw new DBRecordException("Can not close statement", e);
                }
                log.debug("Statement has been closed");
            }
        }

        log.debug("Read user by Username " + user);

        return user;

    }

    /** Saves the state of the object User in the database */
    public void update(User user) throws DBRecordException {

        String updatePassQuery = getUpdateParamQuery("text_value");
        String updateBooleanQuery = getUpdateParamQuery("boolean_value");

        PreparedStatement stm_object = null;
        PreparedStatement stm_pass = null;
        PreparedStatement stm_admin = null;
        try {
            //sets transaction begin
            connection.setAutoCommit(false);

            //object
            stm_object = connection.prepareStatement("UPDATE objects SET name = ? WHERE object_id= ?");
            stm_object.setString(1, user.getUsername());
            stm_object.setInt(2, user.getUserID());
            int countObj = stm_object.executeUpdate();
            if (countObj != 1) {
                throw new DBRecordException("On update username modify more then 1 record: " + countObj);
            }
            //pass
            stm_pass = connection.prepareStatement(updatePassQuery);
            stm_pass.setString(1, user.getPassword());
            stm_pass.setInt(2, user.getUserID()); //object_type = user
            stm_pass.setInt(3, Settings.USER_PASSWORD_ATTR_ID); //attr_id = password
            int countPass = stm_pass.executeUpdate();
            if (countPass != 1) {
                throw new DBRecordException("On update password modify more then 1 record: " + countPass);
            }
            //admin
            stm_admin = connection.prepareStatement(updateBooleanQuery);
            stm_admin.setInt(1, user.isAdmin() ? 1 : 0);
            stm_admin.setInt(2, user.getUserID()); //object_id
            stm_admin.setInt(3, Settings.USER_ADMIN_ATTR_ID); //attr_id = admin
            int countAdm = stm_admin.executeUpdate();
            if (countAdm != 1) {
                throw new DBRecordException("On update admin modify more then 1 record: " + countAdm);
            }

            //that is ok - commit
            connection.commit();
        } catch (Exception e) {
            //rollback
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    log.error("Rollback was failed", e1);
                    throw new DBRecordException(e1);
                }
            }
            throw new DBRecordException(e);
        } finally {
            try {

                //returns auto commit
                if (connection != null) {
                    connection.setAutoCommit(true);
                }

                if (stm_object != null) {
                    stm_object.close();
                    log.debug("Statement (obj) has been closed");
                }
                if (stm_pass != null) {
                    stm_pass.close();
                    log.debug("Statement (pass) has been closed");
                }
                if (stm_admin != null) {
                    stm_admin.close();
                    log.debug("Statement (adm) has been closed");
                }
            } catch (SQLException e) {
                log.error("Can not close statement", e);
                throw new DBRecordException("Can not close statement", e);
            }
        }
    }

    /** Creates update param query with the specified column */
    private static String getUpdateParamQuery(String column) {

        String sql = "UPDATE params SET " + column + " = ? WHERE object_id = ? AND attr_id = ?";
        return sql;

    }

    /** Removes the entry of the object from the database */
    public void delete(User user) throws DBRecordException {

        String sql = Settings.DELETE_USER;

        PreparedStatement stm = null;
        try {
            //sets transaction begin
            connection.setAutoCommit(false);

            stm = connection.prepareStatement(sql);
            stm.setInt(1, user.getUserID());
            stm.execute();
            log.debug("User has been deleted");

            //that is ok - commit
            connection.commit();
        } catch (SQLException e) {
            //rollback
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    log.error("Rollback was failed", e1);
                    throw new DBRecordException(e1);
                }
            }
            throw new DBRecordException("Statement was failed", e);
        } finally {
            //returns auto commit
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    log.error("Set auto commit was failed");
                    throw new DBRecordException("Can not close statement", e);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    log.error("Can not close statement", e);
                    throw new DBRecordException("Can not close statement", e);
                }
                log.debug("Statement has been closed");
            }
        }

    }

    /** Returns a list of objects corresponding to all of the records in the database */
    public List<User> getAll() throws DBRecordException {

        String sql = Settings.GET_USERS;

        List<User> list = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, Settings.USER_PASSWORD_ATTR_ID);
            stm.setInt(2, Settings.USER_ADMIN_ATTR_ID);
            stm.setInt(3, Settings.USER_OBJECT_TYPE_ID);
            ResultSet rs = stm.executeQuery();
            log.debug("Query has been executed");
            while (rs.next()) {
                User user = new User(rs.getInt("object_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("is_admin") == 1 ? true : false);
                list.add(user);
            }
        } catch (SQLException e) {
            throw new DBRecordException(e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    log.error("Can not close statement", e);
                    throw new DBRecordException("Can not close statement", e);
                }
                log.debug("Statement has been closed");
            }
        }
        return list;

    }

    /** Adds coin to user collection */
    public void addCoinToUserCollection(User user, int coinID) throws DBRecordException {

        PreparedStatement stm = null;
        try {
            //add coin to collection
            stm = connection.prepareStatement(Settings.INSERT_REFS);
            stm.setInt(1, user.getUserID());
            stm.setInt(2, Settings.USER_COINS_ATTR_ID);
            stm.setInt(3, coinID);
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new DBRecordException("On creating coin modify more then 1 record: " + count);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DBRecordException(e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    log.error("Can not close statement", e);
                    throw new DBRecordException("Can not close statement", e);
                }
                log.debug("Statement has been closed");
            }
        }

    }

    /** Returns user coin collection */
    public List<Coin> getUserCollection(User user) throws DBRecordException {

        String sql = Settings.GET_COINS_HEAD
                        + Settings.FROM_OBJ
                        + Settings.GET_COINS_FOOT 
                        + Settings.JOIN_REFS
                        + Settings.WHERE_OBJ_TYPE_ID;

        List<Coin> list = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, Settings.COIN_DIAMETER_ATTR_ID);
            stm.setInt(2, Settings.COIN_METALL_ATTR_ID);
            stm.setInt(3, Settings.COIN_NAME_ATTR_ID);
            stm.setInt(4, Settings.COIN_VALUE_ATTR_ID);
            stm.setInt(5, Settings.COIN_YEAR_ATTR_ID);
            stm.setInt(6, Settings.COIN_WEIGHT_ATTR_ID);
            stm.setInt(7, user.getUserID());
            stm.setInt(8, Settings.USER_COINS_ATTR_ID);
            stm.setInt(9, Settings.COIN_OBJECT_TYPE_ID);
            ResultSet rs = stm.executeQuery();
            log.debug("Query has been executed");
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
                list.add(coin);
            }
        } catch (SQLException e) {
            throw new DBRecordException(e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    log.error("Can not close statement", e);
                    throw new DBRecordException("Can not close statement", e);
                }
                log.debug("Statement has been closed");
            }
        }
        return list;
    }

    /** Removes coin from collection */
    public void removeCoinFromCollection(User user, int coinID) throws DBRecordException {

        PreparedStatement stm = null;
        try {
            //sets transaction begin
            connection.setAutoCommit(false);

            stm = connection.prepareStatement(Settings.DELETE_COIN_FROM_REFS);
            stm.setInt(1, user.getUserID());
            stm.setInt(2, Settings.USER_COINS_ATTR_ID);
            stm.setInt(3, coinID);
            stm.execute();
            log.debug("Coin has been deleted");

            //that is ok - commin
            connection.commit();
        } catch (SQLException e) {
            //rollback
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    log.error("Rollback was failed", e1);
                    throw new DBRecordException(e1);
                }
            }
            throw new DBRecordException("Statement was failed", e);
        } finally {
            //returns auto commit
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    log.error("Set auto commit was failed");
                    throw new DBRecordException("Can not close statement", e);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    log.error("Can not close statement", e);
                    throw new DBRecordException("Can not close statement", e);
                }
                log.debug("Statement has been closed");
            }
        }

    }

    /** Returns true if the specified user already has a coin with the given coinID */
    public boolean isCoinExist(User user, int coinID) throws DBRecordException {

        boolean returnedValue = false;
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(Settings.GET_COIN_EXIST);
            stm.setInt(1, user.getUserID());
            stm.setInt(2, Settings.USER_COINS_ATTR_ID);
            stm.setInt(3, coinID);
            ResultSet rs = stm.executeQuery();
            log.debug("Query has been executed");
            if (rs.next()) {
                returnedValue = true;
            }
        } catch (SQLException e) {
            throw new DBRecordException("Statement was failed", e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    log.error("Can not close statement", e);
                    throw new DBRecordException("Can not close statement", e);
                }
                log.debug("Statement has been closed");
            }
        }

        return returnedValue;

    }

}
