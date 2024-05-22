import javax.swing.*;
import java.awt.*;

public class Silnik extends JPanel {
    //liczenie czasu do narysowania
    //paintcomponent
    final int samochodSize=50;
    final int screenX=650;
    final int screenY=650;
    public Silnik(){
        setPreferredSize(new Dimension(screenX,screenY));
        setDoubleBuffered(true); //Nie wiem co robi sprawdzic
    }
    Samochod samochod1= new Samochod(this);
    public void start(){
        double frameTime=16.66667; //60FPS - 1/60s
        double nextFrame=System.currentTimeMillis()+frameTime; //kiedy nastepna klatka
        double timeLeft; //czas pozostaly do nastepnej klatki
        while(true){
            try{
                timeLeft=nextFrame-System.currentTimeMillis();
                if(timeLeft>0) //jezeli jeszcze zostal czas
                    Thread.sleep((long)timeLeft);
                nextFrame=System.currentTimeMillis()+frameTime;
            }catch(Exception e){
                e.printStackTrace();
            }
            update();
            repaint();
        }
    }
    public void update(){
        samochod1.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g); //jak to dodaje to ekran sie czysci?
        Graphics2D g2d=(Graphics2D) g;
        samochod1.movement(g2d);
        //g2d.dispose();
    }
}
