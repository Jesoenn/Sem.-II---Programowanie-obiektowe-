package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;
public class SamochodWybuchowy extends Samochod{
    public static final double promien_obrazen=100;
    private Ellipse2D pole_razenia = new Ellipse2D.Double();
    SamochodWybuchowy(Derby derby, int givenX, int givenY)
    {
        super(derby, givenX, givenY);
        pole_razenia.setFrame(getCurrentX(), getCurrentY(), promien_obrazen, promien_obrazen);
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
        // to moze do zrobienia jak sie ogarnie strate zycia samochodu
    }
}
