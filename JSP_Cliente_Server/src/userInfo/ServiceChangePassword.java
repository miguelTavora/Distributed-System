package userInfo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import loginProf.LoginFormGestor;
import serverData.ServerData;

@WebServlet("/handleChangePassword")
public class ServiceChangePassword extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String lastPass = request.getParameter("last-pass");
		String newPass = request.getParameter("new-pass");
		String repeatPass = request.getParameter("repeat-pass");
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		// quando utilizador destroi a sessão
		if ((String) session.getAttribute("username") == null
				|| ((String) session.getAttribute("username")).equals("")) {
			session.setAttribute("noSession", "");
			getServletContext().getRequestDispatcher("/TemplatesProf/CreateRoom.jsp").forward(request, response);
		} else if(validatePass(lastPass,newPass,repeatPass)){
			session.setAttribute("changedPass", ServerData.changeProfPassword((String)session.getAttribute("key"), (String) session.getAttribute("username"),lastPass,newPass,repeatPass));
			getServletContext().getRequestDispatcher("/TemplatesProf/EditInfo.jsp").forward(request, response);
		} else {
			session.setAttribute("changedPass", "Failure changing the password");
			getServletContext().getRequestDispatcher("/TemplatesProf/EditInfo.jsp").forward(request, response);
		}
			
		
		
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private boolean validatePass(String oldPass, String newPass, String repeatPass) {
		if(LoginFormGestor.checkPassword(oldPass) && LoginFormGestor.checkPassword(newPass) && LoginFormGestor.checkPassword(repeatPass)) {
			if(newPass.equals(repeatPass)) {
				return true;
			}
		}
		return false;
	}

}
