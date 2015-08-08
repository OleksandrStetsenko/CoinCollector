package ua.edu.sumdu.lab3.group11.dao;

import ua.edu.sumdu.lab3.group11.dao.coins.CoinDao;
import ua.edu.sumdu.lab3.group11.dao.counties.CountryDao;
import ua.edu.sumdu.lab3.group11.dao.search.SearchDao;
import ua.edu.sumdu.lab3.group11.dao.users.UserDao;

public interface DaoFactory {

    /** Returns DAO for control the state of User */
    public UserDao getUserDao();

    /** Returns coin DAO */
    public CoinDao getCoinDao();

    /** Returns country DAO */
    public CountryDao getCountryDao();

    /** Returns search DAO */
    public SearchDao getSearchDao();

}
