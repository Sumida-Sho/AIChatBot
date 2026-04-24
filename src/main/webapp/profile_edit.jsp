<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@page import="java.util.List,java.util.ArrayList,java.text.SimpleDateFormat,model.User" %>
      <%User loginUser = (User) session.getAttribute("loginUser"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>AiChatBot</title>
 <link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="container">
	
		<form action="ProfileEditServlet" method="post">
			<input type="hidden" name="userId" value="<%= loginUser.getUserId() %>">
			<table border="0">
				<tr>
					<th>現在の名前：</th>
					<td><%=loginUser.getUsername() %></td>
				</tr>
				<tr>
					<th>新しい名前:</th>
					<td><input type="text" name="username" required></td>
				</tr>
		
				<tr>
					<th>現在の自己紹介:</th>
					<%if(loginUser.getBio()==null){
						%>
					<td>なし</td>
					<%
					}else{
					%>
					<td><%=loginUser.getBio() %></td>
					<%
					}
					%>	
					
				</tr>
				<tr>
					<th>新しい自己紹介:</th>
					<td><textarea id="content" name="bio" class="form-textarea" rows="10" required></textarea></td>
				</tr>
		
			</table>
	
			<div class="form-action">
				<button type="submit" class="btn">更新する</button>
				<a href="MyPageServlet" class="btn">キャンセル</a>
			</div>
			
			
		</form>
		
		<footer class="footer">
			<p>&copy;2026 AIChatBot</p>
		</footer>
	</div>
</body>
</html>