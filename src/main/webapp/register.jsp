<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="0">
		<tr>
			<th>名前：</th>
			<td><textarea rows="1" cols="20"></textarea></td>
		</tr>
		
		<tr>
			<th>メールアドレス</th>
			<td><textarea rows="1" cols="30"></textarea></td>
		</tr>
		
		<tr>
			<th>パスワード</th>
			<td><textarea rows="1" cols="30"></textarea></td>
		</tr>
	</table>
	
	<div class="form-action">
		<button type="submit" class="btn">登録する</button>
		<a href="TopServlet" class="btn">キャンセル</a>
	</div>
	
	<footer class="footer">
		<p>&copy;2026 AIChatBot</p>
	</footer>
</body>
</html>