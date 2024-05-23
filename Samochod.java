import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Random;

public class Samochod {
    public static int carCount=0;   //ile samochodow
    public int carId,targetId;
    boolean targetIdReached=false; //przy kolizji zmieniam na true
    public int x,y,prevX,prevY; //Polozenie samochodu i polozenie w poprzedniej klatce
    public int waga,speed;
    public Image up,right,down,left;
    Silnik silnik;
    Random generateNumber=new Random();
    public Samochod(Silnik silnik,int givenX, int givenY){
        this.silnik=silnik;
        //tymczasowo, zrobic losowo, zeby nic na siebie nie nachodzilo - najpierw generowana map
        prevY=y=givenY;
        prevX=x=givenX;
        waga=1500;
        speed=waga/300;
        //Koncowe, nie zmieniac
        downloadImages();
        carCount++;
        carId=carCount;
        targetId=carId;
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
    public void update(int targetX, int targetY){
        if(targetIdReached){ //jezeli cel zostal zaatakowany, to szukamy nowego celu
            targetId=carId;
            targetIdReached=false;
        }
        while(targetId==carId){
            targetId=generateNumber.nextInt(carCount)+1;
        }
        if(x>targetX && x>0)
            x-=speed;
        else if(x<targetX && x<silnik.screenX)
            x+=speed;
        if(y>targetY && y>0)
            y-=speed;
        else if(y<targetY && y<silnik.screenY)
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
    public int getCurrentX(){
        return x;
    }
    public int getCurrentY(){
        return y;
    }
}
