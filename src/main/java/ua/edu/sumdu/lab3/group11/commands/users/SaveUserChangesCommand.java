package ua.edu.sumdu.lab3.group11.commands.users;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.users.UserDao;
import ua.edu.sumdu.lab3.group11.obj.User;
import ua.edu.sumdu.lab3.group11.servlets.AuthenticationFilter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class SaveUserChangesCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(SaveUserChangesCommand.class.getName());
    private static final String NO_AVALIBLE = "notAvalible.jsp";

    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        User currentUser = (User) request.getSession().getAttribute(AuthenticationFilter.CURRENT_USER);
        if (currentUser == null || (! currentUser.isAdmin())) {
            //redirect to notAvalible.jsp
            forward(NO_AVALIBLE);
            return;
        }

        log.debug("start process save user");

        int userID = Integer.parseInt(request.getParameter("userID"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean admin = "on".equals(request.getParameter("admin"));

        String errorMsg = null;

        if(username == null || username.equals("")){
            errorMsg ="Username can't be null or empty";
        }
        if(password == null || password.equals("")){
            errorMsg = "Password can't be null or empty";
        }

        if(errorMsg != null){
            RequestDispatcher rd = context.getRequestDispatcher("/user.jsp");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>" + errorMsg + "</font>");
            rd.include(request, response);
        }else{

            //creates new user with the same userID but new name or pass
            //and do update
            User user = new User(userID, username, password, admin);

            UserDao userDao = daoFactory.getUserDao();

            try {
                userDao.openConnection();

                userDao.update(user);
                response.sendRedirect("controller?action=listUser");
            } catch (SQLException e) {
                throw new DBRecordException(e);
            } finally {
                try {
                    userDao.closeConnection();
                    log.debug("Connection was close");
                } catch (SQLException e) {
                    log.error("Can not close connection");
                    throw new DBRecordException("Can not close connection");
                }
            }

        }

    }
}
