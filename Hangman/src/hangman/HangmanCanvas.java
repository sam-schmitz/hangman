package hangman;

import javax.swing.*;
import java.awt.*;

public class HangmanCanvas extends JPanel{
	private int wrongGuessCount = 0;
	
	public void setWrongGuessCount(int count) {
		this.wrongGuessCount = count;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw the gallow
		g.drawLine(50, 250, 150, 250);
		g.drawLine(100, 250, 100, 50);
		g.drawLine(100, 50, 200, 50);
		g.drawLine(200, 50, 200, 80);
		
		if (wrongGuessCount >= 1) g.drawOval(175, 80, 50, 50);
		if (wrongGuessCount >= 2) g.drawLine(200, 130, 200, 190);
		if (wrongGuessCount >= 3) g.drawLine(200, 140, 170, 170);
		if (wrongGuessCount >= 4) g.drawLine(200, 140, 230, 170);
		if (wrongGuessCount >= 5) g.drawLine(200, 190, 170, 230);
		if (wrongGuessCount >= 6) g.drawLine(200, 190, 230, 230);
	}
}
