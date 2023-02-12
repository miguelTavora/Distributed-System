package userInfo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import serverData.ServerData;


@WebServlet("/handleCheckDataStudent")
public class ServiceCheckStudentInfo extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		if ((String) session.getAttribute("studentKey") == null || (String) session.getAttribute("studentKey") == "") {// sessão destruida
			session.setAttribute("errorSession", "");
			getServletContext().getRequestDispatcher("/TemplatesAluno/TemplateLoginAluno.jsp").forward(request,
					response);
		} else {
			session.setAttribute("showInfoStudent", createScriptPutData((String) session.getAttribute("studentKey"),(String)session.getAttribute("studentNumber")));
			getServletContext().getRequestDispatcher("/TemplatesAluno/ShowStudentInfo.jsp").forward(request, response);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String createScriptPutData(String key, String studentNumber) {
		String[] info = ServerData.getInfoStudent(key, studentNumber);
		
		return "<script>setInfoToStudent(\""+info[0]+","+info[1]+","+info[2]+","+info[3]+"\");</script>";
	}

}
