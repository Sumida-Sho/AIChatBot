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

		String username = request.getParameter("username");//入力情報の取得
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (username == null || username.trim().isEmpty() || email == null || email.trim().isEmpty() || password == null//入力値の不正チェック
				|| password.trim().isEmpty()) {
			request.setAttribute("errorMessage", "正しく入力してください");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		if (username.length() > 10) {
			request.setAttribute("errorMessage", "名前は１０文字以内で記入してください");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		if (password.length() < 8 || password.length() > 15) {
			request.setAttribute("errorMessage", "パスワードは８文字以上１５文字以内で記入してください");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		try {

			User user = new User(username, email, password);//User型のオブジェクトに入力情報を格納
			UserDAO dao = new UserDAO();
			boolean register = dao.insert(user);//DBのINSERT処理
			HttpSession session = request.getSession();

			if (register) {
				session.setAttribute("successMessage", "新規登録に成功しました");
				response.sendRedirect("LoginServlet");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "システムエラーが発生しました　" + e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}

	}

}
