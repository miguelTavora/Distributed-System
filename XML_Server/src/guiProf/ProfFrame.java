package guiProf;

import javax.swing.JFrame;

import guiProf.answers.AnswersPanel;
import guiProf.mainPanel.ProfPanel;
import guiProf.questions.QuestionPanel;

public class ProfFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//cria a frame
	public ProfFrame(ProfPanel profPanel) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 339);

		createPanel(profPanel);
		

	}
	//onde cria o painel e chamar o listener
	private void createPanel(ProfPanel profPanel) {
		setContentPane(profPanel);
	}
	
	

	//faz a troca entre a frame de escrever o nome do prof e o painel das perguntas
	protected void updateProgress(QuestionPanel questionPanel){
		this.getContentPane().removeAll();
		this.repaint();
		setBounds(100, 100, 470, 760);
		
		setContentPane(questionPanel);
	}
	
	//faz troca da frame entre perguntas e respostas alunos
	protected void updateProcessAnswers(AnswersPanel answersPanel) {
		this.getContentPane().removeAll();
		this.repaint();
		setBounds(100, 100, 460, 360);
		
		setContentPane(answersPanel);
	}
}
