package Main.Games.Game3;

import java.awt.*;

public class Laser {
    private int x, y;
    private int largura, altura;
    private int velocidade;

    public Laser(int x, int y, int largura, int altura, int velocidade) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.velocidade = velocidade;
    }

    public void mover() {
        y -= velocidade;
    }

    public void resetPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void desenhar(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, largura, altura);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }

    public int getY() {
        return y;
    }
}
