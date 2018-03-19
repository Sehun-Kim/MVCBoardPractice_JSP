package com.javalec.ex.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalec.ex.command.LCommand;
import com.javalec.ex.command.LJoinOkCommand;
import com.javalec.ex.command.LLoginOkCommand;
import com.javalec.ex.command.LLogoutOkCommand;
import com.javalec.ex.command.LModifyOkCommand;

/**
 * Servlet implementation class BMemberController
 */
@WebServlet("*.log") // .log 확장자로 끝나면 무조건 MFrontController로 옴
public class MFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MFrontController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MFrontController.doGet");
		actionDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MFrontController.doPost");
		actionDo(request, response);
	}
	
	protected void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MFrontController.actionDo");
		
		request.setCharacterEncoding("UTF-8");
		
		String viewPage = null; // 어떤 뷰로 갈지 
		LCommand command = null; // 어떤 커멘드를 실행할지
		
		String uri = request.getRequestURI(); // request에 넘어온 uri
		String contextPath = request.getContextPath(); // request를 보낸 Context Path ex) /mvcWebBoard
		String commandPath = uri.substring(contextPath.length()); // 컨텍스트 패스를 제외한 실제 uri
		
//		HttpSession session = request.getSession(); // request 객체로 부터 session을 얻어옴

		if(commandPath.equals("/login.log")){ 
			viewPage = "login.jsp";
		}else if(commandPath.equals("/loginOk.log")){
			command = new LLoginOkCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}else if(commandPath.equals("/logout.log")){
			command = new LLogoutOkCommand();
			command.execute(request, response);
			viewPage = "login.log";
		}else if(commandPath.equals("/joinForm.log")){
			viewPage = "joinForm.jsp";
		}else if(commandPath.equals("/joinOk.log")){
			command = new LJoinOkCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}else if(commandPath.equals("/modifyUser.log")){
			viewPage = "modifyUser.jsp";
		}else if(commandPath.equals("/modifyOk.log")){
			command = new LModifyOkCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}


		RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPage); // request를 포워딩 해줄 RequestDispatcher 선언
		requestDispatcher.forward(request, response); // 입력된 viewPage로 포워딩
		
	}

}
