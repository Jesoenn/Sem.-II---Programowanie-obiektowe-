package org.project;

import javax.imageio.ImageIO;
import java.awt.geom.Line2D;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.lang.Math;
public class SamochodLaserowy extends Samochod{
    private int laserDamage;
    private Line2D ray;
    public SamochodLaserowy(Derby derby, int givenX, int givenY)
    {
        super(derby, givenX, givenY);
        laserDamage = 1;
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
    public void rayShooting(Samochod target) // wersja robiona dla starego movementu(czyli gora/dol prawo/lewo skos)
    {
        int targetX = target.getCurrentX();
        int targetY = target.getCurrentY();
        if(prevY>y)
            a = 0;
        else if(prevY<y)
            a = 0;
        else if(prevX>x)
            a = Double.NEGATIVE_INFINITY;
        else if(prevX<x)
            a = Double.POSITIVE_INFINITY;
        ray.setLine(a*y+b,y,a*derby.screenY+b,derby.screenY); // po calej mapie poczawszy od wsp. samochodu
        if(target.getHitbox().intersects(ray.getBounds()))
        {
            rayDamaging(target);
        }
    }
    public void rayDamaging(Samochod enemy)
    {
        enemy.sethp(enemy.gethp()-laserDamage);
        //zmienic na checkDeath(enemy);
        if(enemy.gethp() <= 0)
            enemy.setDead();
    }
    public void laserColoring(Graphics2D g2d)
    {
        g2d.setColor(Color.green);
        g2d.fill(ray);
    }
}
