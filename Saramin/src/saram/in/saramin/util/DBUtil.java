package saram.in.saramin.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 디비 연결을 위한 클래스
 * @author 우성환
 *
 */
public class DBUtil {
	
	/**
	 * 
	 * 디비에 연결하는 메소드
	 * @param server 서버
	 * @param id 유저명
	 * @param pw 비밀번호
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection(url, "saramin", "java1234");
			
		} catch (Exception e) {
			System.out.println("DBUtil.getConnection :" + e.toString());
		}	
		return conn;
	}
}
