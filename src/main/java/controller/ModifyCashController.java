package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dto.Cash;
import dto.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CashDao;
import model.CategoryDao;

@WebServlet("/updateCash")
public class ModifyCashController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// requset 값 받기
		int cashNo = 0;
		if(request.getParameter("cashNo") != null){
			cashNo = Integer.parseInt(request.getParameter("cashNo"));
		}

		// Cash, Category Model
		CashDao cashDao = new CashDao();
		CategoryDao ctDao = new CategoryDao();

		// 캐시 번호 접근해서 cash 데이터 하나 가져오기
		HashMap<String, Object> map;
		try {
			map = cashDao.selectCashOne(cashNo);

			// 가져온 cash에서 수입/지출 값 받기
			String kind = map.get("kind").toString();
			
			// 만약 페이지가 수정되어서 수입/지출 값이 바뀌었다면 request로 받기
			if(request.getParameter("kind") != null){
				kind = request.getParameter("kind");
			}

			// 가져온 cash에서 카테고리 값 받기
			int categoryNo = Integer.parseInt(map.get("categoryNo").toString());
			
			// 만약 페이지가 수정되어서 카테고리 값이 바뀌었다면 request로 받기
			if(request.getParameter("categoryNo") != null){
				categoryNo = Integer.parseInt(request.getParameter("categoryNo"));
			}

			// 가져온 cash에서 생성일 받기
			String targetDate = map.get("cashDate").toString();

			// 카테고리 모델에서 수입/지출들의 카테고리 리스트
			ArrayList<Category> ctList = ctDao.selectCategoryValue(kind);
			
			request.setAttribute("cashNo", cashNo);
			request.setAttribute("map", map);
			request.setAttribute("kind", kind);
			request.setAttribute("categoryNo", categoryNo);
			request.setAttribute("targetDate", targetDate);
			request.setAttribute("ctList", ctList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 포워딩
		request.getRequestDispatcher("/WEB-INF/view/updateCash.jsp").forward(request, response);
	}
	
	// 입력액션 - redirect
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//requset 값 받기
		String cashDate = request.getParameter("cashDate");
		String kind = request.getParameter("kind");
		
		// 임시
		int category = Integer.parseInt(request.getParameter("categoryNo"));
		int cashNo = Integer.parseInt(request.getParameter("cashNo"));
		int amount = Integer.parseInt(request.getParameter("amount"));
		String memo = request.getParameter("memo");
		String color = request.getParameter("color");
		
		Cash cs = new Cash();
		cs.setCashDate(cashDate);
		cs.setKind(kind);
		cs.setCategoryNo(category);
		cs.setCashNo(cashNo);
		cs.setAmount(amount);
		cs.setMemo(memo);
		cs.setColor(color);
		
		CashDao cashDao = new CashDao();
		
		// 캐시 model에서 update 쿼리 호출
		try {
			cashDao.updateCash(cs);

			response.sendRedirect(request.getContextPath()+"/dateList?date="+cashDate);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
