import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.*;

public class Sciana {
    public static int countSciany=1;
    public int id;
    Silnik derby;
    Image wall;
    public int x, y;
    Rectangle hitbox; // DO ZROBIENIA dac ze jak cos wjedzie w sciane to speed=0 I Zobacyzm
    public Sciana(Silnik derby,int x, int y){
        id=countSciany;
        countSciany++;
        downloadImages();
        this.x=x;
        this.y=y;
        this.derby=derby;
    }
    public void downloadImages(){
        try{
            wall= ImageIO.read(getClass().getResource("/wall.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void generateOnMap(Graphics2D g2d){
        g2d.drawImage(wall,x,y,derby.samochodSize,derby.samochodSize,null);
    }
}
