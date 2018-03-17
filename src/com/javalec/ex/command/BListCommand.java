package com.javalec.ex.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.ex.dao.BDao;
import com.javalec.ex.dto.BDto;

public class BListCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BListCommand");
		
		BDao dao = new BDao(); // BDao 객체 선언
		ArrayList<BDto> dtoList = dao.list(); // BDao 객체의 list 메소드를 통해 반환받은 BDto 타입의 arraylist를 선언하고 초기화 해줌
		request.setAttribute("list", dtoList); // request 객체에 list라는 이름으로 dtoList를 셋해줌
	}

}
