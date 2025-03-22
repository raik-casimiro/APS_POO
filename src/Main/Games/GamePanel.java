package Main.Games;

import Main.GameApp.GameApp;

import javax.swing.*;
import java.awt.*; 

// TODO add styling
// TODO create a menu in game without dialog
// TODO optimize code
public class GamePanel extends JPanel {
    protected JFrame frame;

    public GamePanel(JFrame frame) {
        this.frame = frame;
        setLayout(null);

        JButton pauseButton = new JButton("☰");
        pauseButton.setBounds(10, 10, 50, 30);
        pauseButton.addActionListener(_ -> showPauseMenu());
        add(pauseButton);
    }

    protected void showPauseMenu() {
        JDialog pauseMenu = new JDialog(frame, "Pausa", true);
        pauseMenu.setSize(200, 150);
        pauseMenu.setLocationRelativeTo(frame);
        pauseMenu.setLayout(new GridLayout(2, 1, 5, 5));

        JButton continueButton = new JButton("Continuar");
        JButton backButton = new JButton("Voltar ao Menu");

        continueButton.addActionListener(_ -> pauseMenu.dispose());
        backButton.addActionListener(_ -> {
            frame.getContentPane().removeAll();
            GameApp.createAndShowGUI();
            frame.dispose();
        });

        pauseMenu.add(continueButton);
        pauseMenu.add(backButton);
        pauseMenu.setVisible(true);
    }
}

