package hangman;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class gui extends JFrame {
	private final String[] wordList =  {	//list of possible words
			"lynx", "sync", "fly", 
			"gym", "fuzz", "quest",
			"zap", "query", "byte",
			"dog", "cat", "eat",
			"happy", "sad", "angry",
			"pizza", "cheese", "hotdog",
			"goat", "cow", "chicken",
			"mammal", "reptile", "insect",
			"dinosaur", "fish", "rodent",
			"basketball", "football", "soccer",
			"hangman", "man", "word",
			"plant", "water", "earth", 
			"shark", "ocean", "whale",
			"sky", "bird", "cloud",
			"apple", "beach", "chair",
			"drink", "house", "train"
			};
	private final String secretWord = wordList[new Random().nextInt(wordList.length)];	//randomly selects a word to be the secret word
	private final char[] displayWord = new char[secretWord.length()];
	private final Set<Character> wrongGuesses = new HashSet<>();
	private int wrongGuessCount = 0;
	
	// components to be displayed
	private JLabel wordLabel;
	private JLabel attemptsLabel;
	private JLabel wrongLabel;
	private JTextField guessInput;
	private JButton guessButton;
	
	private HangmanCanvas canvas;
	
	public void setWrongGuessCount(int count) {	//function to track the number of wrong guesses
		this.wrongGuessCount = count;	
		canvas.setWrongGuessCount(count);
	}
		
	
	public gui() {
		Arrays.fill(displayWord, '_');	//fill the display word with _ to show that the letter hasn't been guessed

		// default settings for the whole window
		setTitle("Hangman Game");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		// add a hang man canvas - displays the image of the man
		canvas = new HangmanCanvas();
		canvas.setPreferredSize(new Dimension(250, 250));		
		add(canvas, BorderLayout.CENTER);	
		
		// creates a panel to hold all of the text, inputs and controls
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(4, 1));		
		
		// convert game data to strings and attach them to the corresponding labels. 
		wordLabel = new JLabel("Word: " + String.valueOf(displayWord), SwingConstants.CENTER);
		attemptsLabel = new JLabel("Attempts left: " + (6 - wrongGuessCount), SwingConstants.CENTER);
		wrongLabel = new JLabel("Wrong guesses: ", SwingConstants.CENTER);
		
		// add the labels to the control panel
		controlPanel.add(wordLabel);
		controlPanel.add(attemptsLabel);
		controlPanel.add(wrongLabel);	
		
		// creates a panel for the input and associated button
		JPanel guessPanel = new JPanel(new BorderLayout());		
		
		guessInput = new JTextField();
		guessButton = new JButton("Guess");
				
		// adds listeners that activate the handle guess function when component is activated
		guessButton.addActionListener(e -> handleGuess());
		guessInput.addActionListener(e -> handleGuess());
		
		guessPanel.add(guessInput, BorderLayout.CENTER);
		guessPanel.add(guessButton, BorderLayout.EAST);
		
		controlPanel.add(guessPanel);
		
		controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		add(controlPanel, BorderLayout.SOUTH);		
		
		// makes the components formatted to the frame and visible
		pack();
		setVisible(true);
		updateDisplay();
	}
	
	private void handleGuess() {	//activates when a guess is made
		String input = guessInput.getText().toLowerCase();
		guessInput.setText("");
		
		if (input.length() == 0) {	//the guess was empty
			JOptionPane.showMessageDialog(this, "Please enter a letter. ");
			return;
		}
		
		if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {	//the guess is too long or not a letter
			JOptionPane.showMessageDialog(this, "Please enter a single letter. ");
			return;
		}
		
		char guess = input.charAt(0);
		if (wrongGuesses.contains(guess) || new String(displayWord).indexOf(guess) >= 0) {	//the guessed letter was already guessed
			JOptionPane.showMessageDialog(this, "You already guessed that letter. ");
			return;
		}
		
		// check to see if the guess was correct or not
		boolean correct = false;
		for (int i = 0; i < secretWord.length(); i++) {	//loops through the secret word to see if each letter matches the one guessed
			if (secretWord.charAt(i) == guess) {
				displayWord[i] = guess;
				correct = true;
			}
		}
		
		if (!correct) {	//the guess was incorrect so update the game data accordingly
			wrongGuesses.add(guess);
			setWrongGuessCount(wrongGuessCount + 1);
		}
		
		updateDisplay();
		
		// check if the game is over
		if (new String(displayWord).equals(secretWord)) {	// word was guessed correctly
			JOptionPane.showMessageDialog(this, "You won! The word was: " + secretWord);
			guessButton.setEnabled(false);
		} else if (wrongGuessCount == 6) {	//word was guessed incorrectly
			JOptionPane.showMessageDialog(this, "Game over! The word was: " + secretWord);
			guessButton.setEnabled(false);
		}
	}
	
	private void updateDisplay() {	//updates what is being displayed
		// adds spaces between the underscores and letters in the display word
		StringBuilder spacedWord = new StringBuilder();
		
		for (char c : displayWord) {
			spacedWord.append(c).append(' ');
		}
		
		// updates the labels with the new game data
		wordLabel.setText("Word: " + spacedWord.toString().trim());
		attemptsLabel.setText("Attempts left: " + (6 - wrongGuessCount));
		wrongLabel.setText("Wrong guesses: " + wrongGuesses.toString());
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(gui::new);
	}
	
}
