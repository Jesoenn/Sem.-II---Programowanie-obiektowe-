import java.util.Random;
import java.awt.*;
import javax.imageio.ImageIO;

public class Nitro {
    int predkosc;
    public static int CountNitro = 1;
    int id;
    int czas;
    int x, y;
    Image nitro;
    Derby derby;
    public Nitro(int x, int y, int time)
    {
        id = CountNitro;
        CountNitro++;
        this.x = x;
        this.y = y;
        this.czas = time;
        downloadImages();
    }
    public void downloadImages()
    {
        try
        {
            nitro = ImageIO.read(getClass().getResource(("Nitro.png")));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void generateonMap(Graphics2D g2d){
        g2d.drawImage(nitro,x,y,derby.samochodSize,derby.samochodSize,null);
    }
    public int getId(){return this.id;}

}
