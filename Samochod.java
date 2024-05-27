    package org.project;

    import javax.imageio.ImageIO;
    import java.awt.*;
    import java.util.ArrayList;
    import java.util.Random;

    public class Samochod {
        private static int carCount=0;   //ile samochodow - za tego pomocą ustawienie ich id
        private int carId;
        private int targetId; //id samochodu (od 0), id celu, do którego będzie się poruszać
        public boolean collision=false; //przy kolizji zmieniam na true
        private int x,y,prevX,prevY; //Polozenie samochodu obecne i polozenie w poprzedniej klatce
        private int waga,speed;
        private int xMap,yMap; private boolean spaceFound=false;
        private int hp; // Punkty zycia
        private double turningAngle; // promien skretu
        private boolean tires; // stan opon samochodu(narazie tak ze jak raz sie uszkodzi to zmiana na bool'u i potem przy uszkodzeniu nic sie wiecej nie zmienia)
        private Image up,right,down,left; //Zdjecia samochodu obroconego w rozne strony
        private Derby derby;
        private Random generateNumber=new Random();
        private Rectangle hitbox; // DO ZROBIENIA HITBOXY INTERSECTS
        private int killCount; // ile samochodow pokonal dany samochod
        private ArrayList<Samochod> cars;
        private ArrayList<Sciana> walls;
        public Samochod(Derby derby, int givenX, int givenY){
            this.derby = derby;
            //tymczasowo, zrobic losowo, zeby nic na siebie nie nachodzilo - najpierw generowana map
            prevY=y=givenY;
            prevX=x=givenX;
            waga=1500;
            speed=1;
            hp = 100;
            //ponizej koncowe, nie zmieniac
            downloadImages();
            hitbox=new Rectangle(x,y,derby.samochodSize,derby.samochodSize);
            carId=carCount;
            carCount++;
            targetId=carId;
        }
        private void downloadImages(){ //Byc moze zmienic zdjecia dla klas pochodnych - inne kolory aut
            try{
                up=ImageIO.read(getClass().getResource("/carYellowUp.png"));
                right=ImageIO.read(getClass().getResource("/carYellowRight.png"));
                down=ImageIO.read(getClass().getResource("/carYellowDown.png"));
                left=ImageIO.read(getClass().getResource("/carYellowLeft.png"));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        public void updateCars(){
            cars=derby.normalCars;
        }
        public void updateWalls(){
            walls=derby.walls;
        }
        //szukanie przeciwnika
        public void findTarget(){
            while(targetId==carId){
                targetId=generateNumber.nextInt(carCount);
            }
        }
        //Obliczanie obecnej lokalizacji na mapie -> duzo poprawek do zrobienia
        /*public void movement(int targetX, int targetY){
            prevX=x;
            prevY=y;
            if(collision){ //jezeli cel zostal zaatakowany, to szukamy nowego celu, jeszcze nie dziala
                targetId=carId;
                collision=false;
            }
            if(x>targetX && x>0){
                x-=speed;
            }
            else if(x<targetX && x< derby.screenX)
                x+=speed;
            if(y>targetY && y>0)
                y-=speed;
            else if(y<targetY && y< derby.screenY)
                y+=speed;
            hitbox.setLocation(x,y);
        }*/
        public void update(){
            updateCars();
            //szukam przeciwnika jezeli nie mam
            if(targetId==carId)
                findTarget();
            else if(targetId==-1 && !spaceFound){
                findEmptySpaceOnMap(derby.map.getMap());
                moveToEmptySpace();
            }
            else if(targetId==-1 && spaceFound){
                moveToEmptySpace();
            }
            else{
                movement(cars.get(targetId));
            }
        }
        public void movement(Samochod target){
            int targetX=target.getCurrentX();
            int targetY=target.getCurrentY();
            prevX=x;
            prevY=y;
            if(x>targetX && x>0)
                x-=speed;
            else if(x<targetX && x< derby.screenX)
                x+=speed;
            if(y>targetY && y>0)
                y-=speed;
            else if(y<targetY && y< derby.screenY)
                y+=speed;
            hitbox.setLocation(x,y);
            checkCollision();
        }
        public void moveToEmptySpace(){
            prevX=x;
            prevY=y;
            if(x>xMap && x>0)
                x-=speed;
            else if(x<xMap && x<derby.screenX)
                x+=speed;
            if(y>yMap && y>0)
                y-=speed;
            else if(y<yMap && y<derby.screenY)
                y+=speed;
            hitbox.setLocation(x,y);
            if(x<xMap+20 && x>xMap-20){
                if(y<yMap+20 && y>yMap-20){
                    targetId=carId;
                    spaceFound=false;
                }
            }
            checkCollision();
        }
        public void checkCollision(){
            for(Samochod car: cars){
                if(hitbox.intersects(car.getHitbox()) && car.getCarId()!=carId){
                    x=prevX;
                    y=prevY;
                    targetId=carId;
                    hitbox.setLocation(x, y);
                    findTarget(); //Po zderzeniu jedzie na losowe pole
                    break;
                }
            }
            for(Sciana wall: walls){
                if(hitbox.intersects(wall.getHitbox())){
                    x=prevX;
                    y=prevY;
                    hitbox.setLocation(x, y);
                    targetId=-1;
                    spaceFound=false;
                    break;
                }
            }
        }
        public void findEmptySpaceOnMap(int[][] map){
            int newY=generateNumber.nextInt(derby.squaresY);
            int newX=generateNumber.nextInt(derby.squaresX);
            while(map[newY][newX]==1 || map[newY][newX]==2){
                newY=generateNumber.nextInt(derby.squaresY);
                newX=generateNumber.nextInt(derby.squaresX);
            }
            xMap=newX*derby.samochodSize;
            yMap=newY*derby.samochodSize;
            spaceFound=true;
        }

        /*public void checkWallCollision(){
            for(Sciana wall: walls){
                if(hitbox.intersects(wall.getHitbox())){
                    speed=0;
                }
            }
        }/*
        //
        /*public void findFreeSquare(int[][] map){
            int newY=generateNumber.nextInt(derby.squaresY);
            int newX=generateNumber.nextInt(derby.squaresX);
            if(map[newY][newX]!=1 && map[newY][newX]!=2){
                targetXWallCollision=newX;
                targetYWallCollision=newY;
                //targetId=-1;
            }
        }*/
        //Aktualizacja zdjecia na ekranie
        public void updateOnMap(Graphics2D g2d){
            if(prevY>y)
                g2d.drawImage(up,x,y, derby.samochodSize, derby.samochodSize,null);
            else if(prevY<y)
                g2d.drawImage(down,x,y, derby.samochodSize, derby.samochodSize,null);
            else if(prevX>x)
                g2d.drawImage(left,x,y, derby.samochodSize, derby.samochodSize,null);
            else if(prevX<x)
                g2d.drawImage(right,x,y, derby.samochodSize, derby.samochodSize,null);
            else if(prevX==x && prevY==y){
                g2d.drawImage(up,x,y, derby.samochodSize, derby.samochodSize,null);
            }
        }
        public void setSpeed(int speed){
            this.speed=speed;
        }
        public int getCurrentX(){
            return x;
        }
        public int getCurrentY(){
            return y;
        }
        public int getCarId(){
            return carId;
        }
        public Rectangle getHitbox(){
            return hitbox;
        }
        public int getTargetId(){
            return targetId;
        }
        public void setTargetId(int newTargetId){
            targetId=newTargetId;
        }
    }
