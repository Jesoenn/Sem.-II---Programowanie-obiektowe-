package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Random;

public class Mapa {
    private Image mapImage; //Zdjecie tła mapy
    private Derby derby;
    private int[][] map; //Miejsca, w ktorych moga byc generowane Sciany,Nitra,Samochody
    public Mapa(Derby derby){
        this.derby = derby;
        map=new int[derby.squaresY][derby.squaresX]; // wiersze/kolumny
        downloadImages();
        generateLocation(derby.getCarsNormalAmount(),3);
        generateLocation(derby.getCarsExplosiveAmount(),4);
        generateLocation(derby.getNitroAmount(),6);
        generateWallsLocation(derby.getWallsAmount());
    }
    private void downloadImages(){
        try{
            mapImage=ImageIO.read(getClass().getResource("/mapa.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Generowanie miejsca na mapie dla wszystkich samochodów i (potem) nitro
    private void generateLocation(int amount, int symbol){ //symbol - oznaczenie na mapie
        Random location=new Random();
        int row=location.nextInt(derby.squaresY);
        int column=location.nextInt(derby.squaresX);
        for(int i=0; i<amount; i++){
            while(map[row][column]!=0){
                row=location.nextInt(derby.squaresY);
                column=location.nextInt(derby.squaresX);
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
    private void generateWallsLocation(int amount){
        Random location=new Random();
        boolean keepGenerating=false;
        int row=location.nextInt(derby.squaresY);
        int column=location.nextInt(derby.squaresX);
        int facing=location.nextInt(2)+1; //1 pionowa | 2 pozioma
        for(int i=0; i<amount; i++){
            //generowanie do momentu trafienia pustego pola
            while(map[row][column]!=0 || keepGenerating==true){
                row=location.nextInt(derby.squaresY);
                column=location.nextInt(derby.squaresX);
                facing=location.nextInt(2)+1;
                keepGenerating=false;
            }
            if(facing==1){ //sciana pionowa
                //Jezeli jest miejsce na sciane nad
                if(row>0 && row<= derby.squaresY-1 && map[row-1][column]==0){
                    map[row][column]=1;
                    map[row-1][column]=1;
                }
                //Jezeli jest miejsce na sciane pod
                else if(row==0 && map[row+1][column]==0){
                    map[row][column]=1;
                    map[row+1][column]=1;
                }
                //Jak nie ma miejsca, to dalej je generuje
                else{
                    keepGenerating=true;
                    amount++;
                }
            }
            else if(facing==2){
                //Jezeli jest miejsce kolumne w lewo
                if(column>0 && column< derby.squaresX-1 && map[row][column-1]==0){
                    map[row][column]=2;
                    map[row][column-1]=2;
                }
                //Jezeli jest miejsce kolumne w prawo
                else if(column==0 && map[row][column+1]==0){
                    map[row][column]=2;
                    map[row][column+1]=2;
                }
                //Jezeli nie ma miejsca to dalej je generuje
                else{
                    keepGenerating=true;
                    amount++;
                }
            }
        }
        //Wypisanie mapy w konsoli (pozniej moze do plikow)
        for(int i = 0; i< derby.squaresY; i++){
            for(int j = 0; j< derby.squaresX; j++){
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
    public int[][] getMap(){
        return map;
    }
    public void drawMap(Graphics2D g2d){
        g2d.drawImage(mapImage,0,0, derby.screenX, derby.screenY,null);
    }
}
