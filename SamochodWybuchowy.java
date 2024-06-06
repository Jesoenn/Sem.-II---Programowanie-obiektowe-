package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;
public class SamochodWybuchowy extends Samochod{
    public static final double promien_obrazen=100;
    private boolean exploded=false;
    private int explosionLength=60; // klatek
    private Ellipse2D pole_razenia;
    SamochodWybuchowy(Derby derby, int givenX, int givenY)
    {
        super(derby, givenX, givenY);
    }
    @Override
    public void downloadImages(){ //Byc moze zmienic zdjecia dla klas pochodnych - inne kolory aut
        try{
            up=ImageIO.read(getClass().getResource("/redcar/carRedUp.png"));
            right=ImageIO.read(getClass().getResource("/redcar/carRedRight.png"));
            down=ImageIO.read(getClass().getResource("/redcar/carRedDown.png"));
            left=ImageIO.read(getClass().getResource("/redcar/carRedLeft.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void Eksplozja()
    {
        exploded=true;
        pole_razenia=new Ellipse2D.Double(x+derby.samochodSize/2-promien_obrazen,y+derby.samochodSize/2-promien_obrazen,promien_obrazen*2,promien_obrazen*2);
        for(Samochod car: cars){
            if(pole_razenia.intersects(car.getHitbox()) && car.getCarId()!=carId){
                car.sethp(car.gethp()-50);
                checkDeath(car);
            }
        }
        // to moze do zrobienia jak sie ogarnie strate zycia samochodu
    }
    public void explosion(Graphics2D g2d){
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
