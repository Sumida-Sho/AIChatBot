<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.List,java.util.ArrayList,java.text.SimpleDateFormat,model.Post,model.User" %>
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
		<header class="header">
			<h1 class="title">マイページ</h1>
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
					<a href="LogoutServlet" class="" onclick="return confirm('本当にログアウトしますか？');">ログアウト</a>
				</li>
			</ul>
		</nav>
		
		<main class="main-content">
		<%
			String msg=(String)session.getAttribute("successMessage");
			if(msg!=null && !msg.trim().isEmpty()){
		%>
			<div class="message message-success"><%=msg %></div>
		<% 
			}
		%>
		
		<% session.removeAttribute("successMessage"); %>
		
			<div class="article-mylist">
				<ul class="bio-list">
					<li class="bio-name">名前:<%=loginUser.getUsername() %></li>
					<li class="bio-email">メールアドレス:<%=loginUser.getEmail() %></li>
					<li class="bio-bio">自己紹介:
					<li class="mypage-bio">
						<% if(loginUser.getBio()==null){ %>
						なし
						<%}else{ %>
						<%=loginUser.getBio() %>
						<%} %>
					</li>
				</ul>
				<%
					@SuppressWarnings("unchecked")
					List<Post> myPosts=(List<Post>)request.getAttribute("myPosts");
					if(myPosts != null && !myPosts.isEmpty()){
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd HH:mm");
						for(Post p : myPosts){
				%>	
				<div class="article-myitem">
					
					<div class="username"><%=p.getUsername() %></div>
					<span class="date"><%=dateFormat.format(p.getCreatedAt()) %></span>
					<div class="question">❓質問</div>
					<h4 class="question-content"><%=p.getContent() %></h4>
					<div class="response">🤖回答</div>
					<p class="ai-response"><%=p.getAiResponse() %></p>
					<a href="DeleteServlet?postId=<%=p.getPostId() %>&userId=<%=p.getUserId() %>>" class="btn"  onclick="return confirm('本当にこの質問を削除しますか？');">削除</a>
				</div>
				
				<%
						}
					}else{
				%>
					<div class="no-posts">
						<p>あなたの投稿はありません</p>
						<a href="PostServlet" class="nopost-btn">最初の質問をする</a>
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