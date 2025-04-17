<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인 | 가계부</title>
    <link href="/cashbook/vendor/fontawesome-free/css/all.min.css" rel="stylesheet">
    <link href="/cashbook/css/sb-admin-2.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to right, #4e73df, #224abe);
        }
        .card-login {
            max-width: 400px;
            margin: auto;
            margin-top: 100px;
            border-radius: 1rem;
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
            animation: fadeIn 0.6s ease-in-out;
        }
        .login-title {
            font-weight: 700;
            font-size: 1.8rem;
            color: #4e73df;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(30px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>

<%
	Object obj = request.getAttribute("error");
	String error = (String)obj;
%>

<div class="container">
    <div class="card card-login shadow-lg border-0">
        <div class="card-body p-5">
            <div class="text-center mb-4">
                <div class="login-title">💰 가계부</div>
                <h5 class="text-gray-900 mt-2">로그인</h5>
            </div>

            <% if(!error.equals("")) { %>
                <div class="alert alert-danger text-center" role="alert">
                    ❗ 로그인 실패: 아이디 또는 비밀번호를 확인해주세요.
                </div>
            <% } %>

            <form class="user" action="login" method="post">
                <div class="form-group">
                    <input type="text" name="id" class="form-control form-control-user" value="admin" readonly required>
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control form-control-user" placeholder="비밀번호 입력" required>
                </div>
                <button type="submit" class="btn btn-primary btn-user btn-block">
                    로그인
                </button>
            </form>
        </div>
    </div>
</div>

<!-- JS -->
<script src="/cashbook/vendor/jquery/jquery.min.js"></script>
<script src="/cashbook/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/cashbook/vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="/cashbook/js/sb-admin-2.min.js"></script>
</body>
</html>
