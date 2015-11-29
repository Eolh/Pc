package DAO;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2DB_Initializer {

	private static PreparedStatement pstmt;
	private static Statement stmt;

	public void initDatabase() throws SQLException, ClassNotFoundException {
		Class.forName("org.gjt.mm.mysql.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "root", "1234");

		String sql = "drop table if exists member";
		stmt = conn.createStatement();
		stmt.execute(sql);
		String sql2 = "CREATE TABLE member("
                + "NUM INT auto_increment, "
                + "ID VARCHAR primary key, "
                + "PASSWORD VARCHAR, "
                + "AGE INT,"
                + "PHONE VARCHAR,"
                + "MILEAGE INT);";

		Statement stmt = conn.createStatement();
		stmt.execute(sql2);

		pstmt = conn.prepareStatement("show tables");
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getObject(1));
		}

		rs.close();
		pstmt.close();
		// add application code here
		conn.close();
	}
}

