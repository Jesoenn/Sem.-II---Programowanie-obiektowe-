package org.project;

import java.util.Random;
import java.awt.*;
import javax.imageio.ImageIO;

public class Nitro {
    private int speed;
    private static int countNitro = 1;
    private int czas=30; // czas dzialania nitra (dalem nie final static,
                         // zeby mozna zmniejszac do 0 - nie robic drugiej zmiennej)
    private int id,x,y;
    private boolean gotNitroMod = false; // info czy zostal zebrany przez samochod czy nie
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
        hitbox=new Rectangle(x,y,derby.samochodSize,derby.samochodSize);
    }
    private void downloadImages()
    {
        try{
            nitro = ImageIO.read(getClass().getResource(("/nitro.png")));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void generateonMap(Graphics2D g2d){
        g2d.drawImage(nitro,x,y,derby.samochodSize,derby.samochodSize,null);
    }
    public int getId(){return id;}
    
    public Rectangle getHitbox(){return hitbox;}
    
    public int getSpeed() {return speed;}
    public void setSpeed(int newSpeed) {speed = newSpeed;}
    public double getTime() {return time;}
    public void setTime(double czas) {time = czas;}
    public void SetX(int x_) {x = x_;}
    public void SetY(int y_) {y = y_;}
    public boolean getGotNitro() {return gotNitroMod;}
    public void setGotNitroMod(boolean getter) {gotNitroMod = getter;}

}
