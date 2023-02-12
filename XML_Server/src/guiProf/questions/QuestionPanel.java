package guiProf.questions;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class QuestionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField numberStudents;
	@SuppressWarnings("rawtypes")
	private JComboBox theme;
	private JTextField time;
	
	@SuppressWarnings("rawtypes")
	private JComboBox question;
	private JTextField[] opcs;
	private JCheckBox[] btns;

	private JButton allStudents;
	private JButton specificStudent;
	
	private JTextArea questions;
	private JButton submit;
	private JButton seeAnswers;

	protected QuestionPanel(String profName,String[] themes, String[] questions) {
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		opcs = new JTextField[6];
		btns = new JCheckBox[6];
		this.setBackground(new Color(40,40,40));

		paintText("Prof: " + profName, 5, 10, 340, 25, 20);
		paintText("Nº alunos:", 300, 10, 340, 25, 20);
		paintAlunos();
		
		paintText("Tema:", 15, 40, 340, 25, 18);
		createTheme(themes);
		paintText("Tempo:", 300, 40, 340, 25, 18);
		createTime();

		paintText("Pergunta:", 15, 70, 340, 25, 17);
		paintQuestion(questions);
		

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

		createSelectStudent();
		
		paintText("Perguntas adicionadas:", 15, 525, 340, 25, 18);
		createAnswers();
		
		createSubmitButton();
		createSeeAnswers();

	}
	
	//quando a janela é destruida voltar a criar os conteudos
	public void createContent(String profName) {
		paintText("Prof: " + profName, 5, 10, 340, 25, 20);
		paintText("Nº alunos:", 300, 10, 340, 25, 20);
		this.add(numberStudents);
		
		paintText("Tema:", 15, 40, 340, 25, 18);
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
		for(int i = 0;i<6;i++) {
			this.add(opcs[i]);
			this.add(btns[i]);
		}

		this.add(allStudents);
		this.add(specificStudent);
		
		paintText("Perguntas adicionadas:", 15,525, 340, 25, 18);
		JScrollPane scroll = new JScrollPane(questions,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(15, 550, 420, 80);
		this.add(scroll);
		
		this.add(submit);
		this.add(seeAnswers);
	}

	// pinta no canto nç alunos e a caixa com o número
	private void paintAlunos() {
		numberStudents = new JTextField();
		numberStudents.setEditable(false);
		numberStudents.setBounds(410, 15, 25, 20);
		numberStudents.setText("0");
		this.add(numberStudents);
	}

	//criar caixa com o tema
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createTheme(String[] text) {
		theme = new JComboBox(text);
		theme.setEditable(true);
		theme.setFont(new Font("Tahoma", Font.PLAIN, 13));
		theme.setBounds(75, 43, 140, 20);
		this.add(theme);
		
	}
	
	//criar caixa com o tempo
	private void createTime() {
		time= new JTextField();
		time.setFont(new Font("Tahoma", Font.PLAIN, 13));
		time.setBounds(370, 43, 65, 20);
		time.setEditable(false);
		this.add(time);
	}

	// pinta as perguntas e para escrever
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void paintQuestion(String[] questions) {
		question = new JComboBox(questions);
		question.setEditable(true);
		question.setFont(new Font("Tahoma", Font.PLAIN, 13));
		question.setBounds(15, 95, 420, 64);
		this.add(question);

	}

	// mostrar as opções de respostas
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

	// para mostrar os checkBox com resposta certa
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
		
		btns[0].setEnabled(false);
		btns[1].setEnabled(false);
		btns[2].setEnabled(false);
		btns[3].setEnabled(false);
		btns[4].setEnabled(false);
		btns[5].setEnabled(false);

		this.add(btns[0]);
		this.add(btns[1]);
		this.add(btns[2]);
		this.add(btns[3]);
		this.add(btns[4]);
		this.add(btns[5]);
	}

	//selector dos alunos
	private void createSelectStudent() {
		allStudents = new JButton("Todos os alunos");
		allStudents.setFont(new Font("Tahoma", Font.PLAIN, 17));
		allStudents.setBounds(15, 435, 205, 35);
		this.add(allStudents);

		specificStudent = new JButton("Aluno aleatório");
		specificStudent.setFont(new Font("Tahoma", Font.PLAIN, 17));
		specificStudent.setBounds(230, 435, 205, 35);
		this.add(specificStudent);
	}
	
	//mostrar as perguntar
	private void createAnswers() {
		questions = new JTextArea();
		questions.setFont(new Font("Tahoma", Font.PLAIN, 13));
		questions.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(questions,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(15, 550, 420, 80);
		this.add(scroll);
		
	}
	
	//butão de enviar as coisas
	private void createSubmitButton() {
		submit = new JButton("Submeter");
		submit.setFont(new Font("Tahoma", Font.PLAIN, 17));
		submit.setBounds(15, 640, 420, 35);
		this.add(submit);
		
	}
	
	//butao para ver respostas alunos
	private void createSeeAnswers() {
		seeAnswers = new JButton("Respostas alunos");
		seeAnswers.setFont(new Font("Tahoma", Font.PLAIN, 13));
		seeAnswers.setBounds(300, 685, 150, 25);
		this.add(seeAnswers);
	}


	// função para pintar texto e numa dada posição
	private void paintText(String text, int posX, int posY, int sizeX, int sizeY, int fontSize) {
		JLabel lblNewLabel = new JLabel(text);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, fontSize));
		lblNewLabel.setForeground(new Color(150, 55, 39));
		lblNewLabel.setBounds(posX, posY, sizeX, sizeY);
		this.add(lblNewLabel);

	}
	
	protected JTextField getNumberOfStudents() {
		return this.numberStudents;
	}
	
	@SuppressWarnings("rawtypes")
	protected JComboBox getTheme() {
		return this.theme;
	}
	
	protected JTextField getTime() {
		return this.time;
	}
	
	@SuppressWarnings("rawtypes")
	protected JComboBox getQuestion() {
		return this.question;
	}
	
	protected JTextField[] getOpcions() {
		return this.opcs;
	}
	
	protected JCheckBox[] getOpcionsCorrect() {
		return this.btns;
	}
	
	protected JButton getAllStudents() {
		return this.allStudents;
	}
	
	protected JButton getRandomStudent() {
		return this.specificStudent;
	}
	
	protected JTextArea getQuestionsAdded() {
		return this.questions;
	}
	
	public JButton getSubmitButton() {
		return this.submit;
	}
	
	public JButton getSeeStudentsAnswers() {
		return this.seeAnswers;
	}


}
