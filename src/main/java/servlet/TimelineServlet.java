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
		List<Post> posts = null;//Postクラスの要素しか入らないList(データ数に合わせて拡大)のpostsを作成し、とりあえずの初期値null
		try {
			posts = dao.findAll();//PostDAO
			request.setAttribute("posts", posts);
			request.getRequestDispatcher("/timeline.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "記事取得中にエラー発生：" + e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}

	}

}
