package Dao;

import Dao.RepositoryException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtils {

    static void resultSetClosing(ResultSet toClose){
        try {
            if (toClose != null) toClose.close();
        } catch (SQLException e) {
            throw new RepositoryException("Failure Closing ResultSet", e);
        }
    }
}
