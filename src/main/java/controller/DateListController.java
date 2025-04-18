package controller;

import java.io.IOException;
import java.util.ArrayList;

import dto.Cash;
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

@WebServlet("/dateList")
public class DateListController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request 값 받기
	    String targetDate = request.getParameter("date");

		// 년도, 월, 일 쪼개서 데이터 저장함
	    int year = Integer.parseInt(targetDate.substring(0,4));
	    int month = Integer.parseInt(targetDate.substring(5,7)) - 1;
	    int day = Integer.parseInt(targetDate.substring(8,10));

	    // Cash와 Receit 모델
	    CashDao cashDao = new CashDao();
	    ReceitDao reDao = new ReceitDao();
	    
	    int totalIncome = 0;	// 총 수입
        int totalExpense = 0;	// 총 지출
	    
	    // 타겟날의 거래 리스트 출력
	    try {
			ArrayList<Cash> cashList = cashDao.selectCashList(targetDate);
			
			for(Cash c : cashList){
	        	
	        if(c.getKind().equals("수입")){		// kind에 따라 수입 지출액 +
	        	totalIncome += c.getAmount();
	        }
	        
	        else{
	        	totalExpense += c.getAmount();
	        }
	        
	        // 영수증 모델에서 해당하는 캐시 번호로 접근해서 영수증 데이터 가져옴
	       	Receit re = reDao.selectReceitOne(c.getCashNo());
	       	request.setAttribute("re", re);
			}
			
			request.setAttribute("date", targetDate);
	       	request.setAttribute("year", year);
	       	request.setAttribute("month", month);
	       	request.setAttribute("day", day);
	       	request.setAttribute("totalIncome", totalIncome);
	       	request.setAttribute("totalExpense", totalExpense);
	       	request.setAttribute("cashList", cashList);
	       	
			request.getRequestDispatcher("/WEB-INF/view/dateList.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 입력액션 - redirect
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request 값 받기
	    String targetDate = request.getParameter("date");

		// 년도, 월, 일 쪼개서 데이터 저장함
	    int year = Integer.parseInt(targetDate.substring(0,4));
	    int month = Integer.parseInt(targetDate.substring(5,7)) - 1;
	    int day = Integer.parseInt(targetDate.substring(8,10));

	    // Cash와 Receit 모델
	    CashDao cashDao = new CashDao();
	    ReceitDao reDao = new ReceitDao();
	    
	    int totalIncome = 0;	// 총 수입
        int totalExpense = 0;	// 총 지출
	    
	    // 타겟날의 거래 리스트 출력
	    try {
			ArrayList<Cash> cashList = cashDao.selectCashList(targetDate);
			
			for(Cash c : cashList){
	        	
	        if(c.getKind().equals("수입")){		// kind에 따라 수입 지출액 +
	        	totalIncome += c.getAmount();
	        }
	        
	        else{
	        	totalExpense += c.getAmount();
	        }
	        
	        // 영수증 모델에서 해당하는 캐시 번호로 접근해서 영수증 데이터 가져옴
	       	Receit re = reDao.selectReceitOne(c.getCashNo());
	       	request.setAttribute("re", re);
			}
			
			request.setAttribute("date", targetDate);
	       	request.setAttribute("year", year);
	       	request.setAttribute("month", month);
	       	request.setAttribute("day", day);
	       	request.setAttribute("totalIncome", totalIncome);
	       	request.setAttribute("totalExpense", totalExpense);
	       	request.setAttribute("cashList", cashList);
	       	
			request.getRequestDispatcher("/WEB-INF/view/dateList.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
