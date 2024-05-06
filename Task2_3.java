package internship.oasis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Task2_3 extends JFrame {

    private int totalRounds, currentRound, randomNumber, attempts;
    private final Random random;
    private final JLabel roundLabel;
    private final JLabel guessLabel;
    private final JTextField guessField;
    private final JButton guessButton;

    private final JButton nextRoundButton;
    private final JLabel attemptsLabel;
    private int totalAttempts;
    private int score;

    public Task2_3() {
        setTitle("Guess the Number");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        roundLabel = new JLabel();
        topPanel.add(roundLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        JPanel guessPanel = new JPanel(new FlowLayout());
        guessLabel = new JLabel("Enter your guess: ");
        guessPanel.add(guessLabel);
        guessField = new JTextField(10);
        guessPanel.add(guessField);
        centerPanel.add(guessPanel);

        attemptsLabel = new JLabel();
        centerPanel.add(attemptsLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        guessButton = new JButton("Guess");
        guessButton.addActionListener(new GuessListener());
        buttonPanel.add(guessButton);
        centerPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);


        nextRoundButton = new JButton("Next Round");
        nextRoundButton.addActionListener(new NextRoundListener());
        nextRoundButton.setEnabled(false);
        add(nextRoundButton, BorderLayout.SOUTH);

        random = new Random();

        initializeGame();
    }

    private void initializeGame() {
        try {
            JOptionPane.showMessageDialog(this, "Guess a number between 1 and 100");
            totalRounds = Integer.parseInt(JOptionPane.showInputDialog("Enter the total number of rounds: "));
            if (totalRounds <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number of rounds.");
                initializeGame();
                return;
            }
            currentRound = 1;
            startNewRound();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for total rounds.");
            initializeGame();
        }
    }


    private void startNewRound() {
        randomNumber = random.nextInt(100) + 1;
        attempts = 0;
        guessField.setText("");

        roundLabel.setText("Round " + currentRound);
        attemptsLabel.setText("Attempts left: 5");
        guessButton.setEnabled(true);
        nextRoundButton.setEnabled(false);
        totalAttempts += attempts;
        attempts = 0;
    }

    private class GuessListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int guess;
            try {
                guess = Integer.parseInt(guessField.getText());
                if (guess < 1 || guess > 100) {
                    JOptionPane.showMessageDialog(Task2_3.this, "Please enter a number between 1 and 100.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Task2_3.this, "Please enter a valid number.");
                return;
            }

            attempts++;
            totalAttempts++;
            int attemptsLeft = 5 - attempts;
            attemptsLabel.setText("Attempts left: " + attemptsLeft);


            if (guess == randomNumber) {
                JOptionPane.showMessageDialog(Task2_3.this, "Congratulations! You guessed the number in " + attempts + " attempts.\n");
                guessButton.setEnabled(false);
                nextRoundButton.setEnabled(true);
            } else if (attempts >= 5) {
                JOptionPane.showMessageDialog(Task2_3.this, "You ran out of guesses. The number was: " + randomNumber + ".\n");
                guessButton.setEnabled(false);
                nextRoundButton.setEnabled(true);
            } else if (guess < randomNumber) {
                JOptionPane.showMessageDialog(Task2_3.this, "Too low, try again.\n");
            } else {
                JOptionPane.showMessageDialog(Task2_3.this, "Too high, try again.\n");
            }
        }
    }


    private class NextRoundListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentRound < totalRounds) {
                currentRound++;
                startNewRound();
            } else {
                int choice = JOptionPane.showConfirmDialog(Task2_3.this,
                        "Game Over! Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    initializeGame();
                } else {

                    score = calculateScore(totalRounds, totalAttempts);

                    showScore();
                    dispose();
                }
            }
        }
    }

    private int calculateScore(int totalRounds, int totalAttempts) {
        final int POINTS_PER_ROUND = 20, PENALTY_PER_ATTEMPT = 10;


        int baseScore = totalRounds * POINTS_PER_ROUND;
        System.out.println(baseScore);
        int penalty = totalAttempts * PENALTY_PER_ATTEMPT;
        System.out.println(penalty);


        int finalScore = baseScore - penalty;

        return finalScore;
    }


    private void showScore() {
        JOptionPane.showMessageDialog(Task2_3.this, "Your score is: " + score);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Task2_3().setVisible(true);
            }
        });
    }
}
