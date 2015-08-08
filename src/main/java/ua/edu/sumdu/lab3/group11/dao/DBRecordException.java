package ua.edu.sumdu.lab3.group11.dao;

import org.apache.log4j.Logger;

public class DBRecordException extends Exception {

    private static Logger log = Logger.getLogger(DBRecordException.class.getName());

    public DBRecordException() {
        super();
    }

    public DBRecordException(String message) {
        super(message);
    }

    public DBRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBRecordException(Throwable cause) {
        super(cause);
    }
}
