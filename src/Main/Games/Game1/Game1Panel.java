package Main.Games.Game1;

import Main.Games.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game1Panel extends GamePanel {
    private static final int NUM_PAIRS = 6;
    private MemoriaCartaoBotao[] buttons = new MemoriaCartaoBotao[NUM_PAIRS * 2];
    private ImageIcon[] cardIcons = new ImageIcon[NUM_PAIRS];
    private ImageIcon cardBack;
    private int firstIndex = -1;
    private boolean isProcessing = false;
    private int paresEncontrados = 0;
    private JPanel cardPanel;
    private BackgroundPanel bgPanel;

    public Game1Panel(JFrame frame) {
        super(frame);
        setLayout(new BorderLayout());

        loadImages();

        bgPanel = new BackgroundPanel(new ImageIcon("src/Main/Games/Game1/Assets/fundo.png").getImage());
        bgPanel.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Jogo da Memória", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));

        cardPanel = new JPanel(new GridLayout(3, 4, 1, 1));
        cardPanel.setOpaque(false);
        initializeCards(cardPanel);

        JButton botaoReiniciar = new JButton("Reiniciar Jogo");
        botaoReiniciar.addActionListener(e -> reiniciarJogo());

        JPanel painelInferior = new JPanel();
        painelInferior.setOpaque(false);
        painelInferior.add(botaoReiniciar);

        bgPanel.add(titulo, BorderLayout.NORTH);
        bgPanel.add(cardPanel, BorderLayout.CENTER);
        bgPanel.add(painelInferior, BorderLayout.SOUTH);

        add(bgPanel, BorderLayout.CENTER);
    }

    private void loadImages() {
        for (int i = 0; i < NUM_PAIRS; i++) {
            cardIcons[i] = new ImageIcon("src/Main/Games/Game1/Assets/card" + i + ".png");
        }
        cardBack = new ImageIcon("src/Main/Games/Game1/Assets/bg.png");
    }

    private void initializeCards(JPanel panel) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < NUM_PAIRS; i++) {
            indices.add(i);
            indices.add(i);
        }

        Collections.shuffle(indices);
        panel.removeAll();

        for (int i = 0; i < buttons.length; i++) {
            int cardId = indices.get(i);
            final int index = i;
            MemoriaCartaoBotao btn = new MemoriaCartaoBotao(cardIcons[cardId], cardBack, cardId, () -> onCardClick(index));
            btn.reveal();
            buttons[i] = btn;
            panel.add(btn);
        }

        panel.revalidate();
        panel.repaint();

        Timer escondeCartas = new Timer(1300, e -> {
            for (MemoriaCartaoBotao btn : buttons) {
                btn.hide();
            }
        });
        escondeCartas.setRepeats(false);
        escondeCartas.start();
    }

    private void reiniciarJogo() {
        firstIndex = -1;
        isProcessing = false;
        paresEncontrados = 0;
        initializeCards(cardPanel);
    }

    private void onCardClick(int index) {
        if (isProcessing || buttons[index].isRevealed()) return;

        buttons[index].reveal();

        if (firstIndex == -1) {
            firstIndex = index;
        } else {
            int secondIndex = index;
            isProcessing = true;

            Timer timer = new Timer(250, e -> {

                if (buttons[firstIndex].getCardId() == buttons[secondIndex].getCardId()) {
                    paresEncontrados++;
                    if (paresEncontrados == NUM_PAIRS) {
                        JOptionPane.showMessageDialog(this, "Parabéns! Você encontrou todos os pares!");
                        reiniciarJogo();
                    }
                } else {
                    buttons[firstIndex].hide();
                    buttons[secondIndex].hide();
                }

                firstIndex = -1;
                isProcessing = false;
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
}
