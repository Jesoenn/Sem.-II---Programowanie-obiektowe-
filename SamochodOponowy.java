package org.project;

import javax.imageio.ImageIO;

public class SamochodOponowy extends Samochod{
    private int tireRepairCooldown=60*2; //Opona tego auta naprawia sie po 2 sekundach
    private int damagedTires=0; //Do zapisow
    SamochodOponowy(Derby derby, int givenX, int givenY, int carsWeight)
    {
        super(derby, givenX, givenY,carsWeight);
    }
    @Override
    public void downloadImages(){ //Byc moze zmienic zdjecia dla klas pochodnych - inne kolory aut
        try{
            up= ImageIO.read(getClass().getResource("/bluecar/carBlueUp.png"));
            right=ImageIO.read(getClass().getResource("/bluecar/carBlueRight.png"));
            down=ImageIO.read(getClass().getResource("/bluecar/carBlueDown.png"));
            left=ImageIO.read(getClass().getResource("/bluecar/carBlueLeft.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void update(){
        super.update();
        if(wheels!=4 && alive){
            tireRepairCooldown--;
            if(tireRepairCooldown==0)
                repairTire();
        }
    }
    private void repairTire(){
        wheels++;
        skiptick--;
        tireRepairCooldown=2*60;
    }
    public void BreakEnemyWheel(Samochod enemy)
    {
        damagedTires++; //do zapisow
        enemy.damageWheel();
    }
}
