import javax.imageio.ImageIO;
import java.awt.*;

public class Samochod {
    public int x,y,prevX,prevY;
    public int celX,celY;
    public int waga,speed;
    String facing;
    public Image currentImage,up,right,down,left;
    Silnik silnik;
    public Samochod(Silnik silnik){
        this.silnik=silnik;
        //tymczasowo, zrobic losowo, zeby nic na siebie nie nachodzilo - najpierw generowana map
        prevY=y=60;
        prevX=x=20;
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
        if(prevY>y)
            g2d.drawImage(up,x,y,silnik.samochodSize,silnik.samochodSize,null);
        else if(prevY<y)
            g2d.drawImage(down,x,y,silnik.samochodSize,silnik.samochodSize,null);
        else if(prevX>x)
            g2d.drawImage(left,x,y,silnik.samochodSize,silnik.samochodSize,null);
        else if(prevX<x)
            g2d.drawImage(right,x,y,silnik.samochodSize,silnik.samochodSize,null);
    }
}
