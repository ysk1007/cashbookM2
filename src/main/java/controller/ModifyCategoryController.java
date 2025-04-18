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

@WebServlet("/updateCategory")
public class ModifyCategoryController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 요청 파라미터 값 받아오기
	    int num = Integer.parseInt(request.getParameter("num"));

	    // 카테고리 정보 가져오기
	    CategoryDao ctDao = new CategoryDao();
	    try {
			Category ct = ctDao.selectCategoryOne(num);
			
			request.setAttribute("num", num);
			request.setAttribute("ct", ct);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 포워딩
		request.getRequestDispatcher("/WEB-INF/view/updateCategory.jsp").forward(request, response);
	}
	
	// 입력액션 - redirect
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// requset 값 받기
		int num = Integer.parseInt(request.getParameter("num"));
		String kind = request.getParameter("kind");
		String title = request.getParameter("title");
		
		// Category Model
		Category ct = new Category();
		ct.setCategoryNo(num);
		ct.setKind(kind);
		ct.setTitle(title);
		
		CategoryDao ctDao = new CategoryDao();
		
		// ctDao.updateCategory(카테고리) -> update 쿼리를 실행해서 성공 유무를 반환 함
		try {
			if(ctDao.updateCategory(ct)){	// 수정 성공
				
				// 리스트로
				response.sendRedirect(request.getContextPath()+"/categoryList");
			}
			else{	// 수정 실패
				
				// 다시 수정 페이지로
				response.sendRedirect(request.getContextPath()+"/updateCategory?num="+num);
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
