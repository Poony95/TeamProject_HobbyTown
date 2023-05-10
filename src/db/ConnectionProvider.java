package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Spliterator;

public class ConnectionProvider {
	//JDBC 드라이버를 메모리로 로드하고 DB서버에 연결한 커넥션 객체를 반환하는 클래스 메서드 만들기
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.59:1521:XE","C##hobbytown","admin");
		} catch (Exception e) {System.out.println("getConnection() 예외 발생 : "+e.getMessage());}
		return conn;
	}
	
	public static void close(ResultSet rs, Statement stmt, Connection conn) {
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {System.out.println("close() 예외 발생 : "+e.getMessage());}
	}
	public static void close(Statement stmt, Connection conn) {
		try {
			stmt.close();
			conn.close();
		} catch (Exception e) {System.out.println("close() 예외 발생 : "+e.getMessage());}
	}
}
