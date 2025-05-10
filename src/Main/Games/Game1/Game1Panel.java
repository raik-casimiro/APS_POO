package Main.Games.Game1;

import Main.Games.GamePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game1Panel extends GamePanel implements ActionListener, KeyListener { //O GameColeta herda o Game1Panel (que é o menu principal)
//ActionLister serve para detectar os cliques
//KeyListerner serve para detectar o teclado

    private static final int LIXEIRA_W = 130;
    private static final int LIXEIRA_H = 160;
    private static final int LIXO_SIZE = 60;
    private static final int LIXO_SPEED = 2;
    private static final int GAME_SPEED = 16;
    private static final int LIXEIRA_SPEED = 7;
//Toda essa parte serve para controlat o tamanho largura das lixeiras, velocidade em que os lixinhos caem

    private int selectedBin = -1;
    private int binX;
    private List<Lixo> trashList;
    private Timer gameTimer;
    private int score = 0;
    private boolean isGameOver = false;
    private JLabel scoreLabel;
    private JButton playAgainButton;

    private enum TrashType { PAPEL, PLASTICO, LATA }

    public static Image imgPapel, imgPlastico, imgLata;
    private Image binImgAzul, binImgVermelha, binImgVerde;
    private Image backgroundImage;

    private JFrame frame;
    private int width, height;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    private static class Lixo {
        int x, y;
        TrashType type;

        public Lixo(int x, TrashType type) {
            this.x = x;
            this.y = 0;
            this.type = type;
        }

        public void move() {
            y += LIXO_SPEED;
        }

        public void draw(Graphics g) {
            Image img = switch (type) {
                case PAPEL -> imgPapel;
                case PLASTICO -> imgPlastico;
                case LATA -> imgLata;
            };
            if (img != null) {
                g.drawImage(img, x, y, LIXO_SIZE, LIXO_SIZE, null);
            }
        }
    }

    public Game1Panel(JFrame frame) {
        super(frame);
        this.frame = frame;
        setLayout(null);
        setFocusable(true);
        addKeyListener(this);

        loadImages();

        binX = 0;
        trashList = new ArrayList<>();
        gameTimer = new Timer(GAME_SPEED, e -> {
            if(this.isPaused) return;

            requestFocusInWindow();
            
            if (!isGameOver && selectedBin != -1) {
                updateGame();
                repaint();
            }
        });

        scoreLabel = createLabel("Pontuação: 0", 0, 0, 200, 30);
        scoreLabel.setForeground(Color.WHITE);

        playAgainButton = createButton("Jogar Novamente", 0, 0, 150, 30, e -> restartGame());
        playAgainButton.setVisible(false);

        add(scoreLabel);
        add(playAgainButton);

        JButton btnAzul = createImageButton(binImgAzul, e -> selectBin(0));
        JButton btnVermelha = createImageButton(binImgVermelha, e -> selectBin(1));
        JButton btnVerde = createImageButton(binImgVerde, e -> selectBin(2));

        add(btnAzul);
        add(btnVermelha);
        add(btnVerde);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                width = getWidth();
                height = getHeight();
                scoreLabel.setBounds(width - 180, 20, 200, 30);
                playAgainButton.setBounds((width - 150) / 2, height / 2 + 50, 150, 30);

                btnAzul.setBounds(20, height - LIXEIRA_H - 20, LIXEIRA_W, LIXEIRA_H);
                btnVermelha.setBounds(140, height - LIXEIRA_H - 20, LIXEIRA_W, LIXEIRA_H);
                btnVerde.setBounds(260, height - LIXEIRA_H - 20, LIXEIRA_W, LIXEIRA_H);
            }
        });
    }

    private void loadImages() {
        imgPapel = new ImageIcon("src/Main/Games/Game1/Assets/Lixo_Papel.png").getImage(); //Add todas as imagens
        imgPlastico = new ImageIcon("src/Main/Games/Game1/Assets/Lixo_Plastico.png").getImage();
        imgLata = new ImageIcon("src/Main/Games/Game1/Assets/Lixo_Lata.png").getImage();
        binImgAzul = new ImageIcon("src/Main/Games/Game1/Assets/Lixeira_azul.png").getImage();
        binImgVermelha = new ImageIcon("src/Main/Games/Game1/Assets/Lixeira_Vermelha.png").getImage();
        binImgVerde = new ImageIcon("src/Main/Games/Game1/Assets/Lixeira_Verde.png").getImage();
        backgroundImage = new ImageIcon("src/Main/Games/Game1/Assets/Fundo_Coleta.jpg").getImage();
    }

    private JLabel createLabel(String text, int x, int y, int w, int h) {
        JLabel label = new JLabel(text);
        label.setFont(loadNerillkidFont(24f));
        label.setBounds(x, y, w, h);
        return label;
    }

    private JButton createButton(String text, int x, int y, int w, int h, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, w, h);
        button.addActionListener(listener);
        return button;
    }

    private JButton createImageButton(Image img, ActionListener listener) {
        JButton button = new JButton(new ImageIcon(img.getScaledInstance(LIXEIRA_W, LIXEIRA_H, Image.SCALE_SMOOTH)));
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.addActionListener(listener);
        button.setBounds(0, 0, LIXEIRA_W, LIXEIRA_H);
        return button;
    }

    private void selectBin(int binIndex) {
        playAgainButton.setVisible(false); //
        selectedBin = binIndex;
        binX = width / 2 - LIXEIRA_W / 2;
        trashList.clear();
        score = 0;
        isGameOver = false;
        scoreLabel.setText("Pontuação: 0");
        gameTimer.start();
        requestFocusInWindow();
    }

    private void restartGame() { // essa parte serve para que quando o usuario aperte "jogar novamente" o jogo reinicie
        selectedBin = -1;
        playAgainButton.setVisible(false);
        scoreLabel.setText("Pontuação: 0");
        isGameOver = false;
        trashList.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

        if (selectedBin != -1) {
            drawBin(g);
            for (Lixo trash : trashList) {
                trash.draw(g);
            }

            if (isGameOver) {
                g.setColor(Color.WHITE);
                g.setFont(loadNerillkidFont(60f));
                String text = "Fim de Jogo! Pontuação: " + score;
                g.drawString(text, (width - g.getFontMetrics().stringWidth(text)) / 2, height / 2);
                playAgainButton.setVisible(true);
            }
        } else {
            g.setColor(Color.WHITE);
            g.setFont(loadNerillkidFont(60f));
            String text = "Escolha uma lixeira para começar!";
            g.drawString(text, (width - g.getFontMetrics().stringWidth(text)) / 2, height / 2);
        }
    }

    private void drawBin(Graphics g) {
        int y = height - LIXEIRA_H - 150;
        Image binImg = switch (selectedBin) {
            case 0 -> binImgAzul;
            case 1 -> binImgVermelha;
            case 2 -> binImgVerde;
            default -> null;
        };
        if (binImg != null) {
            g.drawImage(binImg, binX, y, LIXEIRA_W, LIXEIRA_H, null);
        }
    }

    private void updateGame() {
        if (movingLeft && binX > 0) {
            binX -= LIXEIRA_SPEED;
        }
        if (movingRight && binX + LIXEIRA_W < width) {
            binX += LIXEIRA_SPEED;
        }

        if (Math.random() < 0.01) {
            int x = new Random().nextInt(width - LIXO_SIZE);
            trashList.add(new Lixo(x, TrashType.values()[new Random().nextInt(3)]));
        }

        List<Lixo> collected = new ArrayList<>();
        int binY = height - LIXEIRA_H - 150;
        for (Lixo trash : trashList) {
            trash.move();
            if (trash.y + LIXO_SIZE > binY &&
                    trash.y < binY + LIXEIRA_H &&
                    trash.x + LIXO_SIZE > binX &&
                    trash.x < binX + LIXEIRA_W) {
                if ((selectedBin == 0 && trash.type == TrashType.PAPEL) ||
                        (selectedBin == 1 && trash.type == TrashType.PLASTICO) ||
                        (selectedBin == 2 && trash.type == TrashType.LATA)) {
                    score++;
                    collected.add(trash);
                } else {
                    isGameOver = true;
                    gameTimer.stop();
                }
            } else if (trash.y > height) {
                collected.add(trash);
            }
        }
        trashList.removeAll(collected);
        scoreLabel.setText("Pontuação: " + score);
    }

    private Font loadNerillkidFont(float size) { //personaliza a fonte ou usar "ARIAL"
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("src/Assets/Fonts/Nerillkid.ttf"));
            return font.deriveFont(size);
        } catch (Exception e) {
            return new Font("Arial", Font.BOLD, (int) size);
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (!isGameOver) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                movingLeft = true;
            } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                movingRight = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            movingLeft = false;
        } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            movingRight = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {}

}