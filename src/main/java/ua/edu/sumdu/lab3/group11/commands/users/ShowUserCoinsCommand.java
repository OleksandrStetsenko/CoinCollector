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

public class ShowUserCoinsCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(ShowUserCoinsCommand.class.getName());

    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        User user = (User) request.getSession().getAttribute(AuthenticationFilter.CURRENT_USER);
        log.debug("current user: " + user);

        String page = "myCoins";
        log.info(" Var page ="+page);
        request.getSession().setAttribute("page", page);
        
        UserDao userDao = daoFactory.getUserDao();

        try {
            userDao.openConnection();

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
