// =====================================================
// Flashcard.java — Data model for a single flashcard
// Stores a question and its correct answer as a pair.
// =====================================================

public class Flashcard {

    // --- Fields: hold the question text and its answer ---
    private String question;
    private String answer;

    // --- Constructor: creates a flashcard with a question and answer ---
    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // --- Getter: returns the question text ---
    public String getQuestion() {
        return question;
    }

    // --- Getter: returns the answer text ---
    public String getAnswer() {
        return answer;
    }
}
