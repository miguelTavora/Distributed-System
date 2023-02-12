package userInfo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/handleEditProfInfo")
public class ServiceEditProfInfo extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		// quando utilizador destroi a sessão
		if ((String) session.getAttribute("username") == null
				|| ((String) session.getAttribute("username")).equals("")) {
			session.setAttribute("noSession", "");
			getServletContext().getRequestDispatcher("/TemplatesProf/CreateRoom.jsp").forward(request, response);
		} else {
			session.setAttribute("editProfUser", createScriptPutData((String) session.getAttribute("username")));
			if(session.getAttribute("changedPass") != null)
				session.setAttribute("changedPass",null);
			getServletContext().getRequestDispatcher("/TemplatesProf/EditInfo.jsp").forward(request, response);
		}
		
		
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String createScriptPutData(String username) {
		return "<script>setUsernameToEditProf(\""+username+"\");</script>";
	}

}
