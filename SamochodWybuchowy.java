import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;
public class SamochodWybuchowy extends Samochod{
    double promien_obrazen;
    public Ellipse2D pole_razenia = new Ellipse2D.Double();
    SamochodWybuchowy(Derby derby, int givenX, int givenY, double radius)
    {
        super(derby, givenX, givenY);
        this.promien_obrazen = radius;
        pole_razenia.setFrame(x, y, promien_obrazen, promien_obrazen);
    }
    public void Eksplozja()
    {
        // to moze do zrobienia jak sie ogarnie strate zycia samochodu
    }
}
