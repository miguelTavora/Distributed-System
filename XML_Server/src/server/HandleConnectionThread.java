package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import PathAndExpressions.Expression;
import server.xml.DocumentXML;
import server.xml.SerializeObjectsXML;

class HandleConnectionThread extends Thread {

	private Socket connection;
	private BufferedReader in = null;
	private PrintWriter out = null;
	@SuppressWarnings("unused")
	private DocumentXML doc;
	private DataDynamicStructure data;
	private Semaphore semaphore;

	public HandleConnectionThread(Socket connection,DocumentXML doc,DataDynamicStructure data) {
		this.connection = connection;
		this.doc = doc;
		this.data = data;
		semaphore = new Semaphore(1);
	}

	@Override
	public void run() {
		try {
			// circuito virtual estabelecido: socket cliente na variavel newSock
			System.out.println("Thread " + this.getId() + ": " + connection.getRemoteSocketAddress());

			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = new PrintWriter(connection.getOutputStream(), true);

			String inputLine;
			for (;;) {
				// para adicionar perguntas
				if ((inputLine = in.readLine()) != null) {
					semaphore.acquire();
					System.out.println("Recebi -> " + inputLine);
					if(inputLine.substring(0,8).equals("<info id")) {
						out.println(data.checkInfoProf(inputLine));
					}
					else if (inputLine.equals(Expression.requestForXmlFile)) //vai buscar o documento XML
						out.println(ServidorTCPConcorrente.getStringXML());
					
					else if (inputLine.substring(0,12).equals("<changepass>"))
						out.println(SerializeObjectsXML.changePassword(inputLine));
					
					else if (inputLine.startsWith(Expression.putNewProf)) {
						out.println(data.writeToProfXML(inputLine));
						
					} else if (inputLine.startsWith(Expression.putNewQuestion)) {
						if(data.addQuestionToXML(inputLine)) {
							out.println(ServidorTCPConcorrente.getStringXMLReload());
						}else {
							out.println(Expression.errorAddQuestion);
						}
					}
					/*if (inputLine.substring(0,10).equals(Expression.requestForQuestions)) {
						if (data.getQuestions().length == 0) //quando não há pergunta envia empty
							out.println(Expression.questionSectionEmpty);
						
							
						else {//existe informação
							if (data.canAccessQuestion(inputLine)) //verifica pode aceder a informação
								out.println(doc.questionXMLFormat(data.getQuestions()[data.indexOfQuest(inputLine)]));
							else if(data.shouldIncreaseIndex(inputLine)) //caso n possa mas existam outras perguntas dá increase e volta a perguntar
								out.println(Expression.increaseQuestionIndex);
							
							else
								out.println(Expression.goToResults);//n ha mais perguntas vai para os resultados
						}
							
					} else if (inputLine.substring(0, 11).equals(Expression.putQuestionOnList)) //mete pergunta respondida do aluno no hashMap
						data.addQuestion(inputLine);
						
					
					else if (inputLine.substring(0,8).equals(Expression.notifyAlive))//mensagem avisar o server que está vido
						data.addStudent(data.processNotifyXML(inputLine));
					
					else if (inputLine.equals(Expression.requestForStudents)) //request para os alunos conetados
						out.println(data.createStudentesConnectedXML());

					else if (inputLine.substring(0,8).equals(Expression.submitQuestion)){//guarda as respostas dadas pelo aluno
						String number = data.processStudentNumber(inputLine);
						data.addStudentForQuestion(number);
						data.addAnswersToStudents(number, data.processAnswerOfStudent(inputLine));
					} 
					else if (inputLine.equals(Expression.requestForAnswers))//guarda as respostas dadas pelo aluno
						out.println(data.sendStudentAnswers());

					else if (inputLine.substring(0,12).equals(Expression.requestForResults))//envia os resultados das perguntas
						out.println(doc.sendResultWithCorrection(data.processResultsOfQuestions(inputLine)));
					
					else if (inputLine.substring(0,12).equals(Expression.disconnectMessage)) // enviar o servidor que o aluno X de desconctou
						data.processDisconnectMessage(inputLine);*/
					
					semaphore.release();
				}
			}

		} catch (IOException | InterruptedException e) {
			System.err.println("erro na ligaçao " + connection + ": " + e.getMessage());
		}
	}
	
	
}