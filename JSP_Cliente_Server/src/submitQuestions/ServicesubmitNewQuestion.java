package submitQuestions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import loginProf.LoginFormGestor;
import serverData.ServerData;

/**
 * Servlet implementation class submitNewQuestion
 */
@WebServlet("/submitNewQuestion")
public class ServicesubmitNewQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;


	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//Ir buscar os valores 
		String theme = request.getParameter("theme");
		String question = request.getParameter("question");
		String time = request.getParameter("time-quest");

		String[] opcs = new String[6];
		String[] responses = new String[6];
		for(int i = 1; i< 7;i++) {
			opcs[i-1] = request.getParameter("opc" + i);
			responses[i-1] = request.getParameter("chx" + i);
		}
		
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
		
		else if(validateData(theme, question, time,opcs,responses)) {
			
			String msn = convertInfoToMsnXML(theme, question, time,opcs,responses);
			
			String result = ServerData.addQuestionOnOtherServer((String) session.getAttribute("username"), msn);
			
			session.setAttribute("addQuestServer", result);
			getServletContext().getRequestDispatcher("/TemplatesProf/TemplateCriarPergunta.jsp").forward(request,response);
		} else {
			session.setAttribute("addQuestServer", "Failure adding the question");
			getServletContext().getRequestDispatcher("/TemplatesProf/TemplateCriarPergunta.jsp").forward(request,response);
		}

		
	}

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private boolean validateData(String theme, String question, String time, String[] opcs, String[] responses) {
		if(validateTheme(theme) && validateQuestion(question) && validateTime(time) && validateOpcions(opcs) && validateResponses(opcs,responses)) {
			return true;
		}
		return false;
	}
	
	private boolean validateTheme(String theme) {
		if(theme != null) {
			if(theme.length() > 1 && LoginFormGestor.isAlphaNumeric(theme)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean validateQuestion(String question) {
		if(question != null) {
			if(question.length() > 3) {
				return true;
			}
		}
		return false;
	}
	
	private boolean validateTime(String time) {
		if(time != null) {
			if(time.length() > 1 && LoginFormGestor.isANumer(time) && time.length() < 4) {
				return true;
			}
		}
		return false;
	}
	
	private boolean validateOpcions(String[] opcs) {
		if(opcs[0] != null && opcs[1] != null) {
			if(opcs[0].length() > 1 && opcs[1].length() > 1) {
				return true;
			}
		}
		return false;
	}
	
	private boolean validateResponses(String[] opcs, String[] responses) {
		for(int i = 0; i < responses.length;i++) {
			if(responses[i] != null && opcs[i] !=null ) {
				if(opcs[i].length() > 1)
					return true;
			}
		}
		
		return false;
	}
	
	private String convertTimeToTimeFormat(String time) {
		int timeSeconds = Integer.parseInt(time);
		if (timeSeconds >= 3600) {
			int hours = timeSeconds / 3600;
			String timeF = "";
			if (hours < 10)
				timeF = timeF + "0" + hours + ":";
			else
				timeF = timeF + hours + ":";
			int minF = (timeSeconds - (int) hours * 3600)/60;
			if (minF < 10)
				timeF = timeF + "0" + minF + ":";
			else
				timeF = timeF + minF + ":";
			
			int secs = timeSeconds - (int) hours * 3600 - (int) minF * 60;
			if (secs < 10)
				timeF = timeF + "0" + secs;
			else
				timeF = timeF + secs;
			return timeF;
		}
		else if(timeSeconds >= 60) {
			String timeF = "00:";
			int minF = timeSeconds/60;
			if(minF < 10)
				timeF = timeF+"0"+minF+":";
			else
				timeF = timeF+minF+":";
			int secs = (int)timeSeconds-(int)(minF*60);
			if(secs < 10)
				timeF = timeF+"0"+secs;
			else
				timeF = timeF+secs;
			return timeF;
		}
		else {
			return "00:00:"+timeSeconds;
		}
	}

	
	private String convertInfoToMsnXML(String theme, String question, String time, String[] opcs, String[] responses) {
		String msn = "<registaPergunta><theme>" + theme + "</theme><question>"+ question + "</question>";
		msn+= "<time>"+convertTimeToTimeFormat(time)+"</time>";
		for(int i = 0; i<opcs.length;i++) {
			if(opcs[i] != null) {
				msn+= "<option>"+opcs[i]+"</option>";
			}
		}
		for(int i = 0; i<responses.length;i++) {
			if(responses[i] != null) {
				msn+= "<correctIndex>"+(i+1)+"</correctIndex>";
			}
		}
		msn+= "</registaPergunta>";
		return msn;
	}
	
	/*public static String resultRegisterQuestionXML(String theme,
			String question, ArrayList<String> opcoes, int numeroOpcaoCorreta) {

		String opcoesXML = "";
		for (String string : opcoes) {
			opcoesXML += "<option>" + string + "</option>";
		}

		return "<registaPergunta><theme>" + theme + "</theme><question>"
				+ question + "</question>" + opcoesXML + "<correctIndex>"
				+ numeroOpcaoCorreta + "</correctIndex>" + "</registaPergunta>";
	}*/
}
