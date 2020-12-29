package com.cos.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cos.hello.config.DBConn;
import com.cos.hello.dto.JoinDto;
import com.cos.hello.dto.LoginDto;
import com.cos.hello.model.Users;

public class UsersDao {
	
	private static UsersDao usersDao = null;
    
	private UsersDao() {};
	
	public static UsersDao getInstance() {//new를 한번만 수행
		if(usersDao == null) {
			usersDao = new UsersDao();
		}
		
		return usersDao; 
	}
	
	
	public int insert(JoinDto joinDto) {
		
		StringBuffer sb = new StringBuffer(); //스트링 전용 컬렉션(동기화)
		sb.append("INSERT INTO users(username,password,email)");
		sb.append("VALUES(?,?,?)");
		String sql = sb.toString();
		
//		String sql = "INSERT INTO users(username,password,email)"; 안 좋은 방법 
//		sql+="VALUES(?,?,?)";// +=연산자는 메모리 많이 소비
		
		
		Connection conn = DBConn.getInstance(); //선 연결
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, joinDto.getUsername());
			pstmt.setString(2, joinDto.getPassword());
			pstmt.setString(3, joinDto.getEmail());
			int result = pstmt.executeUpdate();//변경된 행의 개수를 리턴
	
			return result;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //버퍼 달기
		
		
		return -1;
	}
	
	
	public Users login(LoginDto loginDto) {
//		StringBuffer sb = new StringBuffer(); //스트링 전용 컬렉션(동기화) 이거는 안되는데?
//		sb.append("SELECT id, username, email from users "); //띄어쓰기 중요!!!!
//		sb.append("where username = ? and password= ?");
//		String sql = sb.toString();
		
		String sql = "SELECT id, username, email from users where username = ? and password= ?";
//        boolean loginCon = false;
		
		Connection conn = DBConn.getInstance(); //선 연결
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginDto.getUsername());
			pstmt.setString(2, loginDto.getPassword());
			ResultSet rs = pstmt.executeQuery();//변경된 행의 개수를 리턴
	
			if(rs.next()) {
				Users userEntity = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.build();
				return userEntity;
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //버퍼 달기
		
		return null;
		//return loginCon;
	}
	
	
	public Users selectById(int id) {
		String sql = "SELECT id, password, username, email from users where id = ?";
		
		Connection conn = DBConn.getInstance(); //선 연결
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();//변경된 행의 개수를 리턴
	
			if(rs.next()) {
				Users userEntity = Users.builder()
						.id(rs.getInt("id"))
						.password(rs.getString("password"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.build();
				return userEntity;
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	
	}
	
	
	public int update(Users user) {

		String sql = "UPDATE users SET password = ?, email = ? where Id = ?";
		
		Connection conn = DBConn.getInstance(); //선 연결
		
		System.out.println(user.getId());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getEmail());
			pstmt.setInt(3, user.getId());
			int result = pstmt.executeUpdate();//변경된 행의 개수를 리턴
			
			System.out.println("result 값" + result);
			return result;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //버퍼 달기
		
		
		return -1;
	}
	
	public int deleteById(int id) {

		String sql = "DELETE FROM users WHERE id = ?";
		
		Connection conn = DBConn.getInstance(); //선 연결
		

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);		
			int result = pstmt.executeUpdate();//변경된 행의 개수를 리턴
			
			System.out.println("result 값" + result);
			return result;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //버퍼 달기
		
		
		return -1;
	}
	
	
	
	
}
