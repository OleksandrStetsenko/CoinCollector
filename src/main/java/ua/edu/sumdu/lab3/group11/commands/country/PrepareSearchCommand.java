package ua.edu.sumdu.lab3.group11.commands.country;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;

/**
 *
 * @author Yulia Lukianenko
 */
public class PrepareSearchCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(PrepareSearchCommand.class.getName());
    private static final String SEARCH_PAGE = "search.jsp";

    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        forward(SEARCH_PAGE);
    }
}
