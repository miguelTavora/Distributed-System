package guiProf.questions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import PathAndExpressions.Expression;
import clienteProf.ClienteProfessor;
import clienteProf.DataDynamicProf;

public class QuestionGestor {

	private final String beginProtocol = Expression.putQuestionOnList + "<pedido operacao=\"";
	private final String afterOperation = "\"><pergunta id=\"";
	private final String afterId = "\" aluno=\"";
	private final String endProtocol = "\"/></pedido></protocolo>";
	private final String allStudents = "all";

	private QuestionPanel panel;
	private XMLReader xml;
	private String warningString;
	private String answersString;
	private DataDynamicProf data;

	public QuestionGestor(String profName, DataDynamicProf data)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		xml = new XMLReader(ClienteProfessor.getDocument());
		panel = new QuestionPanel(profName, xml.getThemes(), xml.getQuestions(xml.getThemes()[0]));
		this.data = data;

		warningString = "";
		answersString = "";

		pressAllStudents();
		pressRandomStudent();
		pressSubmit();
		changeTheme();
		changeQuestion();

		setTime(xml.getTime(String.valueOf(panel.getTheme().getSelectedItem()),
				String.valueOf(panel.getQuestion().getSelectedItem())));
		setOpcions(xml.getOpcions(String.valueOf(panel.getTheme().getSelectedItem()),
				String.valueOf(panel.getQuestion().getSelectedItem())));
		setCorrectOpcions(xml.getCorrectOpcions(String.valueOf(panel.getTheme().getSelectedItem()),
				String.valueOf(panel.getQuestion().getSelectedItem())));

	}

	// quando se clica em todos os estudante muda a cor para depois se saber
	protected void pressAllStudents() {
		panel.getAllStudents().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.getAllStudents().setBackground(new Color(89, 182, 91));
				panel.getRandomStudent().setBackground(new Color(190, 55, 39));
			}
		});
	}

	protected void pressRandomStudent() {
		panel.getRandomStudent().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.getAllStudents().setBackground(new Color(190, 55, 39));
				panel.getRandomStudent().setBackground(new Color(89, 182, 91));

			}
		});
	}

	//listener para o submit
	public void pressSubmit() {
		panel.getSubmitButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkWriteQuestion()) {
					JOptionPane.showMessageDialog(panel, warningString, "Algo correu mal", JOptionPane.WARNING_MESSAGE);
					ClienteProfessor.xmlProtocol = "";
				} else {
					buildStringToScroll();
					if (panel.getAllStudents().getBackground().getRed() == 89) {
						ClienteProfessor.xmlProtocol = getXMLText(
								xml.getIdOfQuestionSelected(String.valueOf(panel.getTheme().getSelectedItem()),
										String.valueOf(panel.getQuestion().getSelectedItem())),
								allStudents);
						System.out.println(ClienteProfessor.xmlProtocol);
					} else {
						if (data.getNumberStudentsConnected() > 0) {
							ClienteProfessor.xmlProtocol = getXMLText(xml.getIdOfQuestionSelected(String.valueOf(panel.getTheme().getSelectedItem()),
											String.valueOf(panel.getQuestion().getSelectedItem())),createRandomStudent());// chama função da um random
							System.out.println(ClienteProfessor.xmlProtocol);
						}
						else {
							JOptionPane.showMessageDialog(panel, "Não existem alunos conectados, espere até se conectarem", "Algo correu mal", JOptionPane.WARNING_MESSAGE);
						}
					}

				}

			}
		});

	}
	//listener do scroll para os temas
	protected void changeTheme() {
		panel.getTheme().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clearCheckBox();
					setQuestions(xml.getQuestions(String.valueOf(panel.getTheme().getSelectedItem())));
					setTime(xml.getTime(String.valueOf(panel.getTheme().getSelectedItem()),
							String.valueOf(panel.getQuestion().getSelectedItem())));
					setOpcions(xml.getOpcions(String.valueOf(panel.getTheme().getSelectedItem()),
							String.valueOf(panel.getQuestion().getSelectedItem())));
					setCorrectOpcions(xml.getCorrectOpcions(String.valueOf(panel.getTheme().getSelectedItem()),
							String.valueOf(panel.getQuestion().getSelectedItem())));
				} catch (XPathExpressionException e1) {
					e1.printStackTrace();
				}

			}
		});
	}
	//listener do scroll para perguntas
	protected void changeQuestion() {
		panel.getQuestion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clearCheckBox();
					setTime(xml.getTime(String.valueOf(panel.getTheme().getSelectedItem()),
							String.valueOf(panel.getQuestion().getSelectedItem())));
					setOpcions(xml.getOpcions(String.valueOf(panel.getTheme().getSelectedItem()),
							String.valueOf(panel.getQuestion().getSelectedItem())));
					setCorrectOpcions(xml.getCorrectOpcions(String.valueOf(panel.getTheme().getSelectedItem()),
							String.valueOf(panel.getQuestion().getSelectedItem())));
				} catch (XPathExpressionException e1) {
					e1.printStackTrace();
				}

			}
		});
	}

	//cria XML para enviar pergunta
	private String getXMLText(String questionId, String studentNumber) {
		String st = beginProtocol;
		st = st + "enviarPergunta";
		st = st + afterOperation;
		st = st + questionId + afterId;
		st = st + studentNumber + endProtocol;
		return st;
	}

	// constroi a String para mostrar no painel perguntas adicionas
	private void buildStringToScroll() {
		String display = "Tema: " + String.valueOf(panel.getTheme().getSelectedItem());
		display = display + ", com " + panel.getTime().getText() + "segundos. ";
		display = display + "Pergunta: " + String.valueOf(panel.getQuestion().getSelectedItem());

		int count = 0;
		int correct = 0;
		for (int i = 0; i < 6; i++) {
			if (panel.getOpcions()[i].getText().length() > 1) {
				count++;
			}
			if (panel.getOpcionsCorrect()[i].isSelected()) {
				correct++;
			}
		}
		display = display + ", com " + count + " opções e " + correct + " resposta(s) correta(s).\n";

		answersString = answersString + display;

		panel.getQuestionsAdded().setText(answersString);

	}

	public boolean checkWriteQuestion() {
		boolean[] check = new boolean[4];

		// verificar o tempo se é so caracteres e tem menos de 4 caracteres
		String time = panel.getTime().getText().substring(3, panel.getTime().getText().length());
		if (time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
			check[0] = true;

		// verificar se meteram 3 opções
		int[] indexes = new int[6];
		int count = 0;
		for (int i = 0; i < 6; i++) {
			if (panel.getOpcions()[i].getText().length() > 1) {
				indexes[i] = 1;
				count++;
			}
		}
		if (count > 2) {
			check[1] = true;
		}

		// verificar se existe um check numa pergunta escrita
		for (int i = 0; i < 6; i++) {
			if (panel.getOpcionsCorrect()[i].isSelected() && indexes[i] == 1)
				check[2] = true;
		}

		if (panel.getAllStudents().getBackground().getRed() != 238) {
			check[3] = true;
		}

		// verificar tudo e construir a String para dar display
		int counter = 0;
		boolean inside = false;
		for (int i = 0; i < 4; i++) {
			if (check[i] == true) {
				counter++;
			} else {
				errorMessage(i, inside);
				inside = true;
			}
		}

		if (counter == 4)
			return true;

		return false;
	}

	@SuppressWarnings("unchecked")
	private void setQuestions(String[] questions) {
		@SuppressWarnings("rawtypes")
		DefaultComboBoxModel model = new DefaultComboBoxModel(questions);
		panel.getQuestion().setModel(model);
	}

	private void setTime(String time) {
		panel.getTime().setText(time);
	}

	private void setOpcions(String[] opcions) {
		for (int i = 0; i < opcions.length; i++) {
			panel.getOpcions()[i].setText(opcions[i]);
		}
	}

	private void setCorrectOpcions(int[] correct) {
		for (int i = 0; i < correct.length; i++) {
			panel.getOpcionsCorrect()[correct[i] - 1].setSelected(true);
		}
	}

	private void clearCheckBox() {
		for (int i = 0; i < 6; i++) {
			panel.getOpcionsCorrect()[i].setSelected(false);
			;
		}
	}

	// função que constroi a String para mostrar o que falta
	public void errorMessage(int index, boolean alreadyInside) {
		switch (index) {
		case 0:
			warningString = "-O tempo não possui apenas caracteres ou possui demasiado tempo\n";
			break;
		case 1:
			if (!alreadyInside)
				warningString = "-Não existem pelo menos 3 opções\n";
			else
				warningString = warningString + "-Não existem pelo menos 3 opções\n";
			break;
		case 2:
			if (!alreadyInside)
				warningString = "-Não existe nenhuma resposta ou a resposta não tem opção\n";
			else
				warningString = warningString + "-Não existe nenhuma resposta ou a resposta não tem opção\n";
			break;
		case 3:
			if (!alreadyInside)
				warningString = "-Não foi selecionado para quem será dirigada a pergunta\n";
			else
				warningString = warningString + "-Não foi selecionado para quem será dirigada a pergunta\n";
			break;

		}
	}

	//função seleciona um aluno aleatorio dos dentro da lista
	private String createRandomStudent() {
		Random rd = new Random();
		int indexRd = rd.nextInt(data.getNumberStudentsConnected());
		return data.getStudentsConnected()[indexRd];
	}

	public QuestionPanel getProfPanel() {
		return this.panel;
	}

	public JTextField getTextStudentsConnected() {
		return panel.getNumberOfStudents();
	}

}
