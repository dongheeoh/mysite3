package com.douzone.mysite.repository;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserDao {
	

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	
	public UserVo get(long no) {
		return null;
	}

	public UserVo get(String email){
		UserVo result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();

			String sql = 
				" select no, name" + 
				"   from user" + 
				"  where email=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, email);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				long no = rs.getLong(1);
				String name = rs.getString(2);
				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	public UserVo update(UserVo vo) {
		
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		
		try {
			
			conn = dataSource.getConnection();
			
			String sql="update user "+" set name=?, gender=?"+" where no=?";
			pstmt= conn.prepareStatement(sql);
			
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getGender());
			pstmt.setLong(3, vo.getNo());
			
			
			int count=pstmt.executeUpdate();
			 
			
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
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
		return vo;
	}
	
	public UserVo get(Long no) {
		UserVo result =null;
		
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		try {
			
			conn = dataSource.getConnection();
			
			String sql="select name,email,gender "+"from user "+" where no="+no;
			pstmt= conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			
			if(rs.next()) {
				String name=rs.getString(1);
				String email=rs.getString(2);
				String gender=rs.getString(3);
				
				
				result=new UserVo();
				result.setNo(no);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
			}
			
			
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(rs!=null) {
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
		return result;
	}
	
	public UserVo get(String email,String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password",password);
		
		UserVo userVo= sqlSession.selectOne("user.getByEmailAndPassword", map);
		return userVo;
	}
	
	
	public int insert(UserVo vo) {
		int count= sqlSession.insert("user.insert", vo);
		return count;
	}
	
}
