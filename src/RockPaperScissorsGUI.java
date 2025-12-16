import javax.swing.*;
import java.awt.*;
import java.util.Random;


    public class RockPaperScissorsGUI extends JFrame {
        private JButton rockButton, paperButton, scissorsButton, resetButton;
        private JLabel userChoiceLabel, computerChoiceLabel, computerImageLabel, resultLabel, scoreLabel, roundLabel;
        private int userScore = 0, computerScore = 0, round = 0;
        private final int maxRounds = 5;
        private String[] choices = {"Rock", "Paper", "Scissors"};
        private Random random = new Random();

        public RockPaperScissorsGUI() {
            setTitle("Rock, Paper, Scissors");
            setSize(780, 700);
            setLocation(430,80);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new GridLayout(5, 1));
            getContentPane().setBackground(new Color(245, 245, 245)); // Attractive background
//        getContentPane().setBackground(new Color(102, 204, 255)); // light blue

            // Panel for buttons with images
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.setBackground(new Color(102, 204, 255));

            rockButton = createButton("Rock", "rock.png");
            paperButton = createButton("Paper", "paper.jpg");
            scissorsButton = createButton("Scissors", "scissors.png");

            buttonPanel.add(rockButton);
            buttonPanel.add(paperButton);
            buttonPanel.add(scissorsButton);
            add(buttonPanel);

            // Panel for choices
            JPanel choicesPanel = new JPanel(new FlowLayout());
            choicesPanel.setBackground(new Color(102, 204, 255));

            userChoiceLabel = new JLabel("Your choice: ");
            userChoiceLabel.setFont(new Font("Arial", Font.BOLD, 14));

            computerChoiceLabel = new JLabel("Computer choice: ");
            computerChoiceLabel.setFont(new Font("Arial", Font.BOLD, 14));

            computerImageLabel = new JLabel();
            computerImageLabel.setPreferredSize(new Dimension(120, 140));

            choicesPanel.add(userChoiceLabel);
            choicesPanel.add(computerChoiceLabel);
            choicesPanel.add(computerImageLabel);

            add(choicesPanel);

            // Panel for result
            JPanel resultPanel = new JPanel();
            resultPanel.setBackground(new Color(102, 204, 255));
            resultLabel = new JLabel("Make your move!");
            resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
            resultLabel.setForeground(new Color(0, 102, 204));
            resultPanel.add(resultLabel);
            add(resultPanel);

            // Panel for score and rounds
            JPanel scorePanel = new JPanel(new FlowLayout());
            scorePanel.setBackground(new Color(102, 204, 255));

            scoreLabel = new JLabel("Score => You: 0 | Computer: 0");
            scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));

            roundLabel = new JLabel("Round: 0 / " + maxRounds);
            roundLabel.setFont(new Font("Arial", Font.BOLD, 14));

            scorePanel.add(scoreLabel);
            scorePanel.add(Box.createHorizontalStrut(20));
            scorePanel.add(roundLabel);
            add(scorePanel);

            // Panel for reset button
            JPanel resetPanel = new JPanel();
            resetPanel.setBackground(new Color(102, 204, 255));

            resetButton = new JButton("Reset Game");
            resetButton.setFont(new Font("Arial", Font.BOLD, 14));
            resetButton.setBackground(new Color(220, 220, 220));
            resetButton.setFocusPainted(false);
            resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            resetButton.addActionListener(e -> resetGame());

            resetPanel.add(resetButton);
            add(resetPanel);

            // Button actions
            rockButton.addActionListener(e -> playRound("Rock"));
            paperButton.addActionListener(e -> playRound("Paper"));
            scissorsButton.addActionListener(e -> playRound("Scissors"));

            setVisible(true);
        }

        private JButton createButton(String text, String imageName) {
            JButton button = new JButton(text);
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource("/resources/" + imageName));
                Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH); // larger image
                button.setIcon(new ImageIcon(img));
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
            } catch (Exception e) {
                System.out.println("Image not found: " + imageName);
            }

            button.setPreferredSize(new Dimension(140, 160));
            button.setBackground(new Color(220, 220, 220));
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(180, 180, 255));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(220, 220, 220));
                }
            });

            return button;
        }

        private void playRound(String userChoice) {
            if (round >= maxRounds) {
                resultLabel.setText("Game over! Reset to play again.");
                return;
            }

            String computerChoice = choices[random.nextInt(3)];
            userChoiceLabel.setText("Your choice: " + userChoice);
            computerChoiceLabel.setText("Computer choice: " + computerChoice);

            // Show computer choice image
            try {
                ImageIcon compIcon = new ImageIcon(getClass().getResource("/resources/" + computerChoice.toLowerCase() + ".png" ));
                Image compImg = compIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                computerImageLabel.setIcon(new ImageIcon(compImg));
            } catch (Exception e) {
                System.out.println("Computer image not found!");
            }

            // Determine winner
            if (userChoice.equals(computerChoice)) {
                resultLabel.setText("It's a tie!");
                resultLabel.setForeground(new Color(102, 102, 102));
            } else if (
                    (userChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                            (userChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                            (userChoice.equals("Scissors") && computerChoice.equals("Paper"))
            ) {
                resultLabel.setText("You win this round!");
                resultLabel.setForeground(new Color(0, 153, 0));
                userScore++;
            } else {
                resultLabel.setText("Computer wins this round!");
                resultLabel.setForeground(new Color(204, 0, 0));
                computerScore++;
            }

            round++;
            scoreLabel.setText("Score => You: " + userScore + " | Computer: " + computerScore);
            roundLabel.setText("Round: " + round + " / " + maxRounds);

            if (round == maxRounds) {
                announceWinner();
            }
        }

        private void announceWinner() {
            if (userScore > computerScore) {
                JOptionPane.showMessageDialog(this, "Congratulations! You won the game!");
            } else if (userScore < computerScore) {
                JOptionPane.showMessageDialog(this, "Computer won the game! Better luck next time.");
            } else {
                JOptionPane.showMessageDialog(this, "The game ended in a tie!");
            }
        }

        private void resetGame() {
            userScore = 0;
            computerScore = 0;
            round = 0;
            userChoiceLabel.setText("Your choice: ");
            computerChoiceLabel.setText("Computer choice: ");
            computerImageLabel.setIcon(null);
            resultLabel.setText("Make your move!");
            resultLabel.setForeground(new Color(0, 102, 204));
            scoreLabel.setText("Score => You: 0 | Computer: 0");
            roundLabel.setText("Round: 0 / " + maxRounds);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new RockPaperScissorsGUI());
        }
    }


