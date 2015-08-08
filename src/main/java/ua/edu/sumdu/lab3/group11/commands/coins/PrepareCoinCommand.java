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
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.coins.CoinDao;
import ua.edu.sumdu.lab3.group11.dao.counties.CountryDao;
import ua.edu.sumdu.lab3.group11.obj.Coin;
import ua.edu.sumdu.lab3.group11.obj.Country;
import ua.edu.sumdu.lab3.group11.obj.User;
import ua.edu.sumdu.lab3.group11.servlets.AuthenticationFilter;


public class PrepareCoinCommand extends FrontCommand {


    private static Logger log = Logger.getLogger(PrepareCoinCommand.class.getName());
    private static final String ADDCOIN_PAGE = "addcoin.jsp";

    /* (non-Javadoc)
     * @see ua.edu.sumdu.lab3.group11.commands.FrontCommand#process()
     */
    @Override
    public void process() throws ServletException, IOException, DBRecordException {
    	
    	 User currentUser = (User) request.getSession().getAttribute(AuthenticationFilter.CURRENT_USER);
         if (currentUser == null || (! currentUser.isAdmin())) {
             //redirect to notAvalible.jsp
             forward(Settings.NO_AVALIBLE);
             return;
         }
         
    	//prepare and send to ADDCOIN_PAGE list of alowed countries
    	
    	 CountryDao countryDao = daoFactory.getCountryDao();

         try {
        	 countryDao.openConnection();
             List<Country> list = countryDao.getAll();
             request.setAttribute("list", list);
             log.debug("Prepare country for coin, forward to "+ADDCOIN_PAGE);
             
        
         /*Check, edit or create new*/    
             
             String coinID = request.getParameter("coinID"); // if exist- this is redirect from allcoins button "edit" 
             
             log.debug("coinID ="+coinID);
             if (coinID!=null&&coinID.matches("[0-9]+")){
            	 request.setAttribute("head","Update coin"); //to edit exist coin
            	 //request.setAttribute("addOrEditCoin","editCoin"); //to edit exist coin
            	 
            	 CoinDao coinDao = daoFactory.getCoinDao();
                     coinDao.openConnection();
                     Coin coin;
                     try {
                         coin = coinDao.read(Integer.parseInt(coinID));
                     } catch (SQLException e) {
                         throw new DBRecordException(e);
                     }
                 request.setAttribute("coin", coin);
            	 
            	 
             }else{
            	 request.setAttribute("head","Create new coin");	//to add new coin
            	 //request.setAttribute("addOrEditCoin","addCoin"); //to add new coin	 
             }
             
             log.debug("forward to "+ADDCOIN_PAGE);
             forward(ADDCOIN_PAGE);
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
    	
    	
    	
           // forward(ADDCOIN_PAGE);
    }
}
