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
import java.util.List;

public class RemoveCoinFromCollectionCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(RemoveCoinFromCollectionCommand.class.getName());

    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        User user = (User) request.getSession().getAttribute(AuthenticationFilter.CURRENT_USER);

        UserDao userDao = daoFactory.getUserDao();

        try {
            userDao.openConnection();

            //add coin to user collection
            int coinID = Integer.parseInt(request.getParameter("coinID"));
            log.debug("user " + user + " coinID " + coinID);
            userDao.removeCoinFromCollection(user, coinID);

            //get user collection
            List list = userDao.getUserCollection(user);
            request.setAttribute("list", list);

            //show user coin collection
            forward("/allcoins.jsp");

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
