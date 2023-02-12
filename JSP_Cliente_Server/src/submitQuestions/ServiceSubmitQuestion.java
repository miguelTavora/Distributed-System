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

@WebServlet("/handleSubmitQuestion")
public class ServiceSubmitQuestion extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String question = request.getParameter("quest1");
		
		String[] corrections = new String[6];
		corrections[0] = request.getParameter("op-a");// retorna a quando tem certo e null quando nao
		corrections[1] = request.getParameter("op-b");
		corrections[2] = request.getParameter("op-c");
		corrections[3] = request.getParameter("op-d");
		corrections[4] = request.getParameter("op-e");
		corrections[5] = request.getParameter("op-f");
		
		String[] empty = new String[6];
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		if ((String) session.getAttribute("studentKey") == null || (String) session.getAttribute("studentKey") == "") {// sessão destruida
			session.setAttribute("errorSession", "");
			getServletContext().getRequestDispatcher("/TemplatesAluno/TemplateLoginAluno.jsp").forward(request,
					response);
		} else if(validateExistingQuestion(question,(String) session.getAttribute("studentKey"),(String)session.getAttribute("studentNumber"))){
			System.out.println("sucesso");
			String key = (String) session.getAttribute("studentKey");
			String studentNumber= (String) session.getAttribute("studentNumber");
			session.setAttribute("profOnline", ServerData.checkProfIsOnline(key));
			ServerData.stopExistingTimers(key);
			if(session.getAttribute("existingQuest") != null)
				session.setAttribute("existingQuest",null);
			if(session.getAttribute("submitError") != null)
				session.setAttribute("submitError",null);
			if (!ServerData.passedTheTime(studentNumber)) 
				ServerData.setAnswerByStudent(createXMLWithAnswers(corrections, key, studentNumber), key,
						studentNumber);
			else
				ServerData.setAnswerByStudent(createXMLWithAnswers(empty, key, studentNumber), key, studentNumber);
			ServerData.increaseIndexQuestion(key,studentNumber);
			session.setAttribute("infoStudent", ServiceLoginAluno.createMessageToShowQuestion(key,studentNumber));
			if((String)session.getAttribute("infoStudent") != null) {
				session.setAttribute("countTime", createScriptTimer());
				ServerData.countTimeToExecute(ServerData.getTimeToAnswerQuestion(key, studentNumber),studentNumber);
			}
			else
				session.setAttribute("countTime", null);
			
			
			getServletContext().getRequestDispatcher("/TemplatesAluno/WaitingRoom.jsp").forward(request, response);
		} else {
			System.out.println("insucesso");
			session.setAttribute("submitError", "");
			getServletContext().getRequestDispatcher("/TemplatesAluno/WaitingRoom.jsp").forward(request, response);
		}
		
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private boolean validateExistingQuestion(String question, String key, String studentNumber) {
		return ServerData.verifyExistingQuestionByNumber(question, key, studentNumber);
	}
	
	private String createXMLWithAnswers(String[] checkedAnswers,String key, String studentNumber) {
		int index = ServerData.getQuestionIndexOfStudent(key, studentNumber);
		
		String xml = "<send><index>"+index+"</index>";
		for(int i = 0; i < checkedAnswers.length;i++) {
			if (checkedAnswers[i] != null) {
				if (!checkedAnswers[i].equals("")) 
					xml = xml + "<opc>" + (i + 1) + "</opc>";
			}
		}
		return xml+"</send>";
	}
	
	public static String createScriptTimer() {
		return "<script>updateTime();</script>";
	}

}
