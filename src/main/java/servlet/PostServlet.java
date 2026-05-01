package servlet;

import java.io.IOException;
import java.sql.SQLException;

import api.GeminiAPI;
import dao.PostDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Post;
import model.User;

@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PostServlet() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		request.getRequestDispatcher("/post.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser"); //入力・ログイン者の情報取得
			String content = request.getParameter("content");

			String errorMessage = validateInput(content); //入力内容の不正確認
			if (errorMessage != null) {
				request.setAttribute("errorMessage", errorMessage);
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			String prompt = "以下の質問に、長くても300文字程度までで詳細に解説してください\n\n『" + content + "』"; //APIへの質問、回答指示
			String aiResponse = GeminiAPI.ask(prompt);

			if (loginUser != null) { //ログイン情報があるかどうか
				int userId = loginUser.getUserId(); //ユーザーIDの取得
				Post post = new Post(userId, content.trim(), aiResponse.trim()); //ID、質問内容、回答をコンストラクタを呼び出して格納

				PostDAO dao = new PostDAO(); //DB操作をインスタンス化
				boolean success = dao.insert(post); //DBのINSERT操作の結果を真偽として保存

				if (!success) {
					request.setAttribute("errorMessage", "エラーが発生しました。"); //リクエストオブジェクトに格納
					request.getRequestDispatcher("/error.jsp").forward(request, response); //error.jspにフォワード
				}
				request.getSession().setAttribute("successMessage", "質問完了");
				response.sendRedirect("TimelineServlet");

			} else { //ログイン情報がない場合再ログイン
				session.setAttribute("errorMessage", "再ログインしてください");
				response.sendRedirect("LoginServlet");
				return;
			}

		} catch (SQLException e) {//SQL関連の例外時処理
			e.printStackTrace();
			request.setAttribute("errorMessage", "エラーが発生しました。" + e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}

	}

	private String validateInput(String content) {
		if (content == null || content.trim().isEmpty()) {
			return "質問内容を入力してください";
		}
		if (content.length() > 100) {
			return "質問内容は１００文字以内で入力してください";
		}
		return null;
	}

}
