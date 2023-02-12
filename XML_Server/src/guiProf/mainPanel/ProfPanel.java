package guiProf.mainPanel;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import PathAndExpressions.Path;

import javax.swing.JTextField;

public class ProfPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String fileName;
	private JTextField textField;

	protected ProfPanel(String fileName) {
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		this.fileName = fileName;

		addTextField();
		
	}

	private void addTextField() {
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textField.setBounds(80, 115, 240, 30);
		this.add(textField);
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(new ImageIcon(Path.pathImgs + fileName).getImage(), 0, 0, null);

	}
	
	public JTextField getNameTextField() {
		return this.textField;
	}
	
}
