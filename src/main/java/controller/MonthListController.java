package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import dto.Cash;
import dto.Category;
import dto.Paging;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CashDao;
import model.CategoryDao;

@WebServlet("/monthList")
public class MonthListController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 달력 출력용 변수
		int lastDate, dayOfWeek, startBlank, endBlank, totalCell;

		// 캘린더 api
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.DATE, 1);	// 캘린더 오늘 날짜 1일로 설정
		
		if(request.getParameter("year") != null){
			cal.set(Calendar.YEAR, Integer.parseInt(request.getParameter("year")));	// 타겟 year가 있으면 받은 값으로 설정
		}
		if(request.getParameter("month") != null){
			cal.set(Calendar.MONTH, Integer.parseInt(request.getParameter("month"))); // 타겟 month가 있으면 받은 값으로 설정
		}
		
		lastDate = cal.getActualMaximum(Calendar.DATE);	// 그 달의 마지막 날짜를 가져옵니다.
		dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);		// 이번달의 1일의 요일을 가져옵니다. 
		startBlank = dayOfWeek - 1;						// 달력 초반부 공백 칸 수를 계산 합니다.
		endBlank = 0;
		totalCell = startBlank + lastDate;				// 전체 칸은 시작 공백 + 마지막 날짜
		
		if(totalCell % 7 != 0){							// 만약 전체 칸이 7로 나누어 떨어지지 않으면
			endBlank = 7 - (totalCell % 7);				// 마지막 공백칸이 있음
			totalCell += endBlank;						// 최종 전체칸 = 시작 공백 + 마지막 날짜 + 끝 공백
		}
		
		int year = cal.get(Calendar.YEAR);				// year 값만 따로 저장
		int month = cal.get(Calendar.MONTH);			// month 값만 따로 저장

		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("lastDate", lastDate);
		request.setAttribute("dayOfWeek", dayOfWeek);
		request.setAttribute("startBlank", startBlank);
		request.setAttribute("endBlank", endBlank);
		request.setAttribute("totalCell", totalCell);
		
		CashDao cashDao = new CashDao();
		try {
			Map<String, ArrayList<Cash>> cashMap = cashDao.selectMonthList(year, month + 1);
			ArrayList<HashMap<String,Object>> totalMap = cashDao.selectMonthAmount(year, month + 1);
			
			request.setAttribute("totalMap", totalMap);
			request.setAttribute("cashMap", cashMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		request.getRequestDispatcher("/WEB-INF/view/monthList.jsp").forward(request, response);
	}
	
	// 입력액션 - redirect
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 달력 출력용 변수
		int lastDate, dayOfWeek, startBlank, endBlank, totalCell;

		// 캘린더 api
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.DATE, 1);	// 캘린더 오늘 날짜 1일로 설정
		cal.set(Calendar.YEAR, Integer.parseInt(request.getParameter("year")));	// 타겟 year가 있으면 받은 값으로 설정
		cal.set(Calendar.MONTH, Integer.parseInt(request.getParameter("month"))); // 타겟 month가 있으면 받은 값으로 설정

		lastDate = cal.getActualMaximum(Calendar.DATE);	// 그 달의 마지막 날짜를 가져옵니다.
		dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);		// 이번달의 1일의 요일을 가져옵니다. 
		startBlank = dayOfWeek - 1;						// 달력 초반부 공백 칸 수를 계산 합니다.
		endBlank = 0;
		totalCell = startBlank + lastDate;				// 전체 칸은 시작 공백 + 마지막 날짜
		
		if(totalCell % 7 != 0){							// 만약 전체 칸이 7로 나누어 떨어지지 않으면
			endBlank = 7 - (totalCell % 7);				// 마지막 공백칸이 있음
			totalCell += endBlank;						// 최종 전체칸 = 시작 공백 + 마지막 날짜 + 끝 공백
		}
		
		int year = cal.get(Calendar.YEAR);				// year 값만 따로 저장
		int month = cal.get(Calendar.MONTH);			// month 값만 따로 저장

		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("lastDate", lastDate);
		request.setAttribute("dayOfWeek", dayOfWeek);
		request.setAttribute("startBlank", startBlank);
		request.setAttribute("endBlank", endBlank);
		request.setAttribute("totalCell", totalCell);
		
		CashDao cashDao = new CashDao();
		try {
			Map<String, ArrayList<Cash>> cashMap = cashDao.selectMonthList(year, month + 1);
			ArrayList<HashMap<String,Object>> totalMap = cashDao.selectMonthAmount(year, month + 1);
			
			request.setAttribute("totalMap", totalMap);
			request.setAttribute("cashMap", cashMap);
			
			request.getRequestDispatcher("/WEB-INF/view/categoryList.jsp").forward(request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
