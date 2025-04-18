package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Cash;
import dto.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CashDao;
import model.CategoryDao;

@WebServlet("/insertCash")
public class AddCashController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// requset 값 받기
		// 날짜 형태
		String targetDate = request.getParameter("date"); // 2025-00-00
		
		// 수입/지출
		String kind = "수입";
		if(request.getParameter("kind") != null){
			kind = request.getParameter("kind");
		}
		
		int categoryNo = 0;
		
		if(request.getParameter("categoryNo") != null){
			categoryNo = Integer.parseInt(request.getParameter("categoryNo"));
		}
		
		// 카테고리 Model
		CategoryDao ctDao = new CategoryDao();
		
		// 내가 만든 카테고리들을 가져옴 ex) [수입or지출] (내용)
		try {
			ArrayList<Category> ctList = ctDao.selectCategoryValue(kind);
			request.setAttribute("categoryNo",categoryNo);
			request.setAttribute("targetDate",targetDate);
			request.setAttribute("kind",kind);
			request.setAttribute("ctList",ctList);
			
			request.getRequestDispatcher("/WEB-INF/view/insertCash.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 입력액션 - redirect
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//requset 값 받기
		String cashDate = request.getParameter("cashDate");
		String kind = request.getParameter("kind");
		
		// 임시
		int category = Integer.parseInt(request.getParameter("categoryNo"));
		int amount = Integer.parseInt(request.getParameter("amount"));
		String memo = request.getParameter("memo");
		String color = request.getParameter("color");
		
		Cash cs = new Cash();
		cs.setCashDate(cashDate);
		cs.setKind(kind);
		cs.setCategoryNo(category);
		cs.setAmount(amount);
		cs.setMemo(memo);
		cs.setColor(color);
		
		CashDao cashDao = new CashDao();
		
		// 캐쉬 모델에서 insert 쿼리 실행
		try {
			cashDao.insertCash(cs);
			
			response.sendRedirect(request.getContextPath()+"/dateList?date="+cashDate);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
