package com.javalec.ex.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalec.ex.command.BCommand;
import com.javalec.ex.command.BContentCommand;
import com.javalec.ex.command.BDeleteCommand;
import com.javalec.ex.command.BListCommand;
import com.javalec.ex.command.BModifyCommand;
import com.javalec.ex.command.BModifyViewCommand;
import com.javalec.ex.command.BReplyCommand;
import com.javalec.ex.command.BReplyViewCommand;
import com.javalec.ex.command.BWriteCommand;

/**
 * Servlet implementation class BFrontController
 */

@WebServlet("*.do") // .do 확장자가 이거면 무조건 BFrontController로 들어옴
public class BFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public BFrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		actionDo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		actionDo(request, response);
	}
	
	protected void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("actionDo");
		
		request.setCharacterEncoding("UTF-8");
		
		String viewPage = null; // 어떤 뷰로 갈지 
		BCommand command = null; // 어떤 커멘드를 실행할지
		
		String uri = request.getRequestURI(); // request에 넘어온 uri
		String contextPath = request.getContextPath(); // request를 보낸 Context Path ex) /mvcWebBoard
		String commandPath = uri.substring(contextPath.length()); // 컨텍스트 패스를 제외한 실제 uri
		
		if(commandPath.equals("/write_view.do")){
			viewPage = "write_view.jsp";
		}else if(commandPath.equals("/write.do")){ // write uri가 호출 되었을 경우
			command = new BWriteCommand(); // BWriteCommand 생성
			command.execute(request, response); // BWriteCommand의 execute 메소드 호출
			viewPage = "list.do"; // list.do command를 실행하기 위해 셋팅
		}else if(commandPath.equals("/list.do")){ // list uri가 호출 되었을 경우
			command = new BListCommand();
			command.execute(request, response);
			viewPage = "list.jsp"; // list.jsp 로 이동
		}else if(commandPath.equals("/content_view.do")){ // content_view uri가 호출 되었을 경우
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "content_view.jsp";
		}else if(commandPath.equals("/modify_form.do")){ // modify_form.do가 호출 되었을 경우
			command = new BModifyViewCommand();
			command.execute(request, response); // 수정할 row를 찾아서 request 객체에 담아줌
			viewPage = "modify_form.jsp"; // 수정후 list uri를 호출하는데, 추후 수정한 콘텐츠로 이동하도록 수정할 것
		}else if(commandPath.equals("/modify.do")){ // modify.do가 호출 되었을 경우
			command = new BModifyCommand();
			command.execute(request, response);
			viewPage = "content_view.do?bId=" + request.getParameter("bId"); // 수정후 수정한 콘텐츠로 이동
		}else if(commandPath.equals("/delete.do")){ // delete.do가 호출 되었을 경우
			command = new BDeleteCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}else if(commandPath.equals("/reply_view.do")){ // reply_view로 이동. 답변을 입력하는 form
			command = new BReplyViewCommand();
			command.execute(request, response);
			viewPage = "reply_view.jsp";
		}else if(commandPath.equals("/reply.do")){ // 딥변 입력을 마칠 경우 이동
			command = new BReplyCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPage); // request를 포워딩 해줄 RequestDispatcher 선언
		requestDispatcher.forward(request, response); // 입력된 viewPage로 포워딩
		
	}

}
