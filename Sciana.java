import javax.imageio.ImageIO;
import java.awt.*;

public class Sciana {
    Image wall;
    public Sciana(){
        downloadImages();
    }
    public void downloadImages(){
        try{
            wall= ImageIO.read(getClass().getResource("/wall.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
