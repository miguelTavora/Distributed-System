package serverData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import answersCorrection.ValidateXmlWithXSD;
import loginAluno.ClientServerAluno;
import loginProf.ClientServerProf;
import xmlWriter.XMLReadWrite;

public class ServerData implements ServletContextListener {

	private static ArrayList<ClientServerProf> profsConnected;
	private static Map<String, ArrayList<ClientServerAluno>> studentsConnectedByRoom;// map de key = pass sala,
																						// arrayList alunos connectados
	private static ArrayList<String> roomsCreated;// lista para quando prof cria sala com chave
	private static Map<String, String> profsConnectedByRoom;// map de key = pass sala, arrayList nome dos profs

	private static Document questionsDoc;//doc das perguntas
	
	private static Map<String,ArrayList<String>> questionsSubmited;//map onde tem key, pergunta submetida pelo prof
	
	private static Map<String,ArrayList<String>> questionsAnsweredStudents;//map onde tem key corresponde numero aluno, array list as respostas dadas
	
	private static Map<String,Timer> questionsTimers;//map onde tem key corresponde numero aluno, array list as instancias de tempo
	
	private static Map<String,TimerMark> questionsTimeFlag;//map onde tem key corresponde numero aluno, TimerMark que diz se o tempo passou ou não(true passou false nao)

	// Notification that the servlet context is about to be shut down.
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		for(int i = 0; i<profsConnected.size();i++) {
			profsConnected.remove(0);
		}
	}

	// do all the tasks that you need to perform just after the server starts
	// Notification that the web application initialization process is starting
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		profsConnected = new ArrayList<ClientServerProf>();
		studentsConnectedByRoom = new HashMap<String, ArrayList<ClientServerAluno>>();
		roomsCreated = new ArrayList<String>();
		profsConnectedByRoom = new HashMap<String, String>();
		questionsSubmited = new HashMap<String, ArrayList<String>>();
		questionsAnsweredStudents = new HashMap<String, ArrayList<String>>();
		questionsTimers = new  HashMap<String, Timer>();
		questionsTimeFlag = new  HashMap<String, TimerMark>();
	}

	// guarda instancia do professor
	public static void storeProfConnected(ClientServerProf prof) {
		profsConnected.add(prof);
	}

	// remove o prof se o user e a pass não estiverem corretos
	public static void removeProfConnectedByName(String profName) {
		for (int i = profsConnected.size() - 1; i > -1; i--) {
			if (profsConnected.get(i).getProfName().equals(profName)) {
				profsConnected.remove(i);
			}
		}
	}

	// devolve instancia de prof pelo nome
	public static ClientServerProf getProfByName(String profName) {
		for (int i = profsConnected.size() - 1; i > -1; i--) {
			if (profsConnected.get(i).getProfName().equals(profName)) {
				return profsConnected.get(i);
			}
		}
		return null;
	}

	// devolve o indice na lista do prof(usado na mensagem do id)
	public static int getIndexProfByName(String profName) {
		for (int i = profsConnected.size() - 1; i > -1; i--) {
			if (profsConnected.get(i).getProfName().equals(profName)) {
				return i;
			}
		}
		return -1;
	}

	public static void setKeyOfRoom(String key) {
		roomsCreated.add(key);

	}

	// devolve se existe a chave para a sala ou não
	public static boolean checkExistingKey(String key) {
		for (int i = 0; i < roomsCreated.size(); i++) {
			if (roomsCreated.get(i).equals(key)) {
				return true;
			}
		}
		return false;
	}

	public static boolean storeStudentWithKey(ClientServerAluno aluno, String key) {
		if (studentsConnectedByRoom.get(key) != null) {
			ArrayList<ClientServerAluno> listStudents = studentsConnectedByRoom.get(key);
			for (int i = 0; i < listStudents.size(); i++) {
				if (listStudents.get(i).getStudentNumber().equals(aluno.getStudentNumber())) {
					return false;
				}
			}
			listStudents.add(aluno);
			studentsConnectedByRoom.put(key, listStudents);
			return true;

		} else {
			ArrayList<ClientServerAluno> listStudents = new ArrayList<ClientServerAluno>();
			listStudents.add(aluno);
			studentsConnectedByRoom.put(key, listStudents);
			return true;
		}
	}

	public static void storeTeacherWithKey(String profName, String key) {
		if (profsConnectedByRoom.get(key) == null) {
			profsConnectedByRoom.put(key, profName);
		}

	}

	public static boolean checkTeacherWithKey(String profName, String key) {
		if (profsConnectedByRoom.get(key) != null) {
			if (profsConnectedByRoom.get(key).equals(profName))
				return true;

		}
		return false;
	}

	//vai buscar as perguntas ao outro servidor e guarda no atributo questionsDoc
	public static void getQuestionsFromServer(String profName) {
		for (int i = 0; i < profsConnected.size(); i++) {
			if (profsConnected.get(i).getProfName().equals(profName)) {
				String str = "<request></request>";
				profsConnected.get(i).sendXMLToServer(str);

				try {
					for (;;) {
						Thread.sleep(200);
						if(profsConnected.get(i).getResponseReceived() != null) {
							Document doc = XMLReadWrite.documentFromString(profsConnected.get(i).getResponseReceived());
							questionsDoc = doc;
							profsConnected.get(i).resetResponseReceived();
							break;
						}
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//verifica se ja recebeu as perguntas
	public static boolean checkReceivedQuestionsFromServer(String profName) {
		if(questionsDoc != null) {//caso de ja estar guardado não faz pedido outra vez ao servidor
			return true;
		}
		
		getQuestionsFromServer(profName);//faz pedido
		
		if(questionsDoc != null) {
			return true;
		}
		return false;
	}
	
	public static Document getDocumentQuestions() {
		return questionsDoc;
	}
	
	//retorna os numeros dos alunos connectados se n tiver retorna null
	public static String[] getStudentsConnected(String key) {
		ArrayList<ClientServerAluno> alunosWithKey = studentsConnectedByRoom.get(key);
		if(alunosWithKey != null) {
			String[] students = new String[alunosWithKey.size()];
			for(int i = 0; i< alunosWithKey.size();i++) {
				students[i] = alunosWithKey.get(i).getStudentNumber();
			}
			return students;
		}
		return null;
	}
	
	public static void setQuestionOfProfWithKey(String key, String xmlQuestion) {
		if (questionsSubmited.get(key) != null) {
			ArrayList<String> questions = questionsSubmited.get(key);
			questions.add(xmlQuestion);
			questionsSubmited.put(key, questions);
			System.out.println(questionsSubmited);
		} else {
			ArrayList<String> quest = new ArrayList<String>();
			quest.add(xmlQuestion);
			questionsSubmited.put(key, quest);
			System.out.println(questionsSubmited);
		}
	}
	
	//vai buscar a pergunta do aluno se o numero for igual ou for para todos e incrementa o indice onde cada aluno esta
	public static String getQuestionByStudentNumber(String key, String studentNumber) {
		ArrayList<ClientServerAluno> students = studentsConnectedByRoom.get(key);
		int index = -1;
		//buscar indice do arraylist onde esta o aluno
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).getStudentNumber().equals(studentNumber)) {
				index = i;
				break;
			}
		}
		
		if (questionsSubmited.get(key) != null) {
			ArrayList<String> questions = questionsSubmited.get(key);
			int count = 0;
			boolean flag = false;
			for (int i = 0; i < questions.size() - students.get(index).getCurrentQuestionIndex(); i++) {
				flag = true;
				Document doc = XMLReadWrite
						.documentFromString(questions.get(students.get(index).getCurrentQuestionIndex() + i));

				NodeList stnList = (NodeList) doc.getElementsByTagName("student");
				String student = stnList.item(0).getTextContent();
				if (student.equals(studentNumber) || student.equals("todos")) {
					students.get(index).setCurrentCquestionIndex(students.get(index).getCurrentQuestionIndex()+count);
					studentsConnectedByRoom.put(key, students);
					return questions.get(students.get(index).getCurrentQuestionIndex());
				}
				count++;
				
			}
			if(flag) 
				students.get(index).setCurrentCquestionIndex(questions.size() - 1);
				
			 else 
				students.get(index).setCurrentCquestionIndex(questions.size());
			
			studentsConnectedByRoom.put(key, students);
			return null;
				
		}
		return null;
	}
	
	//incrementa o indice da pergunta (para quando submete pergunta)
	public static void increaseIndexQuestion(String key, String studentNumber) {
		ArrayList<ClientServerAluno> students = studentsConnectedByRoom.get(key);
		int index = -1;
		//buscar indice do arraylist onde esta o aluno
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).getStudentNumber().equals(studentNumber)) {
				index = i;
				break;
			}
		}
		students.get(index).setCurrentCquestionIndex(students.get(index).getCurrentQuestionIndex()+1);
		studentsConnectedByRoom.put(key, students);
	}
	
	//validar se o nome escrito é igual ao numero guardado na base dados, para evitar estar mandar cliente para tras
	public static boolean verifyStudentNumberAndName(String name, String number, String key) {
		ArrayList<ClientServerAluno> students = studentsConnectedByRoom.get(key);
		
		for (int i = 0; i < students.size(); i++) {
			if(students.get(i).getStudentNumber().equals(number)) {
				if(students.get(i).getFirstName().equals(name))
					return true;
			}
		}
		return false;
	}
	
	//verifica a pergunta onde o aluno com numero ... está corresponde a pergunta passada como argumento
	public static boolean verifyExistingQuestionByNumber(String question, String key, String number) {
		ArrayList<ClientServerAluno> students = studentsConnectedByRoom.get(key);
		
		int index = -1;
		for(int i = 0; i< students.size();i++) {
			if(students.get(i).getStudentNumber().equals(number)) 
				index = i;
		}
		int currentQuestion = students.get(index).getCurrentQuestionIndex();
		
		if (questionsSubmited.get(key) != null) {
			ArrayList<String> questions = questionsSubmited.get(key);
			
			if(questions.size() > currentQuestion) {
				Document doc = XMLReadWrite.documentFromString(questions.get(currentQuestion));

				String quest = doc.getElementsByTagName("quest").item(0).getTextContent();

				if (quest.length() > 300)
					quest = quest.substring(0, 300);

				if (quest.equals(question))
					return true;
			}
		}
		return false;
	}
	
	//para guardar em xml as respostas e indice da pergunta do aluno
	public static void setAnswerByStudent(String xmlResponseAnswers, String key, String number) {
		if(questionsAnsweredStudents.get(number) != null) {
			ArrayList<String> responses = questionsAnsweredStudents.get(number);
			responses.add(xmlResponseAnswers);
			questionsAnsweredStudents.put(number,responses);
			System.out.println(questionsAnsweredStudents);
		}
		else {
			ArrayList<String> responses = new ArrayList<String>();
			responses.add(xmlResponseAnswers);
			questionsAnsweredStudents.put(number,responses);
			System.out.println(questionsAnsweredStudents);
		}
	}
	
	public static int getQuestionIndexOfStudent(String key, String studentNumber) {
		ArrayList<ClientServerAluno> students = studentsConnectedByRoom.get(key);
		
		int index = -1;
		for(int i = 0; i< students.size();i++) {
			if(students.get(i).getStudentNumber().equals(studentNumber)) 
				index = i;
		}
		
		return (students.get(index).getCurrentQuestionIndex());
		
	}
	
	//para criar um timer e executar codigo passado o tempo
	public static void countTimeToExecute(long time, String studentNumber){
		Timer timer = new Timer();
		
		
		questionsTimeFlag.put(studentNumber, new TimerMark());
		
		time = time*1000+(5000);//margem segunrança de 5 segundos por causa da rede
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerMark timer = new TimerMark();
				timer.setTimemarkTrue();
				questionsTimeFlag.put(studentNumber,timer);
			}
		}, time);
		questionsTimers.put(studentNumber,timer);
	}
	
	//destruir os timers se existirem
	public static void stopExistingTimers(String studentNumber){
		if(questionsTimers.get(studentNumber) != null) {
			questionsTimers.remove(studentNumber);
		}
	}
	
	//retorna o tempo para responder á pergunta
	public static long getTimeToAnswerQuestion(String key, String studentNumber) {
		ArrayList<ClientServerAluno> students = studentsConnectedByRoom.get(key);
		
		int index = -1;
		for(int i = 0; i< students.size();i++) {
			if(students.get(i).getStudentNumber().equals(studentNumber)) 
				index = i;
		}
		
		
		ArrayList<String> questions = questionsSubmited.get(key);
		
		
		String xmlQuest = questions.get(students.get(index).getCurrentQuestionIndex());
		
		Document doc = XMLReadWrite.documentFromString(xmlQuest);
		
		String time = doc.getElementsByTagName("time").item(0).getTextContent();
		
		return Long.parseLong(time);
		
	}
	
	//faz set de q o tempo passou
	public static boolean passedTheTime(String studentNumber) {
		if(questionsTimeFlag.get(studentNumber) != null) {
			TimerMark timer = questionsTimeFlag.get(studentNumber);
			
			return timer.getTimerMark();
		}
		return false;
	}
	
	//retorna a informação do aluno
	public static String[] getInfoStudent(String key, String studentNumber) {
		ArrayList<ClientServerAluno> students = studentsConnectedByRoom.get(key);
		
		String[] info = new String[4];
		for(int i = 0; i< students.size();i++) {
			if(students.get(i).getStudentNumber().equals(studentNumber)) {
				info[0] = students.get(i).getFirstName();
				info[1] = students.get(i).getLastName();
				info[2] = students.get(i).getStudentNumber();
				info[3] = students.get(i).getBirthday();
				break;
			}
		}
		return info;
		
		
	}
	
	// retorna as respostas e a correção das resposta de cada aluno
	public static String getStudentAnswersCorrections(String key, String studentNumber) {
		ArrayList<String> studentsAnswers = questionsAnsweredStudents.get(studentNumber);
		ArrayList<String> profQuest = questionsSubmited.get(key);
		
		if(studentsAnswers == null)
			return null;
		
		
		if(studentsAnswers.size() == 0)
			return null;

		String answers = "";
		for(int i= 0; i<studentsAnswers.size();i++) {
			Document doc = XMLReadWrite.documentFromString(studentsAnswers.get(i));
			
			int indexQuest = Integer.parseInt(doc.getElementsByTagName("index").item(0).getTextContent());
			
			Document doc2 = XMLReadWrite.documentFromString(profQuest.get(indexQuest));
			NodeList quest = doc2.getElementsByTagName("quest");
			NodeList answersList = doc2.getElementsByTagName("answer");
			NodeList correct = doc2.getElementsByTagName("correction");
			
			answers = answers +"The question "+(i+1)+" ("+quest.item(0).getTextContent()+"), you answered ";
			NodeList opc = doc.getElementsByTagName("opc");
			
			
			String[] opcSend = new String[opc.getLength()];
			for(int j = 0; j<opc.getLength();j++) {
				if(j == opc.getLength()-1) {
					answers = answers +opc.item(j).getTextContent();
					int qstIndex = Integer.parseInt(opc.item(j).getTextContent())-1;
					answers = answers + " ("+answersList.item(qstIndex).getTextContent()+")";
					opcSend[j] = opc.item(j).getTextContent();
				}
				else {
					answers = answers +opc.item(j).getTextContent();
					int qstIndex = Integer.parseInt(opc.item(j).getTextContent())-1;
					answers = answers + " ("+answersList.item(qstIndex).getTextContent()+"), ";
					opcSend[j] = opc.item(j).getTextContent();
				}
			}
			answers = answers +". The answer ";
			for(int j = 0; j<opcSend.length;j++) {
				boolean flag = false;
				for(int k = 0; k<correct.getLength();k++) {
					String convert = ""+(Integer.parseInt(correct.item(k).getTextContent())+1);
					if(opcSend[j].equals(convert)) {
						if(j == opcSend.length-1) {
							answers = answers +opcSend[j]+" is correct.";
							flag = true;
							break;
						}else {
							answers = answers +opcSend[j]+" is correct, ";
							flag = true;
							break;
						}
							
					}
					
				}
				if(!flag) {
					if(j == opcSend.length-1) {
						answers = answers +opcSend[j]+" is not correct.";
					}
					
					else
						answers = answers +opcSend[j]+" is not correct, ";
				}
			}
			answers = answers+"\n\n";
			
		}
		return answers;

	}

	public static String getAllStudentsAnswers(String key) {
		String result = "";

		ArrayList<ClientServerAluno> students = studentsConnectedByRoom.get(key);
		if (students != null) {
			for (int i = 0; i < students.size(); i++) {
				result = result + "----------------Stundent Number " + students.get(i).getStudentNumber()
						+ "-------------------------\n";
				result = result + getStudentAnswersCorrections(key, students.get(i).getStudentNumber()) + "\n";
			}
		}
		return result;
	}
	
	
	
	//envia e recebe mensagem para mudar a pass
	public static String changeProfPassword(String key, String username,String oldPass, String newPass, String repeatPass) {
		
		for(int i = 0; i<profsConnected.size();i++) {
			if(profsConnected.get(i).getProfName().equals(username)) {
				ClientServerProf cl = profsConnected.get(i);
				if(cl == null)
					return null;
				
				String xmlSend = "<changepass><user>"+username+"</user><old>"+oldPass+"</old><new>"+newPass
						+"</new><repeat>"+repeatPass+"</repeat></changepass>";
				cl.sendXMLToServer(xmlSend);
				
				String received;
				
				for(;;) {
					try {
						Thread.sleep(100);
						if(cl.getResponseReceived() != null) {
							received = cl.getResponseReceived();
							cl.resetResponseReceived();
							break;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if(ValidateXmlWithXSD.validateChangePass(received)) {
					String result = XMLReadWrite.documentFromString(received).getElementsByTagName("resultpass").item(0).getTextContent();
					if(result.equals("sucesso"))
						return "Sucess changing the password";
					else
						return "Failure changing the password";
				}else {
					System.out.println("Not validated XML change pass");
				}
				
			}
		}
		return "Failure changing the password";
	}
	
	public static String addQuestionOnOtherServer(String username,String msn) {
		ClientServerProf prof =  getProfByName(username);
		prof.sendXMLToServer(msn);
		String result = null;
		for(;;) {
			try {
				Thread.sleep(100);
				if(prof.getResponseReceived() != null) {
					if(prof.getResponseReceived().equals("<failure></failure>")) {
						result = "Failure adding the question";
						prof.resetResponseReceived();
						break;
					}else {
						questionsDoc = XMLReadWrite.documentFromString(prof.getResponseReceived());
						result = "Success adding the question";
						prof.resetResponseReceived();
						break;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static long[] getAllStudentsMinutsConnected(String key) {
		if(studentsConnectedByRoom.get(key) != null) {
			ArrayList<ClientServerAluno> students = studentsConnectedByRoom.get(key);
			
			long[] timeConnected = new long[students.size()];
			
			for(int i = 0; i<timeConnected.length;i++) {
				timeConnected[i] = students.get(i).getTimeConnectMinuts();
			}
			return timeConnected;
		}
		return null;
	}
	
	public static void logoutProf(String profName,String key) {
		int index = getIndexProfByName(profName);
		
		profsConnected.remove(index);
		
		for(int i = 0; i<roomsCreated.size();i++) {
			if(roomsCreated.get(i).equals(key)) {
				roomsCreated.remove(i);
				break;
			}
		}
		profsConnectedByRoom.remove(key);
	}
	
	public static void logoutStudent(String studentNumber, String key) {
		ArrayList<ClientServerAluno> students = studentsConnectedByRoom.get(key);
		
		for(int i = 0; i<students.size();i++) {
			if(students.get(i).getStudentNumber().equals(studentNumber)) {
				students.remove(i);
			}
		}
		studentsConnectedByRoom.put(key, students);
	}
	
	public static String checkProfIsOnline(String key) {
		if(profsConnectedByRoom.get(key) != null) {
			return "connected";
		}
		return null;
	}
	
}
