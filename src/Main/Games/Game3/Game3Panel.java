package Main.Games.Game3;

import Main.GameApp.GameApp;
import Main.Games.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Game3Panel extends GamePanel {
    private Nave nave;
    private ArrayList<Lixo> lixos = new ArrayList<>();
    private Laser laser;
    private Image fundo;
    private Image coracaoImg;
    private Clip explosionClip;
    private boolean laserAtivo = false;
    private final int MAX_VIDAS = 5;
    private int pontos = 0;
    private int vidas = MAX_VIDAS;
    private Random random = new Random();
    private boolean esquerdaPressionada = false;
    private boolean direitaPressionada = false;
    private boolean espacoPressionado = false;
    private boolean fimDeJogo = false;
    private JButton botaoJogarNovamente;

    public Game3Panel(JFrame frame) {
        super(frame);
        fundo = new ImageIcon("src/Main/Games/Game3/Assets/Fundo.jpeg").getImage();
        coracaoImg = new ImageIcon("src/Main/Games/Game3/Assets/Coracao.png").getImage();

        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("src/Main/Games/Game3/Assets/explosao.wav"));
            explosionClip = AudioSystem.getClip();
            explosionClip.open(audioIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        nave = new Nave(375, 700, 50, 50);
        laser = new Laser(0, 70, 5, 10, 20);

        for (int i = 0; i < 3; i++) {
            lixos.add(new Lixo(random.nextInt(750), random.nextInt(300) - 600, 40, 40));
        }

        setLayout(null);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A -> esquerdaPressionada = true;
                    case KeyEvent.VK_D -> direitaPressionada = true;
                    case KeyEvent.VK_SPACE -> espacoPressionado = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A -> esquerdaPressionada = false;
                    case KeyEvent.VK_D -> direitaPressionada = false;
                    case KeyEvent.VK_SPACE -> espacoPressionado = false;
                }
            }
        });

        botaoJogarNovamente = new JButton("JOGAR NOVAMENTE");
        botaoJogarNovamente.setFont(new Font("Arial", Font.BOLD, 16));
        botaoJogarNovamente.setSize(220, 50);
        botaoJogarNovamente.setVisible(false);
        botaoJogarNovamente.addActionListener(e -> reiniciarJogo());
        add(botaoJogarNovamente);

        Timer timer = new Timer(16, e -> {
            if (esquerdaPressionada) nave.moverEsquerda();
            if (direitaPressionada) nave.moverDireita();
            if (espacoPressionado) dispararLaser();

            updateGame();
            repaint();
        });
        timer.start();
    }

    private void updateGame() {
        if (this.isPaused || fimDeJogo) return;

        requestFocusInWindow();

        if (laserAtivo) {
            laser.mover();
            if (laser.getY() < 150) {
                laserAtivo = false;
            }

            Iterator<Lixo> it = lixos.iterator();
            while (it.hasNext()) {
                Lixo l = it.next();
                if (laser.getBounds().intersects(l.getBounds())) {
                    explosionClip.setFramePosition(0);
                    explosionClip.start();
                    it.remove();
                    pontos += 10;
                    laserAtivo = false;
                    if (pontos % 100 == 0 && vidas < MAX_VIDAS) vidas++;
                }
            }
        }

        Iterator<Lixo> it = lixos.iterator();
        while (it.hasNext()) {
            Lixo l = it.next();
            l.mover();
            if (l.getY() > getHeight()) {
                it.remove();
                perderVida();
            } else if (l.getBounds().intersects(nave.getBounds())) {
                it.remove();
                perderVida();
            }
        }

        while (lixos.size() < 3) {
            lixos.add(new Lixo(random.nextInt(750), -random.nextInt(500), 40, 40));
        }
    }

    private void perderVida() {
        if (vidas > 0) vidas--;
        if (vidas == 0 && !fimDeJogo) {
            fimDeJogo = true;
            centralizarBotao();
            botaoJogarNovamente.setVisible(true);
        }
    }

    private void centralizarBotao() {
        int centerX = getWidth() / 2 - botaoJogarNovamente.getWidth() / 2;
        int centerY = getHeight() / 2 + 30;
        botaoJogarNovamente.setLocation(centerX, centerY);
    }

    private void reiniciarJogo() {
        frame.getContentPane().removeAll();
        frame.setContentPane(new Game3Panel(frame));
        frame.revalidate();
        frame.repaint();
    }

    private void dispararLaser() {
        if (!laserAtivo) {
            laserAtivo = true;
            laser.resetPosition(nave.getX() + nave.getLargura() / 2 - 2, nave.getY());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);
        nave.desenhar(g);
        for (Lixo l : lixos) l.desenhar(g);
        if (laserAtivo) laser.desenhar(g);

        for (int i = 0; i < vidas; i++) {
            g.drawImage(coracaoImg, 10 + i * 40, 10, 30, 30, null);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Pontos: " + pontos, getWidth() - 150, 30);

        if (fimDeJogo) {
            g.setFont(new Font("Arial", Font.BOLD, 48));
            FontMetrics fm = g.getFontMetrics();
            String msg = "FIM DE JOGO";
            int msgWidth = fm.stringWidth(msg);
            int x = (getWidth() - msgWidth) / 2;
            int y = getHeight() / 2 - 30;
            g.setColor(Color.RED);
            g.drawString(msg, x, y);
        }
    }
}