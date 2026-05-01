<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>AiChatBot-新規登録</title>
 <link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="container">
	<h1 class="title">新規登録</h1>
		<%
			String msg=(String)request.getAttribute("errorMessage");
			if(msg!=null && !msg.trim().isEmpty()){
		%>
			<div class="message message-error"><%=msg %></div>
		<% 
			}
		%>
		<div class="about">
			<ul>
				<li class="list-about">名前、メールアドレス、パスワードを記入して登録してください</li>
				<li class="list-about">パスワードは８文字以上１５文字以内で設定お願いします</li>
			</ul>
		</div>
		
		<form action="RegisterServlet" method="post">
			<table border="0" class="table">
				<tr>
					<th>名前：</th>
					<td><input type="text" name="username"></td>
				</tr>
		
				<tr>
					<th>メールアドレス：</th>
					<td><input type="email" name="email"></td>
				</tr>
		
				<tr>
					<th>パスワード：</th>
					<td><input type="password" name="password"></td>
				</tr>
			</table>
	
			<div class="form-action">
				<button type="submit" class="btn">登録する</button>
				<a href="TopServlet" class="btn">キャンセル</a>
			</div>
			
			
		</form>
		
		<footer class="footer">
			<p>&copy;2026 AIChatBot</p>
		</footer>
	</div>
</body>
</html>