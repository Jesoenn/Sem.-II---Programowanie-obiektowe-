package org.project;

import javax.imageio.ImageIO;
import java.awt.geom.Line2D;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.lang.Math;
public class SamochodLaserowy extends Samochod{
    private Color laserColor=Color.green;
    private static int laserDamage;
    private String facing="up";
    //private Line2D ray;
    private Rectangle ray;
    private boolean rayGenerated=false;
    private int timeUntilLaser;
    private int laserDuration=2*60;
    public SamochodLaserowy(Derby derby, int givenX, int givenY,int carsWeight)
    {
        super(derby, givenX, givenY,carsWeight);
        laserDamage=2;
        timeUntilLaser=(generateNumber.nextInt(3)+2)*60; //Od 3 do 5sekund razy klatki na sekunde
    }
  @Override
    public void downloadImages(){
        try{
            up=ImageIO.read(getClass().getResource("/greencar/carGreenUp.png"));
            right=ImageIO.read(getClass().getResource("/greencar/carGreenRight.png"));
            down=ImageIO.read(getClass().getResource("/greencar/carGreenDown.png"));
            left=ImageIO.read(getClass().getResource("/greencar/carGreenLeft.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void createRay(){
        //patrze jak skierowany jest samochod, i wystrzeliwuje
        if(facing=="up"){
            ray=new Rectangle(x+23,0,4,y); // po calej mapie poczawszy od wsp. samochodu //x,y,szer,wys
        }
        else if(facing=="down"){
            ray=new Rectangle(x+23,y+50,4,derby.screenY-y+50);
        }
        else if(facing=="left"){
            ray=new Rectangle(0,y+23,x,4);
        }
        else if(facing=="right"){
            ray=new Rectangle(x+50,y+23,derby.screenX-x+50,4);
        }
        rayGenerated=true;
    }
    public void rayShooting() // wersja robiona dla starego movementu(czyli gora/dol prawo/lewo skos)
    {
        for(Samochod car: cars){
            if(ray.intersects(car.getHitbox()) && car.getCarId()!=carId){
                car.sethp(car.gethp()-laserDamage);
                checkDeath(car);
            }
        }
    }
    public void rayColoring(Graphics2D g2d)
    {
        g2d.setColor(laserColor);
        if(laserDuration%8==0){
            laserColor=new Color(generateNumber.nextInt(256),generateNumber.nextInt(256),generateNumber.nextInt(256));
        }
        g2d.fill(ray);
        //LOSOWY KOLOR JAK PODZIELNE PRZEZ 3 CZAS POZOSTALY ?
    }
    @Override
    public void update(){
        if(hp>0 && alive){
            timeUntilLaser--;
            if(timeUntilLaser<=0 && !rayGenerated && !usingNitro){
                speed=0;
                createRay();
            }
            if(rayGenerated){
                rayShooting();
                laserDuration--;
                if(laserDuration==0){
                    rayGenerated=false;
                    timeUntilLaser=(generateNumber.nextInt(3)+2)*60;
                    laserDuration=2*60;
                    speed=1;
                }
            }
        }
        super.update();
    }
    @Override
    public void updateOnMap(Graphics2D g2d){
        if(hp>0){
            if(prevY>y){
                facing="up";
                g2d.drawImage(up,x,y, derby.samochodSize, derby.samochodSize,null);
            }
            else if(prevY<y){
                facing="down";
                g2d.drawImage(down,x,y, derby.samochodSize, derby.samochodSize,null);
            }
            else if(prevX>x){
                facing="left";
                g2d.drawImage(left,x,y, derby.samochodSize, derby.samochodSize,null);
            }
            else if(prevX<x){
                facing="right";
                g2d.drawImage(right,x,y, derby.samochodSize, derby.samochodSize,null);
            }
            else if(prevX==x && prevY==y){
                if(facing=="up")
                    g2d.drawImage(up,x,y, derby.samochodSize, derby.samochodSize,null);
                else if(facing=="down")
                    g2d.drawImage(down,x,y, derby.samochodSize, derby.samochodSize,null);
                else if(facing=="left")
                    g2d.drawImage(left,x,y, derby.samochodSize, derby.samochodSize,null);
                else if(facing=="right")
                    g2d.drawImage(right,x,y, derby.samochodSize, derby.samochodSize,null);
            }
            if(rayGenerated){
                rayColoring(g2d);
            }
        }
        g2d.setColor(Color.red);
        g2d.drawString(hp+" id:"+carId,x,y);
    }
}
