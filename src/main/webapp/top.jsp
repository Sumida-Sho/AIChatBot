<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		
		<h1>AIチャットボット-トップ画面</h1>
		<div class="about">初めてご利用になられる方は新規登録ボタンより登録を済ませてログインしてください。<br>
		既に登録されている方はログインしてください。
		</div>
		<ul class="list">
			<li class="list-item"><a href="RegisterServlet">新規登録</a></li>
			<li class="list-item"><a href="LoginServlet">ログイン</a></li>
		
			<a href="DeleteServlet">削除</a>
			<a href="MyPageServlet">マイページ</a>
			<a href="PostServlet">質問</a>
			<a href="ProfileEditServlet">プロフィール編集</a>
			
		</ul>
	
		<footer class="footer">
			<p>&copy;2026 AIChatBot</p>
		</footer>
	</div>
</body>
</html>