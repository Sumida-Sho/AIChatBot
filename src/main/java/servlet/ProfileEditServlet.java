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

		HttpSession session = request.getSession();//セッションIDの確認、オブジェクトの取得
		User loginUser = (User) session.getAttribute("loginUser");//取得情報をUserにキャストして使用

		if (loginUser != null) {
			request.getRequestDispatcher("/profile_edit.jsp").forward(request, response);//ログイン確認成功で遷移
		} else {
			session.setAttribute("errorMessage", "再ログインしてください");
			response.sendRedirect("LoginServlet");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username"); //入力情報の取得
		String bio = request.getParameter("bio");

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser != null) {
			User user = new User(username, bio, loginUser.getUserId());//取得した情報を新しいものとしてUser変数userに保存
			UserDAO dao = new UserDAO();

			try {
				boolean register = dao.update(user);//DBのUPDATE処理

				if (register) {
					loginUser.setUsername(username);//取得した情報を新しいものに書き換える
					loginUser.setBio(bio);
					session.setAttribute("loginUser", loginUser);//セッションに保存
					session.setAttribute("successMessage", "更新完了しました");
					response.sendRedirect("MyPageServlet");
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", "システムエラーが発生しました" + e.getMessage());
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
		}

	}

}
