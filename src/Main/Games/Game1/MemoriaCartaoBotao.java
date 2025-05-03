package Main.Games.Game1;

import javax.swing.*;

public class MemoriaCartaoBotao extends JButton {

	private final ImageIcon frontIcon;
    private final ImageIcon backIcon;
    private final int cardId;
    private boolean revealed = false;

    public MemoriaCartaoBotao (ImageIcon frontIcon, ImageIcon backIcon, int cardId, Runnable onClick) {
        super(backIcon);
        this.frontIcon = frontIcon;
        this.backIcon = backIcon;
        this.cardId = cardId;

        addActionListener(e -> {
            if (!revealed) onClick.run();
        });
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
    }

    public int getCardId() {
        return cardId;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void reveal() {
        setIcon(frontIcon);
        revealed = true;
    }

    public void hide() {
        setIcon(backIcon);
        revealed = false;
    }
    
}

