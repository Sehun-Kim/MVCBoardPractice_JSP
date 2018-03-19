package com.javalec.ex.command;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.ex.dao.MDao;
import com.javalec.ex.dto.MDto;

public class LModifyOkCommand implements LCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("LModifyOkCommand");

		HttpSession session = request.getSession();
		
		String id = (String)session.getAttribute("id");
		String name = (String)session.getAttribute("name");
		
		MDto dto = new MDto(id, request.getParameter("pw"), name, request.getParameter("eMail"), new Timestamp(System.currentTimeMillis()), request.getParameter("address"));

		MDao dao = MDao.getInstance();
		int resultInt = dao.updateMember(dto);

		if(resultInt == 1) {
			System.out.println("modify : 정보수정성공");
			request.setAttribute("alert", 3);
		} else {
			System.out.println("modify : 정보수정실패");
			request.setAttribute("alert", 4);
		}

	}

}
