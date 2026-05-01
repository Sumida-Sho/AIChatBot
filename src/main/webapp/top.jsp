<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>AiChatBot-トップ</title>
 <link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="container">
		
		<h1 class="title">AIチャットボット-トップ画面</h1>
		
		<%
			String msg=(String)session.getAttribute("successMessage");
			if(msg!=null && !msg.trim().isEmpty()){
		%>
			<div class="message message-success"><%=msg %></div>
		<% 
			}
		%>
		
		<% session.removeAttribute("successMessage"); %>
		
		<div class="about">初めてご利用になられる方は新規登録ボタンより登録を済ませてログインしてください。<br>
		既に登録されている方はログインしてください。
		</div>
		<ul class="list">
			<li class="list-item"><a href="RegisterServlet">新規登録</a></li>
			<li class="list-item"><a href="LoginServlet">ログイン</a></li>
			
		</ul>
	
		<footer class="footer">
			<p>&copy;2026 AIChatBot</p>
		</footer>
	</div>
</body>
</html>