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

public class EditUserCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(EditUserCommand.class.getName());
    private static final String EDIT_PAGE = "user.jsp";
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

            // open user page for edit user
            int userID = Integer.parseInt(request.getParameter("userID"));
            User user = userDao.read(userID);
            request.setAttribute("user", user);

            forward(EDIT_PAGE);
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
