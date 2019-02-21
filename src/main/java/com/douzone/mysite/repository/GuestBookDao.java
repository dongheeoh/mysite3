package com.douzone.mysite.repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public GuestBookVo get(long noVal)
	{
		GuestBookVo guVo = new GuestBookVo();		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try 
		{
			 conn = dataSource.getConnection();
			 
			 // SQL문 준비
			 String sql = "select no, name, date_format(reg_date, '%Y-%m-%d %h:%i:%s'), message from guestbook where no = ?";
			 
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setLong(1, noVal);
			 
			 rs = pstmt.executeQuery();
			 
			 if( rs.next())
			 {
				 long no = rs.getLong(1);
				 String name = rs.getString(2);
				 String regDate = rs.getString(3);
				 String message = rs.getString(4);
				 
				 guVo.setNo(no);
				 guVo.setName(name);
				 guVo.setRegDate(regDate);
				 guVo.setText(message);
			 }
		} 
		catch (SQLException e) 
		{
			System.out.println("error : " + e);
		}
		finally 
		{
			try 
			{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		return guVo;
	}
	
	public long insert(GuestBookVo vo){
		return sqlSession.insert("guestbook.insert", vo);
	}
	
	
	public int delete(GuestBookVo vo)
	{
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try 
		{
			 conn = dataSource.getConnection();
			 
			 String sql = "delete from guestbook where no = ? and pasword = ?";
			 
			 pstmt = conn.prepareCall(sql);
			 
			 pstmt.setLong(1, vo.getNo());
			 pstmt.setString(2, vo.getPassword());
			 
			 int count = pstmt.executeUpdate();
			 result = (count == 1) ? (int)vo.getNo() : -1;
		} 
		catch (SQLException e) 
		{
			System.out.println("error 여기 : " + e);
		}
		finally 
		{
			try 
			{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public List<GuestBookVo> getList(int page)
	{
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try 
		{
			 conn = dataSource.getConnection();
			 
			 // SQL문 준비
			 String sql = "select no, name, date_format(reg_date, '%Y-%m-%d %h:%i:%s'), message from guestbook order by reg_date desc limit ?, 5";
			 
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setInt(1, (page-1) * 5);
			 
			 rs = pstmt.executeQuery();
			 
			 while (rs.next())
			 {
				 long no = rs.getLong(1);
				 String name = rs.getString(2);
				 String regDate = rs.getString(3);
				 String message = rs.getString(4);
				 
				 GuestBookVo guVo = new GuestBookVo();
				 guVo.setNo(no);
				 guVo.setName(name);
				 guVo.setRegDate(regDate);
				 guVo.setText(message);
				 
				 list.add(guVo);
			 }
		} 
		catch (SQLException e) 
		{
			System.out.println("error : " + e);
		}
		finally 
		{
			try 
			{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	
	public List<GuestBookVo> getList() {
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql=
					"select no,name,pasword,message,reg_date"+ 
					" from guestbook"+ 
					" order by no desc";
			
			pstmt= conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				long no=rs.getLong(1);
				String name=rs.getString(2);
				String password=rs.getString(3);
				String message=rs.getString(4);
				String regdate=rs.getString(5);
				
				
				GuestBookVo vo=new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				vo.setText(message);
				vo.setRegDate(regdate);
				
				list.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error" + e);
		} finally {
			try {
				if(rs !=null) {
					rs.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
}
