package com.douzone.mysite.repository;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {

	@Autowired
	private SqlSession sqlSession;
	
	public long insert(GuestBookVo vo){
		sqlSession.insert("guestbook.insert", vo);
		long no=vo.getNo();
		return no;
	}
	
	
	public int delete(GuestBookVo vo)
	{
		int count=sqlSession.delete("guestbook.delete", vo);
		return count;
	}
	
	public GuestBookVo get(long noVal)
	{
		return sqlSession.selectOne("getByNoval", noVal);
	}
	
	public List<GuestBookVo> getList(int page)
	{
		List<GuestBookVo> list = sqlSession.selectList("guestbook.getByPage", page);
		return list;
	}
	
	
	public List<GuestBookVo> getList() {
		List<GuestBookVo> list = sqlSession.selectList("guestbook.getList");
		return list;
	}
	
}
