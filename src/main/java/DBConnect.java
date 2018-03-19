import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public abstract class DBConnect {
    protected Connection conn;
    public DBConnect () {
    }
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // Properties for user and password. Here the user and password are both 'paulr'
            Properties p = new Properties();
            p.put("user", "myuser");
            p.put("password", "mypassword");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?autoReconnect=true&useSSL=false",p);
        } catch (Exception e)
        {
            throw new RuntimeException("Unable to connect", e);
        }
    }
}