package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.UUID;

import dto.Receit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ReceitDao;

@WebServlet("/insertReceit")
public class AddReceitController extends HttpServlet {
	
	// 입력폼 - View forward
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request 값 받기
	    int cashNo = Integer.parseInt(request.getParameter("cashNo"));
		
	    request.setAttribute("cashNo", cashNo);
	    request.getRequestDispatcher("/WEB-INF/view/insertReceit.jsp").forward(request, response);
	}
	
	// 입력액션 - redirect
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cashNo = 0;
		if(request.getParameter("cashNo") != null){
			cashNo = Integer.parseInt(request.getParameter("cashNo"));
		}

		Part part = request.getPart("receiptFile");	// 파일 받는 API
		String originalName = part.getSubmittedFileName();	// 이미지.png
		
		// 중복되지 않는 새로운 파일이름 생성 - java.util.UUID API 사용
		UUID uuid = UUID.randomUUID();
		String fileName = uuid.toString();
		
		// 하이픈 제거
		fileName = fileName.replace("-","");
		
		int dotLastPos = originalName.lastIndexOf(".");	// 마지막 .(확장자)의 인덱스 값 반환
		
		// 확장자
		String ext = originalName.substring(dotLastPos);
		
		// png 확장자가 아니라면 돌아가기
		if(!ext.equals(".png")){
			response.sendRedirect("/cashbook/Form/insertReceitForm.jsp?cashNo="+cashNo);
			return;
		}
		
		// 확장자 붙여주기
		fileName += ext;
		
		// Receit dto
		Receit re = new Receit();
		re.setCashNo(cashNo);
		re.setFileName(fileName);
		
		// 파일 저장하기
		
		// 톰켓안에 poll 프로젝트안 upload폴더 실제 물리적 주소를 반환
		String path = request.getServletContext().getRealPath("upload");
		
		// 빈파일 생성
		File emptyFile = new File(path,fileName);
		
		// 파일 보낼 inputstream 설정
		
		InputStream is = part.getInputStream();	// 파트안의 스트림(이미지파일의 바이너리 파일)
		// 파일을 받을 outputstream 설정
		OutputStream os = Files.newOutputStream(emptyFile.toPath());
		
		is.transferTo(os);	// inputstream binary -> 반복(1byte씩) -> outputstream
		
		os.close();
		
		// db 저장
		ReceitDao receitDao = new ReceitDao();
		try {
			receitDao.insertReceit(re);
			
			response.sendRedirect(request.getContextPath()+"/cashOne?cashNo="+cashNo);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
