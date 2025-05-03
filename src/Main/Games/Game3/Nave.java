package Main.Games.Game3;

import javax.swing.*;
import java.awt.*;

public class Nave {
    private int x, y;
    private int largura, altura;
    private Image imagem;
    private final int VELOCIDADE = 5;

    public Nave(int x, int y, int largura, int altura) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.imagem = new ImageIcon("src/Main/Games/Game3/Assets/nave.png").getImage();
    }

    public void moverEsquerda() {
        if (x - VELOCIDADE >= 0) {
            x -= VELOCIDADE;
        }
    }

    public void moverDireita() {
        if (x + largura + VELOCIDADE <= 800) {
            x += VELOCIDADE;
        }
    }

    public void desenhar(Graphics g) {
        g.drawImage(imagem, x, y, largura, altura, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLargura() {
        return largura;
    }
}
