import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root", "root");
        } catch (Exception e)
        {
            throw new RuntimeException("Unable to connect", e);
        }
    }

    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}