package guiProf.mainPanel;

import javax.swing.JTextField;

public class ProfPanelGestor {
	
	private ProfPanel profPanel;
	
	public ProfPanelGestor(String fileName) {
		profPanel = new ProfPanel(fileName);
		
	}
	
	public JTextField getNameTextField() {
		return this.profPanel.getNameTextField();
	}
	
	public ProfPanel getProfPanel() {
		return this.profPanel;
	}

}
