<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>AiChatBot-質問画面</title>
 <link rel="stylesheet" href="css/style.css">
</head>
<body>
 	<div class="container">
		<header class="header">
			<h1><a href="TimelineServlet">AIチャットボット</a></h1>
		</header>
		
		<nav  class="navigation">
			<ul class="nav-list">
				<li class="nav-item">
					<a href="TimelineServlet" class="">質問・回答一覧</a>
				</li>
				<li class="nav-item">
					<a href="MyPageServlet" class="">マイページ</a>
				</li>
				<li class="nav-item">
					<a href="LogoutServlet" class="" onclick="return confirm('本当にログアウトしますか？');">ログアウト</a>
				</li>
			</ul>
		</nav>
		
		<main class="main-content">
		<h2 class="page-title">AIに質問</h2>
		
		<%
			String errorMessage=(String) request.getAttribute("errorMessagge");		
			if(errorMessage != null && !errorMessage.trim().isEmpty()){
		%>
		
			<div class="message message-error"><%=errorMessage %></div>
		
		<%
			}
		%>
		
		<form action="PostServlet" method="post">
			<div class="form-group">
				<label for="title" class="form-label">
					質問内容<span class="required">*</span>
				</label>
				<input type="text" id="content" name="content" class="form-input" maxlength="200" required value="<%=request.getParameter("content") != null ? request.getParameter("content") : ""%>">	
			</div>
			<button type="submit" class="btn">AIに質問する</button>
			<a href="TimelineServlet" class="btn">キャンセル</a>
		</form>	
			
			
		
		
		</main>
		<footer class="footer">
			<p>&copy;2026 AIChatBot</p>
		</footer>
	</div>
</body>
</html>