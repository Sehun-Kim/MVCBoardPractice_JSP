package com.javalec.ex.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.ex.dao.MDao;
import com.javalec.ex.dto.MDto;

public class LLoginOkCommand implements LCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("LLoginOkCommand");
		
		HttpSession session = request.getSession();
	
		String id = request.getParameter("id"); // form을 통해 넘어온 id
		String pw = request.getParameter("pw"); // form을 통해 넘어온 pw
		
		MDao dao = MDao.getInstance(); // login할때 db로 접근하기 위한 MDao 객체 접근(이미 static으로 생성되어 있는 객체를 가져오는 것)
		int checkNum = dao.userCheck(id, pw); // id와 비밀번호로 검증
		if(checkNum == -1){ // id가 없을 경우
			System.out.println("login 실패 : id가 없음");
			request.setAttribute("alert", -1 );
		}else if(checkNum == 0){ // pw가 틀린경우 
			System.out.println("login 실패 : pw가 다름");
			request.setAttribute("alert", 0 );
		}else if(checkNum == 1){ // id와 pw 모두 일치할 때
			MDto dto = dao.getMember(id); // id값과 일치하는 dto객체 생성
			if(dto == null){ // 존재하지 않는 id일 경우
				System.out.println("존재하지 않는 회원입니다.");
				request.setAttribute("alert", -1 );
	 		}else{ // id가 존재할 경우
	 			request.setAttribute("alert", 2);
				String name = dto.getName(); // dto 객체에서 이름을 가져옴
				session.setAttribute("id", id); // session 객체에 id 값 저장
				session.setAttribute("name", name); // session 객체에 name값 저장
				session.setAttribute("ValidMember", "yes"); // session 객체에 ValidMember 속성에 yes값 저장
			}
		}
	}

}
