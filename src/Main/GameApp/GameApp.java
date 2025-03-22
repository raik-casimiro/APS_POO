package Main.GameApp;

import Main.Games.Game1.Game1Panel;
import Main.Games.Game2.Game2Panel;
import Main.Games.Game3.Game3Panel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameApp::createAndShowGUI);
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("APS - Jogo Meio Ambiente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);

        MenuPanel menuPanel = new MenuPanel("src/Assets/Images/bg_menu.jpeg");
        menuPanel.setLayout(new BorderLayout());

        JPanel topPanel = createTopPanel();
        JPanel buttonPanel = createButtonPanel(frame);

        menuPanel.add(topPanel, BorderLayout.NORTH);
        menuPanel.add(buttonPanel);
        frame.add(menuPanel);
        frame.setVisible(true);
    }

    private static JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("JOGOS MEIO AMBIENTE");
        titleLabel.setForeground(Color.WHITE);

        Font font = loadCustomFont("src/Assets/Fonts/LuckiestGuy.ttf", 42f);
        titleLabel.setFont(font);

        topPanel.add(titleLabel);

        return topPanel;
    }

    private static JPanel createButtonPanel(JFrame frame) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridBagLayout());

        MenuGameButton gameButton1 = new MenuGameButton("src/Assets/Images/game1_button.png");
        MenuGameButton gameButton2 = new MenuGameButton("src/Assets/Images/game2_button.png");
        MenuGameButton gameButton3 = new MenuGameButton("src/Assets/Images/game3_button.png");

        gameButton1.addActionListener(_ -> startGame(frame, new Game1Panel(frame)));
        gameButton2.addActionListener(_ -> startGame(frame, new Game2Panel(frame)));
        gameButton3.addActionListener(_ -> startGame(frame, new Game3Panel(frame)));

        buttonPanel.add(gameButton1);
        buttonPanel.add(gameButton2);
        buttonPanel.add(gameButton3);

        return buttonPanel;
    }

    private static void startGame(JFrame frame, JPanel gamePanel) {
        frame.getContentPane().removeAll();
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
    }

    /// Load custom font or fallback to Arial Bold
    private static Font loadCustomFont(String fontPath, float size) {
        try {

            File fontFile = new File(fontPath);
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

            return font.deriveFont(size);

        } catch (FontFormatException | IOException e) {
            System.out.println("Couldn't load font: " + e.getMessage());
            return new Font("Arial", Font.BOLD, (int) size);
        }
    }
}
