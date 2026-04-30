package servlet;

import java.io.IOException;
import java.sql.SQLException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		request.getRequestDispatcher("/login.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			UserDAO dao = new UserDAO();

			if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
				request.setAttribute("errorMessage", "正しく入力してください");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			User loginUser = dao.login(email, password);

			if (loginUser != null) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", loginUser);
				session.setAttribute("successMessage", "ログイン成功");
				response.sendRedirect("TimelineServlet");
			} else {
				request.setAttribute("errorMessage", "メールアドレスかパスワードが間違っています");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "ログインエラーが発生しました" + e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}

	}

}
