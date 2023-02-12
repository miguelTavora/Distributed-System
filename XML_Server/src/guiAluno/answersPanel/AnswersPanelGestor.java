package guiAluno.answersPanel;

import javax.swing.JButton;

import clienteAluno.DataDynamicAluno;

public class AnswersPanelGestor {

	
	private AnswersPanel answers;
	private XMLRead xml;
	private String xmlQuest;
	private DataDynamicAluno data;
	

	public AnswersPanelGestor(String alunoName, String number, DataDynamicAluno data) {
		answers = new AnswersPanel(alunoName, number);
		xml = new XMLRead();
		this.data = data;
	}

	public void setContent() {
		setTextTheme(xml.getThemeFromXML(xmlQuest));
		xml.setXMLQuest(xml.clearString(xmlQuest));
		setQuestionText(xml.getTextQuestion(xml.getXMLQuestion()));
		setQuestionsWithXML(xml.getTextOpcions(xml.getXMLQuestion()));
		disactivateOpcions(xml.getTextOpcions(xml.getXMLQuestion()).length);
		data.addQuestion(xml.getIdOfQuestion(xml.getXMLQuestion()));
	}

	private void setTextTheme(String text) {
		answers.getThemeText().setText(text);
	}

	public void setTime(String time) {
		answers.getTimeText().setText(time);
	}

	private void setQuestionText(String question) {
		answers.getQuestionArea().setText(question);
	}

	public int[] getAnswers() {
		int count = 0;
		int[] questions = new int[answers.getSelectedOpcions().length];
		for (int i = 0; i < answers.getSelectedOpcions().length; i++) {
			if (answers.getSelectedOpcions()[i].isSelected()) {
				questions[i] = i;
				count++;
			}
		}
		int[] selected = new int[count];
		int count2 = 0;
		for (int i = 0; i < answers.getSelectedOpcions().length; i++) {
			if (questions[i] != 0) {
				selected[count2] = questions[i];
				count2++;
			}
		}
		return selected;

	}

	private void setQuestionsWithXML(String[] strs) {
		switch (strs.length) {
		case 3:
			setQuestions(strs[0], strs[1], strs[2], "", "", "");
			break;
		case 4:
			setQuestions(strs[0], strs[1], strs[2], strs[3], "", "");
			break;
		case 5:
			setQuestions(strs[0], strs[1], strs[2], strs[3], strs[4], "");
			break;
		case 6:
			setQuestions(strs[0], strs[1], strs[2], strs[3], strs[4], strs[5]);
			break;
		}
	}

	private void disactivateOpcions(int size) {
		switch (size) {
		case 3:
			answers.getSelectedOpcions()[3].setEnabled(false);
			answers.getSelectedOpcions()[4].setEnabled(false);
			answers.getSelectedOpcions()[5].setEnabled(false);
			break;
		case 4:
			answers.getSelectedOpcions()[4].setEnabled(false);
			answers.getSelectedOpcions()[5].setEnabled(false);
			;
			break;
		case 5:
			answers.getSelectedOpcions()[5].setEnabled(false);
			;
			break;
		}
	}

	private void setQuestions(String q1, String q2, String q3, String q4, String q5, String q6) {
		answers.getOpcions()[0].setText(q1);
		answers.getOpcions()[1].setText(q2);
		answers.getOpcions()[2].setText(q3);
		answers.getOpcions()[3].setText(q4);
		answers.getOpcions()[4].setText(q5);
		answers.getOpcions()[5].setText(q6);
	}
	
	public void uncheckBoxes() {
		answers.uncheckCheckBox();
	}
	
	public void showComponents() {
		answers.showContent();
	}

	public JButton getButton() {
		return answers.getSubmitButton();
	}

	public AnswersPanel getAnswersPanel() {
		return this.answers;
	}

	public void setXMLQuest(String xml) {
		this.xmlQuest = xml;
	}

}
