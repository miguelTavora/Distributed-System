package guiAluno.dataPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import PathAndExpressions.Path;

public class AlunoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField[] textFields;
	private JButton proceed;
	private String fileName;

	public AlunoPanel(String fileName) {
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		this.fileName = fileName;
		textFields = new JTextField[3];

		paintText("Nome:", 10, 60, 100, 30, 22);
		addNameTextField();
		paintText("Número:", 10, 100, 100, 30, 22);
		addNumberTextField();
		paintText("Data Nascimento:", 10, 140, 200, 30, 22);
		addBirthTextField();
		addProceedBtn();
	}

	// mostrar o textField
	private void addNameTextField() {
		textFields[0] = new JTextField();
		textFields[0].setFont(new Font("Tahoma", Font.PLAIN, 17));
		textFields[0].setBounds(90, 63, 240, 30);
		this.add(textFields[0]);

	}

	// mostrar o textField
	private void addNumberTextField() {
		textFields[1] = new JTextField();
		textFields[1].setFont(new Font("Tahoma", Font.PLAIN, 17));
		textFields[1].setBounds(115, 103, 215, 30);
		this.add(textFields[1]);
	}

	// mostrar o textField
	private void addBirthTextField() {
		textFields[2] = new JTextField();
		textFields[2].setFont(new Font("Tahoma", Font.PLAIN, 17));
		textFields[2].setBounds(210, 143, 120, 30);
		this.add(textFields[2]);
	}
	
	private void addProceedBtn() {
		proceed = new JButton("Continuar");
		proceed.setFont(new Font("Tahoma", Font.PLAIN, 13));
		proceed.setBounds(310, 270, 130, 25);
		this.add(proceed);
	}

	// mostrar background
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(new ImageIcon(Path.pathImgs + fileName).getImage(), 0, 0, null);

	}

	// função para pintar texto e numa dada posição
	private void paintText(String text, int posX, int posY, int sizeX, int sizeY, int fontSize) {
		JLabel lblNewLabel = new JLabel(text);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, fontSize));
		lblNewLabel.setForeground(new Color(150, 55, 39));
		lblNewLabel.setBounds(posX, posY, sizeX, sizeY);
		this.add(lblNewLabel);

	}

	// ir buscar o texto
	protected JTextField[] getTextFields() {
		return this.textFields;
	}
	
	public JButton getButtonProceed() {
		return this.proceed;
	}

}
