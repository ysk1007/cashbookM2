package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ReceitDao;

@WebServlet("/deleteReceit")
public class RemoveReceitController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int cashNo = Integer.parseInt(request.getParameter("cashNo"));
		String fileName = request.getParameter("fileName");

		// db 삭제
		ReceitDao reDao = new ReceitDao();
		
		// Receit 모델에서 delete 쿼리 실행
		try {
			reDao.deleteReceit(cashNo);
			// 파일 삭제
			
			// 톰켓안에 poll 프로젝트안 upload폴더 실제 물리적 주소를 반환
			String path = request.getServletContext().getRealPath("upload");
			File file = new File(path, fileName);	// new File 경로에 파일이 없으면 빈파일을 생성을 준비
			
			if(file.exists()){	// 빈파일이 아니라면
				file.delete();			
			}

			response.sendRedirect(request.getContextPath()+"/cashOne?cashNo="+cashNo);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
}
