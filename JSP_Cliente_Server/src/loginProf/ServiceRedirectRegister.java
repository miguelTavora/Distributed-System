package loginProf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/redirectRegister")
public class ServiceRedirectRegister  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		
		if(session.getAttribute("registerResult") != null) {
			session.setAttribute("registerResult",null);
		}

		getServletContext().getRequestDispatcher("/TemplatesProf/TemplateCriarNovoProf.jsp").forward(request, response);
	}

}
