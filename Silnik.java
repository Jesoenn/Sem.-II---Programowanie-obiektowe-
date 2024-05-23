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
    //Tworzenie obiektow
    Mapa map=new Mapa(this);
    Samochod samochod1= new Samochod(this,50,50);
    Samochod samochod2=new Samochod(this,500,500);
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
        //zrobic petle aktualizujaca tablice samochodow
        samochod1.movement(samochod2.getCurrentX(),samochod2.getCurrentY());
        samochod2.movement(samochod1.getCurrentX(),samochod1.getCurrentY());
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g); //jak to dodaje to ekran sie czysci
        Graphics2D g2d=(Graphics2D) g;
        map.drawMap(g2d);
        samochod1.updateOnMap(g2d);
        samochod2.updateOnMap(g2d);
        //g2d.dispose();
    }
}
