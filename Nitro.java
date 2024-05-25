import java.util.Random;
import java.awt.*;
import javax.imageio.ImageIO;

public class Nitro {
    private int speed;
    private static int countNitro = 1;
    private int id,czas,x,y;
    private Image nitro;
    private Derby derby;
    public Nitro(int x, int y, int time)
    {
        id = countNitro;
        countNitro++;
        this.x = x;
        this.y = y;
        this.czas = time;
        downloadImages();
    }
    private void downloadImages()
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
