package dao.connectionPool;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DNAPC on 29.01.2018.
 */
public class DBResourceManagerTest {
    private static final String DB_USER_KEY_PROPERTY = "db.user";
    private static final String DB_USER_VALUE_PROPERTY = "root";

    @Test
    public void getInstance() throws Exception {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        assertTrue(dbResourceManager!=null);
    }

    @Test
    public void getValue() throws Exception {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        String actual = dbResourceManager.getValue(DB_USER_KEY_PROPERTY);
        assertEquals(DB_USER_VALUE_PROPERTY, actual);
    }

}