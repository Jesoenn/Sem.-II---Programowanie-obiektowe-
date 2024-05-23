import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Random;

public class Mapa {
    Image mapImage; //Zdjecie tła mapy
    Silnik silnik;
    int[][] map; //Miejsca, w ktorych moga byc generowane Sciany,Nitra,Samochody
    public Mapa(Silnik silnik){
        this.silnik=silnik;
        map=new int[silnik.squaresY][silnik.squaresX]; // wiersze/kolumny
        downloadImages();
        generateLocation(silnik.carsNormalAmount,3);
    }
    public void downloadImages(){
        try{
            mapImage=ImageIO.read(getClass().getResource("/mapa.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Generowanie miejsca na mapie dla wszystkich samochodów i nitro
    public void generateLocation(int amount, int symbol){ //symbol - oznaczenie na mapie
        Random location=new Random();
        int row=location.nextInt(silnik.squaresY);
        int column=location.nextInt(silnik.squaresY);
        for(int i=0; i<amount; i++){
            while(map[row][column]!=0){
                row=location.nextInt(silnik.squaresY);
                column=location.nextInt(silnik.squaresY);
            }
            map[row][column]=symbol;
        }
//        for(int i=0; i<silnik.squaresY; i++){
//            for(int j=0; j<silnik.squaresX; j++){
//                System.out.print(map[i][j]);
//            }
//            System.out.println();
//        }
    }
    public void drawMap(Graphics2D g2d){
        g2d.drawImage(mapImage,0,0,silnik.screenX,silnik.screenY,null);
//        for(int i=0; i<silnik.squaresY; i++){
//            for(int j=0; j<silnik.squaresX; j++){
//                if()
//            }
//        }
    }
}
