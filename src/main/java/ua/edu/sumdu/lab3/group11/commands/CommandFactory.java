package ua.edu.sumdu.lab3.group11.commands;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ua.edu.sumdu.lab3.group11.commands.coins.AddCoinCommand;
import ua.edu.sumdu.lab3.group11.commands.coins.DeleteCoinCommand;
import ua.edu.sumdu.lab3.group11.commands.coins.PrepareCoinCommand;
import ua.edu.sumdu.lab3.group11.commands.coins.ShowAllCoinsCommand;
import ua.edu.sumdu.lab3.group11.commands.country.CoinListCommand;
import ua.edu.sumdu.lab3.group11.commands.country.CountryListCommand;
import ua.edu.sumdu.lab3.group11.commands.country.PrepareSearchCommand;
import ua.edu.sumdu.lab3.group11.commands.country.SearchCommand;
import ua.edu.sumdu.lab3.group11.commands.users.AddCoinToCollectionCommand;
import ua.edu.sumdu.lab3.group11.commands.users.DeleteUserCommand;
import ua.edu.sumdu.lab3.group11.commands.users.EditUserCommand;
import ua.edu.sumdu.lab3.group11.commands.users.LoginCommand;
import ua.edu.sumdu.lab3.group11.commands.users.LogoutCommand;
import ua.edu.sumdu.lab3.group11.commands.users.RegisterCommand;
import ua.edu.sumdu.lab3.group11.commands.users.RemoveCoinFromCollectionCommand;
import ua.edu.sumdu.lab3.group11.commands.users.SaveUserChangesCommand;
import ua.edu.sumdu.lab3.group11.commands.users.ShowUserCoinsCommand;
import ua.edu.sumdu.lab3.group11.commands.users.UserListCommand;

public class CommandFactory {

    private static Logger log = Logger.getLogger(CommandFactory.class.getName());
    private Map<String, Class> commandMap;

    /** Creates factory with commands */
    public CommandFactory() {

        commandMap = new HashMap<>();
        commandMap.put("listUser",                      UserListCommand.class);
        commandMap.put("edit",                          EditUserCommand.class);
        commandMap.put("delete",                        DeleteUserCommand.class);
        commandMap.put("login",                         LoginCommand.class);
        commandMap.put("register",                      RegisterCommand.class);
        commandMap.put("saveUserChanges",               SaveUserChangesCommand.class);
        commandMap.put("listCountry",                   CountryListCommand.class);
        commandMap.put("showAllCoins",                  ShowAllCoinsCommand.class);
        commandMap.put("addCoin",                       AddCoinCommand.class);
        commandMap.put("prepCoin",                      PrepareCoinCommand.class);
        commandMap.put("getListCoins",                  CoinListCommand.class);
        commandMap.put("deleteCoin",                    DeleteCoinCommand.class);
        commandMap.put("prepSearch",                    PrepareSearchCommand.class);
        commandMap.put("search",                        SearchCommand.class);
        commandMap.put("logout",                        LogoutCommand.class);
        commandMap.put("addCoinToMyCollection",         AddCoinToCollectionCommand.class);
        commandMap.put("removeCoinFromMyCollection",    RemoveCoinFromCollectionCommand.class);
        commandMap.put("showUserCoins",                 ShowUserCoinsCommand.class);

        log.debug("commands map was created");
    }

    /** Returns command class with specified action */
    public FrontCommand getCommand(String action) {

        FrontCommand returnedCommand = null;

        if (commandMap.containsKey(action)) {
            try {
                Class clazz = commandMap.get(action);
                log.debug(clazz);
                returnedCommand = (FrontCommand) clazz.newInstance();
            } catch (Exception e) {
                log.error("Unknown command. Go to login page", e);
                returnedCommand = new LoginCommand(); //default
            }
        } else {
            returnedCommand = new LoginCommand(); //default
        }

        return returnedCommand;

    }

}
