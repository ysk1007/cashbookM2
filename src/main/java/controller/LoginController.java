package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.AdminDao;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 요청 분석
	    String error = "";
	    if(request.getParameter("error") != null){	// 로그인 실패시 파람 값으로 error 메시지가 들어옴
	    	error = request.getParameter("error");
	    }
	    request.setAttribute("error", error);
		// 포워딩
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
	}
	
	// 입력액션 - redirect
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request 값 받기
		String id = request.getParameter("id");
		String pw = request.getParameter("password");
		
		// admin DAO
		AdminDao adminDao = new AdminDao();
		
		// adminDao.login은 로그인 성공 유무에 따라 boolean 값 반환
		if(adminDao.login(id, pw)){	// 로그인 성공
			//request.setAttribute("admin", id);	// 아이디 저장
			HttpSession session = request.getSession();
			session.setAttribute("admin", id);	// 세션에 아이디 저장
			response.sendRedirect(request.getContextPath()+"/categoryList");
		}
		else{	// 로그인 실패
			response.sendRedirect(request.getContextPath()+"/login?error=false");
		}
	}
	
}
