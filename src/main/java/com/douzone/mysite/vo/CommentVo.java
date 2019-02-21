package com.douzone.mysite.vo;

public class CommentVo {
	private long no;
	private String contents;
	private String regDate;
	private int oNo;
	private BoardVo boardVo;
	private UserVo userVo;
	@Override
	public String toString() {
		return "CommentVo [no=" + no + ", contents=" + contents + ", regDate=" + regDate + ", oNo=" + oNo + ", boardVo="
				+ boardVo + ", userVo=" + userVo + "]";
	}
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getoNo() {
		return oNo;
	}
	public void setoNo(int oNo) {
		this.oNo = oNo;
	}
	public BoardVo getBoardVo() {
		return boardVo;
	}
	public void setBoardVo(BoardVo boardVo) {
		this.boardVo = boardVo;
	}
	public UserVo getUserVo() {
		return userVo;
	}
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}
}
