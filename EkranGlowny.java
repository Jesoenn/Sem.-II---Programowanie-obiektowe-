package org.project;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EkranGlowny extends JPanel implements ChangeListener {
    private Image background_image,samochod,samochodwybuchowy,samochodlaserowy,samochodoponowy,dalejImage,wall,nitro;
    private int sciany=0,nitra=0,waga=1000;
    private ArrayList<Integer> samochody=new ArrayList<>();
    private ArrayList<JSlider> sliders=new ArrayList<>();
    /*
    0 - ZWYKLE
    1 - WYBUCHOWE
    2 - LASEROWE
    3 - OPONOWE
    */
    private JButton dalej=new JButton();
    private int etap=0;
    EkranGlowny(){
        setPreferredSize(new Dimension(650,650));
        setLayout(null);
        dalej.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dalejPressed();
            }
        });
        for(int i=0; i<4; i++){
            samochody.add(0);
        }
        try{
            background_image = ImageIO.read(getClass().getResource("/mapa.png"));
            samochod = ImageIO.read(getClass().getResource("/yellowcar/carYellowUp.png"));
            samochodwybuchowy = ImageIO.read(getClass().getResource("/redcar/carRedUp.png"));
            samochodlaserowy = ImageIO.read(getClass().getResource("/greencar/carGreenUp.png"));
            samochodoponowy = ImageIO.read(getClass().getResource("/bluecar/carBlueUp.png"));
            dalejImage = ImageIO.read(getClass().getResource("/extra/dalej.png"));
            wall=ImageIO.read(getClass().getResource("/wallHorizontal.png"));
            nitro=ImageIO.read(getClass().getResource("/nitro.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    //wyswietlenie tła
    public void paintComponent(Graphics g){
        Graphics2D g2d=(Graphics2D) g;
        super.paintComponent(g);
        g2d.fillRect(0,0,650,650);
        g2d.setColor(Color.white);
        g2d.setFont(new Font("default", Font.BOLD, 30));
        if(etap==0){
            g2d.drawImage(samochod,80,40,100,100,null);
            g2d.drawString("Zwykle",80,30);
            g2d.drawImage(samochodwybuchowy,470,40,100,100,null);
            g2d.drawString("Wybuchowe",430,30);
            g2d.drawImage(samochodlaserowy,80,350,100,100,null);
            g2d.drawString("Laserowe",60,340);
            g2d.drawImage(samochodoponowy,470,350,100,100,null);
            g2d.drawString("Oponowe",450,340);
        }
        else if(etap==1){
            g2d.drawImage(wall,80,40,100,100,null);
            g2d.drawString("Sciany",80,30);
            g2d.drawImage(nitro,470,40,100,100,null);
            g2d.drawString("Nitra",485,30);
        }
        else if(etap==2){
            g2d.drawString("Wybierz wage dla wszystkich samochodow",17,40);
            g2d.drawString("WAGA: "+waga,230,190);
        }
        g2d.drawImage(dalejImage,463,564,150,50,null);
    }
    public void start(){
        slidery();
        dalejbutton();
        revalidate();
        while(etap!=3){
            repaint();
        }
    }
    private void ustalanieWagi(){
        for(JSlider slider: sliders){
            remove(slider);
        }
        sliders.clear();
        addSlider(1000,3000,100);
        sliders.get(0).setBounds(0,300,650,50);
        add(sliders.get(0));
        repaint();
    }
    public void dalejbutton(){
        dalej.setBounds(470,570,150,50);
        dalej.setOpaque(false);
        dalej.setContentAreaFilled(false);
        dalej.setBorderPainted(false);
        add(dalej);

    }
    //Wywolywac dla kazdego przycisku
    public void slidery(){
        for(int i=0; i<4; i++){
            addSlider(0,5,1);
        }
        sliders.get(0).setBounds(35,150,200,80);
        sliders.get(1).setBounds(420,150,200,80);
        sliders.get(2).setBounds(35,460,200,80);
        sliders.get(3).setBounds(420,460,200,80);
        for(JSlider slider: sliders){
            add(slider);
        }
    }
    private void addSlider(int min,int max,int odstepy){
        int startingValue=0;
        if(min>0)
            startingValue=min;
        JSlider slider=new JSlider(min,max,startingValue);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(odstepy);
        slider.setPaintLabels(true);
        slider.setForeground(Color.white);
        slider.setOpaque(false);
        slider.addChangeListener(this);
        sliders.add(slider);
    }
    public void dalejPressed(){
        if(sliders.size()==4 && (samochody.get(0)!=0 || samochody.get(1)!=0 || samochody.get(2)!=0 || samochody.get(3)!=0)){
            etap++;
            for(JSlider slider: sliders){
                remove(slider);
            }
            sliders.clear();
            addSlider(0,10,1);
            addSlider(0,15,1);
            sliders.get(0).setBounds(35,150,200,80);
            sliders.get(1).setBounds(395,150,250,80);
            for(JSlider slider: sliders){
                add(slider);
            }
            repaint();
        }
        else if(etap==1){
            etap++;
            ustalanieWagi();
        }
        else if(etap==2)
            etap++;
    }
    public void stateChanged(ChangeEvent e) {
        if(etap==0){
            for(int i=0; i<sliders.size();i++){
                samochody.set(i,sliders.get(i).getValue());
            }
        }
        else if(etap==1){
            nitra=sliders.get(1).getValue();
            sciany=sliders.get(0).getValue();
        }
        if(etap==2 && sliders.get(0).getValue()%100!=0){
            sliders.get(0).setValue(sliders.get(0).getValue()/100*100);
            waga=sliders.get(0).getValue();
        }
        repaint();
    }
    public int getSciany(){
        return sciany;
    }
    public int getNitra(){
        return nitra;
    }
    public ArrayList<Integer> getSamochody(){
        return samochody;
    }
    public int getWaga(){
        return waga;
    }
}
