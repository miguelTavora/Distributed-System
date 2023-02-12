package submitQuestions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/handleRedirectNewQuest")
public class RedictToNewQuestion extends HttpServlet {

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
			if(session.getAttribute("addQuestServer") != null) {
				session.setAttribute("addQuestServer",null);
			}
			getServletContext().getRequestDispatcher("/TemplatesProf/TemplateCriarPergunta.jsp").forward(request,response);
		}
		
		
	}

}
