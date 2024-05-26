import java.util.Random;
import java.awt.*;
import javax.imageio.ImageIO;

public class Nitro {
    private int speed;
    private static int countNitro = 1;
    private int id,x,y;
    private final int czas = 30; // czas dzialania nitra
    private Image nitro;
    private Derby derby;
    public Nitro(Derby derby, int x, int y)
    {
        this.derby = derby;
        id = countNitro;
        countNitro++;
        this.x = x;
        this.y = y;
        downloadImages();
    }
    private void downloadImages()
    {
        try
        {
            nitro = ImageIO.read(getClass().getResource(("/Nitro.png")));
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
