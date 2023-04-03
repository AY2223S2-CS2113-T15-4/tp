package com.clanki.commands;

import com.clanki.exceptions.UpdatedContentIsEmptyException;
import com.clanki.objects.Flashcard;
import com.clanki.objects.FlashcardList;
import com.clanki.parser.InvalidIdentifierException;
import com.clanki.parser.Parser;
import com.clanki.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class UpdateCommand extends Command {
    String query;
    ArrayList<Flashcard> matchingFlashcards = new ArrayList<>();

    public UpdateCommand(String query) {
        super();
        this.query = query;
    }

    public void printFlashCard(Flashcard flashcard) {
        System.out.println("Q: " + flashcard.getQuestion());
        System.out.println("A: " + flashcard.getAnswer());
//        System.out.println("D: " + flashcard.getDueDate());
    }

    public void printFlashCards(ArrayList<Flashcard> flashcards) {
        for (int i = 0; i < flashcards.size(); i++) {
            System.out.println("[" + (i + 1) + "]");
            printFlashCard(flashcards.get(i));
        }
    }

    public void findFlashcard(ArrayList<Flashcard> flashcards, String query) {
        for (int i = 0; i < flashcards.size(); i++) {
            Flashcard currentFlashcard = flashcards.get(i);
            if (currentFlashcard.getQuestion().toLowerCase().contains(query.toLowerCase())
                    || currentFlashcard.getAnswer().toLowerCase().contains(query.toLowerCase()) ||
                    currentFlashcard.getDueDate().toString().equals(query)) {
                matchingFlashcards.add(currentFlashcard);
            }
        }
    }

    public int implementUpdate(ArrayList<Flashcard> flashcards, int indexInMatchList, String identifier, String updatedContent) {
        Flashcard flashcardToChange = matchingFlashcards.get(indexInMatchList);
        int index = flashcards.indexOf(flashcardToChange);
        if (identifier.contains("q")) {
            flashcards.get(index).setQuestion(updatedContent.substring(0, 1).toUpperCase() +
                    updatedContent.substring(1));
        }
        if (identifier.contains("a")) {
            flashcards.get(index).setAnswer(updatedContent.substring(0, 1).toUpperCase() +
                    updatedContent.substring(1));
        }
        if (identifier.contains("d")) {
            LocalDate dateToChange = LocalDate.parse(updatedContent);
            flashcards.get(index).setDueDate(dateToChange);
        }
        return index;
    }

    @Override
    public void execute(FlashcardList flashcardList, Ui display) {
        ArrayList<Flashcard> flashcards = flashcardList.getFlashCards();
        findFlashcard(flashcards, query);
        if (matchingFlashcards.size() > 0) {
            System.out.println(
                    "Found " + matchingFlashcards.size() + " cards with the query \"" + query + "\":");
            printFlashCards(matchingFlashcards);
            System.out.println("Which flashcard do you want to update?");
            int checker = 0;
            while (checker == 0) {
                try {
                    String userText = display.getUserCommand();
                    int indexInMatchList = Parser.getIndexForUpdateCommand(userText);
                    String identifier = Parser.getIdentifierForUpdateCommand(userText);
                    String updatedContent = Parser.parseInputForUpdateCommand(userText);
                    int index = implementUpdate(flashcards, indexInMatchList, identifier, updatedContent);
                    System.out.println("Understood. The card has been updated to");
                    printFlashCard(flashcards.get(index));
                    checker = 1;
                } catch (InvalidIdentifierException e) {
                    System.out.println("You can only enter /q, /a or /d");
                } catch (UpdatedContentIsEmptyException e) {
                    System.out.println("Please enter the changes to be modified.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please enter the input in the correct format as shown in the user guide.");
                } catch (NumberFormatException e) {
                    System.out.println("Please enter the index of the flashcard you want to update.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.print("You have selected an index out of the list.");
                    System.out.println("There are only " + matchingFlashcards.size() + " flashcards that match the query.");
                } catch (DateTimeParseException e) {
                    System.out.println("Please enter the date in the format: yyyy-mm-dd");
                }
            }
        } else {
            System.out.println("There are no flashcards with the query \"" + query + "\".");
        }
    }
}
