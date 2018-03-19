package com.javalec.ex.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LLogoutOkCommand implements LCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("LLogoutOkCommand");
		HttpSession session = request.getSession();
		session.invalidate(); // session 객체내부 값들 모두 삭제
	}

}
