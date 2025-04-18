package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Category;
import dto.Paging;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CategoryDao;

@WebServlet("/insertCategory")
public class AddCategoryController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 포워딩
		request.getRequestDispatcher("/WEB-INF/view/insertCategory.jsp").forward(request, response);
	}
	
	// 입력액션 - redirect
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// requset 값 받기
		String title = request.getParameter("title");
		String kind = request.getParameter("kind");
		
		Category ct = new Category();
		CategoryDao ctDao = new CategoryDao();
		
		ct.setTitle(title);
		ct.setKind(kind);
		
		// 카테고리 모델에서 insert 쿼리 실행
		try {
			ctDao.insertCategory(ct);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		response.sendRedirect(request.getContextPath()+"/categoryList");
	}
	
}
