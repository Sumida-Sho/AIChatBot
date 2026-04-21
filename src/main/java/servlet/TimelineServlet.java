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
import model.Post;

@WebServlet("/TimelineServlet")
public class TimelineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TimelineServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PostDAO dao = new PostDAO();
		List<Post> posts = null;
		try {
			posts = dao.findAll();
			request.setAttribute("posts", posts);

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "記事取得中にエラー発生：" + e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
		request.getRequestDispatcher("/timeline.jsp").forward(request, response);

	}

}
