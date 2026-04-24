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

@WebServlet("/ProfileEditServlet")
public class ProfileEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProfileEditServlet() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser != null) {
			request.getRequestDispatcher("/profile_edit.jsp").forward(request, response);
		} else {
			response.sendRedirect("LoginServlet");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String bio = request.getParameter("bio");

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser != null) {
			User user = new User(username, bio, loginUser.getUserId());
			UserDAO dao = new UserDAO();

			try {
				boolean register = dao.update(user);

				if (register) {
					loginUser.setUsername(username);
					loginUser.setBio(bio);
					session.setAttribute("loginUser", loginUser);
					session.setAttribute("successMessage", "更新完了しました");
					response.sendRedirect("MyPageServlet");
				} else {
					request.setAttribute("errorMessage", "エラーが発生しました");
					request.getRequestDispatcher("/error.jsp").forward(request, response);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", "登録中にシステムエラーが発生しました");
				request.getRequestDispatcher("/profile_edit.jsp").forward(request, response);
			}
		}

	}

}
