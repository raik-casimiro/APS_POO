package Main.Games.Game1;

import Main.Games.GamePanel;

import javax.swing.*;

public class Game1Panel extends GamePanel {
    public Game1Panel(JFrame frame) {
        super(frame);
        JLabel label = new JLabel("Jogo 1", SwingConstants.CENTER);
        label.setBounds(590, 300, 100, 30);
        add(label);
    }
}