package ua.edu.sumdu.lab3.group11.commands.country;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.DaoFactory;
import ua.edu.sumdu.lab3.group11.dao.counties.CountryDao;
import ua.edu.sumdu.lab3.group11.dao.search.SearchDao;
import ua.edu.sumdu.lab3.group11.obj.Country;

/**
 *
 * @author Yulia Lukianenko
 */
public class InitDataForSearch {

    private static Logger log = Logger.getLogger(InitDataForSearch.class.getName());

    /**
     * Initialization of parameters of search for drop-down lists
     */
    public void initDataSearch(HttpServletRequest request, DaoFactory daoFactory)
            throws ServletException, IOException, DBRecordException {

        CountryDao countryDao = daoFactory.getCountryDao();
        SearchDao searchDao = daoFactory.getSearchDao();

        try {
            countryDao.openConnection();

            List<Country> list = countryDao.getAll();
            log.info("list of Country : " + list.toString());
            request.getSession().setAttribute("listCountrys", list);

            searchDao.openConnection();

            List<String> listYears = searchDao.getAllYears();
            log.info("list of Years : " + listYears.toString());
            request.getSession().setAttribute("listYears", listYears);

            List<String> listMetalls = searchDao.getAllMettals();
            log.info("list of Mettals : " + listMetalls.toString());
            request.getSession().setAttribute("listMetalls", listMetalls);

        } catch (SQLException e) {
            throw new DBRecordException(e);
        } finally {
            try {
                countryDao.closeConnection();
                searchDao.closeConnection();
                log.debug("Connection was closed");
            } catch (SQLException e) {
                log.error("Can not close connection");
                throw new DBRecordException("Can not close connection");
            }
        }
    }
    /**
     * Initialization of parameters from tree countries
     * 
     */
    public void initDataCountryList(HttpServletRequest request, DaoFactory daoFactory)
            throws ServletException, IOException, DBRecordException {
        
        CountryDao countryDao = daoFactory.getCountryDao();
        try {
            if (request.getSession().getAttribute("listTreeCountry") == null) {
                countryDao.openConnection();
                List<Country> list = countryDao.getCountryTree();
                log.info(" Get list of Countries length = " + list.size());
                request.getSession().setAttribute("listTreeCountry", list);
                log.info(" List TreeCountry : " + list.toString());
            }
        } catch (SQLException e) {
            throw new DBRecordException(e);
        } finally {
            try {
                countryDao.closeConnection();
                log.debug("Connection was closed");
            } catch (SQLException e) {
                log.error("Can not close connection");
                throw new DBRecordException("Can not close connection");
            }
        }
    }

}
