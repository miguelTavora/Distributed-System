package guiAluno.emptyPanel;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import PathAndExpressions.Path;

public class EmptyPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private String fileName;
	
	public EmptyPanel(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(new ImageIcon(Path.pathImgs + fileName).getImage(), 0, 0, null);

	}

}
