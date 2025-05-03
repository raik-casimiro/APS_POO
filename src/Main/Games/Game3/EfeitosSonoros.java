package Main.Games.Game3;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class EfeitosSonoros {

    public void tocarSom(String caminho) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("src/Assets/Sons/explosao.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
