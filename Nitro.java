package org.project;

import java.util.Random;
import java.awt.*;
import javax.imageio.ImageIO;

public class Nitro {
    public static final int speedBoost=1; //przyspieszenie speed+=speedBoost
    private static int countNitro = 0;
    private boolean pickedUp=false;
    private int time=60*2; // ilosc iteracji=fps*sekundy dzialania nitra
    private int id,x,y;
    private Image nitro;
    private Derby derby;
    private Rectangle hitbox;
    public Nitro(Derby derby, int x, int y)
    {
        this.derby=derby;
        id = countNitro;
        countNitro++;
        this.x = x;
        this.y = y;
        downloadImages();
        hitbox=new Rectangle(x+10,y+3,30,44);
    }
    private void downloadImages()
    {
        try{
            nitro = ImageIO.read(getClass().getResource(("/nitro.png")));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void takeNitro(){
        pickedUp=true;
        hitbox.setLocation(-500,-500);
    }
    public void nitroUsage(){
        time--;
    }
    public void generateonMap(Graphics2D g2d){
        if(!pickedUp)
            g2d.drawImage(nitro,x+10,y+3,30,44,null);
    }
    public int getId(){return id;}
    public Rectangle getHitbox(){
        return hitbox;
    }
    public int getTime(){
        return time;
    }

}
