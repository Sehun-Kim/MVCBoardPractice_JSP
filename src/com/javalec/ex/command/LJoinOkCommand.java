package com.javalec.ex.command;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.ex.dao.MDao;
import com.javalec.ex.dto.MDto;

public class LJoinOkCommand implements LCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("LJoinOkCommand");
		
		HttpSession session = request.getSession();
		MDto dto = new MDto(request.getParameter("id"), request.getParameter("pw"), request.getParameter("name"), request.getParameter("eMail"), new Timestamp(System.currentTimeMillis()), request.getParameter("address"));
		MDao dao = MDao.getInstance(); // dao 객체 가져오기
		if(dao.confirmId(dto.getId()) == MDao.MEMBER_EXITENT){ // 해당 id가 존재한다면
			System.out.println("join 실패 : id가 존재합니다.");
			request.setAttribute("alert", 2 );
		}else{ // 해당 id가 존재하지 않는다면
			int resultInt = dao.insertMember(dto); // dto 객체를 DB에 넣어줌
			if(resultInt == MDao.MEMBER_JOIN_SUCCESS){ // db에 dao객체의 값들을 넣는데 성공했을 때
				System.out.println("join 성공");
				request.setAttribute("alert", 1 );
		
				session.setAttribute("id", dto.getId()); // session 객체에 id값을 넣어줌
				session.setAttribute("name",dto.getName()); // session 객체에 name값 저장
				session.setAttribute("ValidMember", "yes"); // session 객체에 ValidMember 속성에 yes값 저장
			}else{ // db에 dao객체의 값들을 넣는데 실패했을 때
				System.out.println("join 실패 : SYSTEM 에러 다시 시도해 주세요.");
				request.setAttribute("alert", 3 );
			}	
		}
	}

}
