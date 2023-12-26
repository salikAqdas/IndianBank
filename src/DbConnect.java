import java.sql.*;

public class DbConnect {
    public static ResultSet execute(String query) throws SQLException {
        String databaseUrl = "jdbc:postgresql://localhost:5432/IndianBank";
        String userName = "postgres";
        String password = "123456";
        Connection con = DriverManager.getConnection(databaseUrl, userName, password);
        Statement st = con.createStatement();
        ResultSet s = st.executeQuery(query);
        con.close();
        return s;
    }
    public static boolean executeQuery(String query) throws SQLException{
        String databaseUrl = "jdbc:postgresql://localhost:5432/IndianBank";
        String userName = "postgres";
        String password = "123456";
        Connection con = DriverManager.getConnection(databaseUrl, userName, password);
        Statement st = con.createStatement();
        boolean s = st.execute(query);
        con.close();
        return s;
    }
}
