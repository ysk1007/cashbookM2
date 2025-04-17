package model;

import java.sql.*;

import cashbook.util.DBUtil;

public class AdminDao {
	
	//-------------------- SELECT --------------------
	public boolean login(String id, String pw){
		boolean isSuccess = false;	// 로그인 성공 유무
		
		// SQL 연결
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			// 쿼리
			String sql = "SELECT * FROM admin "
					+ " WHERE admin_id = ? AND admin_pw = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, pw);
			
			// 쿼리 실행
			rs = stmt.executeQuery();
			
			// 쿼리 디버깅
			//System.out.println(stmt);
			
			if(rs.next()) {	// 로그인 성공
				isSuccess = true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("예외 발생");
        } finally {
            try {stmt.close();} catch(Exception e){}
            try {conn.close();} catch(Exception e){}
        } 
		
		return isSuccess;
	}

	//-------------------- UPDATE --------------------
	
	public boolean updatePw(String admin, String pw, String newPw){
		boolean isSuccess = false;	// 쿼리 성공 유무
		int row = 0;	// 영향 받은 행의 개수
		
		// SQL 연결
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DBUtil.getConnection();
			// 수정 쿼리
			String sql = "UPDATE admin SET "
					+ " admin_pw = ? WHERE admin_id = ? AND admin_pw = ?";
			
			stmt = conn.prepareStatement(sql);
			
			// ? 할당
			stmt.setString(1, newPw);
			stmt.setString(2, admin);
			stmt.setString(3, pw);
			
			// 쿼리 실행
			row = stmt.executeUpdate();
			
			// 쿼리 디버깅
			//System.out.println(stmt);

			if(row == 1) {
				isSuccess = true;
			}
			
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("예외 발생");
        } finally {
            try {stmt.close();} catch(Exception e){}
            try {conn.close();} catch(Exception e){}
        } 
		
		return isSuccess;
	}
	
	//-------------------- INSERT --------------------
	
	//-------------------- DELETE --------------------
}
