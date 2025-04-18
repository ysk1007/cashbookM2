package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CashDao;
import model.ReceitDao;

@WebServlet("/deleteCash")
public class RemoveCashController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request 값 받기
		int cashNo = Integer.parseInt(request.getParameter("cashNo"));
		String date = request.getParameter("date");

		CashDao csDao = new CashDao();
		
		ReceitDao reDao = new ReceitDao();
		
		// 영수증 먼저 지우기
		try {
			reDao.deleteReceit(cashNo);
			// csDao.deleteCash(번호) -> delete 쿼리를 실행해서 성공 유무를 반환 함
			if(csDao.deleteCash(cashNo)){	// 삭제 성공
				
				// 리스트 페이지로
				response.sendRedirect(request.getContextPath()+"/dateList?date="+date);
			}
			else{	// 삭제 실패
				response.sendRedirect(request.getContextPath()+"/dateList?date="+date);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
