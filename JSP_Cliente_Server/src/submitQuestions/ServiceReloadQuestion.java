package submitQuestions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import loginAluno.ServiceLoginAluno;
import serverData.ServerData;

@WebServlet("/handleReloadQuestion")
public class ServiceReloadQuestion extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String question = request.getParameter("quest1");
		
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		if ((String) session.getAttribute("studentKey") == null || (String) session.getAttribute("studentKey") == "") {// sessão destruida
			session.setAttribute("errorSession", "");
			getServletContext().getRequestDispatcher("/TemplatesAluno/TemplateLoginAluno.jsp").forward(request,
					response);
		} else if(ServerData.verifyExistingQuestionByNumber(question, (String) session.getAttribute("studentKey"), (String)session.getAttribute("studentNumber"))) {//ja existe pergunta
			session.setAttribute("existingQuest", "");
			getServletContext().getRequestDispatcher("/TemplatesAluno/WaitingRoom.jsp").forward(request, response);
		} else if(question == null || question.equals("")) {//esta certo para dar reload
			String key = (String) session.getAttribute("studentKey");
			String studentNumber= (String) session.getAttribute("studentNumber");
			session.setAttribute("profOnline", ServerData.checkProfIsOnline(key));
			ServerData.stopExistingTimers(key);
			session.setAttribute("infoStudent", ServiceLoginAluno.createMessageToShowQuestion(key,studentNumber));
			
			if((String)session.getAttribute("infoStudent") != null) {
				session.setAttribute("countTime", ServiceSubmitQuestion.createScriptTimer());
				ServerData.countTimeToExecute(ServerData.getTimeToAnswerQuestion(key, studentNumber),studentNumber);
			}
			else
				session.setAttribute("countTime", null);
			getServletContext().getRequestDispatcher("/TemplatesAluno/WaitingRoom.jsp").forward(request, response);
		} else {//erro
			session.setAttribute("errorReload", "");
			getServletContext().getRequestDispatcher("/TemplatesAluno/WaitingRoom.jsp").forward(request, response);
		}
		
		
	}
	
	

}
