    package org.project;

    import javax.imageio.ImageIO;
    import java.awt.*;
    import java.util.ArrayList;
    import java.util.Random;
    import java.lang.Math;

    public class Samochod {
        protected static int carCount=0;   //ile samochodow - za tego pomocą ustawienie ich id
        protected static int carsAlive=0; protected boolean resetPreviousTarget=false;
        protected int carId;
        private static int weight;
        protected int prevTargetId,targetId; //id samochodu (od 0), id celu, do którego będzie się poruszać
        public boolean collision=false; //przy kolizji zmieniam na true
        protected int x,y,prevX,prevY; //Polozenie samochodu obecne i polozenie w poprzedniej klatce
        protected double doubleX, doubleY; // do movementu
        protected int speed;
        protected int xMap,yMap; protected boolean spaceFound=false;
        protected int hp; // Punkty zycia
        protected double turningRadius; // promien skretu
        protected boolean tires; // stan opon samochodu(narazie tak ze jak raz sie uszkodzi to zmiana na bool'u i potem przy uszkodzeniu nic sie wiecej nie zmienia)
        protected Image up,right,down,left; //Zdjecia samochodu obroconego w rozne strony
        protected Derby derby;
        protected Random generateNumber=new Random();
        protected Rectangle hitbox;
        private int killCount; // ile samochodow pokonal dany samochod
        protected int a,b; // wspolczynniki f. liniowej potrzebnej do skrecania i poruszania sie
        private int xEquals; // gdy tg(alfa) = pipuł
        private double actuallAngle; // przechowuje kat, pod jakim nachylony jest samochod
        private int turningX, turningY; // punkt wzgledem ktorego skreca
        protected boolean prevDirection; // info w ktora strone wczesniej samochod skrecal
        protected ArrayList<Samochod> cars;
        protected ArrayList<Sciana> walls;
        protected ArrayList<Nitro> nitros;
        protected boolean usingNitro=false;
        protected Nitro myNitro;
        protected boolean alive=true;
        protected int iterationsWithoutMoving=0;
        public Samochod(Derby derby, int givenX, int givenY, int carsWeight){
            this.derby = derby;
            //tymczasowo, zrobic losowo, zeby nic na siebie nie nachodzilo - najpierw generowana map
            weight=carsWeight;
            hp=weight/10;
            speed=1;
            carsAlive++;
            // do skrecania ponizej:
            
            /*xEquals = speed;
            actuallAngle = generateNumber.nextDouble(90.0); //losowy kierunek poczatkowy ruchu
            a = 0; // i tak nie wykorzystywane przy xEquals = speed
            b = y; // tez defaultowa wartosc
            turningX = 0;
            turningY = 0;
            turningRadius = 25;
            ;*/
            
            //ponizej koncowe, nie zmieniac
            downloadImages();
            prevY=y=givenY;
            prevX=x=givenX;
            hitbox=new Rectangle(x,y,derby.samochodSize,derby.samochodSize);
            carId=carCount;
            carCount++;
            targetId=carId;
            prevTargetId=carId;
        }
        public void downloadImages(){ //Byc moze zmienic zdjecia dla klas pochodnych - inne kolory aut
            try{
                up=ImageIO.read(getClass().getResource("/yellowcar/carYellowUp.png"));
                right=ImageIO.read(getClass().getResource("/yellowcar/carYellowRight.png"));
                down=ImageIO.read(getClass().getResource("/yellowcar/carYellowDown.png"));
                left=ImageIO.read(getClass().getResource("/yellowcar/carYellowLeft.png"));
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
            if(hp>0 && alive){
                //Zuzycie nitra
                if(usingNitro){
                    myNitro.nitroUsage();
                    if(myNitro.getTime()<=0){
                        usingNitro=false;
                        speed-=myNitro.speedBoost;
                        myNitro=null;
                    }
                }
                //System.out.println("carId: " + carId + ", carsAlive: " + carsAlive + ", spaceFound: " + spaceFound + ", resetPreviousTarget: " + resetPreviousTarget); // DO USUNIECIA
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

            
            // do f. linowych:
            double a1 = 0; // wsp. kierunkowy f. liniowej prostopadlej do f. liniowej ruchu samochodu
            int b1 = 0; // rowniez parametr f. liniowej _|_
            //    PONIZEJ BUGOWATE SKRECANIE(JUZ DZIALA LEPIEJ ALE STILL DO DOROBIENIA)
            // Note: przyjalem uklad wspolrzednych taki ze x to y, a y to x(na przykladzie f. liniowej: x = ay+b zamiast y = ax+b (tzn wykonalem obrot mapy o 90 stopni, bo tak sie ukladaja wspolrzedne mapy))
            /*if((targetX-b)/Math.tan(actuallAngle*3.14/180) < targetY) // jesli przeciwnik jest na lewo
            {
                // y = a1(x - x0) + y0, gdzie a1 = -1/a. <- wzor do f. liniowej _|_ do kierunkowej i przech. przez sr. samochodu (potrzebne do wyznaczenia turningX i turningY)
                System.out.println("Target na prawo");
                if(prevDirection == true) // do zmiany kata o 180(bo jak skreca w inna strone to faza na okregu sie zmienia o pi radianow)
                {
                    if (actuallAngle > 0 && actuallAngle < 180)
                        actuallAngle = 180 + actuallAngle;
                    if (actuallAngle > 180 && actuallAngle < 360)
                        actuallAngle = 360 - actuallAngle;
                }
                prevDirection = false;
                actuallAngle += 3;
                if(actuallAngle == 360) // chociaz i tak to bez znaczenia
                    actuallAngle = 0;
                a = Math.tan(actuallAngle*3.14/180);
                a1 = -1/a;
                b1 = x - (int)a1*y; //<- wartosc teoretyczna

                System.out.println("a1: " + a1);

                // \/ aktualizacja wspolrzednych punktu, wzgledem ktorego wykonuje sie obrot w kolko
                if(a > 0) { // samochod jedzie na prawo
                    turningX = x + (int) Math.round((Math.sqrt(turningRadius * turningRadius / (1 + (1 / a1 * a1)))));
                    System.out.println("turningX: " + turningX);
                    turningY = y + (int) Math.round(Math.sqrt((turningRadius * turningRadius / (1 + (1 / a1 * a1)))-b1)/a1);
                }
                if(a < 0) { // samochod jedzie na lewo
                    turningX = x -  (int) Math.round((Math.sqrt(turningRadius * turningRadius / (1 + (1 / a1 * a1)))));
                    System.out.println("turningX: " + turningX);
                    turningY = y - (int) Math.round(Math.sqrt((turningRadius * turningRadius / (1 + (1 / a1 * a1)))-b1)/a1);
                }
                System.out.println("a1 : " + a1);
                System.out.println("turningY: " + turningY);
                doubleY = turningY - (turningRadius *Math.cos(actuallAngle*3.14/180)); // zamiana stopni na radiany
                y = (int)Math.round(doubleY);
                if(targetX < x && x > 0) // przeciwnik na gorze
                {
                    doubleX = turningX - (turningRadius *Math.sin(actuallAngle*3.14/180));
                    x = (int)Math.round(doubleX);
                }
                else if(targetX > x && x < derby.screenX) // przeciwnik na dole
                {
                    doubleX = turningX - (turningRadius *Math.sin(actuallAngle*3.14/180));
                    x = (int)Math.round(doubleX);
                }
            }
            else if((targetX-b)/Math.tan(actuallAngle*3.14/180) > targetY) // jesli przeciwnik jest na prawo
            {
//                    turningX += (int)Math.sqrt(1 - y*y);
                System.out.println("Target na lewo");
                if(prevDirection == false) {
                    if (actuallAngle > 0 && actuallAngle < 180)
                        actuallAngle = 180 + actuallAngle;
                    if (actuallAngle > 180 && actuallAngle < 360)
                        actuallAngle = 180 + actuallAngle;
                }
                prevDirection = true;
                actuallAngle -= 3;
                if(actuallAngle < 0)
                    actuallAngle = 357;
                a = Math.tan(actuallAngle*3.14/180);
                a1 = -1/a;
                b1 = x - (int)a1*y;
                if(a > 0) // samochod jedzie na prawo
                {
                    turningX = x + (int) Math.round((Math.sqrt(turningRadius * turningRadius / (1 + (1 / a1 * a1)))));
                    System.out.println("turningX: " + turningX);
                    turningY = y + (int) Math.round(Math.sqrt((turningRadius * turningRadius / (1 + (1 / a1 * a1)))-b1)/a1);
                    System.out.println("turningY: " + turningY);
                }
                if(a < 0) // samochod jedzie na lewo
                {
                    turningX = x - (int) Math.round((Math.sqrt(turningRadius * turningRadius / (1 + (1 / a1 * a1)))));
                    System.out.println("turningX: " + turningX);
                    turningY = y - (int) Math.round(Math.sqrt((turningRadius * turningRadius / (1 + (1 / a1 * a1)))-b1)/a1);
                    System.out.println("turningY: " + turningY);
                }
                doubleY = turningY - (turningRadius *Math.cos(actuallAngle*3.14/180)); // zamiana stopni na radiany
                y = (int)Math.round(doubleY);
                if(targetX < x && x > 0)
                {
                    doubleX = turningX + (turningRadius *Math.sin(actuallAngle*3.14/180));
                    x = (int)Math.round(doubleX);
                }
                else if(targetX > x && x < derby.screenX)
                {
                    doubleX = turningX + (turningRadius *Math.sin(actuallAngle*3.14/180));
                    x = (int)Math.round(doubleX);
                }
            }
            else if((targetX-b)/Math.tan(actuallAngle*3.14/180) == targetY)  // przeciwnik ustawia sie "na lini" f. liniowej (to dziala bo raz sie ustawily tak i normalnie jechaly)
            {
                a = Math.tan(actuallAngle*3.14/180);
                    x += (int)Math.round(a*1 + b);
                    y += (int)Math.round(Math.sqrt(1 - (Math.pow((a*1 + b), 2)))); // pitagoras zeby speed byl rowny 1
            }
            b = x - (int)Math.round(a*y); // jak w funkcji liniowej*/
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
            checkDeath(enemy);
        }
        public void checkDeath(Samochod enemy){
            if(enemy.gethp()<=0 && enemy.isAlive()){
                enemy.setDead();
                if(enemy instanceof SamochodWybuchowy){
                    ((SamochodWybuchowy) enemy).Eksplozja();
                }
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
            }
            g2d.setColor(Color.red);
            g2d.drawString(String.valueOf(hp)+" id:"+carId,x,y);
            //POTEM WRZUCIC 2 LINIJKI PONIZEJ DO IFA
        }
        public void updateCars(){
            cars=derby.normalCars;
        }
        public void updateNitros(){
            nitros=derby.nitros;
        }
        protected double calculateDistance(int x1, int y1, int x2, int y2) {
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
        }
        public int getCarsAlive(){
            return carsAlive;
        }
        public void setWeight(int weight){
            this.weight=weight;
        }
    }
