/**
 *
 * @author Vitalii Tregub
 */

package ua.edu.sumdu.lab3.group11.commands.coins;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.commands.country.InitDataForSearch;
import ua.edu.sumdu.lab3.group11.commands.users.EditUserCommand;
import ua.edu.sumdu.lab3.group11.dao.coins.CoinDao;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.obj.Coin;
import ua.edu.sumdu.lab3.group11.obj.Country;
import ua.edu.sumdu.lab3.group11.obj.User;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowAllCoinsCommand.
 */
public class ShowAllCoinsCommand extends FrontCommand {

    /** The log. */
    private static Logger log = Logger.getLogger(ShowAllCoinsCommand.class.getName());
    
    /** The Constant SAC_PAGE. */
    private static final String SAC_PAGE = "allcoins.jsp";

    /* (non-Javadoc)
     * @see ua.edu.sumdu.lab3.group11.commands.FrontCommand#process()
     */
    @Override
    public void process() throws ServletException, IOException, DBRecordException {
        
        String page = "allCoins";
        log.info(" Var page ="+page);
        request.getSession().setAttribute("page",page);
        
        InitDataForSearch initData = new InitDataForSearch();
        initData.initDataCountryList(request, daoFactory);
        initData.initDataSearch(request, daoFactory); //Lukyanenko

        CoinDao coinDao = daoFactory.getCoinDao();

        try {
            coinDao.openConnection();
            // show coin list
            List<Coin> list;
            try {
                list = coinDao.getAll();
            } catch (SQLException e) {
                throw new DBRecordException(e);
            }
            request.setAttribute("list", list);

            forward(SAC_PAGE);
        } catch (SQLException e) {
            throw new DBRecordException(e);
        } finally {
            try {
                coinDao.closeConnection();
                log.debug("Connection was closed");
            } catch (SQLException e) {
                log.error("Can not close connection");
                throw new DBRecordException("Can not close connection");
            }
        }
    }

   
}
