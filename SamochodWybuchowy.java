package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;
public class SamochodWybuchowy extends Samochod{
    public static final double promien_obrazen=15;
    private Ellipse2D pole_razenia = new Ellipse2D.Double();
    SamochodWybuchowy(Derby derby, int givenX, int givenY)
    {
        super(derby, givenX, givenY);
        pole_razenia.setFrame(getCurrentX(), getCurrentY(), promien_obrazen, promien_obrazen);
    }
    public void Eksplozja()
    {
        // to moze do zrobienia jak sie ogarnie strate zycia samochodu
    }
}
