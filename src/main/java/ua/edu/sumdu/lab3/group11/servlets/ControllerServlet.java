package ua.edu.sumdu.lab3.group11.servlets;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.commands.CommandFactory;
import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.DaoFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="controller", urlPatterns = "/controller")
public class ControllerServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(ControllerServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.debug("do get " + req.getRequestURI());
        processRequest(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.debug("do post" + req.getRequestURI());
        processRequest(req, resp);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        DaoFactory daoFactory = (DaoFactory) request.getAttribute("daoFactory");
        log.info(" daoFactory = " + daoFactory);
        if (daoFactory == null) {
            return;
        }

        String action = request.getParameter("action");
        log.debug("action is " + action);

        CommandFactory commandFactory = new CommandFactory();
        FrontCommand command = commandFactory.getCommand(action);
        command.init(getServletContext(), request, response, daoFactory);
        try {
            command.process();
        } catch (DBRecordException e) {
            log.debug(e.getMessage());
            throw new ServletException(e);
        }
    }

}
