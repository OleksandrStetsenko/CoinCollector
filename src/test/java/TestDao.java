import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ua.edu.sumdu.lab3.group11.dao.DaoFactory;
import ua.edu.sumdu.lab3.group11.dao.OracleDaoFactory;
import ua.edu.sumdu.lab3.group11.dao.users.UserDao;
import ua.edu.sumdu.lab3.group11.obj.User;

import java.sql.Connection;
import java.util.List;

public class TestDao {

    static Logger log = Logger.getLogger(TestDao.class.getName());

    @Ignore
    @Test
    public void testGetAll() throws Exception {

        DaoFactory daoFactory = new OracleDaoFactory();
        List<User> list;

        Connection con = null;
        try {
            UserDao dao = daoFactory.getUserDao();
            list = dao.getAll();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

}
