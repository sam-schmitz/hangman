package hangman;

import javax.swing.*;
import java.awt.*;

public class HangmanCanvas extends JPanel{
	private int wrongGuessCount = 0;
	
	public void setWrongGuessCount(int count) {	//called by outside function in order to update the HangmanCanvas
		this.wrongGuessCount = count;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {	//draws the man and gallow
		super.paintComponent(g);

		// Draw the gallow
		g.drawLine(50, 250, 150, 250);
		g.drawLine(100, 250, 100, 50);
		g.drawLine(100, 50, 200, 50);
		g.drawLine(200, 50, 200, 80);
		
		// Draw the man
		if (wrongGuessCount >= 1) g.drawOval(175, 80, 50, 50);	//head
		if (wrongGuessCount >= 2) g.drawLine(200, 130, 200, 190);	//body
		if (wrongGuessCount >= 3) g.drawLine(200, 140, 170, 170);	// arm 1
		if (wrongGuessCount >= 4) g.drawLine(200, 140, 230, 170);	//arm 2
		if (wrongGuessCount >= 5) g.drawLine(200, 190, 170, 230);	//leg 1
		if (wrongGuessCount >= 6) g.drawLine(200, 190, 230, 230);	//leg 2
	}
}
