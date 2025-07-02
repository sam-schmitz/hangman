package hangman;

import java.util.Scanner;

public class HangmanGame {

	public static void main(String[] args) { 
		String[] wordList = {"dog", "cat", "pizza"};
		String secretWord = wordList[(int) (Math.random() * wordList.length)];
		
		char[] guessedWord = new char[secretWord.length()];
		for (int i = 0; i < guessedWord.length; i++) {
			guessedWord[i] = '_';
		}
		
		int attempts = 6;
		Scanner scanner = new Scanner(System.in);
		
		while (attempts > 0 && new String(guessedWord).contains("_")) {
			System.out.println("Word: " + String.valueOf(guessedWord));
			System.out.println("Attempts left: " + attempts);
			System.out.println("Guess a letter: ");
			char guess = scanner.nextLine().toLowerCase().charAt(0);
			
			boolean correct = false;
			for (int i = 0; i < secretWord.length(); i++) {
				if (secretWord.charAt(i) == guess) {
					guessedWord[i] = guess;
					correct = true;
				}
			}
			
			if (!correct) {
				attempts--;
				System.out.println("Wrong guess");
			}
		}
		
		if (new String(guessedWord).equals(secretWord)) {
			System.out.println("Congratulations! The word was: " + secretWord);
		} else {
			System.out.print("Game over! the word was: " + secretWord);
		}
		
		scanner.close();
	}

}
