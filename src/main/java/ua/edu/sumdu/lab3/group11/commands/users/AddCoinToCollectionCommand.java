package ua.edu.sumdu.lab3.group11.commands.users;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.users.UserDao;
import ua.edu.sumdu.lab3.group11.obj.User;
import ua.edu.sumdu.lab3.group11.servlets.AuthenticationFilter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

public class AddCoinToCollectionCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(AddCoinToCollectionCommand.class.getName());

    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        User user = (User) request.getSession().getAttribute(AuthenticationFilter.CURRENT_USER);
        log.debug("current user: " + user);

        UserDao userDao = daoFactory.getUserDao();

        try {
            userDao.openConnection();
            if(request.getParameterValues("selectedItems") == null) {
                request.setAttribute("message", "First select a coin.");
                forward("controller?action=showAllCoins");
                return;
            }
            String[] selectedItems = request.getParameterValues("selectedItems");

            boolean someCoinsExist = false;
            for (String selectedItem : selectedItems) {
                int coinID = Integer.parseInt(selectedItem);

                if ( ! userDao.isCoinExist(user, coinID) ) {
                    //add coin to user collection
                    userDao.addCoinToUserCollection(user, coinID);
                } else {
                    someCoinsExist = true;
                }
            }

            if (someCoinsExist) {
                request.setAttribute("message", "Some coins already exists in your collection");
            }

            forward("controller?action=showUserCoins");

        } catch (SQLException e) {
            throw new DBRecordException(e);
        } finally {
            try {
                userDao.closeConnection();
            } catch (SQLException e) {
                throw new DBRecordException(e);
            }
        }

    }
}
