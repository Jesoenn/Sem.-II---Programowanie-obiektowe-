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
        generateWallsLocation(5);
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
    //BYC MOZE DOBRZE, ALE JESZCZE DO SPRAWDZENIA
    /*
    JEST BUG: po lewej trojkacik sie zrobil
1100000000003
0100000000000
0000000000000
     */
    public void generateWallsLocation(int amount){
        Random location=new Random();
        boolean keepGenerating=false;
        int row=location.nextInt(silnik.squaresY);
        int column=location.nextInt(silnik.squaresY);
        int facing=location.nextInt(2)+1; //1 pionowa | 2 pozioma
        for(int i=0; i<amount; i++){
            while(map[row][column]!=0 || keepGenerating==true){
                row=location.nextInt(silnik.squaresY);
                column=location.nextInt(silnik.squaresY);
                facing=location.nextInt(2)+1;
                keepGenerating=false;
            }
            if(facing==1){ //sciana pionowa
                if(row>0 && row<=silnik.squaresY-1 && map[row-1][column]==0){
                    map[row][column]=1;
                    map[row-1][column]=1;
                }
                else if(row==0 && map[row+1][column]==0){
                    map[row][column]=1;
                    map[row+1][column]=1;
                }
                else{
                    keepGenerating=true;
                    amount++;
                }
            }
            else if(facing==2){
                if(column>0 && column<silnik.squaresX-1 && map[row][column-1]==0){
                    map[row][column]=1;
                    map[row][column-1]=1;
                }
                else if(column==0 && map[row][column+1]==1){
                    map[row][column]=1;
                    map[row][column+1]=1;
                }
                else{
                    keepGenerating=true;
                    amount++;
                }
            }
        }
        for(int i=0; i<silnik.squaresY; i++){
            for(int j=0; j<silnik.squaresX; j++){
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
    public void drawMap(Graphics2D g2d){
        g2d.drawImage(mapImage,0,0,silnik.screenX,silnik.screenY,null);
    }
}
