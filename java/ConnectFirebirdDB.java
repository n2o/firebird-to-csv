/*
 * ConnectFirebirdDB.java
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * @author www.javaworkspace.com
 *
 */
public class ConnectFirebirdDB {
    public static void main(String[] args) {

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;

        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            connection = DriverManager
                    .getConnection(
                            "jdbc:firebirdsql://localhost:3050/C:/FirebirdDB/EMPLOYEEDB.fdb",
                            "sysdba", "masterkey");
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery("SELECT EMPNAME FROM EMPLOYEEDETAILS");
            while (resultSet.next()) {
                System.out.println("EMPLOYEE NAME:"
                        + resultSet.getString("EMPNAME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
