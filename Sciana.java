import javax.imageio.ImageIO;
import java.awt.*;

public class Sciana {
    public static int countSciany=1;
    public int id;
    Derby derby;
    Image wallHorizontal,wallVertical;
    public int x, y;
    String facing;
    Rectangle hitbox; // DO ZROBIENIA dac ze jak cos wjedzie w sciane to speed=0 I Zobacyzm
    public Sciana(Derby derby, int x, int y, String facing){
        id=countSciany;
        countSciany++;
        downloadImages();
        this.x=x;
        this.y=y;
        this.derby=derby;
        this.facing=facing;
        hitbox=new Rectangle(x,y,derby.samochodSize,derby.samochodSize);
    }
    public void downloadImages(){
        try{
            wallHorizontal= ImageIO.read(getClass().getResource("/wallHorizontal.png"));
            wallVertical=ImageIO.read(getClass().getResource("/wallVertical.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void generateOnMap(Graphics2D g2d){
        if(facing=="vertical")
            g2d.drawImage(wallVertical,x,y,derby.samochodSize,derby.samochodSize,null);
        else if(facing=="horizontal")
            g2d.drawImage(wallHorizontal,x,y,derby.samochodSize,derby.samochodSize,null);
    }
}
