package rooms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import loginProf.LoginFormGestor;
import serverData.ServerData;

@WebServlet("/handleCreateRoom")
public class ServiceRoom extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String key = request.getParameter("key");// vai buscar o name que está no input da JSP

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
		// dados corretos
		else if (LoginFormGestor.isAlphaNumeric(key) && key.length() > 3 && key.length() < 26) {
			if (!ServerData.checkExistingKey(key)) {// dados todos corretos
				if (ServerData.checkReceivedQuestionsFromServer((String) session.getAttribute("username"))) {
					ServerData.setKeyOfRoom(key);
					ServerData.storeTeacherWithKey((String) session.getAttribute("username"), key);
					session.setAttribute("key", key);
					String[] themes = convertThemesToHTML();
					session.setAttribute("themesOpc", themes[0]);
					session.setAttribute("themesDiv", themes[1]);
					session.setAttribute("questions", getQuestionsFromXML());
					session.setAttribute("infoQuest", convertInfoToScript());
					if(session.getAttribute("submitQuest") != null) {
						session.setAttribute("submitQuest",null);
					}
					getServletContext().getRequestDispatcher("/TemplatesProf/SendQuestions.jsp").forward(request,
							response);
				}
			// a pass introduzida foi feitapelo prof que a meteu	
			} else if (ServerData.checkTeacherWithKey((String) session.getAttribute("username"), key)) {
				if (ServerData.checkReceivedQuestionsFromServer((String) session.getAttribute("username"))) {
					ServerData.setKeyOfRoom(key);
					String[] themes = convertThemesToHTML();
					session.setAttribute("themesOpc", themes[0]);
					session.setAttribute("themesDiv", themes[1]);
					session.setAttribute("questions", getQuestionsFromXML());
					session.setAttribute("infoQuest", convertInfoToScript());
					if(session.getAttribute("submitQuest") != null) {
						session.setAttribute("submitQuest",null);
					}
					getServletContext().getRequestDispatcher("/TemplatesProf/SendQuestions.jsp").forward(request,response);
				}
			} else {// ja existe uma chave
				session.setAttribute("existingKey", key);
				getServletContext().getRequestDispatcher("/TemplatesProf/CreateRoom.jsp").forward(request, response);
			}

		}
		// dados incorretos
		else {
			session.setAttribute("errorKey", key);
			getServletContext().getRequestDispatcher("/TemplatesProf/CreateRoom.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public static String[] getThemesFromXML() {
		Document doc = ServerData.getDocumentQuestions();
		NodeList category = (NodeList) doc.getElementsByTagName("categoria");

		String[] categorys = new String[category.getLength()];
		for (int i = 0; i < category.getLength(); i++) {
			categorys[i] = category.item(i).getAttributes().getNamedItem("tema").getNodeValue();
		}
		return categorys;
	}

	// função que retorna array 2 dimensões onde [0] = opcions e [1] = divs
	// fomrato opions - <option value="1">deus</option>
	// formato divs - <div id="d1">deus</div>
	public static String[] convertThemesToHTML() {
		String[] categorys = getThemesFromXML();

		int count = 1;
		String opcions = "";
		String divs = "";

		for (int i = 0; i < categorys.length; i++) {
			opcions = opcions + "<option value=\"" + count + "\">" + categorys[i] + "</option>\n";
			divs = divs + "<div id=\"d" + count + "\">" + categorys[i] + "</div>\n";
			count++;
		}

		String[] result = { opcions, divs };
		return result;

	}

	//mete perguntas com value do tema
	public static String getQuestionsFromXML() {
		Document doc = ServerData.getDocumentQuestions();
		NodeList category = (NodeList) doc.getElementsByTagName("categoria");

		String htmlResult = "";
		int counter = 1;

		for (int i = 0; i < category.getLength(); i++) {
			Element elemCat = (Element) category.item(i);
			NodeList questions = elemCat.getElementsByTagName("pergunta");
			for (int j = 0; j < questions.getLength(); j++) {
				Element elem0 = (Element) questions.item(j);
				NodeList question = elem0.getElementsByTagName("questao");
				String theme = category.item(i).getAttributes().getNamedItem("tema").getNodeValue();
				htmlResult = htmlResult + convertCategoryQuestionToHTML(theme, question.item(0).getTextContent(), counter);
				counter++;
			}
		}
		
		return htmlResult;

	}

	// converte info para <li id="lb1-al" role="option" value="a">Alabama</li>
	private static String convertCategoryQuestionToHTML(String theme, String question, int index) {
		return "<li id=\"q" + index + "\" name=\"q-name\" role=\"option\" value=\"" + theme + "\">" + question + "</li>\n";
	}
	
	//mensagem no formato tempo,pergunta1,pergunta2,pergunta3,opc1,opc2,opc3
	private static String getInfoQuestion() {
		Document doc = ServerData.getDocumentQuestions();
		NodeList questions = doc.getElementsByTagName("pergunta");
		String msn = "";
		for (int i = 0; i < questions.getLength(); i++) {
			Element elem0 = (Element) questions.item(i);
			NodeList timeList = elem0.getElementsByTagName("tempo");
			String time = convertStringTimeToSeconds(timeList.item(0).getTextContent());
			NodeList opcionList = elem0.getElementsByTagName("opcao");
			String opc = "";
			String correct = "";
			for(int j = 0; j < opcionList.getLength(); j++) {
				Element elem2 = (Element) opcionList.item(j);
				opc = opc+elem2.getTextContent()+",";
				if(j == opcionList.getLength()-1) {
					if(i != questions.getLength()-1) 
						correct = correct+opcionList.item(j).getAttributes().getNamedItem("resposta").getNodeValue()+";";
					else
						correct = correct+opcionList.item(j).getAttributes().getNamedItem("resposta").getNodeValue();
				}
				else 
					correct = correct+opcionList.item(j).getAttributes().getNamedItem("resposta").getNodeValue()+",";
				
			}
			msn = msn+time+","+opc+correct;
		}
		return msn;
	}
	
	//converte string formato 00:01:00 para 60 segundos
	private static String convertStringTimeToSeconds(String time) {
		String[] timeSplit = time.split(":");
		
		int seconds = 0;
		for(int i= timeSplit.length -1; i > -1;i--) {
			if(i == 2) {
				seconds = Integer.parseInt(timeSplit[i]);
			}
			else if(i ==1) {
				seconds = seconds +(Integer.parseInt(timeSplit[i])*60);
			}
			else {
				seconds = seconds +(Integer.parseInt(timeSplit[i])*60*60);
			}
		}
		return ""+seconds;
	}
	
	public static String convertInfoToScript() {
		String script = "<script>selectorBasedOnQuestion(\"";
		String str = getInfoQuestion();
		String endScript = "\");</script>";
		return script+str+endScript;
	}

}
