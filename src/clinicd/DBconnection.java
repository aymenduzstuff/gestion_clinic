
package clinicd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author aymen
 */
public class DBconnection implements conInterface{
    
    private final String url;
    private final String userName;
    private final String password;

    public DBconnection(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url, userName, password);

    }
}
