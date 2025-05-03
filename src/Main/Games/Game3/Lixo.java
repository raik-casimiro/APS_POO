package Main.Games.Game3;

import javax.swing.*;
import java.awt.*;

public class Lixo {
    private int x, y;
    private int largura, altura;
    private Image imagem;
    private final int VELOCIDADE = 2;

    public Lixo(int x, int y, int largura, int altura) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.imagem = new ImageIcon("src/Main/Games/Game3/Assets/Lixos.png").getImage();
    }

    public void mover() {
        y += VELOCIDADE;
    }

    public void desenhar(Graphics g) {
        g.drawImage(imagem, x, y, largura, altura, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }

    public int getY() {
        return y;
    }
}
