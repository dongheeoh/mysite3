package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {

	protected Connection getConnection() throws SQLException  
	{
		Connection con =null;
		try {
			// 1. JDBC Driver(MySQL) 로딩
			Class.forName("com.mysql.jdbc.Driver");
			
			//2 연결
			String url = "jdbc:mysql://localhost:3306/webdb?useSSL=false";
			 con = DriverManager.getConnection(url, "webdb", "webdb");
		}
		catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:"+e);
		}
		return con;
	}
	
	public void allDelete()
	{
		Connection con =null;
		Statement stmt = null;
		try {
		
//	Class.forName("com.mysql.jdbc.Driver");
//			
//			//2 연결
//			String url = "jdbc:mysql://localhost:3306/webdb?useSSL=false";
//			 con = DriverManager.getConnection(url, "webdb", "webdb");
//			
//			//3. Statment 객체를 생성
//			stmt = con.createStatement();
//
//			//4 sql 문 실행
//			String sql ="delete from pet where name = '"+name+"'";
//			int count= stmt.executeUpdate(sql);
//			result = count==1;
			
			 con = getConnection();
			 stmt = con.createStatement();
			 String sql = "";
			 
			 
			 sql="delete from order_book";
			 stmt.executeUpdate(sql);
				System.out.println("order_book 테이블 삭제");
			//3. SQL문 준비
			 sql="delete from orderlist";
			  stmt.executeUpdate(sql);

				System.out.println("orderlist 테이블 삭제");

			 
			 sql="delete from cart";
			 stmt.executeUpdate(sql);
				System.out.println("cart 테이블 삭제");
			 
			 sql="delete from member";
			 stmt.executeUpdate(sql);
				System.out.println("member 테이블 삭제");
			 
			 sql="delete from book";
			 stmt.executeUpdate(sql);
				System.out.println("book 테이블 삭제");
			 
			 sql="delete from category";
			 stmt.executeUpdate(sql);
				System.out.println("category 테이블 삭제");

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("sql exception:"+e);
		} 
		finally
		{
		
				try {
					if(con!=null)
					con.close();
					if(stmt!=null)
						stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
		}
	}
	
	

}
