package ua.edu.sumdu.lab3.group11.commands.country;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.coins.CoinDao;
import ua.edu.sumdu.lab3.group11.dao.search.SearchDao;
import ua.edu.sumdu.lab3.group11.dao.users.UserDao;
import ua.edu.sumdu.lab3.group11.obj.Coin;
import ua.edu.sumdu.lab3.group11.obj.User;

/**
 *
 * @author Yulia Lukianenko
 */
public class SearchCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(SearchCommand.class.getName());
    private static final String COINS_PAGE = "allcoins.jsp";

    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        User u = (User) request.getSession().getAttribute("currentUser");
        int userID = u.getUserID();

        List<Coin> list = new ArrayList<Coin>();

        String country = request.getParameter("country");
        String yearStr = request.getParameter("year");
        String metall = request.getParameter("metall");
        log.info("User selected country = " + country + " year = " + yearStr 
                + " metall = " + metall);

        int countryID = 0;
        int year = 0;

        if (!country.equals("allCountries")) {
            countryID = Integer.parseInt(country);
        }
        if (!yearStr.equals("allYears")) {
            year = Integer.parseInt(yearStr);
        }
        if (metall.equals("allMetalls")) {
            metall = "";
        }
        CoinDao coinDao = daoFactory.getCoinDao();
        SearchDao searchDao = daoFactory.getSearchDao();
        UserDao userDao = daoFactory.getUserDao();

        try {
            String requestFromPage = (String) request.getSession().getAttribute("page");
            log.info(" Var requestFromPage =" + requestFromPage);

            if (countryID == 0 && year == 0 && metall.equals("")) {

                switch (requestFromPage) {
                    case "allCoins":
                        coinDao.openConnection();
                        list = coinDao.getAll();
                        log.info(" Search from page =" + requestFromPage);
                        break;
                    case "myCoins":
                        userDao.openConnection();
                        list = userDao.getUserCollection(u);
                        log.info(" Search from page =" + requestFromPage);
                        break;
                }
            } else {
                
                searchDao.openConnection();
                switch (requestFromPage) {
                    case "allCoins":
                        list = searchDao.getCoinsOfSearch(countryID, year, metall, 0);
                        log.info(" Search with  parametrs from page :" 
                                + requestFromPage);
                        break;
                    case "myCoins":
                        list = searchDao.getCoinsOfSearch(countryID, year, metall, userID);
                        log.info(" Search with  parametrs from page :" 
                                + requestFromPage);
                        break;
                }

            }
            request.setAttribute("list", list);
            forward(COINS_PAGE);
            log.info(" Forward from allcoins.jsp ");

        } catch (SQLException e) {
            log.error("Can not create/update coin ", e);
            throw new DBRecordException(e);
        } finally {
            try {
                searchDao.closeConnection();
                coinDao.closeConnection();
                userDao.closeConnection();
                log.debug("Connection was closed");
            } catch (SQLException e) {
                log.error("Can not close connection");
                throw new DBRecordException("Can not close connection");
            }
        }
    }
}
