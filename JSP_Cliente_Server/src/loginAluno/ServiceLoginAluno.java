package loginAluno;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import serverData.ServerData;
import submitQuestions.ServiceSubmitQuestion;
import xmlWriter.XMLReadWrite;

@WebServlet("/handleAlunoLogin")
public class ServiceLoginAluno extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstname = request.getParameter("firstname");// vai buscar o name que está no input da JSP
		String lastname = request.getParameter("lastname");
		String number = request.getParameter("number");
		String birthday = request.getParameter("birthday");

		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		
		if ((String) session.getAttribute("studentKey") == null || (String) session.getAttribute("studentKey") == "") {// sessão destruida
			session.setAttribute("errorSession", "");
			getServletContext().getRequestDispatcher("/TemplatesAluno/TemplateLoginAluno.jsp").forward(request,
					response);

		}else if (validateLogin(firstname, lastname, number, birthday)) {// cria instancia aluno ficar guardado, se ja existir o número não deixa criar
			if (createNewClientStudent(firstname, lastname, number, birthday,(String) session.getAttribute("studentKey"))) {// parte onde acerta
				session.setAttribute("studentNumber", number);
				if(session.getAttribute("submitError") != null)
					session.setAttribute("submitError",null);
				session.setAttribute("infoStudent", createMessageToShowQuestion((String) session.getAttribute("studentKey"),number));
				session.setAttribute("profOnline", ServerData.checkProfIsOnline((String) session.getAttribute("studentKey")));
				if(session.getAttribute("infoStudent") != null) {
					ServerData.countTimeToExecute(ServerData.getTimeToAnswerQuestion((String) session.getAttribute("studentKey"), number),number);
					session.setAttribute("countTime", ServiceSubmitQuestion.createScriptTimer());
				}
				else
					session.setAttribute("countTime", null);
				
				getServletContext().getRequestDispatcher("/TemplatesAluno/WaitingRoom.jsp").forward(request, response);
			}else if(ServerData.verifyStudentNumberAndName(firstname, number, (String) session.getAttribute("studentKey"))) {
				if(session.getAttribute("submitError") != null)
					session.setAttribute("submitError",null);
				getServletContext().getRequestDispatcher("/TemplatesAluno/WaitingRoom.jsp").forward(request, response);
			}
			
			else {// ja existe o numero guardado
				session.setAttribute("existingNumber", "");
				getServletContext().getRequestDispatcher("/TemplatesAluno/TemplateLoginAluno.jsp").forward(request,response);
			}
		} else {// dados incorretos
			session.setAttribute("errorData", "");
			getServletContext().getRequestDispatcher("/TemplatesAluno/TemplateLoginAluno.jsp").forward(request,response);

		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// validações para os dados do aluno
	private boolean validateLogin(String firstName, String lastname, String studentNumber, String birthday) {
		if (checkFirstName(firstName) && checkLastName(lastname) && checkStudentNumber(studentNumber)
				&& checkBirthdayDate(birthday)) {
			return true;
		}
		return false;
	}

	public boolean isOnlyNumbers(String s) {
		String pattern = "[0-9]+";
		return s.matches(pattern);
	}

	public boolean isOnlyLetters(String s) {
		String pattern = "^[a-zA-Z]+$";
		return s.matches(pattern);
	}

	public boolean isOnDateFormat(String s) {
		 String[] dateSplit = s.split("/");
		 boolean[] checkDate = new boolean[3];
		 if(dateSplit.length == 0) {
			 return false;
		 }
		 if(Integer.parseInt(dateSplit[0]) > 0 && Integer.parseInt(dateSplit[0]) < 32 && dateSplit[0].length()== 2 ) {
			 if(Integer.parseInt(dateSplit[0]) < 10) {
				 if(dateSplit[0].substring(0, 1).equals("0")) {
					 checkDate[0] = true;
				 }else 
					 return false;
				 
			 }else 
				 checkDate[0] = true;
			 

		 }else 
			 return false;
		 
		 if(Integer.parseInt(dateSplit[1]) > 0 && Integer.parseInt(dateSplit[1]) < 13 && dateSplit[1].length()== 2 ) {
			 if(Integer.parseInt(dateSplit[1]) < 10) {
				 if(dateSplit[1].substring(0, 1).equals("0")) {
					 checkDate[1] = true;
				 }else 
					 return false;
				 
			 }else 
				 checkDate[1] = true;
			 

		 }else 
			 return false;
		 
		 if(Integer.parseInt(dateSplit[2]) > 1900 && Integer.parseInt(dateSplit[2]) < 2020) 
			 checkDate[2] = true;
		 
		 int count = 0;
		 for(int i = 0; i < 3; i++) {
			 if(checkDate[i] == true) 
				 count++;
			 
		 }
		 if(count ==3 ) 
			 return true;
		 
		 return false;
	}

	private boolean checkFirstName(String firstName) {
		if (isOnlyLetters(firstName) && firstName.length() > 2 && firstName.length() < 51) {
			return true;
		}
		return false;
	}

	private boolean checkLastName(String lastName) {
		if (isOnlyLetters(lastName) && lastName.length() > 3 && lastName.length() < 51) {
			return true;
		}
		return false;
	}

	private boolean checkStudentNumber(String studentNumber) {
		if (isOnlyNumbers(studentNumber) && Long.parseLong(studentNumber) > 0
				&& Long.parseLong(studentNumber) < 80000) {
			return true;
		}
		return false;
	}

	private boolean checkBirthdayDate(String birthday) {
		if (isOnDateFormat(birthday)) {
			return true;
		}
		return false;
	}

	private boolean createNewClientStudent(String firstName, String lastname, String studentNumber, String birthday,
			String key) {
		ClientServerAluno student = new ClientServerAluno(firstName, lastname, studentNumber, birthday,System.currentTimeMillis());
		if (ServerData.storeStudentWithKey(student, key)) {
			return true;
		}
		return false;

	}
	
	// retorna string de script a chamar função js para mostrar resultados
	public static String createMessageToShowQuestion(String key, String studentNumber) {
		String data = ServerData.getQuestionByStudentNumber(key, studentNumber);
		if (data != null && !data.equals("")) {
			Document doc = XMLReadWrite.documentFromString(data);

			if (doc != null) {
				String theme = doc.getElementsByTagName("theme").item(0).getTextContent();
				String question = doc.getElementsByTagName("quest").item(0).getTextContent();
				String time = doc.getElementsByTagName("time").item(0).getTextContent();
				NodeList listAns = doc.getElementsByTagName("answer");
				String xmlAnswers = "";
				for (int i = 0; i < listAns.getLength(); i++) {
					if (i != listAns.getLength() - 1)
						xmlAnswers = xmlAnswers + listAns.item(i).getTextContent() + ",";
					else
						xmlAnswers = xmlAnswers + listAns.item(i).getTextContent();
				}
				String msnInfoXML = theme + "," + question + "," + time + "," + xmlAnswers;
				return "<script>setQuestionToStudent(\"" + msnInfoXML + "\");</script>";

			}
			return null;
		}
		return null;
	}
}
