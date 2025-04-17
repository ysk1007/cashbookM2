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

@WebServlet("/categoryList")
public class CategoryListController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 페이징 변수
	    int currentPage = 1;
	    int rowPerPage = 8;
	    int lastPage = 0;

	    String kind = "";
	    String title = "";

	    // request 값 받기
	    if(request.getParameter("currentPage") != null){
	        currentPage = Integer.parseInt(request.getParameter("currentPage"));
	    }

	    if(request.getParameter("kind") != null){
	        kind = request.getParameter("kind");
	    }

	    if(request.getParameter("title") != null){
	        title = request.getParameter("title");
	    }

	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("kind", kind);
	    request.setAttribute("title", title);
	    
	    // 페이징 Class
	    Paging p = new Paging();
	    
	    // 페이징 옵션으로 현재 페이지와 한 페이지에 보여줄 데이터 수 변수로 넣기
	    p.setCurrentPage(currentPage);
	    p.setRowPerPage(rowPerPage);

	    // 카테고리 모델
	    CategoryDao ctDao = new CategoryDao();
	    
	    // 카테고리 리스트 데이터 가져와서 출력
	    try {
			ArrayList<Category> ctList = ctDao.selectCategoryList(p, kind, title);
			// 전체 페이지와 마지막 페이지 가져오기
		    int totalRow = ctDao.selectListRow(kind, title);
		    lastPage = p.getLastPage(totalRow);
		    
		    request.setAttribute("lastPage", lastPage);
		    request.setAttribute("ctList", ctList);
		    // 포워딩
			request.getRequestDispatcher("/WEB-INF/view/categoryList.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 입력액션 - redirect
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 페이징 변수
	    int currentPage = 1;
	    int rowPerPage = 8;
	    int lastPage = 0;

	    String kind = "";
	    String title = "";

	    // request 값 받기
	    if(request.getParameter("currentPage") != null){
	        currentPage = Integer.parseInt(request.getParameter("currentPage"));
	    }

	    if(request.getParameter("kind") != null){
	        kind = request.getParameter("kind");
	    }

	    if(request.getParameter("title") != null){
	        title = request.getParameter("title");
	    }

	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("kind", kind);
	    request.setAttribute("title", title);
	    
	    // 페이징 Class
	    Paging p = new Paging();
	    
	    // 페이징 옵션으로 현재 페이지와 한 페이지에 보여줄 데이터 수 변수로 넣기
	    p.setCurrentPage(currentPage);
	    p.setRowPerPage(rowPerPage);

	    // 카테고리 모델
	    CategoryDao ctDao = new CategoryDao();
	    
	    // 카테고리 리스트 데이터 가져와서 출력
	    try {
			ArrayList<Category> ctList = ctDao.selectCategoryList(p, kind, title);
			// 전체 페이지와 마지막 페이지 가져오기
		    int totalRow = ctDao.selectListRow(kind, title);
		    lastPage = p.getLastPage(totalRow);
		    
		    request.setAttribute("lastPage", lastPage);
		    request.setAttribute("ctList", ctList);
		    // response
		    response.sendRedirect(request.getContextPath()+"/categoryList");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
