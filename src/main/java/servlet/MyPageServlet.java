package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.PostDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Post;
import model.User;

@WebServlet("/MyPageServlet")
public class MyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MyPageServlet() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");

		PostDAO dao = new PostDAO();
		List<Post> myPosts = null;
		int userId = loginUser.getUserId();

		try {
			myPosts = dao.findByUserId(userId);

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "エラーが発生しました。" + e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
		session.setAttribute("loginUser", loginUser);
		request.setAttribute("myPosts", myPosts);
		request.getRequestDispatcher("/mypage.jsp").forward(request, response);
	}

}
