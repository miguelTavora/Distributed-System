package rooms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import loginProf.LoginFormGestor;
import serverData.ServerData;

@WebServlet("/handleAccessRoom")
public class ServiceConnectStudentRoom extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String key = request.getParameter("key");// vai buscar o name que está no input da JSP

		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}

		if (LoginFormGestor.isAlphaNumeric(key) && key.length() > 3 && key.length() < 26) {
			if (ServerData.checkExistingKey(key)) {
				session.setAttribute("studentKey", key);
				
				if(session.getAttribute("existingNumber") != null) 
					session.setAttribute("existingNumber",null);
				if(session.getAttribute("errorData") != null) 
					session.setAttribute("errorData",null);
				
				getServletContext().getRequestDispatcher("/TemplatesAluno/TemplateLoginAluno.jsp").forward(request,
						response);
			} else {
				session.setAttribute("errorKey", key);
				getServletContext().getRequestDispatcher("/ErrorPages/EnterRoomError.jsp").forward(request, response);
			}
		} else {
			session.setAttribute("errorKey", key);
			getServletContext().getRequestDispatcher("/ErrorPages/EnterRoomError.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
