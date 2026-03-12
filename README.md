# Flashcard Quiz App

A minimal Java Swing flashcard quiz application. It presents questions one at a time, checks your answer, tracks your score, and lets you restart when you're done.

## Files

| File | Purpose |
|---|---|
| `Flashcard.java` | Data model — stores a question/answer pair |
| `FlashcardQuizApp.java` | Main application — Swing UI, quiz logic, event handling |

## How to Compile and Run

```bash
# Compile both files
javac Flashcard.java FlashcardQuizApp.java

# Run the app
java FlashcardQuizApp
```

## How It Works

1. The app loads 10 sample flashcards and shuffles them.
2. Each card shows a question — type your answer and press **Submit** (or Enter).
3. You get instant feedback (correct / wrong with the right answer).
4. Press **Next** to move to the next card.
5. After the last card, your final score is shown and you can **Restart**.
