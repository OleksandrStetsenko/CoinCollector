package ua.edu.sumdu.lab3.group11.obj;

import org.apache.log4j.Logger;

public class User {

    static Logger log = Logger.getLogger(User.class.getName());

    private int userID;
    private String username;
    private String password;
    private boolean admin;

    /**
     * Creates new user
     * @param userID
     * @param username
     * @param password
     * @param admin
     */
    public User(int userID, String username, String password, boolean admin) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.admin = admin;

        log.debug("New user has been created: " + this.toString());
    }

    /**
     * Returns user ID
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Returns username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns user password
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns true if user is admin
     * @return is admin
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Sets user ID
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Sets username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets user password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets admin rights
     * @param admin
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        if (admin != user.admin)
            return false;

        if (userID != user.userID)
            return false;

        if (password != null ? !password.equals(user.password) : user.password != null)
            return false;

        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userID;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (admin ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }

}
