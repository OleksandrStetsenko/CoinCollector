package ua.edu.sumdu.lab3.group11.servlets;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.dao.OracleDaoFactory;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ControllerServletRequestListener implements ServletRequestListener {

    static Logger log = Logger.getLogger(ControllerServletRequestListener.class.getName());

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {

        try {
            servletRequestEvent.getServletRequest().setAttribute("daoFactory", new OracleDaoFactory());
            log.debug("DAO Factory was created.");
        } catch (Exception e) {
            log.error("Unable to get DAO Factory: " + e.getMessage());
        }

    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        //DaoFactory daoFactory = (DaoFactory) servletRequestEvent.getServletRequest().getAttribute("daoFactory");
        //close resources
    }

}
