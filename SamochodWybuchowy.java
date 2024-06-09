package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;
public class SamochodWybuchowy extends Samochod{
    public static final double promien_obrazen=100;
    private boolean exploded=false;
    private int explosionLength=60; // dlugosc w klatkach
    private Ellipse2D pole_razenia;
    SamochodWybuchowy(Derby derby, int givenX, int givenY,int carsWeight)
    {
        super(derby, givenX, givenY,carsWeight);
    }
    @Override
    public void downloadImages(){
        try{
            up=ImageIO.read(getClass().getResource("/redcar/carRedUp.png"));
            right=ImageIO.read(getClass().getResource("/redcar/carRedRight.png"));
            down=ImageIO.read(getClass().getResource("/redcar/carRedDown.png"));
            left=ImageIO.read(getClass().getResource("/redcar/carRedLeft.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //wywolanie juz po smierci
    public void Eksplozja()
    {
        exploded=true;
        pole_razenia=new Ellipse2D.Double(x+derby.samochodSize/2-promien_obrazen,y+derby.samochodSize/2-promien_obrazen,promien_obrazen*2,promien_obrazen*2);
        for(Samochod car: cars){
            if(pole_razenia.intersects(car.getHitbox()) && car.getCarId()!=carId){
                car.sethp(car.gethp()-30);
                checkDeath(car);
            }
        }
    }
    public void explosion(Graphics2D g2d){
        g2d.setColor(Color.orange);
        g2d.fill(pole_razenia);
        explosionLength--;
    }
    public boolean getExploded(){
        return exploded;
    }
    public int getExplosionLength(){
        return explosionLength;
    }
}
