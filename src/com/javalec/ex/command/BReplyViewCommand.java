package com.javalec.ex.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.ex.dao.BDao;
import com.javalec.ex.dto.BDto;

public class BReplyViewCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BReplyViewCommand");
		
		String bId = request.getParameter("bId");
		BDao dao = new BDao();
		BDto dto = dao.contentFind(bId);
		
		request.setAttribute("reply_content", dto);
	}

}
