package ua.edu.sumdu.lab3.group11.commands.users;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.servlets.AuthenticationFilter;

import javax.servlet.ServletException;
import java.io.IOException;

public class LogoutCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(LogoutCommand.class.getName());

    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        request.getSession().removeAttribute(AuthenticationFilter.CURRENT_USER);

        request.setAttribute("message", "You have logged out.");
        forward("/login.jsp");

    }
}
