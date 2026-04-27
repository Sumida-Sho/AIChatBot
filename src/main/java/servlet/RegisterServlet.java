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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		request.getRequestDispatcher("/register.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (username == null || username.trim().isEmpty() || email == null || email.trim().isEmpty() || password == null
				|| password.trim().isEmpty()) {
			request.setAttribute("errorMessage", "正しく入力してください");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		try {

			User user = new User(username, email, password);
			UserDAO dao = new UserDAO();
			boolean register = dao.insert(user);

			if (register) {
				HttpSession session = request.getSession();
				session.setAttribute("successMessage", "新規登録に成功しました");
				response.sendRedirect("LoginServlet");
			} else {

				request.setAttribute("errorMessage", "既に登録済みか、入力内容が正しくありません");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "システムエラーが発生しました" + e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}

	}

}
