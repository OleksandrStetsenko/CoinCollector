package ua.edu.sumdu.lab3.group11.commands;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.DaoFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class FrontCommand {

    private static Logger log = Logger.getLogger(FrontCommand.class.getName());

    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected DaoFactory daoFactory;

    public void init(ServletContext context,
                     HttpServletRequest request,
                     HttpServletResponse response,
                     DaoFactory daoFactory) {

        this.context = context;
        this.request = request;
        this.response = response;
        this.daoFactory = daoFactory;

        log.debug("Command " + getClass() + " has been initialized");

    }

    public abstract void process() throws ServletException, IOException, DBRecordException;

    protected void forward(String target) throws ServletException, IOException {

        log.debug("Forward to " + target);
        request.getRequestDispatcher(target).forward(request, response);

        //RequestDispatcher dispatcher = context.getRequestDispatcher(target);
        //log.debug("Dispatcher - " + dispatcher);
        //dispatcher.forward(request, response);

    }

}
