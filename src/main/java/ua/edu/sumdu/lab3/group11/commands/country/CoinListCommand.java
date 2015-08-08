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
public class CoinListCommand extends FrontCommand {

    private static final Logger log = Logger.getLogger(CoinListCommand.class.getName());
    private static final String COIN_PAGE = "allcoins.jsp";

    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        User u = (User) request.getSession().getAttribute("currentUser");
        int userID = u.getUserID();

        SearchDao searchDao = daoFactory.getSearchDao();
        CoinDao coinDao = daoFactory.getCoinDao();
        UserDao userDao = daoFactory.getUserDao();

        try {
            List<Coin> list = new ArrayList<Coin>();
            String requestFromPage = (String) request.getSession().getAttribute("page");
            log.info(" Var requestFromPage =" + requestFromPage);

            if (request.getParameter("countryID").equals("0")) {
                switch (requestFromPage) {
                    case "allCoins":
                        coinDao.openConnection();
                        list = coinDao.getAll();
                        log.info(" ' clic on all countries ' from page =" 
                                + requestFromPage);
                        break;
                    case "myCoins":
                        userDao.openConnection();
                        list = userDao.getUserCollection(u);
                        log.info(" ' clic on all countries ' from page =" 
                                + requestFromPage);
                        break;
                }
            } else {
                int countryID = Integer.parseInt(request.getParameter("countryID"));
                searchDao.openConnection();
                log.info(" countryID = " + countryID);
                switch (requestFromPage) {
                    case "allCoins":
                        list = searchDao.getCoinsOfCountry(countryID, 0);
                        log.info(" ' clic on countries ' with ID = " + countryID 
                                + " in page =" + requestFromPage);
                        break;
                    case "myCoins":
                        list = searchDao.getCoinsOfCountry(countryID, userID);
                        log.info(" ' clic on countries ' with ID = " + countryID 
                                + " in page =" + requestFromPage);
                        break;
                }
            }
            log.info("List of Coins : " + list.toString());
            request.setAttribute("list", list);
            forward(COIN_PAGE);
            log.info(" Forward CoinList to allcoins.jsp ");

        } catch (SQLException e) {
            throw new DBRecordException(e);
        } finally {
            try {
                coinDao.closeConnection();
                searchDao.closeConnection();
                log.debug("Connection was closed");
            } catch (SQLException e) {
                log.error("Can not close connection");
                throw new DBRecordException("Can not close connection");
            }
        }
    }

}
