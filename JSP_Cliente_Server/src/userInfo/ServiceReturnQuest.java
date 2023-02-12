package userInfo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rooms.ServiceRoom;

@WebServlet("/handleReturnQuestProf")
public class ServiceReturnQuest extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		if ((String) session.getAttribute("username") == null
				|| ((String) session.getAttribute("username")).equals("")) {
			session.setAttribute("noSession", "");
			getServletContext().getRequestDispatcher("/TemplatesProf/CreateRoom.jsp").forward(request, response);
		}
		else {
			String[] themes = ServiceRoom.convertThemesToHTML();
			session.setAttribute("themesOpc", themes[0]);
			session.setAttribute("themesDiv", themes[1]);
			session.setAttribute("questions", ServiceRoom.getQuestionsFromXML());
			session.setAttribute("infoQuest", ServiceRoom.convertInfoToScript());
			if(session.getAttribute("submitQuest") != null) {
				session.setAttribute("submitQuest",null);
			}
			getServletContext().getRequestDispatcher("/TemplatesProf/SendQuestions.jsp").forward(request,response);
		}
		
		
		
		
	}

}
