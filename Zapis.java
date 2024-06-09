package org.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class Zapis {
    ArrayList<Samochod> cars;
    String mainPath="src/main/java/org/project/dane/";
    String path;
    FileWriter rankings;
    FileWriter writer;
    public Zapis(Derby derby){
        cars=derby.normalCars;
    }
    public void startSaving(){
        saveRanking();
        getCarInfo("normalCar");
        getCarInfo("explosiveCar");
        getCarInfo("laserCar");
        getCarInfo("tirePopperCar");
    }
    public void saveRanking(){
        path=mainPath+"rankings.txt";
        boolean createdFile=createFile(path);
        try {
            rankings=new FileWriter(path,true);
            if(createdFile){
                rankings.write("SamochodZwykly;SamochodWybuchowy;SamochodLaserowy;SamochodOponowy;\n");
            }
            //1 - zwykly, 2-wybuch, 3-laser, 4-opon
            for(int i=1; i<=4; i++){
                int averageRanking=getAverageRanking(i);
                rankings.write(averageRanking+";");
            }
            rankings.write("\n");
            rankings.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int getAverageRanking(int index){
        int averageRanking=0;
        int howMany=0;
        for(Samochod car: cars){
            if(index==1){
                if(!(car instanceof SamochodWybuchowy) && !(car instanceof SamochodLaserowy) && !(car instanceof SamochodOponowy)){
                    averageRanking+=car.getRank();
                    howMany++;
                }
            }
            else if(index==2){
                if(car instanceof SamochodWybuchowy){
                    averageRanking+=car.getRank();
                    howMany++;
                }
            }
            else if(index==3){
                if(car instanceof SamochodLaserowy){
                    averageRanking+=car.getRank();
                    howMany++;
                }
            }
            else if(index==4){
                if(car instanceof SamochodOponowy){
                    averageRanking+=car.getRank();
                    howMany++;
                }
            }
        }
        if(howMany==0)
            return 0;
        else{
            return averageRanking/howMany;
        }
    }
    public void getCarInfo(String endPath){
        path=mainPath+endPath+".txt";
        boolean createdFile=createFile(path);
        try {
            writer=new FileWriter(path,true);
            if(createdFile){
                writer.write(endPath+"Id;Time Survived;Rank;Used Nitros;");
                if(endPath.equals("explosiveCar"))
                    writer.write("Cars Exploded;");
                else if(endPath.equals("tirePopperCar"))
                    writer.write("Damaged Tires;");
                writer.write("\n");
            }
            for(Samochod car: cars){
                if(endPath.equals("normalCar") && !(car instanceof SamochodWybuchowy) && !(car instanceof SamochodLaserowy) && !(car instanceof SamochodOponowy)){
                    writer.write(car.getCarId()+";"+car.getTicksSurvived()/60+";"
                            +car.getRank()+";"+car.getNitrosTaken()+";\n");
                }
                if(endPath.equals("explosiveCar") && car instanceof SamochodWybuchowy){
                    writer.write(car.getCarId()+";"+car.getTicksSurvived()/60+";"
                            +car.getRank()+";"+car.getNitrosTaken()+";");
                    writer.write(((SamochodWybuchowy) car).getExplodedCars()+";\n");
                }
                else if(endPath.equals("tirePopperCar") && car instanceof SamochodOponowy){
                    writer.write(car.getCarId()+";"+car.getTicksSurvived()/60+";"
                            +car.getRank()+";"+car.getNitrosTaken()+";");
                    writer.write(((SamochodOponowy) car).getDamagedTires()+";\n");
                }
                else if(endPath.equals("laserCar") && car instanceof SamochodLaserowy){
                    writer.write(car.getCarId()+";"+car.getTicksSurvived()/60+";"
                            +car.getRank()+";"+car.getNitrosTaken()+";\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public boolean createFile(String path){
        boolean fileCreated=false;
        try{
            File file=new File(path);
            if(file.createNewFile())
                fileCreated=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return fileCreated;
    }
}
