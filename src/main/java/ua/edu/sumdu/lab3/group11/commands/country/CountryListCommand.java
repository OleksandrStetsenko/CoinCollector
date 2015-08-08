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
public class CountryListCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(CountryListCommand.class.getName());
    private static final String TREE_PAGE = "countryTree.jsp";

    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        forward(TREE_PAGE);

    }
}
