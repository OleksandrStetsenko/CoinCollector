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

import ua.edu.sumdu.lab3.group11.Settings;
import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.commands.users.EditUserCommand;
import ua.edu.sumdu.lab3.group11.dao.coins.CoinDao;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.obj.Coin;
import ua.edu.sumdu.lab3.group11.obj.User;
import ua.edu.sumdu.lab3.group11.servlets.AuthenticationFilter;


// TODO: Auto-generated Javadoc
/**
 * The Class DeleteCoinCommand.
 */
public class DeleteCoinCommand extends FrontCommand {


    /** The log. */
    private static Logger log = Logger.getLogger(DeleteCoinCommand.class.getName());
    
    /** The Constant SAC_PAGE. */
    private static final String SAC_PAGE = "controller?action=showAllCoins";

    /* (non-Javadoc)
     * @see ua.edu.sumdu.lab3.group11.commands.FrontCommand#process()
     */
    @Override
    public void process() throws ServletException, IOException, DBRecordException {
    	
    	// only administrator can delete- check
    	User currentUser = (User) request.getSession().getAttribute(AuthenticationFilter.CURRENT_USER);
         if (currentUser == null || (! currentUser.isAdmin())) {
             //redirect to notAvalible.jsp
             forward(Settings.NO_AVALIBLE);
             return;
         }
    	        
        CoinDao coinDao = daoFactory.getCoinDao();
        try {
            coinDao.openConnection();
            int coinID = Integer.parseInt(request.getParameter("coinID"));
			coinDao.delete(coinID);

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
