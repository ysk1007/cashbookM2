package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import dto.Category;
import dto.Paging;
import dto.Receit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CashDao;
import model.CategoryDao;
import model.ReceitDao;

@WebServlet("/cashOne")
public class CashOneController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// requset 받기
	    int cashNo = Integer.parseInt(request.getParameter("cashNo"));

		// Cash 모델에서 캐시 데이터 하나 가져옴
	    CashDao cashDao = new CashDao();
	    HashMap<String, Object> map;
		try {
			map = cashDao.selectCashOne(cashNo);
			// Receit 모델에서 캐시에 영수증 데이터 가져옴
		    ReceitDao reDao = new ReceitDao();
		    Receit re = reDao.selectReceitOne(cashNo);

		    // 수입/지출 텍스트 Color
		    String kind = map.get("kind").toString();
		    String kindColor = kind.equals("수입") ? "text-success" : "text-danger";
		    
		    request.setAttribute("cashNo", cashNo);
		    request.setAttribute("map", map);
		    request.setAttribute("re", re);
		    request.setAttribute("kind", kind);
		    request.setAttribute("kindColor", kindColor);
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // 포워딩
		request.getRequestDispatcher("/WEB-INF/view/cashOne.jsp").forward(request, response);
	}
	
	// 입력액션 - redirect
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 페이징 변수
	    int currentPage = 1;
	    int rowPerPage = 8;
	    int lastPage = 0;

	    String kind = request.getParameter("kind");
	    String title = request.getParameter("title");

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
	
}
