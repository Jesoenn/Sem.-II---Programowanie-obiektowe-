    package org.project;

    import javax.imageio.ImageIO;
    import java.awt.*;
    import java.util.ArrayList;
    import java.util.Random;

    public class Samochod {
        private static int carCount=0;   //ile samochodow - za tego pomocą ustawienie ich id
        private static int carsAlive=0; private boolean resetPreviousTarget=false;
        private int carId;
        private int prevTargetId,targetId; //id samochodu (od 0), id celu, do którego będzie się poruszać
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
        private Rectangle hitbox;
        private int killCount; // ile samochodow pokonal dany samochod
        private int a,b; // wspolczynniki f. liniowej potrzebnej do skrecania i poruszania sie
        private int xEquals; // gdy tg(alfa) = pipuł
        private double actuallAngle; // przechowuje kat, pod jakim nachylony jest samochod
        private int turningX, turningY; // punkt wzgledem ktorego skreca
        private ArrayList<Samochod> cars;
        public ArrayList<Samochod> Ranking; // do rankingu samochodow
        private ArrayList<Sciana> walls;
        private ArrayList<Nitro> nitros;
        private boolean usingNitro=false;
        private Nitro myNitro;
        private boolean alive=true;
        private int iterationsWithoutMoving=0;
        public Samochod(Derby derby, int givenX, int givenY){
            this.derby = derby;
            //tymczasowo, zrobic losowo, zeby nic na siebie nie nachodzilo - najpierw generowana map
            waga=1500;
            speed=1;
            carsAlive++;
            // do skrecania ponizej:
            
            /*xEquals = speed;
            actuallAngle = 90.0; // przy tg(alfa) = 90
            a = 0; // i tak nie wykorzystywane przy xEquals = speed
            b = y; // tez defaultowa wartosc
            turningX = 0;
            turningY = 0;*/
            
            //ponizej koncowe, nie zmieniac
            downloadImages();
            prevY=y=givenY;
            prevX=x=givenX;
            hitbox=new Rectangle(x,y,derby.samochodSize,derby.samochodSize);
            carId=carCount;
            carCount++;
            targetId=carId;
            prevTargetId=carId;
            hp=100;
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
        public void updateWalls(){
            walls=derby.walls;
        }
        //szukanie przeciwnika
        public void findTarget(){
            while(targetId==carId || targetId==prevTargetId){
                targetId=generateNumber.nextInt(carCount);
            }
            Samochod tempCar=cars.get(targetId);
            if(!tempCar.isAlive()){
                targetId=carId;
            }
        }
        //aktualizacja przeciwnika samochodu
        public void update(){
            updateCars();
            if(hp>0){
                //Zuzycie nitra
                if(usingNitro){
                    myNitro.nitroUsage();
                    if(myNitro.getTime()<=0){
                        usingNitro=false;
                        speed-=myNitro.speedBoost;
                        myNitro=null;
                    }
                }
                System.out.println("carId: " + carId + ", carsAlive: " + carsAlive + ", spaceFound: " + spaceFound + ", resetPreviousTarget: " + resetPreviousTarget); // DO USUNIECIA
                //Zapobiega utknieciu samochodu w 1 miejscu na dluzej niz sekunde
                if(x==prevX && y==prevY){
                    iterationsWithoutMoving++;
                    System.out.println(carId+": "+iterationsWithoutMoving);
                }
                //ZROBIC ZE JEZELI 2 SAMOCHODY TO JADA DO SIEBIE, POTEM UDERZAJA, JADA NA LOSOWE POLE I ZNOWU DO SIEBIE
                if(carsAlive==2 && !spaceFound && resetPreviousTarget){
                    targetId=-1;
                    prevTargetId=carId;
                    findEmptySpaceOnMap(derby.map.getMap());
                    moveToEmptySpace();
                }
                else if(carsAlive==2 && spaceFound && resetPreviousTarget){
                    moveToEmptySpace();
                }
                //szukam przeciwnika jezeli nie mam
                else if(targetId==carId || targetId==prevTargetId){
                    findTarget();
                }
                else if((targetId==-1 && !spaceFound) || iterationsWithoutMoving>=60){
                    iterationsWithoutMoving=0;
                    targetId=-1;
                    if(carsAlive==2)
                        prevTargetId=carId;
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
            else{
                hitbox.setLocation(-100,-100);
            }
        }
        //ruch po mapie
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

            //    PONIZEJ BUGOWATE SKRECANIE(DO DOROBIENIA)
            /* if((targetY-b)/Math.tan(actuallAngle*2*3.14/360) < getCurrentX()) // jesli przeciwnik jest na prawo
            {
                actuallAngle += 2; // jeden stopien na iteracje
                if(actuallAngle == 0 || actuallAngle == 180)
                {
                    xEquals = speed;
                    x += speed;
                }
                else if(actuallAngle != 90)
                {
                    x += turningAngle*Math.sin(actuallAngle*2*3.14/360);
                }
                if(targetY < y && y > 0)
                {
                    y -= turningAngle*Math.cos(actuallAngle*2*3.14/360);
                }
                else if(targetY > y && y < derby.screenY)
                {
                    y += turningAngle*Math.cos(actuallAngle);
                }
            }
            else if((target.getCurrentY()-b)/Math.tan(actuallAngle*2*3.14/360) > getCurrentX()) // jesli przeciwnik jest na lewo
            {
                actuallAngle -= 1;
                if(actuallAngle == 0 || actuallAngle == 180)
                {
                    xEquals = speed;
                }
                else if(actuallAngle != 90)
                {
                    x -= turningAngle*Math.sin(actuallAngle*2*3.14/360);
                }
                if(targetY < y && y > 0)
                {
                    y -= turningAngle*Math.cos(actuallAngle*2*3.14/360);
                }
                else if(targetY > y && y < derby.screenY)
                {
                    y += turningAngle*Math.cos(actuallAngle*2*3.14/360);
                }
            }
            else if((target.getCurrentY()-b)/Math.tan(actuallAngle*2*3.14/360) == getCurrentX())
            {
                // ruch prosto na przeciwnika
                x += speed;
                y += Math.tan(actuallAngle*2*3.14/360)*getCurrentX() + b;
            }
            b = y;*/
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
                    if(carsAlive==2)
                        resetPreviousTarget=false;
                }
            }
            checkCollision();
        }
        //sprawdzenie kolizji
        public void checkCollision(){
            for(Samochod car: cars){
                if(hitbox.intersects(car.getHitbox()) && car.getCarId()!=carId){
                    //Zadawanie obrazen
                    damageCar(car);

                    //
                    x=prevX;
                    y=prevY;
                    prevTargetId=targetId;
                    targetId=carId;
                    if(carsAlive==2){
                        resetPreviousTarget=true;
                        prevTargetId=targetId;
                    }
                    spaceFound=false; //po zderzeniu ze sciana przerwanie jechania na puste pole
                    hitbox.setLocation(x, y);
                    findTarget(); //Po zderzeniu zmienia target
                    break;
                }
            }
            for(Sciana wall: walls){
                if(hitbox.intersects(wall.getHitbox())){
                    x=prevX;
                    y=prevY;
                    hitbox.setLocation(x, y);
                    targetId=-1; //po zderzeniu jedzie na losowe pole
                    spaceFound=false;
                    if(carsAlive==2)
                        resetPreviousTarget=false;
                    break;
                }
            }
            if(!usingNitro){
                for(Nitro nitro: nitros){
                    if(hitbox.intersects(nitro.getHitbox())){
                        myNitro=nitro;
                        usingNitro=true;
                        nitro.takeNitro();
                        speed+=nitro.speedBoost;
                        break;
                    }
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
        //W kazdej chwili mozna zmienic
        public void damageCar(Samochod enemy){
            int damage=0;
            if(speed>enemy.getSpeed())
                damage=(speed-enemy.getSpeed())*10;
            else if(speed<enemy.getSpeed())
                damage=(enemy.getSpeed()-speed)*10;
            else if(speed==enemy.getSpeed())
                damage=25; //TYMCZASOWO ZEBY SZYBKO UMARL
            enemy.sethp(enemy.gethp()-damage);
            //Usmiercenie samochodu
            if(enemy.gethp()<=0 && enemy.isAlive()){
                enemy.setDead();
                carsAlive--;
                System.out.println("Samochodow w symulacji: "+carsAlive);
            }
        }
        //Aktualizacja zdjecia na ekranie
        public void updateOnMap(Graphics2D g2d){
            if(hp>0){
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
                g2d.setColor(Color.red);
                g2d.drawString(String.valueOf(hp)+" id:"+carId,x,y);
            }
            //POTEM WRZUCIC 2 LINIJKI PONIZEJ DO IFA
            if(derby.simulationTime <= 0.0) {
                g2d.setColor(Color.CYAN);
                g2d.drawString("KONIEC CZASU", 325, 325); // wstepnie, potem moze byc jakis obrazek ladny + wyniki
                showRank();
            }
        }
        public void showRank()
        {
            int counter = 0;
            if(Ranking.isEmpty())
            {
                System.out.println("Żaden pojazd nie poległ, nie ma więc rankingu");
            }
            else {
                for (Samochod car : Ranking) {
                    System.out.println("miejsce: " + (carCount - counter) + ", ID samochodu: " + car.getCarId());
                    counter++;
                }
            }
        }
        public void updateCars(){
            cars=derby.normalCars;
        }
        public void updateNitros(){
            nitros=derby.nitros;
        }
        private double calculateDistance(int x1, int y1, int x2, int y2) {
            return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        }
        public void setSpeed(int speed){
            this.speed=speed;
        }
        public int getSpeed(){
            return speed;
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
        public int gethp(){
            return hp;
        }
        public void sethp(int hp){
            this.hp=hp;
        }
        public boolean isAlive(){
            return alive;
        }
        public void setDead(){
            alive=false;
            Ranking.add(this);
        }
    }
