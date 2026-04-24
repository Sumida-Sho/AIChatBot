<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>AiChatBot-ログイン</title>
 <link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="container">
		<h1>AIチャットボット-ログイン画面</h1>
		
		<%
				String successMsg=(String)session.getAttribute("successMessage");
				if(successMsg!=null){
					out.println("<p style='color:blue;'>"+successMsg+"</p>");
				}
		%>
		
		<%
			session.removeAttribute("successMessage");
		%>
	
		<%
				String errorMsg=(String)request.getAttribute("errorMessage");
				if(errorMsg!=null){
					out.println("<p style='color:red;'>"+errorMsg+"</p>");
				}
		%>
		<div class="about">
			メールアドレスとパスワードを入力してください
		</div>
		<form action="LoginServlet" method="post">
			<table border="0" class="">
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
				<button type="submit" class="btn">ログイン</button>
				<a href="TopServlet" class="btn">キャンセル</a>
			</div>
		</form>
			
		
		<footer class="footer">
			<p>&copy;2026 AIChatBot</p>
		</footer>
	</div>
	
</body>
</html>