package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AdminDao;

@WebServlet("/updateAdminPw")
public class ModifyPwController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request 값 받기
		String admin = request.getParameter("admin");
		
		request.setAttribute("admin", admin);
		
		// 포워딩
		request.getRequestDispatcher("/WEB-INF/view/updateAdminPw.jsp").forward(request, response);
	}
	
	// 입력액션 - redirect
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// requset 값 받기
		String admin = request.getParameter("admin");
		String pw = request.getParameter("pw");
		String newPw = request.getParameter("newPw");

		// adminDao model
		AdminDao adminDao = new AdminDao();
		
		// admin.updatePw(계정, 기존 비밀번호, 새로운 비밀번호) -> update 쿼리를 실행해서 성공 유무를 반환 함
		if(adminDao.updatePw(admin, pw, newPw)){	// 수정 성공
			
			// 로그인 폼으로
			response.sendRedirect(request.getContextPath()+"/logout");
		}
		else{	// 수정 실패
			
			// 다시 수정 페이지로
			response.sendRedirect(request.getContextPath()+"/updateAdminPw?admin="+admin);
		}
	}
	
}
