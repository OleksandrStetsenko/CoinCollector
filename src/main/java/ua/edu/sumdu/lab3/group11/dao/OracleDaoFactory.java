package ua.edu.sumdu.lab3.group11.dao;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.dao.coins.CoinDao;
import ua.edu.sumdu.lab3.group11.dao.coins.OracleCoinDao;
import ua.edu.sumdu.lab3.group11.dao.counties.CountryDao;
import ua.edu.sumdu.lab3.group11.dao.counties.OracleCountryDao;
import ua.edu.sumdu.lab3.group11.dao.users.OracleUserDao;
import ua.edu.sumdu.lab3.group11.dao.users.UserDao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import ua.edu.sumdu.lab3.group11.dao.search.OracleSearchDao;
import ua.edu.sumdu.lab3.group11.dao.search.SearchDao;

public class OracleDaoFactory implements DaoFactory {

    private static Logger log = Logger.getLogger(OracleDaoFactory.class.getName());
    private DataSource ds;

    /** Returns user DAO */
    public UserDao getUserDao() {
        return new OracleUserDao(ds);
    }

    /** Returns coin DAO */
    public CoinDao getCoinDao() {
        return new OracleCoinDao(ds);
    }

    /** Returns country DAO */
    public CountryDao getCountryDao() {
        return new OracleCountryDao(ds);
    }

    /** Returns search DAO */
    public SearchDao getSearchDao() {
        return new OracleSearchDao(ds);
    }

    /** Creates DAO factory */
    public OracleDaoFactory() throws IOException, NamingException, SQLException {

        ds = (DataSource) new InitialContext().lookup("java:comp/env/dataSource");
        log.debug("Oracle DAO factory and DataSource has been created.");

    }

}
