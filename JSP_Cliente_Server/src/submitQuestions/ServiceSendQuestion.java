package submitQuestions;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import loginProf.LoginFormGestor;
import rooms.ServiceRoom;
import serverData.ServerData;

@WebServlet("/handleSendQuestion")
public class ServiceSendQuestion extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String theme = request.getParameter("district-name2");
		String question = request.getParameter("cb1-input");
		String time = request.getParameter("question-time");
		String[] answers = new String[6];
		for(int i = 1; i<7;i++) {
			answers[i-1] = request.getParameter("op"+i);
		}
		String[] corrections = new String[6];
		corrections[0] = request.getParameter("op-a");// retorna a quando tem certo e null quando nao
		corrections[1] = request.getParameter("op-b");
		corrections[2] = request.getParameter("op-c");
		corrections[3] = request.getParameter("op-d");
		corrections[4] = request.getParameter("op-e");
		corrections[5] = request.getParameter("op-f");
		
		String randomStudents = request.getParameter("random-student");// não precisa validar o random porque se n for este é o outro
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		// quando utilizador destroi a sessão
		if ((String) session.getAttribute("username") == null
				|| ((String) session.getAttribute("username")).equals("")) {
			session.setAttribute("noSession", "");
			getServletContext().getRequestDispatcher("/TemplatesProf/CreateRoom.jsp").forward(request, response);
		}
		else if(validateQuestionSubmited(theme,question,time,answers,corrections)) {
			//validação se e para todos os alunos ou um aleatorio
			if(randomStudents != null) {
				if (randomStudents.equals("picked")) {
					String[] studentsConnected = ServerData.getStudentsConnected((String) session.getAttribute("key"));
					if (studentsConnected != null) {
						int rnd = new Random().nextInt(studentsConnected.length);
						ServerData.setQuestionOfProfWithKey((String) session.getAttribute("key"),
								createMessageQuestionSubmitedXML(studentsConnected[rnd], theme, question, time, answers,
										corrections));
						
					}
				}
				session.setAttribute("submitQuest", "success");
				getServletContext().getRequestDispatcher("/TemplatesProf/SendQuestions.jsp").forward(request,response);
			}else {
				ServerData.setQuestionOfProfWithKey((String)session.getAttribute("key"),
						createMessageQuestionSubmitedXML("todos", theme, question, time, answers, corrections));
				session.setAttribute("submitQuest", "success");
				getServletContext().getRequestDispatcher("/TemplatesProf/SendQuestions.jsp").forward(request,response);
			}
			
			
		}
		else {
			session.setAttribute("submitQuest", "failure");
			getServletContext().getRequestDispatcher("/TemplatesProf/SendQuestions.jsp").forward(request,response);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	//função que valida os dados 
	private boolean validateQuestionSubmited(String theme, String question, String time, String[] answers, String[] corrections) {
		if(validateTheme(theme) && validateQuestion(question) && validateTime(time)){
			int count = 0;
			for(int i = 0; i<answers.length;i++) {
				if(validateAnswers(answers[i])) {
					count++;
				}else
					break;
			}
			if(count < 2) 
				return false;
			
			
			for(int i = 0; i<corrections.length;i++) {
				if(corrections[i] != null) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean validateTheme(String theme) {
		String[] themes = ServiceRoom.getThemesFromXML();
		for(int i = 0; i<themes.length;i++) {
			if(themes[i].equals(theme)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean validateQuestion(String question) {
		Document doc = ServerData.getDocumentQuestions();
		NodeList categorys = (NodeList) doc.getElementsByTagName("questao");
		
		for(int i = 0; i<categorys.getLength();i++) {
			if(categorys.item(i).getTextContent().equals(question)){
				return true;
			}
		}
		return false;
	}
	
	private boolean validateTime(String time) {
		if(LoginFormGestor.isANumer(time)) {
			return true;
		}
		return false;
	}
	
	private boolean validateAnswers(String answer) {
		if(answer.equals("")|| answer == null) {
			return false;
		}
		return true;
	}
	
	//retorna baseado na informação
	//<question>
	//<student>..</student>
	//<theme>tema</theme>
	//<quest>pergunta</quest>
	//<time>tempo</time>
	//<answers>...</answers>...(2 a 6)
	//<correction>...</correction>...(1 a mais)
	private String createMessageQuestionSubmitedXML(String questPerson, String theme, String question, String time, String[] answers, String[] corrections) {
		String xml = "<question><student>"+questPerson+"</student><theme>"+theme+"</theme><quest>"+question+"</quest><time>"+time+"</time>";
		for(int i = 0; i<answers.length;i++) {
			if(answers[i] != null && !answers[i].equals("")) {
				xml = xml+"<answer>"+answers[i]+"</answer>";
			}else 
				break;
		}
		for(int i = 0; i<corrections.length;i++) {
			if(corrections[i] != null) {
				xml = xml+"<correction>"+i+"</correction>";
			}
		}
		xml = xml+"</question>";
		return xml;
	}

}
