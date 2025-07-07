package hangman;

import java.util.Scanner;
import java.util.*;

public class HangmanGame {

	public static void main(String[] args) { 
		// draws a random word from the list to be guessed
		String[] wordList = {"dog", "cat", "bird", "pizza", "cheese", "hamburger", "hotdog", "boat"};
		String secretWord = wordList[(int) (Math.random() * wordList.length)];
		
		// creates guessed word to show how much of the word the user has guessed
		char[] guessedWord = new char[secretWord.length()];
		for (int i = 0; i < guessedWord.length; i++) {
			guessedWord[i] = '_';
		}
		
		// stores the wrong guesses
		List<Character> wrongGuesses = new ArrayList<>();
		
		int attempts = 6;
		Scanner scanner = new Scanner(System.in);
		
		// game loop - ends when the game is over
		while (attempts > 0 && new String(guessedWord).contains("_")) {
			System.out.println("Word: " + String.valueOf(guessedWord));
			System.out.println("Letters guessed: " + String.valueOf(wrongGuesses));
			System.out.println("Attempts left: " + attempts);
			System.out.println("Guess a letter: ");
			char guess = scanner.nextLine().toLowerCase().charAt(0);
			
			// check if the guess was already made
			if (wrongGuesses.contains(guess) || new String(guessedWord).indexOf(guess) >= 0) {
                System.out.println("You already guessed that letter.");
                continue;
            }
			
			// check if the guess was correct
			boolean correct = false;
			for (int i = 0; i < secretWord.length(); i++) {
				if (secretWord.charAt(i) == guess) {
					guessedWord[i] = guess;
					correct = true;
				}
			}
			
			if (!correct) {	//guess was wrong - update game data accordingly
				attempts--;
				wrongGuesses.add(guess);
				System.out.println("Wrong guess");
			}
		}
		
		// the game is over - display the proper ending message
		if (new String(guessedWord).equals(secretWord)) {
			System.out.println("Congratulations! The word was: " + secretWord);
		} else {
			System.out.print("Game over! the word was: " + secretWord);
		}
		
		scanner.close();
	}

}
