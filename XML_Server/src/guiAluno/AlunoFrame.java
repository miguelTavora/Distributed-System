package guiAluno;

import javax.swing.JFrame;

import guiAluno.answersPanel.AnswersPanel;
import guiAluno.dataPanel.AlunoPanelGestor;
import guiAluno.emptyPanel.EmptyPanel;
import guiAluno.resultpanel.ResultPanel;

public class AlunoFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	// Cria a frame
	public AlunoFrame(AlunoPanelGestor alunoPanel) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 339);

		createPanel(alunoPanel);

	}

	// onde cria o painel e chamar o listener
	protected void createPanel(AlunoPanelGestor alunoPanel) {
		setContentPane(alunoPanel.getAlunoPanel());
	}

	// faz a troca entre a frame de escrever o nome do prof e o painel das perguntas
	protected void updateProgress(AnswersPanel answersPanel) {
		this.getContentPane().removeAll();
		this.repaint();

		setBounds(100, 100, 470, 530);

		setContentPane(answersPanel);
	}

	protected void updateProgressEmpty(EmptyPanel empty) {
		this.getContentPane().removeAll();
		this.repaint();

		setBounds(100, 100, 450, 339);

		setContentPane(empty);
	}

	protected void updateProgressResult(ResultPanel result) {
		this.getContentPane().removeAll();
		this.repaint();

		setBounds(100, 100, 460, 360);

		setContentPane(result);
	}

}
