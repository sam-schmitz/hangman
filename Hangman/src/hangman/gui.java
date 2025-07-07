package hangman;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class gui extends JFrame {
	private final String[] wordList =  {"dog", "cat", "bird", "pizza", "cheese", "hamburger", "hotdog", "boat"};
	private final String secretWord = wordList[new Random().nextInt(wordList.length)];
	private final char[] displayWord = new char[secretWord.length()];
	private final Set<Character> wrongGuesses = new HashSet<>();
	private int wrongGuessCount = 0;
	
	private JLabel wordLabel;
	private JLabel attemptsLabel;
	private JLabel wrongLabel;
	private JTextField guessInput;
	private JButton guessButton;
	
	private HangmanCanvas canvas;
	
	public void setWrongGuessCount(int count) {
		this.wrongGuessCount = count;	
		canvas.setWrongGuessCount(count);
	}
		
	
	public gui() {
		Arrays.fill(displayWord, '_');
		
		setTitle("Hangman Game");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		canvas = new HangmanCanvas();
		canvas.setPreferredSize(new Dimension(250, 250));		
		add(canvas, BorderLayout.CENTER);	
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(4, 1));		
		
		wordLabel = new JLabel("Word: " + String.valueOf(displayWord), SwingConstants.CENTER);
		attemptsLabel = new JLabel("Attempts left: " + (6 - wrongGuessCount), SwingConstants.CENTER);
		wrongLabel = new JLabel("Wrong guesses: ", SwingConstants.CENTER);
		
		controlPanel.add(wordLabel);
		controlPanel.add(attemptsLabel);
		controlPanel.add(wrongLabel);	
		
		JPanel guessPanel = new JPanel(new BorderLayout());		
		
		guessInput = new JTextField();
		guessButton = new JButton("Guess");
		
		guessButton.addActionListener(e -> handleGuess());
		guessInput.addActionListener(e -> handleGuess());
		
		guessPanel.add(guessInput, BorderLayout.CENTER);
		guessPanel.add(guessButton, BorderLayout.EAST);
		
		controlPanel.add(guessPanel);
		
		controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		add(controlPanel, BorderLayout.SOUTH);		
		
		pack();
		setVisible(true);
		updateDisplay();
	}
	
	private void handleGuess() {
		String input = guessInput.getText().toLowerCase();
		guessInput.setText("");
		
		if (input.length() == 0) {
			JOptionPane.showMessageDialog(this, "Please enter a letter. ");
			return;
		}
		
		if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
			JOptionPane.showMessageDialog(this, "Please enter a single letter. ");
			return;
		}
		
		char guess = input.charAt(0);
		if (wrongGuesses.contains(guess) || new String(displayWord).indexOf(guess) >= 0) {
			JOptionPane.showMessageDialog(this, "You already guessed that letter. ");
			return;
		}
		
		boolean correct = false;
		for (int i = 0; i < secretWord.length(); i++) {
			if (secretWord.charAt(i) == guess) {
				displayWord[i] = guess;
				correct = true;
			}
		}
		
		if (!correct) {
			wrongGuesses.add(guess);
			setWrongGuessCount(wrongGuessCount + 1);
		}
		
		updateDisplay();
		
		if (new String(displayWord).equals(secretWord)) {
			JOptionPane.showMessageDialog(this, "You won! The word was: " + secretWord);
			guessButton.setEnabled(false);
		} else if (wrongGuessCount == 6) {
			JOptionPane.showMessageDialog(this, "Game over! The word was: " + secretWord);
			guessButton.setEnabled(false);
		}
	}
	
	private void updateDisplay() {
		StringBuilder spacedWord = new StringBuilder();
		
		for (char c : displayWord) {
			spacedWord.append(c).append(' ');
		}
		wordLabel.setText("Word: " + spacedWord.toString().trim());
		attemptsLabel.setText("Attempts left: " + (6 - wrongGuessCount));
		wrongLabel.setText("Wrong guesses: " + wrongGuesses.toString());
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(gui::new);
	}
	
}
