import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Random;

public class Samochod {
    public static int carCount=0;   //ile samochodow - za tego pomocą ustawienie ich id
    public int carId,targetId; //id samochodu (od 0), id celu, do którego będzie się poruszać
    boolean targetIdReached=false; //przy kolizji zmieniam na true
    public int x,y,prevX,prevY; //Polozenie samochodu obecne i polozenie w poprzedniej klatce
    public int waga,speed;
    public Image up,right,down,left; //Zdjecia samochodu obroconego w rozne strony
    Silnik silnik;
    Random generateNumber=new Random();
    Rectangle hitbox; // DO ZROBIENIA HITBOXY INTERSECTS
    public Samochod(Silnik silnik,int givenX, int givenY){
        this.silnik=silnik;
        //tymczasowo, zrobic losowo, zeby nic na siebie nie nachodzilo - najpierw generowana map
        prevY=y=givenY;
        prevX=x=givenX;
        waga=1500;
        speed=waga/750;
        //ponizej koncowe, nie zmieniac
        downloadImages();
        carId=carCount;
        carCount++;
        targetId=carId;
    }
    public void downloadImages(){ //Byc moze zmienic zdjecia dla klas pochodnych - inne kolory aut
        try{
            up=ImageIO.read(getClass().getResource("/carYellowUp.png"));
            right=ImageIO.read(getClass().getResource("/carYellowRight.png"));
            down=ImageIO.read(getClass().getResource("/carYellowDown.png"));
            left=ImageIO.read(getClass().getResource("/carYellowLeft.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //szukanie przeciwnika
    public void findTarget(){
        while(targetId==carId){
            targetId=generateNumber.nextInt(carCount);
        }
    }
    //Obliczanie obecnej lokalizacji na mapie -> duzo poprawek do zrobienia
    public void movement(int targetX, int targetY){
        prevX=x;
        prevY=y;
        if(targetIdReached){ //jezeli cel zostal zaatakowany, to szukamy nowego celu, jeszcze nie dziala
            targetId=carId;
            targetIdReached=false;
        }
        if(x>targetX && x>0){
            x-=speed;
        }
        else if(x<targetX && x<silnik.screenX)
            x+=speed;
        if(y>targetY && y>0)
            y-=speed;
        else if(y<targetY && y<silnik.screenY)
            y+=speed;
    }
    //Aktualizacja zdjecia na ekranie
    public void updateOnMap(Graphics2D g2d){
        if(prevY>y)
            g2d.drawImage(up,x,y,silnik.samochodSize,silnik.samochodSize,null);
        else if(prevY<y)
            g2d.drawImage(down,x,y,silnik.samochodSize,silnik.samochodSize,null);
        else if(prevX>x)
            g2d.drawImage(left,x,y,silnik.samochodSize,silnik.samochodSize,null);
        else if(prevX<x)
            g2d.drawImage(right,x,y,silnik.samochodSize,silnik.samochodSize,null);
        else if(prevX==x && prevY==y){
            g2d.drawImage(up,x,y,silnik.samochodSize,silnik.samochodSize,null);
        }
    }
}
