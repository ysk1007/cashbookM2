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

@WebServlet("/deleteCategory")
public class RemoveCategoryController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request 값 받기
		int num = Integer.parseInt(request.getParameter("num"));

		CategoryDao ctDao = new CategoryDao();
		
		// ctDao.deleteCategory(번호) -> delete 쿼리를 실행해서 성공 유무를 반환 함
		try {
			if(ctDao.deleteCategory(num)){	// 삭제 성공
				
				// 리스트 페이지로
				response.sendRedirect(request.getContextPath()+"/categoryList");
			}
			else{	// 삭제 실패
				
				// 일단 리스트 페이지로
				response.sendRedirect(request.getContextPath()+"/categoryList");
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
