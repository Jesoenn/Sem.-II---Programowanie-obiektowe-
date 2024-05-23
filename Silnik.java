import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Silnik extends JPanel {
    public int carsNormalAmount=5; //TYMCZASOWO ILE SAMOCHODOW, BEDZIE TO LICZONE W EKANGŁOWNY
    public int carsExplosiveAmount,carsTirePopersAmount,nitroAmount,wallAmount; //Liczba wspomnianych rzeczy na mapie
    final int samochodSize=50;
    final int screenX=650;
    final int screenY=650;
    final int squaresX=screenX/samochodSize;
    final int squaresY=screenY/samochodSize;
    //Tworzenie obiektow
    Mapa map=new Mapa(this);
    ArrayList<Samochod> normalCars=new ArrayList<>();
    //Samochod samochod1=new Samochod(this,50,50);
    //Samochod samochod2=new Samochod(this,500,500);
    public Silnik(){
        setPreferredSize(new Dimension(screenX,screenY));
        setDoubleBuffered(true); //Nie wiem co robi sprawdzic
    }
    public void setStartingPositions(){
        int[][] startingMap=map.map;
        //Przejscie przez kazde pole na mapie i stworzenie odpowiednich obiektow
        for(int i=0; i<squaresY; i++){
            for(int j=0; j<squaresX; j++){
                if(startingMap[i][j]==3){
                    normalCars.add(new Samochod(this,i*samochodSize,j*samochodSize));
                }
            }
        }
    }
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
            gameStateUpdate();
            repaint();
        }
    }
    public void gameStateUpdate(){
        //zrobic petle aktualizujaca tablice samochodow
        //2 komentarze ponizej to jest jazdy samochodow w swoja strone
        //samochod1.movement(samochod2.getCurrentX(),samochod2.getCurrentY());
        //samochod2.movement(samochod1.getCurrentX(),samochod1.getCurrentY());
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g); //jak to dodaje to ekran sie czysci
        Graphics2D g2d=(Graphics2D) g;
        map.drawMap(g2d);
        for(Samochod normalCar: normalCars){
            normalCar.updateOnMap(g2d);
        }
    }
}
