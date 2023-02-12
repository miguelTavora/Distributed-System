package guiAluno.dataPanel;

import javax.swing.JButton;

public class AlunoPanelGestor {
	
	private AlunoPanel alunoPanel;
	
	public AlunoPanelGestor(String fileName) {
		alunoPanel = new AlunoPanel(fileName);
		
	}
	
	public String[] getTextFromFields() {
		String[] strgs = new String[3];
		
		strgs[0] = alunoPanel.getTextFields()[0].getText();
		strgs[1] = alunoPanel.getTextFields()[1].getText();
		strgs[2] = alunoPanel.getTextFields()[2].getText();
		
		return strgs;
	}
	
	public AlunoPanel getAlunoPanel() {
		return this.alunoPanel;
	}
	
	
	public JButton getProceedButton() {
		return alunoPanel.getButtonProceed();
	}

}
