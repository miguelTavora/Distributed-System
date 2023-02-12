package guiAluno;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import PathAndExpressions.Expression;
import clienteAluno.ClienteAluno;
import clienteAluno.DataDynamicAluno;
import guiAluno.answersPanel.AnswersPanelGestor;
import guiAluno.dataPanel.AlunoPanelGestor;
import guiAluno.emptyPanel.EmptyPanel;
import guiAluno.resultpanel.ResultGestor;
import guiAluno.answersPanel.XMLRead;
import xml.XMLReadWrite;

public class AlunoGestor {

	private AlunoFrame frame;
	private EmptyPanel empty;
	private AlunoPanelGestor alunoPanelGestor;
	private AnswersPanelGestor answerPanel;
	private ResultGestor result;
	private String name;
	private String number;
	private String warningString;
	private String resultOfRequest;
	private DataDynamicAluno data;
	private boolean inside;
	private long timer;
	private boolean stopTimer;

	public AlunoGestor(DataDynamicAluno data) {
		alunoPanelGestor = new AlunoPanelGestor("aluno.png");
		frame = new AlunoFrame(alunoPanelGestor);
		getTextFromField(alunoPanelGestor);
		warningString = "";
		this.data = data;
		inside = false;
		onCloseText();

	}

	// para quando se clica no X avisar o server que já não esta conectado
	private void onCloseText() {
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				String st = Expression.disconnectMessage;
				st = st + "<aluno numero=\"" + number + "\"/>";
				st = st + Expression.disconnectMessageEnd;
				ClienteAluno.xmlQuest = st;
				try {
					Thread.sleep(100);
					System.exit(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
	}

	private void getTextFromField(AlunoPanelGestor alunoPanel) {
		alunoPanel.getProceedButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkDataFromField(alunoPanel)) {
					name = alunoPanelGestor.getTextFromFields()[0];
					number = alunoPanelGestor.getTextFromFields()[1];
					answerPanel = new AnswersPanelGestor(name, number, data);
					empty = new EmptyPanel("empty.png");
					result = new ResultGestor();

					String st = Expression.requestForQuestions + "<aluno numero=\"" + number + "\"/>";
					st = st + "<pergunta indice=\"" + data.getIndexQuestion() + "\"/>"
							+ Expression.requestForQuestionsEnd;
					ClienteAluno.xmlQuest = st;
					
					try {
						Thread.sleep(200);
						if (!resultOfRequest.equals(Expression.questionSectionEmpty)) {
							answerPanel.setXMLQuest(resultOfRequest);
							answerPanel.setContent();
							createLaunchTimer();

						}
						notifyAlive();
						stateMachine(changerStateInformation(resultOfRequest));

					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(frame, warningString, "Algo correu mal", JOptionPane.WARNING_MESSAGE);
					warningString = "";
				}

			}
		});
	}

	// listener para quando se clica no botão submeter
	private void getSubmitContent(AnswersPanelGestor alunoPanel) {
		alunoPanel.getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClienteAluno.xmlQuest = createSubmitXML(alunoPanel.getAnswers());
				stopTimer = true;
				data.questionAnswered();
				try {
					Thread.sleep(100);
					String st = Expression.requestForQuestions + "<aluno numero=\"" + number + "\"/>";
					st = st + "<pergunta indice=\"" + data.getIndexQuestion() + "\"/>"
							+ Expression.requestForQuestionsEnd;
					ClienteAluno.xmlQuest = st;
					Thread.sleep(400);
					stateMachineSecond(changerStateInformation(resultOfRequest));
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void getReturnToQuestions(ResultGestor result) {
		result.getReturnButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String st = Expression.requestForQuestions + "<aluno numero=\"" + number + "\"/>";
				st = st + "<pergunta indice=\"" + data.getIndexQuestion() + "\"/>" + Expression.requestForQuestionsEnd;
				ClienteAluno.xmlQuest = st;
				try {
					Thread.sleep(300);
					stateMachineSecond(changerStateInformation(resultOfRequest));
					if (resultOfRequest.equals(Expression.goToResults))
						JOptionPane.showMessageDialog(frame, "Ainda não existem novas questões", "Algo correu mal",
								JOptionPane.WARNING_MESSAGE);

				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			}
		});
	}

	private String changerStateInformation(String str) {
		if (str.equals(Expression.questionSectionEmpty)) {
			return str;
		} else if (str.substring(0, 13).equals("<pergunta id=")) {
			return Expression.questionSectionSomething;
		} else if (str.equals(Expression.goToResults)) {
			return Expression.sendAnswers;
		}
		return "";
	}

	//state machine para antes receber uma pergunta
	private void stateMachine(String state) {
		switch (state) {
		case Expression.questionSectionEmpty:
			frame.updateProgressEmpty(empty);
			checkForUpdates();
			break;
		case Expression.questionSectionSomething:
			frame.updateProgress(answerPanel.getAnswersPanel());
			getSubmitContent(answerPanel);
			break;
		}
	}

	//state machine após receber 1 pergunta
	private void stateMachineSecond(String state) {
		switch (state) {
		case Expression.questionSectionSomething:
			frame.updateProgress(answerPanel.getAnswersPanel());
			answerPanel.setXMLQuest(resultOfRequest);
			answerPanel.setContent();
			createLaunchTimer();
			answerPanel.showComponents();
			answerPanel.uncheckBoxes();
			
			break;
		case Expression.sendAnswers:
			ClienteAluno.xmlQuest = createResultXML();
			frame.updateProgressResult(result.getResultPanel());
			result.showContent();
			if (!inside) {
				getReturnToQuestions(result);
				inside = true;
			}
			break;
		}
	}

	// função para perguntar por uma determinada pergunta pelo indice
	private void checkForUpdates() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					try {
						String st = Expression.requestForQuestions + "<aluno numero=\"" + number + "\"/>";
						st = st + "<pergunta indice=\"" + data.getIndexQuestion() + "\"/>"
								+ Expression.requestForQuestionsEnd;
						ClienteAluno.xmlQuest = st;
						Thread.sleep(2000);
						if (!resultOfRequest.equals(Expression.questionSectionEmpty)) {
							answerPanel.setXMLQuest(resultOfRequest);
							answerPanel.setContent();
							frame.updateProgress(answerPanel.getAnswersPanel());
							createLaunchTimer();
							getSubmitContent(answerPanel);
							break;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

	// função para avisar o servidor que está conectado
	private void notifyAlive() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					try {
						ClienteAluno.xmlQuest = createNotifyXML(number);// notificar que o aluno com nºX esta vivo
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

	}
	
	private void createLaunchTimer() {
		this.timer = System.nanoTime();
		long totalTime = Long.parseLong(XMLRead.getTimeFromXML(XMLRead.clearExtraString(resultOfRequest)));
		stopTimer = false;
		answerPanel.setTime(XMLRead.getTimeFromXML(XMLRead.clearExtraString(resultOfRequest)));
		System.out.println("AIII");
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					long currentTime = (System.nanoTime() - timer) / 1000000000;
					if(stopTimer) {
						System.out.println("TIMER STOPPED");
						stopTimer = false;
						break;
					}
					
					if (currentTime > totalTime) {
						ClienteAluno.xmlQuest = createSubmitXML(answerPanel.getAnswers());
						data.questionAnswered();
						try {
							Thread.sleep(100);
							String st = Expression.requestForQuestions + "<aluno numero=\"" + number + "\"/>";
							st = st + "<pergunta indice=\"" + data.getIndexQuestion() + "\"/>"
									+ Expression.requestForQuestionsEnd;
							ClienteAluno.xmlQuest = st;
							Thread.sleep(400);
							stateMachineSecond(changerStateInformation(resultOfRequest));
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						break;
					} else {
						long timeLeft = totalTime - currentTime;
						answerPanel.setTime("" + timeLeft);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();

	}

	// função para pintar os resultados do aluno às perguntas
	public void setResultTextToBoard(String xml) {
		Document doc = XMLReadWrite.documentFromString(xml);

		String numero = doc.getElementsByTagName("aluno").item(0).getAttributes().getNamedItem("numero").getNodeValue();

		if (numero.equals(number)) {
			NodeList resposta = doc.getElementsByTagName("pergunta");
			String st = "";
			for (int i = 0; i < resposta.getLength(); i++) {
				st = "Resposta à pergunta com id:" + doc.getElementsByTagName("pergunta").item(i).getAttributes()
						.getNamedItem("id").getNodeValue();
				if (resposta.item(i).hasChildNodes()) {
					Element elemCat = (Element) resposta.item(i);
					NodeList res = elemCat.getElementsByTagName("resposta");
					for (int j = 0; j < res.getLength(); j++) {
						if (j == 0)
							st = st + " com resposta:"
									+ res.item(j).getAttributes().getNamedItem("correcao").getNodeValue();
						else
							st = st + ", " + res.item(j).getAttributes().getNamedItem("correcao").getNodeValue();
						if (j == res.getLength() - 1)
							st = st + "\n";
					}
				} else {
					st = st + " Não respondeu\n";
				}
			}
			result.setResultQuestions(st);
		}

	}

	// cria o XML para notificar o server que está vivo
	private String createNotifyXML(String number) {
		String notify = Expression.notifyAlive;
		notify = notify + "<aluno numero=\"" + number + "\"/>" + Expression.notifyAliveEnd;
		return notify;
	}

	private String createSubmitXML(int[] response) {
		String submit = Expression.submitQuestion;
		submit = submit + "<pergunta id=\"" + data.getIdOfQuestion(data.getIndexQuestion()) + "\"" + " aluno=\""
				+ number + "\">";
		for (int i = 0; i < response.length; i++) {
			submit = submit + "<resposta indice=\"" + response[i] + "\"/>";
		}
		submit = submit + "</pergunta>";
		return submit + Expression.submitQuestionEnd;

	}

	private String createResultXML() {
		String st = Expression.requestForResults;
		st = st + "<aluno numero=\"" + number + "\"/>";
		st = st + Expression.requestForResultsEnd;
		return st;
	}

	// verifica se os campos estão todos preenchidos
	private boolean checkDataFromField(AlunoPanelGestor alunoPanel) {
		boolean[] check = new boolean[3];

		if (alunoPanel.getTextFromFields()[0].length() > 2) {
			check[0] = true;
		}

		if (alunoPanel.getTextFromFields()[1].matches("[0-9]+")
				&& Integer.parseInt(alunoPanel.getTextFromFields()[1]) < 50000) {
			check[1] = true;
		}
		if (alunoPanel.getTextFromFields()[2].length() > 10) {
			check[2] = true;
		}
		int count = 0;
		for (int i = 0; i < 3; i++) {
			if (check[i] == true) {
				count++;
			}
			if (count == 3) {
				return true;
			}
		}
		createString(check);
		return false;

	}

	// caso não esteja td envia a mensagem a dizer o que falta
	private void createString(boolean[] check) {
		if (check[0] == false) {
			warningString = "O nome não possui pelo menos 3 caracteres\n";
		}
		if (check[1] == false) {
			warningString = warningString + "O número não possui apenas caracteres ou é superior a 50000\n";
		}
		if (check[2] == false) {
			warningString = warningString + "A data não possui pelo menos 10 caracteres";
		}
	}

	public void showFrame() {
		frame.setVisible(true);
	}

	public void setResquestOfQuestion(String response) {
		this.resultOfRequest = response;
	}

}
