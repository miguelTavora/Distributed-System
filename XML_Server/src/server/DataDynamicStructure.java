package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import PathAndExpressions.Expression;
import server.xml.DocumentXML;
import server.xml.SerializeObjectsXML;
import server.xml.ValidatorXML;
import xml.XMLReadWrite;

public class DataDynamicStructure {//função para validar guardar e outras funções

	private ArrayList<String> questions;
	private ArrayList<String> students;
	private Map<String, List<String>> studentAnswers;

	public DataDynamicStructure() {
		questions = new ArrayList<String>();
		students = new ArrayList<String>();
		studentAnswers = new HashMap<>();
	}

	protected void addQuestion(String question) {
		questions.add(question);
	}

	protected void addStudent(String student) {
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).equals(student))
				return;
		}
		students.add(student);
	}

	protected void addStudentForQuestion(String student) {
		List<String> questions = new ArrayList<>();
		studentAnswers.put(student, questions);
	}

	private void addQuestionToStudent(String student, String answer) {
		List<String> questions = studentAnswers.get(student);
		questions.add(answer);
		studentAnswers.put(student, questions);
	}

	protected void addAnswersToStudents(String student, String[] answers) {
		for (int i = 0; i < answers.length; i++) {
			addQuestionToStudent(student, answers[i]);
		}
	}

	protected void removeStudent(String student) {
		if (studentAnswers.containsKey(student)) {
			studentAnswers.remove(student);
		}
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).equals(student)) {
				students.remove(i);

			}
		}
	}

	//envia para o prof as respostas dos alunos
	protected String sendStudentAnswers() {
		String xml = Expression.sendAnswers;
		for (String key : studentAnswers.keySet()) {
			xml = xml + "<aluno numero=\"" + key + "\">";
			List<String> questions = studentAnswers.get(key);
			boolean inside = false;
			int count = 0;
			for (String quest : questions) {
				count++;
				if (quest.subSequence(0, 1).equals("I") && inside) {
					xml = xml + "<pergunta/><pergunta id=\"" + quest + "\">";
					if (count == questions.size())
						xml = xml + "</pergunta>";
				} else if (quest.subSequence(0, 1).equals("I") && !inside) {
					xml = xml + "<pergunta id=\"" + quest + "\">";
					if (count == questions.size())
						xml = xml + "</pergunta>";
				}

				else {
					xml = xml + "<resposta indice=\"" + quest + "\"/>";
					if (count == questions.size())
						xml = xml + "</pergunta>";
				}

			}
			xml = xml + "</aluno>";
		}
		return xml + Expression.sendAnswersEnd;

	}

	protected String[] getStudents() {
		String[] data = new String[students.size()];

		for (int i = 0; i < students.size(); i++) {
			data[i] = students.get(i);
		}
		return data;
	}

	protected String[] getQuestions() {
		String[] data = new String[questions.size()];

		for (int i = 0; i < questions.size(); i++) {
			data[i] = questions.get(i);
		}
		return data;
	}

	//função verifica se o aluno pode aceder caso seja all podem todos senão so se o numero dele corresponder
	protected boolean canAccessQuestion(String strXML) {
		Document doc = XMLReadWrite.documentFromString(strXML);

		NodeList quest = doc.getElementsByTagName("pergunta");
		int index = Integer.parseInt(quest.item(0).getAttributes().getNamedItem("indice").getNodeValue());

		if (questions.size() == index)
			return false;

		Document doc2 = XMLReadWrite.documentFromString(questions.get(index));

		NodeList questList = doc2.getElementsByTagName("pergunta");

		String aluno = questList.item(0).getAttributes().getNamedItem("aluno").getNodeValue();

		if (aluno.equals("all")) {
			return true;
		}

		NodeList number = doc.getElementsByTagName("aluno");
		if (aluno.equals(number.item(0).getAttributes().getNamedItem("numero").getNodeValue())) {
			return true;
		}
		return false;
	}

	protected int indexOfQuest(String strXML) {
		Document doc = XMLReadWrite.documentFromString(strXML);

		NodeList quest = doc.getElementsByTagName("pergunta");
		return Integer.parseInt(quest.item(0).getAttributes().getNamedItem("indice").getNodeValue());
	}

	//função que diz se o aluno deve incrementar o indice da pergunta ou não, deve caso haja mais perguntas
	protected boolean shouldIncreaseIndex(String strXML) {
		Document doc = XMLReadWrite.documentFromString(strXML);

		NodeList quest = doc.getElementsByTagName("pergunta");
		int index = Integer.parseInt(quest.item(0).getAttributes().getNamedItem("indice").getNodeValue());

		if (index < questions.size() - 1) {
			return true;
		}
		return false;
	}

	protected String processNotifyXML(String xml) {
		Document doc = XMLReadWrite.documentFromString(xml);

		NodeList request = (NodeList) doc.getElementsByTagName("aluno");
		return request.item(0).getAttributes().getNamedItem("numero").getNodeValue();

	}

	protected String createStudentesConnectedXML() {
		String st = Expression.notifyAlive;
		for (int i = 0; i < students.size(); i++) {
			st = st + "<aluno numero=\"" + students.get(i) + "\"/>";
		}
		return st + Expression.notifyAliveEnd;
	}

	protected String processStudentNumber(String xml) {
		Document doc = XMLReadWrite.documentFromString(xml);
		return doc.getElementsByTagName("pergunta").item(0).getAttributes().getNamedItem("aluno").getNodeValue();
	}

	//processa a resposta do aluno, primeiro guarda o id depois a informação caso nao responda so guarda o id
	protected String[] processAnswerOfStudent(String xml) {
		Document doc = XMLReadWrite.documentFromString(xml);

		String id = doc.getElementsByTagName("pergunta").item(0).getAttributes().getNamedItem("id").getNodeValue();
		NodeList quest = (NodeList) doc.getElementsByTagName("pergunta");

		NodeList response = (NodeList) doc.getElementsByTagName("resposta");
		if (!quest.item(0).hasChildNodes()) {
			String[] result = new String[1];
			result[0] = id;
			return result;
		} else {

			String[] result = new String[response.getLength() + 1];
			result[0] = id;
			for (int i = 0; i < response.getLength(); i++) {
				if (response.item(i).getAttributes().getNamedItem("indice").getNodeValue() != null) {

				}
				result[i + 1] = response.item(i).getAttributes().getNamedItem("indice").getNodeValue();
			}
			return result;
		}
	}

	//processa os resultados do aluno e envia para a classe DocumentXML para fazer a correção
	protected String processResultsOfQuestions(String xml) {
		Document doc = XMLReadWrite.documentFromString(xml);

		String number = doc.getElementsByTagName("aluno").item(0).getAttributes().getNamedItem("numero").getNodeValue();
		String result = Expression.sendResultOfQuestions;
		result = result + "<aluno numero=\"" + number + "\">";
		List<String> questions = studentAnswers.get(number);

		boolean inside = false;
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i).subSequence(0, 1).equals("I") && !inside) {
				if(questions.size() == 1) 
					result = result + "<pergunta id=\"" + questions.get(i) + "\"/>";
				
				else {
					result = result + "<pergunta id=\"" + questions.get(i) + "\">";
					inside = true;
				}
					
			} else if (questions.get(i).subSequence(0, 1).equals("I") && inside) {
				result = result + "</pergunta><pergunta id=\"" + questions.get(i) + "\">";
			} else {
				result = result + "<resposta indice=\"" + questions.get(i) + "\"/>";
				if (i == questions.size() - 1) {
					result = result + "</pergunta>";
				}
			}
		}
		result = result + "</aluno>";
		return result + Expression.sendResultOfQuestionsEnd;
	}

	protected void processDisconnectMessage(String xml) {
		Document doc = XMLReadWrite.documentFromString(xml);

		NodeList aluno = (NodeList) doc.getElementsByTagName("aluno");
		String number = aluno.item(0).getAttributes().getNamedItem("numero").getNodeValue();
		if (!number.equals("null")) {
			removeStudent(number);
		}
	}
	
	protected String checkInfoProf(String xmlInfo) {
		Document doc = XMLReadWrite.documentFromString(xmlInfo);
		
		NodeList infoList = (NodeList) doc.getElementsByTagName("info");
		NodeList usernameList = (NodeList) doc.getElementsByTagName("username");
		NodeList passwordList = (NodeList) doc.getElementsByTagName("password");

		String user = usernameList.item(0).getTextContent();
		String pass = passwordList.item(0).getTextContent();
		String idProf = infoList.item(0).getAttributes().getNamedItem("id").getNodeValue();
		
		
		if(SerializeObjectsXML.validateUser(user, pass) && ValidatorXML.validateProfLogin(xmlInfo)) {
			return "<info id=\""+idProf+"\" client=\"prof\">"
					+ "<answer>correto</answer>"
				  +"</info>";
		}
		return "<info id=\""+idProf+"\" client=\"prof\">"
			+ "<answer>incorreto</answer>"
				+"</info>";
		
	}
	
	public String writeToProfXML(String strXML) {
		// 1Âº Intrepretar o resultado
		Document doc = XMLReadWrite.documentFromString(strXML);

		String username = doc.getElementsByTagName("userName").item(0).getTextContent();
		String password = doc.getElementsByTagName("password").item(0).getTextContent();

		if (SerializeObjectsXML.addUser(username, password) && ValidatorXML.validateAddProf(strXML)) {
			return "<newprof>sucesso</newprof>";
		}

		return "<newprof>insucesso</newprof>";
	}
	public boolean addQuestionToXML(String categoria, String questao,
			String tempo, String[] opcoesResposta, String[] responses) {
		DocumentXML temp;
		try {
			temp = new DocumentXML();
			
			boolean val = temp.addQuestionToXML(categoria, questao, tempo,
					opcoesResposta, responses);
			return val;
		} catch (ParserConfigurationException | SAXException
				| IOException exception) {
			exception.printStackTrace();
			return false;
		}

	}

	public boolean addQuestionToXML(String inputLine) {
		Document doc = XMLReadWrite.documentFromString(inputLine);

		String theme = doc.getElementsByTagName("theme").item(0).getTextContent();
		
		String question = doc.getElementsByTagName("question").item(0).getTextContent();
		
		String time = doc.getElementsByTagName("time").item(0).getTextContent();
		
		
		NodeList rawOptions = doc.getElementsByTagName("option");
		ArrayList<String> options = new ArrayList<String>();
		for (int i = 0; i < rawOptions.getLength(); i++) {
			options.add(rawOptions.item(i).getTextContent());
		}
		NodeList rawCorrect = doc.getElementsByTagName("correctIndex");
		String[] responses = new String[rawCorrect.getLength()];
		for (int i = 0; i < rawCorrect.getLength(); i++) {
			responses[i] = rawCorrect.item(i).getTextContent();
		}
		if(ValidatorXML.validateAddQuestion(inputLine)) {
			return addQuestionToXML(theme, question, time, options.toArray(new String[0]), responses);
		}
		else
			return false;
	}

}
