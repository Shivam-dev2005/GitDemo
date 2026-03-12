// =====================================================
// FlashcardQuizApp.java — Main application entry point
// A minimal Swing-based flashcard quiz application.
// Users see a question, type an answer, and get instant
// feedback. Tracks score across all cards.
// =====================================================

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlashcardQuizApp {

    // =====================================================
    // Block 1 — Application state
    // Holds the deck of flashcards, the index of the
    // current card, and the running score.
    // =====================================================
    private List<Flashcard> deck;
    private int currentIndex;
    private int score;

    // =====================================================
    // Block 2 — Swing UI components
    // References to the widgets that make up the minimal
    // user interface: labels, text field, and buttons.
    // =====================================================
    private JFrame frame;
    private JLabel questionLabel;
    private JLabel feedbackLabel;
    private JLabel scoreLabel;
    private JLabel progressLabel;
    private JTextField answerField;
    private JButton submitButton;
    private JButton nextButton;
    private JButton restartButton;

    // =====================================================
    // Block 3 — Constructor
    // Initialises the deck, shuffles it, and builds the UI.
    // =====================================================
    public FlashcardQuizApp() {
        deck = buildDeck();
        Collections.shuffle(deck);
        currentIndex = 0;
        score = 0;
        createUI();
        showCard();
    }

    // =====================================================
    // Block 4 — Deck builder
    // Creates a list of sample flashcards covering basic
    // programming / general-knowledge topics.
    // =====================================================
    private List<Flashcard> buildDeck() {
        List<Flashcard> cards = new ArrayList<>();
        cards.add(new Flashcard("What keyword is used to define a class in Java?", "class"));
        cards.add(new Flashcard("What does JVM stand for?", "Java Virtual Machine"));
        cards.add(new Flashcard("Which data type is used for true/false values?", "boolean"));
        cards.add(new Flashcard("What method is the entry point of a Java program?", "main"));
        cards.add(new Flashcard("What keyword is used to inherit a class?", "extends"));
        cards.add(new Flashcard("What is the default value of an int variable?", "0"));
        cards.add(new Flashcard("Which loop checks condition after executing the body?", "do-while"));
        cards.add(new Flashcard("What package contains the Scanner class?", "java.util"));
        cards.add(new Flashcard("What is the size of an int in Java (in bits)?", "32"));
        cards.add(new Flashcard("Which keyword prevents a class from being inherited?", "final"));
        return cards;
    }

    // =====================================================
    // Block 5 — UI construction
    // Builds the JFrame window, lays out all components
    // using a simple BoxLayout, and wires up button
    // listeners. Keeps the design minimal and clean.
    // =====================================================
    private void createUI() {
        // --- Frame setup ---
        frame = new JFrame("Flashcard Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 340);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // --- Main panel with vertical layout and padding ---
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(Color.WHITE);

        // --- Progress label (e.g. "Card 1 / 10") ---
        progressLabel = new JLabel();
        progressLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        progressLabel.setForeground(Color.GRAY);
        progressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Question label ---
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Answer text field ---
        answerField = new JTextField();
        answerField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        answerField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        answerField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Feedback label (correct / wrong) ---
        feedbackLabel = new JLabel(" ");
        feedbackLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        feedbackLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Score label ---
        scoreLabel = new JLabel("Score: 0 / 0");
        scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        scoreLabel.setForeground(Color.DARK_GRAY);
        scoreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Button panel (horizontal) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        submitButton = new JButton("Submit");
        nextButton = new JButton("Next");
        restartButton = new JButton("Restart");

        nextButton.setEnabled(false);
        restartButton.setVisible(false);

        buttonPanel.add(submitButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(restartButton);

        // --- Add components to the panel with spacing ---
        panel.add(progressLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(questionLabel);
        panel.add(Box.createVerticalStrut(12));
        panel.add(answerField);
        panel.add(Box.createVerticalStrut(8));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(feedbackLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(scoreLabel);

        frame.setContentPane(panel);

        // =====================================================
        // Block 6 — Event listeners
        // Wires Submit, Next, and Restart buttons to their
        // respective handler methods. Enter key also submits.
        // =====================================================
        submitButton.addActionListener(e -> checkAnswer());
        nextButton.addActionListener(e -> advanceCard());
        restartButton.addActionListener(e -> restartQuiz());
        answerField.addActionListener(e -> checkAnswer());

        frame.setVisible(true);
    }

    // =====================================================
    // Block 7 — Show current card
    // Updates the question label and resets the input field
    // and feedback for the current flashcard.
    // =====================================================
    private void showCard() {
        Flashcard card = deck.get(currentIndex);
        progressLabel.setText("Card " + (currentIndex + 1) + " / " + deck.size());
        questionLabel.setText("<html><body style='width:420px'>" + card.getQuestion() + "</body></html>");
        answerField.setText("");
        feedbackLabel.setText(" ");
        answerField.setEnabled(true);
        submitButton.setEnabled(true);
        nextButton.setEnabled(false);
        answerField.requestFocusInWindow();
    }

    // =====================================================
    // Block 8 — Answer checking
    // Compares the user's input with the correct answer
    // (case-insensitive). Updates score and feedback label.
    // =====================================================
    private void checkAnswer() {
        if (!submitButton.isEnabled()) return;

        String userAnswer = answerField.getText().trim();
        String correctAnswer = deck.get(currentIndex).getAnswer();

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            feedbackLabel.setText("✓  Correct!");
            feedbackLabel.setForeground(new Color(34, 139, 34));
            score++;
        } else {
            feedbackLabel.setText("✗  Wrong — answer: " + correctAnswer);
            feedbackLabel.setForeground(Color.RED);
        }

        scoreLabel.setText("Score: " + score + " / " + (currentIndex + 1));
        submitButton.setEnabled(false);
        answerField.setEnabled(false);

        if (currentIndex < deck.size() - 1) {
            nextButton.setEnabled(true);
        } else {
            // --- Quiz finished: show final message and restart option ---
            feedbackLabel.setText(feedbackLabel.getText()
                    + "   |   Quiz complete! Final score: " + score + " / " + deck.size());
            restartButton.setVisible(true);
        }
    }

    // =====================================================
    // Block 9 — Advance to next card
    // Moves the index forward and shows the next flashcard.
    // =====================================================
    private void advanceCard() {
        currentIndex++;
        showCard();
    }

    // =====================================================
    // Block 10 — Restart quiz
    // Reshuffles the deck, resets score and index, then
    // shows the first card again.
    // =====================================================
    private void restartQuiz() {
        Collections.shuffle(deck);
        currentIndex = 0;
        score = 0;
        scoreLabel.setText("Score: 0 / 0");
        restartButton.setVisible(false);
        showCard();
    }

    // =====================================================
    // Block 11 — Main method (entry point)
    // Launches the application on the Swing event-dispatch
    // thread to ensure thread-safe UI creation.
    // =====================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FlashcardQuizApp::new);
    }
}
