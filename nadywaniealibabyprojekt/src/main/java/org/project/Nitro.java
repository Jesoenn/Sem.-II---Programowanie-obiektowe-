package org.project;

import java.util.Random;
import java.awt.*;
import javax.imageio.ImageIO;

public class Nitro {
    private int speed;
    private static int countNitro = 1;
    private int id,czas,x,y;
    private Image nitro;
    private Derby derby;
    private Rectangle hitbox;
    public Nitro(int x, int y, int time)
    {
        id = countNitro;
        countNitro++;
        this.x = x;
        this.y = y;
        czas = time;
        downloadImages();
        hitbox=new Rectangle(x,y,derby.samochodSize,derby.samochodSize);
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
    public int getId(){return id;}
    public Rectangle getHitbox(){
        return hitbox;
    }

}
