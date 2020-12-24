package com.cos.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.cos.hello.config.DBConn;
import com.cos.hello.model.Users;

public class UsersDao {
	
	
	public int 회원가입(Users user) {
		
		StringBuffer sb = new StringBuffer(); //스트링 전용 컬렉션(동기화)
		sb.append("INSERT INTO users(username,password,email)");
		sb.append("VALUES(?,?,?)");
		String sql = sb.toString();
		
//		String sql = "INSERT INTO users(username,password,email)"; 안 좋은 방법 
//		sql+="VALUES(?,?,?)";// +=연산자는 메모리 많이 소비
		
		
		Connection conn = DBConn.getInstance(); //선 연결
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			int result = pstmt.executeUpdate();//변경된 행의 개수를 리턴
	
			return result;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //버퍼 달기
		
		
		return -1;
	}
}
