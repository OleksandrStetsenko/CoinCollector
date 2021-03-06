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

public class UserListCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(UserListCommand.class.getName());
    private static final String USERLIST_PAGE = "userlist.jsp";
    private static final String NO_AVALIBLE = "notAvalible.jsp";

    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        User currentUser = (User) request.getSession().getAttribute(AuthenticationFilter.CURRENT_USER);
        if (currentUser == null || (! currentUser.isAdmin())) {
            //redirect to notAvalible.jsp
            forward(NO_AVALIBLE);
            return;
        }

        UserDao userDao = daoFactory.getUserDao();

        try {
            userDao.openConnection();

            // show user list
            List<User> list = userDao.getAll();
            request.setAttribute("list", list);

            request.setAttribute("username", currentUser.getUsername());//TREGUB
            if (currentUser.isAdmin()) {
                request.setAttribute("isadmin", "1");
            }//TREGUB
            
            
            forward(USERLIST_PAGE);
        } catch (SQLException e) {
            throw new DBRecordException(e);
        } finally {
            try {
                userDao.closeConnection();
                log.debug("Connection was closed");
            } catch (SQLException e) {
                log.error("Can not close connection");
                throw new DBRecordException("Can not close connection");
            }
        }

    }

}
