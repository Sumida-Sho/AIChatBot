package servlet;

import java.io.IOException;
import java.sql.SQLException;

import dao.PostDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteServlet() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int postId = Integer.parseInt(request.getParameter("postId"));
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");

			if (loginUser != null) {
				int userId = loginUser.getUserId();
				PostDAO dao = new PostDAO();
				dao.delete(postId, userId);
				session.setAttribute("errorMessage", "削除エラーが発生しました");
				response.sendRedirect("MyPageServlet");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "エラーが発生しました。" + e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
