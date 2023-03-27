# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

### Add Flashcard Feature

#### Current implementation

The current add flashcard allows the user to add a flashcard to the list of flashcards,
it is implemented through the following steps:

Step 1:
The input of user is collected by `getUserCommand()` inside class `Ui`.

Step 2:
The input string will be converted into a `Command` object by being passed through
`parseCommand(String userInput)` inside `Parser`.

In this case, an `AddCommand` will be created and returned.

Step 3:
The `execute()` function of `AddCommand` will run, calling `addNewFlashcard(questionText, answerText)`
of class `FlashcardList` to create and add the new flashcard to the list.

Then it will also call `printSuccessfulAddMessage(questionText, answerText)` of class `Ui`
to display text indicating the successful adding function to the user.

At this point, the adding process is completed and the program is ready to take another
command.

The following sequence diagram show how the add operation works:
![AddFlashcard-0.png](umlDiagrams%2FAddFlashcard-0.png)

#### Reason for current implementation

Through using `AddCommand` class, which extends `Command` class it increases the level of
abstraction as the code can now perform the various commands on a class level.

Moreover, since the creating of new `Flashcard` of object and adding of the newly created
flashcard are both done in the same class as where the flashcards are stored, this reduces
coupling in the program as the `AddCommand` will not have access to the inner structure of
`FlashcardList`, which stores the list of flashcards.

#### Alternative implementation

- Alternative 1: Have the add command function directly in `FlashcardList`
    - Pros: Easy to implement
    - Cons: Will require another function in another program to differentiate it from other
      commands
- Alternative 2: Have the constructor of `Flashcard` include adding the card to list of flashcards
    - Pros: Simplifies code
    - Cons: Will cause trouble when temporary flashcard (that need not be stored) are
      created

### Delete Flashcard Feature

#### Current implementation

The current delete flashcard allows the user to remove a flashcard from the list of flashcards,
it is implemented through the following steps:

Step 1: The input of user is collected by `getUserCommand()` inside class `Ui`.

Step 2: The input string will be converted into a `Command` object by being passed through
`parseCommand(String userInput)` inside `Parser`.

In this case, a `DeleteCommnad` will be created and returned.

Step 3: The `execute()` function of `DeleteCommand` will run, creating a copy of the list of flashcards.
Then `findFlashcards(flashcards, query)` is called to find the flashcards with questions matching the query,
before calling `printFlashcardList(matchingFlashcards)` to display the found flashcards.

User input is taken to get the index of the flashcard to be removed. `deleteFlashcard` is called from
class `flashcardList` to remove the flashcard from the original list of flashcards. Finally
`printSuccessfulDelete` is called from class `Ui` to indicate a successful removal of the flashcard.

The deletion process is now completed and the program will await another command.

An overview of how the Delete operation works is shown with the following sequence diagram
![DeleteFlashcard.png](umlDiagrams%2FDeleteFlashcard.png)

#### Reason for current implementation

Through using `DeleteCommand` class, which extends `Command` class it increases the level of
abstraction as the code can now perform the various commands on a class level.

In order to minimise the time for users to search for the flashcard to delete, they are able
to first search for a sub-list of flashcards with matching questions as the query. This method
makes the deletion process simple even if the user does not remember the index of the flashcard.

#### Alternative implementation

- Alternative 1: Delete flashcard by index from the start
    - Pros: Easy to implement and simplifies code
    - Cons: Cumbersome to delete if user forgets the flashcard's index and has to search
      through the whole list of flashcards.

## Product scope

### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

| Version | As a ... | I want to ...             | So that I can ...                                           |
|---------|----------|---------------------------|-------------------------------------------------------------|
| v1.0    | new user | see usage instructions    | refer to them when I forget how to use the application      |
| v2.0    | user     | find a to-do item by name | locate a to-do without having to go through the entire list |

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
