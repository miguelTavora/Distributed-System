package guiProf.answers;

public class AnswersGestor {
	
	private AnswersPanel answersPanel;
	private String answers;
	private XMLReader xml;
	
	public AnswersGestor() {
		answersPanel = new AnswersPanel();
		xml = new XMLReader();
	}
	
	private void setTextToPainel(String text) {
		answersPanel.getAnswers().setText(text);
	}
	
	public AnswersPanel getAnswersPanel() {
		return this.answersPanel;
	}
	
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	
	public void setContent() {
		if(answers != null && answers != "") {
			setTextToPainel(xml.setTextToScroll(answers));
		}
	}

}
