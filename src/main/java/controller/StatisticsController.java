package controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import dto.Cash;
import dto.Receit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CashDao;
import model.ReceitDao;

@WebServlet("/statistics")
public class StatisticsController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int year = Calendar.getInstance().get(Calendar.YEAR);
	    if(request.getParameter("year") != null){
	        year = Integer.parseInt(request.getParameter("year"));
	    }

	    CashDao csDao = new CashDao();

	    // 전체 통계
	    try {
			ArrayList<HashMap<String,Object>> allStats = csDao.selectAllAmount();

		    // 연도 통계
		    ArrayList<HashMap<String,Object>> yearStats = csDao.selectYearAmount(year);

		    // 월 통계
		    ArrayList<HashMap<String,Object>> monthStats = csDao.selectMonthAmount(year);
		    ArrayList<HashMap<String,Object>> monthStatsIncome = new ArrayList<>();
		    ArrayList<HashMap<String,Object>> monthStatsExpense = new ArrayList<>();
		    for (HashMap<String,Object> map : monthStats) {
		        if ("수입".equals(map.get("kind"))) {
		            monthStatsIncome.add(map);
		        } else {
		            monthStatsExpense.add(map);
		        }
		    }

		    // 카테고리 통계
		    ArrayList<HashMap<String,Object>> ctStats = csDao.selectCategoryAmount(year);
		    ArrayList<HashMap<String,Object>> ctStatsIncome = new ArrayList<>();
		    ArrayList<HashMap<String,Object>> ctStatsExpense = new ArrayList<>();
		    for (HashMap<String,Object> map : ctStats) {
		        if ("수입".equals(map.get("kind"))) {
		            ctStatsIncome.add(map);
		        } else {
		            ctStatsExpense.add(map);
		        }
		    }

		    // 숫자 포맷
		    NumberFormat nf = NumberFormat.getInstance();

		    // 월별 수입 데이터 처리
		    int[] incomeData = new int[12];
		    for (int i = 0; i < monthStatsIncome.size(); i++) {
		        HashMap<String, Object> map = monthStatsIncome.get(i);
		        int month = (Integer) map.get("month");
		        int amount = (Integer) map.get("amount");
		        incomeData[month - 1] = amount;
		    }
		    
		    int[] expenseData = new int[12];  // 12개월
			for (int i = 0; i < monthStatsExpense.size(); i++) {
			    HashMap<String, Object> map = monthStatsExpense.get(i);
			    int month = (Integer) map.get("month");
			    int amount = (Integer) map.get("amount");
			    expenseData[month - 1] = amount;
			}
			
			request.setAttribute("year", year);
			
			request.setAttribute("allStats", allStats);
			request.setAttribute("yearStats", yearStats);
			request.setAttribute("monthStats", monthStats);
			request.setAttribute("monthStatsIncome", monthStatsIncome);
			request.setAttribute("monthStatsExpense", monthStatsExpense);
			request.setAttribute("ctStats", ctStats);
			request.setAttribute("ctStatsIncome", ctStatsIncome);
			request.setAttribute("ctStatsExpense", ctStatsExpense);
			request.setAttribute("nf", nf);
			request.setAttribute("incomeData", incomeData);
			request.setAttribute("expenseData", expenseData);
			
			request.getRequestDispatcher("/WEB-INF/view/statistics.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
