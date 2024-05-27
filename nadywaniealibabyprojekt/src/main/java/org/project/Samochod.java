package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Random;

public class Samochod {
    private static int carCount=0;   //ile samochodow - za tego pomocą ustawienie ich id
    private int carId;
    private int targetId; //id samochodu (od 0), id celu, do którego będzie się poruszać
    private int targetBuf; // id poprzedniego celu
    public boolean collision=false; //przy kolizji zmieniam na true
    private int x,y,prevX,prevY; //Polozenie samochodu obecne i polozenie w poprzedniej klatce
    private int waga,speed;
    private int iterationMoment, iterationBuf; // po numerze iteracji z derby patrzy na warunek ilosci iteracji zeby nie jechal ciagle w strone odbicia sie od sciany
    private int hp; // Punkty zycia
    private double turningAngle; // promien skretu
    private boolean tires; // stan opon samochodu(narazie tak ze jak raz sie uszkodzi to zmiana na bool'u i potem przy uszkodzeniu nic sie wiecej nie zmienia)
    private Image up,right,down,left; //Zdjecia samochodu obroconego w rozne strony
    private Derby derby;
    private Random generateNumber=new Random();
    private Rectangle hitbox; // DO ZROBIENIA HITBOXY INTERSECTS
    private int killCount; // ile samochodow pokonal dany samochod
    public Samochod(Derby derby, int givenX, int givenY){
        this.derby = derby;
        //tymczasowo, zrobic losowo, zeby nic na siebie nie nachodzilo - najpierw generowana map
        prevY=y=givenY;
        prevX=x=givenX;
        waga=1500;
        speed=waga/750;
        hp = 100;
        //ponizej koncowe, nie zmieniac
        downloadImages();
        hitbox=new Rectangle(x,y,derby.samochodSize,derby.samochodSize);
        carId=carCount;
        carCount++;
        targetId=carId;
        targetBuf = carId;
        iterationMoment = iterationBuf = 0;
    }
    private void downloadImages(){ //Byc moze zmienic zdjecia dla klas pochodnych - inne kolory aut
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
        while(targetId==carId && targetId == targetBuf){
            targetId=generateNumber.nextInt(carCount);
        }
    }
    public void turnWhenIntersectWall() // po "odbiciu sie" od sciany
    {
        if(iterationMoment - iterationBuf == 30) // po przykladowej ilosci iteracji
        {
            setSpeed(-getSpeed());
            findTarget();
        }
    }
    //Obliczanie obecnej lokalizacji na mapie -> duzo poprawek do zrobienia
    public void movement(int targetX, int targetY){
        prevX=x;
        prevY=y;
        if(collision){ //jezeli cel zostal zaatakowany, to szukamy nowego celu, jeszcze nie dziala
            //targetId=carId;
            targetBuf = targetId;
            findTarget();
            collision=false;
        }
        if(x>targetX && x>0){
            x-=speed;
        }
        else if(x<targetX && x< derby.screenX)
            x+=speed;
        if(y>targetY && y>0)
            y-=speed;
        else if(y<targetY && y< derby.screenY)
            y+=speed;
        hitbox.setLocation(x,y);
    }
    //Aktualizacja zdjecia na ekranie
    public void updateOnMap(Graphics2D g2d){
        if(prevY>y)
            g2d.drawImage(up,x,y, derby.samochodSize, derby.samochodSize,null);
        else if(prevY<y)
            g2d.drawImage(down,x,y, derby.samochodSize, derby.samochodSize,null);
        else if(prevX>x)
            g2d.drawImage(left,x,y, derby.samochodSize, derby.samochodSize,null);
        else if(prevX<x)
            g2d.drawImage(right,x,y, derby.samochodSize, derby.samochodSize,null);
        else if(prevX==x && prevY==y){
            g2d.drawImage(up,x,y, derby.samochodSize, derby.samochodSize,null);
        }
    }
    public void setSpeed(int speed){
        this.speed=speed;
    }
    public int getSpeed() { return speed;};
    public int getCurrentX(){
        return x;
    }
    public int getCurrentY(){
        return y;
    }
    public int getCarId(){
        return carId;
    }
    public Rectangle getHitbox(){
        return hitbox;
    }
    public int getTargetId(){
        return targetId;
    }
    public void setTargetId(int newTargetId){
        targetId=newTargetId;
    }
    public int getIterationMoment() {return iterationMoment;}
    public void setIterationMoment(int iteration) {iterationMoment = iteration;}
    public int getIterationBuf() {return iterationBuf;}
    public void setIterationBuf(int iteration) {iterationBuf = iteration;}
    public void gotDamage(int damage) {hp -= damage;}
}
