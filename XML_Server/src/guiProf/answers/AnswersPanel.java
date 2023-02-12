package guiProf.answers;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class AnswersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextArea answers;
	private JButton backButton;

	protected AnswersPanel() {
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		this.setBackground(new Color(40,40,40));
		
		paintText("Respostas:",15,-10,200,50,17);
		createAnswers();
		createReturnButton();
	}
	
	public void createContent() {
		paintText("Respostas:",15,-10,200,50,17);
		
		JScrollPane scroll = new JScrollPane(answers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(15, 30, 420,250);
		this.add(scroll);
		this.add(backButton);
	}

	// mostrar as perguntar
	private void createAnswers() {
		answers = new JTextArea();
		answers.setFont(new Font("Tahoma", Font.PLAIN, 13));
		answers.setEditable(false);
		answers.setBounds(15, 30, 420,250);
		JScrollPane scroll = new JScrollPane(answers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(15, 30, 420,250);
		this.add(scroll);

	}
	
	private void createReturnButton() {
		backButton = new JButton("Retornar");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		backButton.setBounds(320, 290, 120, 25);
		this.add(backButton);
	}

	// função para pintar texto e numa dada posição
	private void paintText(String text, int posX, int posY, int sizeX, int sizeY, int fontSize) {
		JLabel lblNewLabel = new JLabel(text);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, fontSize));
		lblNewLabel.setForeground(new Color(150, 55, 39));
		lblNewLabel.setBounds(posX, posY, sizeX, sizeY);
		this.add(lblNewLabel);

	}
	protected JTextArea getAnswers() {
		return this.answers;
	}
	
	public JButton getReturnButton() {
		return this.backButton;
	}

}
