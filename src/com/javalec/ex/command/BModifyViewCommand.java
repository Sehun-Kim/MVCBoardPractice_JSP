package com.javalec.ex.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.ex.dao.BDao;
import com.javalec.ex.dto.BDto;

public class BModifyViewCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BModifyViewCommand");
		
		String bId = request.getParameter("bId"); // bId값
		BDao dao = new BDao();
		BDto dto = dao.contentFind(bId);
		
		request.setAttribute("modify_content", dto);
	}

}
