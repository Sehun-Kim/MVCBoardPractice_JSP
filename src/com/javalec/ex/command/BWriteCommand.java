package com.javalec.ex.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.ex.dao.BDao;

public class BWriteCommand implements BCommand {

	@Override
	// 글 작성을 위한 command execute
	public void execute(HttpServletRequest request, HttpServletResponse response){
		System.out.println("BWriteCommand");
		
		String bName = request.getParameter("bName"); // request 객체에서 bName의 값을 얻어옴
		String bTitle = request.getParameter("bTitle"); // request 객체에서 bTitle의 값을 얻어옴
		String bContent = request.getParameter("bContent");
		
		BDao dao = new BDao(); // BDao 객체 생성 
		dao.write(bName, bTitle, bContent); // 이름, 제목, 내용을 DB에 넣기 위해 BDao 객체의 write 메소드 실행
	}
}
