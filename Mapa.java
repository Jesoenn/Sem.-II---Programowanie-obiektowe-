import javax.imageio.ImageIO;
import java.awt.*;

public class Mapa {
    Image map;
    Silnik silnik;
    public Mapa(Silnik silnik){
        this.silnik=silnik;
        downloadImages();
    }
    public void downloadImages(){
        try{
            map=ImageIO.read(getClass().getResource("/mapa.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void drawMap(Graphics2D g2d){
        g2d.drawImage(map,0,0,silnik.screenX,silnik.screenY,null);
    }
}
