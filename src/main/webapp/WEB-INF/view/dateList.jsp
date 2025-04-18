<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="dto.*" %>
<%@ page import="model.*" %>
<%@ include file="/inc/nav.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<%
	// request 값 받기
    String targetDate = (String)request.getAttribute("date");

    int year = (Integer)request.getAttribute("year");
    int month = (Integer)request.getAttribute("month");
    int day = (Integer)request.getAttribute("day");
    
    int totalIncome = (Integer)request.getAttribute("totalIncome");
    int totalExpense = (Integer)request.getAttribute("totalExpense");
    
    // 타겟날의 거래 리스트 출력
    ArrayList<Cash> cashList = (ArrayList<Cash>)request.getAttribute("cashList");
    
 	// 영수증 모델에서 해당하는 캐시 번호로 접근해서 영수증 데이터 가져옴
   	Receit re = (Receit)request.getAttribute("re");
	%>
    <meta charset="UTF-8">
    <title><%=targetDate%> 내역</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .date-title {
            font-size: 2rem;
            font-weight: 700;
            color: #0d6efd;
        }
        .btn-action {
            min-width: 140px;
        }
        .summary-box {
            background-color: #f8f9fa;
            padding: 1.2rem;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.05);
        }
        .kind-income {
            color: #198754; /* Bootstrap success */
            font-weight: bold;
        }
        .kind-expense {
            color: #dc3545; /* Bootstrap danger */
            font-weight: bold;
        }
        .table-hover tbody tr:hover {
            background-color: #f1f1f1;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container py-5">
        <div class="d-flex flex-wrap justify-content-between align-items-center mb-4 gap-3">
            <div class="date-title"><%=year%>.<%=month+1%>.<%=day%> 내역</div>
            <div class="d-flex gap-2">
                <a href="insertCash?date=<%=targetDate%>" class="btn btn-outline-primary btn-action">내역 추가</a>
                <a href="monthList?year=<%=year%>&month=<%=month%>" class="btn btn-outline-secondary btn-action">달력으로 돌아가기</a>
            </div>
        </div>

        <div class="table-responsive">
            <table class="table table-bordered table-hover bg-white shadow-sm text-center align-middle">
                <thead class="table-primary">
                    <tr>
                        <th>구분</th>
                        <th>카테고리</th>
                        <th>금액</th>
                        <th>작성일</th>
                        <th>수정</th>
                        <th>삭제</th>
                        <th>상세보기</th>
                    </tr>
                </thead>
                <tbody>
                <%
                	for(Cash c : cashList){
                		%>
                		<tr>
                        <td class="<%=c.getKind().equals("수입") ? "kind-income" : "kind-expense"%>"><%=c.getKind()%></td>
                        <!-- [타이틀] + [영수증 있으면 아이콘] -->
                        <td><%=c.getTitle()%><%=re.getFileName() != null ? "🧾":""%></td>
                        <td><%=String.format("%,d원", c.getAmount())%></td>
                        <td><%=c.getCreateDate()%></td>
                        <td><a href="updateCash?cashNo=<%=c.getCashNo()%>" class="btn btn-sm btn-outline-secondary">수정</a></td>
                        <td><a href="deleteCash?cashNo=<%=c.getCashNo()%>&date=<%=targetDate%>" class="btn btn-sm btn-outline-danger">삭제</a></td>
                        <td><a href="cashOne?cashNo=<%=c.getCashNo()%>" class="btn btn-sm btn-outline-info">보기</a></td>
                    </tr>
                		<%
                	}
                %>
                </tbody>
            </table>
        </div>

        <div class="summary-box mt-4">
            <div class="row text-center">
                <div class="col-md-4 mb-2">
                    <h5 class="text-success">총 수입</h5>
                    <p class="fs-5 fw-bold"><%=String.format("%,d원", totalIncome)%></p>
                </div>
                <div class="col-md-4 mb-2">
                    <h5 class="text-danger">총 지출</h5>
                    <p class="fs-5 fw-bold"><%=String.format("%,d원", totalExpense)%></p>
                </div>
                <div class="col-md-4 mb-2">
                    <h5 class="text-dark">잔액</h5>
                    <p class="fs-5 fw-bold"><%=String.format("%,d원", totalIncome - totalExpense)%></p>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
