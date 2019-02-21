package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;
import com.douzone.mysite.vo.UserVo;

@Repository
public class BoardDao extends Dao {
	
	public List<BoardVo> getList(int page)
	{		
		List<BoardVo> list = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		try {
			 conn = getConnection();
			 page=page-1;
			 page=page*10;
			 String sql =  "select bord.no,bord.title,bord.contents,bord.write_date,bord.hit,bord.g_no,bord.o_no,bord.depth,user.no,user.name from bord,user where bord.user_no = user.no order by bord.g_no DESC, bord.o_no ASC limit "+page+",5";
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				 long no = rs.getLong(1);
				 String title = rs.getString(2);
				 String contents = rs.getString(3);
				 String writeDate = rs.getString(4);
				 int hit = rs.getInt(5);
				 int gNo = rs.getInt(6);
				 int oNo = rs.getInt(7);
				 int depth = rs.getInt(8);
				 long userNo = rs.getLong(9);
				 String userName = rs.getString(10);
				 UserVo userVo = new UserVo();
				 userVo.setNo(userNo);
				 userVo.setName(userName);
				 BoardVo boardVo = new BoardVo();
				 boardVo.setNo(no);
				 boardVo.setTitle(title);
				 boardVo.setContents(contents);
				 boardVo.setWriteDate(writeDate);
				 boardVo.setHit(hit);
				 boardVo.setgNo(gNo);
				 boardVo.setoNo(oNo);
				 boardVo.setDepth(depth);
				 boardVo.setuserVo(userVo);
				 list.add(boardVo);
			 }
			 return list;
			 
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} 
		finally 
		{
				try {
					if(conn !=null)
					conn.close();
					if(rs !=null)
						rs.close();
					if(pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		return list;
	}

	public boolean write(BoardVo vo,long userNo)
	{
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try {
			conn = getConnection();

			 String sql =  "select max(g_no)+1,max(o_no)+1 from bord";
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 int gNo =0;
			 int oNo =0;
			 while(rs.next())
			 {
				gNo = rs.getInt(1);
				oNo = rs.getInt(2);
			 }
			 sql = "insert into bord value(null,?,?,current_timestamp(),0,?,?,0,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, gNo);
			pstmt.setInt(4, oNo);
			pstmt.setLong(5, userNo);
			pstmt.setString(6, vo.getFileName());

			count = pstmt.executeUpdate();
			return count==1;
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if(rs !=null)
					rs.close();
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count==1;
		
	}

	public boolean delete(String no)
	{
	boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn=getConnection();
			
			String sql = "delete from bord where no=?";	
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1,(long)Integer.parseInt(no));
			int count = pstmt.executeUpdate();
			result = count ==1;
			
		}catch (SQLException e) {
			System.out.println("error:"+e);
		} 
		finally 
		{
				try {
					if(conn !=null)
					conn.close();
					if(pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		
		
		
		return result;
	}

	public boolean hit(String boardNo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
					"update bord set hit=hit+1 where no=?"; 
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, (long)Integer.parseInt(boardNo));
			int count = pstmt.executeUpdate();
			return result = count==1;
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
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

	public BoardVo view(String no)
	{
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		BoardVo vo = new BoardVo();
		try {
			 conn = getConnection();
			 
			 String sql = "select title,contents,o_no,file_name from bord where no="+no;
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				 String title = rs.getString(1);
				 String contents = rs.getString(2);
				 int oNo =rs.getInt(3);
				 String fileName = rs.getString(4);
				 vo.setTitle(title);
				 vo.setContents(contents);
				 vo.setoNo(oNo);
				 vo.setFileName(fileName);
				 vo.setNo((long)Integer.parseInt(no));
			 }
			return vo;
			
			 
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} 
		finally 
		{
				try {
					if(conn !=null)
					conn.close();
					if(rs !=null)
						rs.close();
					if(pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		
		
		return vo;
	}

	public boolean modify(Long no,String title,String contents)
	{
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
					"update bord set title=?,contents=? where no=?"; 
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setLong(3, no);
			int count = pstmt.executeUpdate();
			return result = count==1;
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
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

	public int replyWrite(BoardVo vo,long userNo)
	{
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try {
			conn = getConnection();		
			
			String  sql =  "select bord.g_no,bord.depth+1,o_no from bord where no="+vo.getNo();
				 pstmt = conn.prepareStatement(sql);
				 rs = pstmt.executeQuery();
				 int gNo =0;
				 int depth =0;
				 int oNo = 0;
				 while(rs.next())
				 {
					gNo=rs.getInt(1);
					depth = rs.getInt(2);
					oNo = rs.getInt(3);
				 }
			
			 sql = 
					"update bord set o_no=o_no+1 where o_no> ?"; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, oNo);
			 count = pstmt.executeUpdate();
			 
			 
			
			 sql = "insert into bord value(null,?,?,current_timestamp(),0,?,?,?,?,null)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, gNo);
			pstmt.setInt(4, oNo+1);
			pstmt.setInt(5, depth);
			pstmt.setLong(6, userNo);
			count = pstmt.executeUpdate();
			return count;
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if(rs !=null)
					rs.close();
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;
	}

	public List<BoardVo> searchGetList(String text,String way,int page)
	{
		
			List<BoardVo> list = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		try {
			 page=page-1;
			 page=page*10;
			 conn = getConnection();
			 if(way.equals("titleSearch"))
				 way = "bord.title";
			 else if(way.equals("writerSearch"))
				 way ="user.name";
			 String sql =  "select bord.no,bord.title,bord.contents,bord.write_date,bord.hit,bord.g_no,bord.o_no,bord.depth,user.no,user.name from bord,user where bord.user_no = user.no and "+way+" like '%"+text+"%' order by g_no DESC, o_no ASC limit "+page+",10";
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
				 while(rs.next())
				 {
					 long no = rs.getLong(1);
					 String title = rs.getString(2);
					 String contents = rs.getString(3);
					 String writeDate = rs.getString(4);
					 int hit = rs.getInt(5);
					 int gNo = rs.getInt(6);
					 int oNo = rs.getInt(7);
					 int depth = rs.getInt(8);
					 long userNo = rs.getLong(9);
					 String userName = rs.getString(10);
					 UserVo userVo = new UserVo();
					 userVo.setNo(userNo);
					 userVo.setName(userName);
					 BoardVo boardVo = new BoardVo();
					 boardVo.setNo(no);
					 boardVo.setTitle(title);
					 boardVo.setContents(contents);
					 boardVo.setWriteDate(writeDate);
					 boardVo.setHit(hit);
					 boardVo.setgNo(gNo);
					 boardVo.setoNo(oNo);
					 boardVo.setDepth(depth);
					 boardVo.setuserVo(userVo);
					 list.add(boardVo);			 
				 }
			return list;
			 
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} 
		finally 
		{
				try {
					if(conn !=null)
					conn.close();
					if(rs !=null)
						rs.close();
					if(pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		
		
		return list;
		
		
		
		
	}	

	public int searchCount(String text,String way) {
		int cnt=0;
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		try {
			 if(way.equals("titleSearch"))
				 way = "bord.title";
			 else if(way.equals("writerSearch"))
				 way ="user.name";
			 conn = getConnection();
			 String sql =  "select count(*) from bord,user where bord.user_no = user.no and "+way+" like '%"+text+"%'";
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				 cnt = rs.getInt(1);
			 }
			 return cnt;
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} 
		finally 
		{
				try {
					if(conn !=null)
					conn.close();
					if(rs !=null)
						rs.close();
					if(pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		
		
		return cnt;
		
	}
	public int BoardCount() {
		int cnt=0;
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		try {
			 conn = getConnection();
			 String sql =  "select count(*) from bord";
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				 cnt = rs.getInt(1);
			 }
			 return cnt;
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} 
		finally 
		{
				try {
					if(conn !=null)
					conn.close();
					if(rs !=null)
						rs.close();
					if(pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		
		
		return cnt;
	}

	public int commentWrite(String contents,long userNo,long boardNo) {

		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try {
			conn = getConnection();

			 String sql =  "select max(comment.o_no)+1 from bord,comment where bord.no="+boardNo;
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 int oNo =0;
			 while(rs.next())
			 {
				oNo = rs.getInt(1);
			 }
			 sql = "insert into comment values(null,?,current_timestamp(),?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, contents);
			pstmt.setInt(2, oNo);
			pstmt.setLong(3, boardNo);
			pstmt.setLong(4, userNo);


			count = pstmt.executeUpdate();
			return count;
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if(rs !=null)
					rs.close();
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;
	}

	public List<CommentVo> getCommentList(int page,int boardNo)
	{
		List<CommentVo> list = new ArrayList<CommentVo>();
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		try {
			 conn = getConnection();
			 page=page-1;
			 page=page*10;
			 String sql = 
			 "select comment.no,comment.contents,comment.reg_date,comment.o_no,comment.board_no,comment.user_no,user.name from comment,user where comment.board_no="+boardNo+" and user.no=user_no order by comment.o_no asc limit "+page+",10";
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				long no = rs.getLong(1);
				String contents = rs.getString(2);
				String regDate = rs.getString(3);
				int oNo = rs.getInt(4);
				long bNo = rs.getLong(5);
				long userNo = rs.getLong(6);
				String userName = rs.getString(7);
				 
				CommentVo commentVo = new CommentVo();
				commentVo.setNo(no);
				commentVo.setContents(contents);
				commentVo.setRegDate(regDate);
				commentVo.setoNo(oNo);
				BoardVo boardVo = new BoardVo();
				boardVo.setNo(bNo);
				UserVo userVo = new UserVo();
				userVo.setNo(userNo);
				userVo.setName(userName);
				commentVo.setBoardVo(boardVo);
				commentVo.setUserVo(userVo);
				
				list.add(commentVo);
				
			 }
			 return list;
			 
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} 
		finally 
		{
				try {
					if(conn !=null)
					conn.close();
					if(rs !=null)
						rs.close();
					if(pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		
		
		return list;
		
	}
	public boolean commentDelete(String no)
	{
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn=getConnection();
			
			String sql = "delete from comment where no=?";	
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1,(long)Integer.parseInt(no));
			int count = pstmt.executeUpdate();
			result = count ==1;
			
		}catch (SQLException e) {
			System.out.println("error:"+e);
		} 
		finally 
		{
				try {
					if(conn !=null)
					conn.close();
					if(pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		
		
		
		return result;
	}

	public int CommentCount(int boardNo) {
		int cnt=0;
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		try {
			 conn = getConnection();
			 String sql =  "select count(*) from comment where bord_no="+boardNo;
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				 cnt = rs.getInt(1);
			 }
			 return cnt;
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} 
		finally 
		{
				try {
					if(conn !=null)
					conn.close();
					if(rs !=null)
						rs.close();
					if(pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		
		
		return cnt;
	}
}
