package answersCorrection;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import serverData.ServerData;

@WebServlet("/handleProfCheckAnswers")
public class ServiceProfCheckAnswers extends HttpServlet {

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
			session.setAttribute("showAllCorrection",
					ServerData.getAllStudentsAnswers((String) session.getAttribute("key")));
			session.setAttribute("minConnect",
					convertTimeMinToHTML(ServerData.getAllStudentsMinutsConnected((String) session.getAttribute("key")),(String) session.getAttribute("key")));
			getServletContext().getRequestDispatcher("/TemplatesProf/ViewAllAnswers.jsp").forward(request, response);
		}

	}
	
	private String convertTimeMinToHTML(long[] timeConnected, String key) {
		if(timeConnected != null) {
			String html = "";
			String[] studentNumbers =  ServerData.getStudentsConnected(key);
			for(int i = 0; i < timeConnected.length;i++) {
				html+= "<li><a href=\"#\">"+studentNumbers[i]+" connetected "+timeConnected[i]+"min</a></li>\n";
			}
			return html;
		}
		return "<li><a href=\"#\">No students conneceted</a></li>\n";
	}

}
		/*<li><a href="#">User</a></li>*/