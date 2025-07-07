package hangman;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
import java.util.*;

public class gui extends JFrame {
	private final String[] wordList =  {"dog", "cat", "bird", "pizza", "cheese", "hamburger", "hotdog", "boat"};
	private final String secretWord = wordList[new Random().nextInt(wordList.length)];
	private final char[] displayWord = new char[secretWord.length()];
	private final Set<Character> wrongGuesses = new HashSet<>();
	private int attemptsLeft = 6;
	
	private JLabel wordLabel;
	private JLabel attemptsLabel;
	private JLabel wrongLabel;
	private JTextField guessInput;
	private JButton guessButton;
	
	public gui() {
		Arrays.fill(displayWord, '_');
		
		setTitle("Hangman Game");
		setSize(400, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(5, 1));
		
		wordLabel = new JLabel("Word: " + String.valueOf(displayWord), SwingConstants.CENTER);
		attemptsLabel = new JLabel("Attempts left: " + attemptsLeft, SwingConstants.CENTER);
		wrongLabel = new JLabel("Wrong guesses: ", SwingConstants.CENTER);
		guessInput = new JTextField();
		guessButton = new JButton("Guess");
		
		guessButton.addActionListener(e -> handleGuess());
		
		add(wordLabel);
		add(attemptsLabel);
		add(wrongLabel);
		add(guessInput);
		add(guessButton);
		
		setVisible(true);
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
			attemptsLeft--;
		}
		
		updateDisplay();
		
		if (new String(displayWord).equals(secretWord)) {
			JOptionPane.showMessageDialog(this, "You won! The word was: " + secretWord);
			guessButton.setEnabled(false);
		} else if (attemptsLeft == 0) {
			JOptionPane.showMessageDialog(this, "Game over! The word was: " + secretWord);
			guessButton.setEnabled(false);
		}
	}
	
	private void updateDisplay() {
		wordLabel.setText("Word: " + String.valueOf(displayWord));
		attemptsLabel.setText("Attempts left: " + attemptsLeft);
		wrongLabel.setText("Wrong guesses: " + wrongGuesses.toString());
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(gui::new);
	}
	
}
