package cashbook.util;

import java.sql.Connection;
import java.sql.DriverManager;

//메서드 마다 드라이브 로딩과 Connection을 구하는 코드가 중복되어 하나의 메서드로 추출하였다
public class DBUtil {
	 public static Connection getConnection() throws Exception {
	     Class.forName("com.mysql.cj.jdbc.Driver");
	     String dbUrl = "jdbc:mysql://localhost:3306/cashbook";
	     String dbUser = "root";
	     String dbPw = "java1234";
	     Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPw);
	     return connection;
	 }
}
