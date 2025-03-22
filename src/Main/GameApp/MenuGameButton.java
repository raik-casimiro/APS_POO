package Main.GameApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuGameButton extends JButton {

    private static final int DEFAULT_WIDTH = 196;
    private static final int DEFAULT_HEIGHT = 264;
    private static final int HOVER_WIDTH = 206;
    private static final int HOVER_HEIGHT = 274;

    public MenuGameButton(String imagePath) {
        ImageIcon icon = createScaledIcon(imagePath, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        setIcon(icon);
        setButtonProperties();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(createScaledIcon(imagePath, HOVER_WIDTH, HOVER_HEIGHT));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(icon);
            }
        });
    }

    private void setButtonProperties() {
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private ImageIcon createScaledIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
