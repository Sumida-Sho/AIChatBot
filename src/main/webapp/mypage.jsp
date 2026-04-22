<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.List,java.util.ArrayList,java.text.SimpleDateFormat,model.Post" %>
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
		<header class="header">
			<a href="TimelineServlet">AIチャットボット</a>
		</header>
		<nav  class="navigation">
			<ul class="nav-list">
				<li class="nav-item">
					<a href="ProfileEditServlet" class="">プロフィール編集</a>
				</li>
				<li class="nav-item">
					<a href="TimelineServlet" class="">質問・回答一覧</a>
				</li>
				<li class="nav-item">
					<a href="LogoutServlet" class="">ログアウト</a>
				</li>
			</ul>
		</nav>
		
		<main class="main-content">
			<div class="article-list">
				<%
					@SuppressWarnings("unchecked")
					List<Post> myPosts=(List<Post>)request.getAttribute("myPosts");
					if(myPosts != null && !myPosts.isEmpty()){
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd HH:mm");
						for(Post p : myPosts){
				%>	
				<div class="article-item">
				
					<div class="username"><%=p.getUsername() %></div>
					<span class="date"><%=dateFormat.format(p.getCreatedAt()) %></span>
					<div class="question">❓質問</div>
					<h4 class="question-content"><%=p.getContent() %></h4>
					<div class="response">🤖回答</div>
					<p class="ai-response"><%=p.getAiResponse() %></p>
				</div>
				
				<%
						}
					}else{
				%>
					<div class="no-posts">
						<p>あなたの投稿はありません</p>
						<a href="PostServlet" class="btn ">最初の質問をする</a>
					</div>
				<%
					}
				%>	
			</div>
		</main>
		<footer class="footer">
			<p>&copy;2026 AIChatBot</p>
		</footer>
	</div>
</body>
</html>