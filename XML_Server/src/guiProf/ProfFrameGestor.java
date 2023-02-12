package guiProf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import PathAndExpressions.Expression;
import clienteProf.ClienteProfessor;
import clienteProf.DataDynamicProf;
import guiProf.answers.AnswersGestor;
import guiProf.mainPanel.ProfPanelGestor;
import guiProf.questions.QuestionGestor;

public class ProfFrameGestor {

	private ProfFrame profFrame;
	private ProfPanelGestor profPanelGestor;
	private QuestionGestor questionGestor;
	private AnswersGestor answersGestor;
	private String profName;
	private String studentAnswers;
	private DataDynamicProf data;

	public ProfFrameGestor(DataDynamicProf data) {
		profPanelGestor = new ProfPanelGestor("prof.png");
		profFrame = new ProfFrame(profPanelGestor.getProfPanel());
		this.data = data;
		
		getTextFromField();
		//linha para ir buscar xml ao servidor
		ClienteProfessor.xmlProtocol = Expression.requestForXmlFile;

	}

	// listener para ir buscar o nome do professor e chama a função para atualizar
	protected void getTextFromField() {
		profPanelGestor.getProfPanel().getNameTextField().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (profPanelGestor.getProfPanel().getNameTextField().getText() != "" && profPanelGestor.getProfPanel().getNameTextField().getText().length() > 2) {
					try {
						questionGestor = new QuestionGestor(profPanelGestor.getProfPanel().getNameTextField().getText(),data);
					} catch (ParserConfigurationException | SAXException | IOException e1) {
						e1.printStackTrace();
					} catch (XPathExpressionException e1) {
						e1.printStackTrace();
					}
					profFrame.updateProgress(questionGestor.getProfPanel());
					profName = profPanelGestor.getProfPanel().getNameTextField().getText();
					getStudentsAnswers();
					requestForStudents();

				}
			}
		});
	}
	
	//listener para quando se clica no botão ver os resultados
	protected void getStudentsAnswers() {
		questionGestor.getProfPanel().getSeeStudentsAnswers().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClienteProfessor.xmlProtocol = Expression.requestForAnswers;
				if(answersGestor == null) {
					answersGestor = new AnswersGestor();
					returnToQuestions();
				}
				profFrame.updateProcessAnswers(answersGestor.getAnswersPanel());
				answersGestor.getAnswersPanel().createContent();
				try {
					//delay para esperar para mostrar os alunos, as vezes n funciona
					Thread.sleep(200);
					answersGestor.setAnswers(studentAnswers);
					answersGestor.setContent();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	//listener para voltar as perguntas
	protected void returnToQuestions() {
		answersGestor.getAnswersPanel().getReturnButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profFrame.updateProgress(questionGestor.getProfPanel());
				questionGestor.getProfPanel().createContent(profName);
			}
		});
	}
	
	public void showFrame() {
		profFrame.setVisible(true);
	}
	
	public JTextField getNumberStudentsField() {
		return questionGestor.getTextStudentsConnected();
	}
	
	//loop para is buscar os alunos conectados de 20 em 20 segundos
	private void requestForStudents() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					try {
						//20 em 20 segundos recebe qts alunos estao ligados
						Thread.sleep(20000);
						ClienteProfessor.xmlProtocol = Expression.requestForStudents;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public void setStudentAnswers(String answers) {
		this.studentAnswers = answers;
	}

}
