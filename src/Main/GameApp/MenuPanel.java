package Main.GameApp;

import java.awt.*;
import javax.swing.*;

class MenuPanel extends JPanel {
    private final Image backgroundImage;

    public MenuPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
