package guiAluno.resultpanel;

import javax.swing.JButton;

public class ResultGestor {
	
	private ResultPanel panel;
	
	public ResultGestor() {
		panel = new ResultPanel();
		
	}
	
	public void setResultQuestions(String text) {
		panel.getAnswers().setText(text);
	}
	
	public JButton getReturnButton() {
		return panel.getBackButton();
	}
	
	public ResultPanel getResultPanel() {
		return this.panel;
	}
	
	public void showContent() {
		panel.createContent();
	}
	
	

}
