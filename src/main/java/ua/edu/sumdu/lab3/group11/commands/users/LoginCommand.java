package ua.edu.sumdu.lab3.group11.commands.users;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.users.UserDao;
import ua.edu.sumdu.lab3.group11.obj.User;
import ua.edu.sumdu.lab3.group11.servlets.AuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import ua.edu.sumdu.lab3.group11.commands.country.CountryListCommand;

public class LoginCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(LoginCommand.class.getName());

    @Override
    public void process() throws ServletException, IOException, DBRecordException {
                
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String errorMsg = null;

        if(username == null || username.equals("")){
            errorMsg ="Username can't be null or empty";
        }
        if(password == null || password.equals("")){
            errorMsg = "Password can't be null or empty";
        }

        if (errorMsg != null) {
            log.error(errorMsg);
            request.setAttribute("message", errorMsg);
            forward("/login.jsp");
        } else {
            //find user in DB
            UserDao userDao = daoFactory.getUserDao();

            try {
                userDao.openConnection();

                User user = userDao.read(username);
                if (user != null) { //if log in successful

                    if (user.getPassword().equals(password)) {
                        log.info("Login successful");
                        HttpSession session = request.getSession();
                        //session.setAttribute("User", user); //TODO: set user to session attribute
                        request.getSession().setAttribute(AuthenticationFilter.CURRENT_USER, user);
                        //forward("controller?action=listUser");
                        forward("controller?action=showAllCoins");
                    } else {
                        log.error("The password is incorrect");
                        request.setAttribute("message", "The password is incorrect");
                        forward("/login.jsp");
                    }

                } else {
                    log.error("No user found, please register first.");
                    request.setAttribute("message", "No user found, please register first.");
                    forward("/login.jsp");
                }
                
                
            } catch (SQLException e) {
                throw new DBRecordException(e);
            } finally {
                try {
                    userDao.closeConnection();
                    log.debug("Can was closed");
                } catch (SQLException e) {
                    log.error("Can not close connection");
                    throw new  DBRecordException("Can  not close connection");
                }
            }

        }

    }
}
