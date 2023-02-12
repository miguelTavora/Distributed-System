package guiAluno.answersPanel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AnswersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField theme;
	private JTextField time;
	private JTextArea question;
	private JTextField[] opcs;
	private JCheckBox[] btns;
	private JButton submit;
	private String alunName;
	private String number;

	protected AnswersPanel(String alunoName, String number) {
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		this.setBackground(new Color(40,40,40));
		opcs = new JTextField[6];
		btns = new JCheckBox[6];
		this.alunName = alunoName;
		this.number = number;
		
		paintText("Aluno: "+alunoName,10,10,200,25,19);
		paintText("Número: "+number,280,10,200,25,19);
		
		paintText("Tema: ",15,40,200,25,17);
		createTheme();
		paintText("Tempo:", 300, 40, 340, 25, 18);
		createTime();
		
		paintText("Pergunta:", 15, 70, 340, 25, 17);
		paintQuestion();
		
		paintText("Opções:", 20, 160, 100, 25, 17);
		paintText("Respostas:", 345, 160, 100, 25, 17);
		paintText("A:", 40, 190, 100, 25, 17);
		paintText("B:", 40, 230, 100, 25, 17);
		paintText("C:", 40, 270, 100, 25, 17);
		paintText("D:", 40, 310, 100, 25, 17);
		paintText("E:", 40, 350, 100, 25, 17);
		paintText("F:", 40, 390, 100, 25, 17);
		paintOptions();
		createCheckBox();
		
		createSubmitButton();

	}
	
	public void showContent() {
		paintText("Aluno: "+alunName,10,10,200,25,19);
		paintText("Número: "+number,280,10,200,25,19);
		
		paintText("Tema: ",15,40,200,25,17);
		this.add(theme);
		paintText("Tempo:", 300, 40, 340, 25, 18);
		this.add(time);
		
		paintText("Pergunta:", 15, 70, 340, 25, 17);
		this.add(question);
		
		paintText("Opções:", 20, 160, 100, 25, 17);
		paintText("Respostas:", 345, 160, 100, 25, 17);
		paintText("A:", 40, 190, 100, 25, 17);
		paintText("B:", 40, 230, 100, 25, 17);
		paintText("C:", 40, 270, 100, 25, 17);
		paintText("D:", 40, 310, 100, 25, 17);
		paintText("E:", 40, 350, 100, 25, 17);
		paintText("F:", 40, 390, 100, 25, 17);
		this.add(opcs[0]);
		this.add(opcs[1]);
		this.add(opcs[2]);
		this.add(opcs[3]);
		this.add(opcs[4]);
		this.add(opcs[5]);
		
		
		this.add(btns[0]);
		this.add(btns[1]);
		this.add(btns[2]);
		this.add(btns[3]);
		this.add(btns[4]);
		this.add(btns[5]);
		
		this.add(submit);

	}
	
	public void uncheckCheckBox() {
		btns[0].setSelected(false);
		btns[1].setSelected(false);
		btns[2].setSelected(false);
		btns[3].setSelected(false);
		btns[4].setSelected(false);
		btns[5].setSelected(false);
	}
	
	private void createTheme() {
		theme = new JTextField();
		theme.setFont(new Font("Tahoma", Font.PLAIN, 13));
		theme.setBounds(75, 43, 140, 20);
		theme.setEditable(false);
		this.add(theme);
	}
	
	private void createTime() {
		time= new JTextField();
		time.setFont(new Font("Tahoma", Font.PLAIN, 13));
		time.setBounds(370, 43, 65, 20);
		time.setEditable(false);
		this.add(time);
	}
	
	private void paintQuestion() {
		question = new JTextArea();
		question.setFont(new Font("Tahoma", Font.PLAIN, 13));
		question.setBounds(15, 95, 420, 64);
		question.setEditable(false);
		this.add(question);

	}
	
	private void paintOptions() {
		opcs[0] = new JTextField();
		opcs[0].setFont(new Font("Tahoma", Font.PLAIN, 13));
		opcs[0].setBounds(65, 190, 260, 25);
		opcs[0].setEditable(false);
		this.add(opcs[0]);

		opcs[1] = new JTextField();
		opcs[1].setFont(new Font("Tahoma", Font.PLAIN, 13));
		opcs[1].setBounds(65, 230, 260, 25);
		opcs[1].setEditable(false);
		this.add(opcs[1]);

		opcs[2] = new JTextField();
		opcs[2].setFont(new Font("Tahoma", Font.PLAIN, 13));
		opcs[2].setBounds(65, 270, 260, 25);
		opcs[2].setEditable(false);
		this.add(opcs[2]);

		opcs[3] = new JTextField();
		opcs[3].setFont(new Font("Tahoma", Font.PLAIN, 13));
		opcs[3].setBounds(65, 310, 260, 25);
		opcs[3].setEditable(false);
		this.add(opcs[3]);
		
		opcs[4] = new JTextField();
		opcs[4].setFont(new Font("Tahoma", Font.PLAIN, 13));
		opcs[4].setBounds(65, 350, 260, 25);
		opcs[4].setEditable(false);
		this.add(opcs[4]);
		
		opcs[5] = new JTextField();
		opcs[5].setFont(new Font("Tahoma", Font.PLAIN, 13));
		opcs[5].setBounds(65, 390, 260, 25);
		opcs[5].setEditable(false);
		this.add(opcs[5]);

	}
	
	private void createCheckBox() {
		btns[0] = new JCheckBox();
		btns[0].setBounds(380, 190, 20, 20);

		btns[1] = new JCheckBox();
		btns[1].setBounds(380, 230, 20, 20);

		btns[2] = new JCheckBox();
		btns[2].setBounds(380, 270, 20, 20);

		btns[3] = new JCheckBox();
		btns[3].setBounds(380, 310, 20, 20);
		
		btns[4] = new JCheckBox();
		btns[4].setBounds(380, 350, 20, 20);
		
		btns[5] = new JCheckBox();
		btns[5].setBounds(380, 390, 20, 20);

		this.add(btns[0]);
		this.add(btns[1]);
		this.add(btns[2]);
		this.add(btns[3]);
		this.add(btns[4]);
		this.add(btns[5]);
	}
	
	private void createSubmitButton() {
		submit = new JButton("Submeter");
		submit.setFont(new Font("Tahoma", Font.PLAIN, 17));
		submit.setBounds(15, 440, 420, 35);
		this.add(submit);
		
	}
	
	
	// função para pintar texto e numa dada posição
	private void paintText(String text, int posX, int posY, int sizeX, int sizeY, int fontSize) {
		JLabel lblNewLabel = new JLabel(text);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, fontSize));
		lblNewLabel.setForeground(new Color(150, 55, 39));
		lblNewLabel.setBounds(posX, posY, sizeX, sizeY);
		this.add(lblNewLabel);

	}
	
	protected JTextField getThemeText() {
		return this.theme;
	}
	protected JTextField getTimeText() {
		return this.time;
	}
	protected JTextArea getQuestionArea() {
		return this.question;
	}
	protected JTextField[] getOpcions() {
		return this.opcs;
	}
	public JCheckBox[] getSelectedOpcions() {
		return this.btns;
	}
	
	public JButton getSubmitButton() {
		return this.submit;
	}

}
