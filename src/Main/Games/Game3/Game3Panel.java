package Main.Games.Game3;

import Main.Games.GamePanel;

import javax.swing.*;

public class Game3Panel extends GamePanel {
    public Game3Panel(JFrame frame) {
        super(frame);
        JLabel label = new JLabel("Jogo 3", SwingConstants.CENTER);
        label.setBounds(590, 300, 100, 30);
        add(label);
    }
}