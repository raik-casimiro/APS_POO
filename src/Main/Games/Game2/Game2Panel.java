package Main.Games.Game2;

import Main.Games.GamePanel;

import javax.swing.*;

public class Game2Panel extends GamePanel {
    public Game2Panel(JFrame frame) {
        super(frame);
        JLabel label = new JLabel("Jogo 2", SwingConstants.CENTER);
        label.setBounds(590, 300, 100, 30);
        add(label);
    }
}