import javax.imageio.ImageIO;
import java.awt.*;

public class Samochod {
    public int x,y,celX,celY,waga,speed;
    String facing;
    public Image currentImage,up,right,down,left;
    Silnik silnik;
    public Samochod(Silnik silnik){
        this.silnik=silnik;
        //tymczasowo, zrobic losowo, zeby nic na siebie nie nachodzilo - najpierw generowana map
        x=0;
        y=0;
        waga=1500;
        speed=waga/500;
        facing="up";
        downloadImages();
    }
    public void downloadImages(){
        try{
            up=ImageIO.read(getClass().getResource("/carYellowUp.png"));
            right=ImageIO.read(getClass().getResource("/carYellowRight.png"));
            down=ImageIO.read(getClass().getResource("/carYellowDown.png"));
            left=ImageIO.read(getClass().getResource("/carYellowLeft.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void update(){
        if(x<600)
            x+=speed;
        if(y<600)
            y+=speed;
    }
    public void movement(Graphics2D g2d){
        g2d.drawImage(down,x,y,silnik.samochodSize,silnik.samochodSize,null);
    }
}
