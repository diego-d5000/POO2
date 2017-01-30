import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Created by diego-d on 28/01/17.
 */
public class Main {
    public static final int DELAY_ILLUMINATE_TIME = 200;

    private static JFrame mainFrame;
    static SimonButton buttonQ;
    static SimonButton buttonW;
    static SimonButton buttonA;
    static SimonButton buttonS;
    static JPanel simonButtonPane;

    private static String sequence;
    private static int actualSequenceIndex;
    private static boolean isShowingSequence = false;

    public static void main(String[] args) {
        initMainGUI();
    }

    public static void initMainGUI() {
        mainFrame = new JFrame("Simon Dice");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new BorderLayout());

        simonButtonPane = new JPanel(new GridLayout(2, 2));
        initSimonButtons();
        attachButtonToPanel(simonButtonPane);

        mainFrame.add(simonButtonPane, BorderLayout.CENTER);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        mainFrame.addKeyListener(new KeyGameListener());
        mainFrame.setFocusable(true);
        mainFrame.setVisible(true);

        generateNewGame();
    }

    public static void generateNewGame(){
        sequence = String.valueOf(generateRandomNextSequence());
        actualSequenceIndex = 0;
        showSequence();
    }

    public static void showSequence() {
        System.out.print("SEQUENCE:" + sequence + "\n");
        isShowingSequence = true;
        for (int i = 0; i<sequence.length(); i++) {
            char seq = sequence.charAt(i);
            JButton button = null;
            switch (seq) {
                case 'Q':
                    button = buttonQ;
                    break;
                case 'W':
                    button = buttonW;
                    break;
                case 'A':
                    button = buttonA;
                    break;
                case 'S':
                    button = buttonS;
                    break;
            }
            JButton finalButton = button;
            int finalI = i;
            Timer timer = new Timer(i * 1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    finalButton.doClick(600);
                    if (finalI == sequence.length()-1){
                        isShowingSequence = false;
                    }
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public static char generateRandomNextSequence() {
        String availableChars = "QWAS";
        Random random = new Random();
        return availableChars.charAt(random.nextInt(availableChars.length()));
    }

    public static void initSimonButtons() {
        buttonQ = new SimonButton("Q", 1752220);
        buttonQ.addActionListener(new SimonButtonListener('Q'));
        buttonQ.addKeyListener(new KeyGameListener());
        buttonW = new SimonButton("W", 12597547);
        buttonW.addActionListener(new SimonButtonListener('W'));
        buttonW.addKeyListener(new KeyGameListener());
        buttonA = new SimonButton("A", 15844367);
        buttonA.addActionListener(new SimonButtonListener('A'));
        buttonA.addKeyListener(new KeyGameListener());
        buttonS = new SimonButton("S", 3447003);
        buttonS.addActionListener(new SimonButtonListener('S'));
        buttonS.addKeyListener(new KeyGameListener());
    }

    public static void attachButtonToPanel(Container panel) {
        panel.add(buttonQ);
        panel.add(buttonW);
        panel.add(buttonA);
        panel.add(buttonS);
    }

    public static class SimonButtonListener implements ActionListener {
        private char buttonChar;

        public SimonButtonListener(char buttonChar) {
            this.buttonChar = buttonChar;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isShowingSequence)
                return;
            boolean isCorrect = sequence.charAt(actualSequenceIndex) == this.buttonChar;
            if (isCorrect) {
                if (actualSequenceIndex+1 == sequence.length()) {
                    sequence = sequence + generateRandomNextSequence();
                    actualSequenceIndex = 0;
                    showSequence();
                } else {
                    actualSequenceIndex++;
                }
            } else {
                String message = "INCORRECTO, el correcto era:" + sequence.charAt(actualSequenceIndex);
                JOptionPane.showMessageDialog(mainFrame, message, "Incorrecto", JOptionPane.ERROR_MESSAGE);
                generateNewGame();
            }
        }
    }

    public static class KeyGameListener extends KeyAdapter{
        @Override
        public void keyTyped(KeyEvent e) {
            super.keyPressed(e);
            switch (e.getKeyChar()) {
                case 'q':
                    buttonQ.doClick();
                    break;
                case 'w':
                    buttonW.doClick();
                    break;
                case 'a':
                    buttonA.doClick();
                    break;
                case 's':
                    buttonS.doClick();
                    break;
            }
        }
    }
}
