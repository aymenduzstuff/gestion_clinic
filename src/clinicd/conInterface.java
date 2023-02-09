
package clinicd;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author aymen
 * DBConnection should be a factory/manger, which wraps the core 
 * functionality and management of the database connection and querying 
 * (or at least forms the bases of)
 */
public interface conInterface {
    public Connection getConnection() throws SQLException ;
}
