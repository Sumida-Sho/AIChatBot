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
	<%
		Post post =(Post)request.getAttribute("post");
	%>
	<div class="container">
		<header class="header">
			<a href="TimelineServlet">AIチャットボット</a>
		</header>
		
		<nav  class="navigation">
			<ul class="nav-list">
				<li class="nav-item">
					<a href="PostServlet" class="">AIに質問する</a>
				</li>
				<li class="nav-item">
					<a href="MyPageServlet" class="">マイページ</a>
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
					List<Post> posts=(List<Post>)request.getAttribute("posts");
					if(posts != null && !posts.isEmpty()){
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd HH:mm");
						for(Post p : posts){
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
						<p>質問とそれに対するAIの回答が存在しません</p>
						<a href="PostServlet" class="btn ">最初の質問を投稿する</a>
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