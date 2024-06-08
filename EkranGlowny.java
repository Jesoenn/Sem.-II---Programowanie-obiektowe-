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
    private ArrayList<Integer> samochody=new ArrayList<>();
    private ArrayList<JSlider> sliders=new ArrayList<>();
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
            g2d.drawString("Zwykle"+samochody.get(0),80,30);
            g2d.drawImage(samochodwybuchowy,470,40,100,100,null);
            g2d.drawString("Wybuchowe"+samochody.get(1),430,30);
            g2d.drawImage(samochodlaserowy,80,350,100,100,null);
            g2d.drawString("Laserowe"+samochody.get(2),60,340);
            g2d.drawImage(samochodoponowy,470,350,100,100,null);
            g2d.drawString("Oponowe"+samochody.get(3),450,340);
        }
        else if(etap==1){
            g2d.drawImage(wall,80,40,100,100,null);
            g2d.drawString("Sciany"+samochody.get(0),80,30);
            g2d.drawImage(nitro,470,40,100,100,null);
            g2d.drawString("Nitra"+samochody.get(1),485,30);
        }

        g2d.drawImage(dalejImage,463,564,150,50,null); //165 x 62
    }
    public void start(){
        slidery();
        dalejbutton();
        while(etap!=2){
            revalidate();
            repaint();
        }
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
            addSlider(5);
        }
        sliders.get(0).setBounds(35,150,200,80);
        sliders.get(1).setBounds(420,150,200,80);
        sliders.get(2).setBounds(35,460,200,80);
        sliders.get(3).setBounds(420,460,200,80);
        for(JSlider slider: sliders){
            add(slider);
        }
    }
    private void addSlider(int max){
        JSlider slider=new JSlider(0,max,0);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(1);
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
            addSlider(10);
            addSlider(15);
            sliders.get(0).setBounds(35,150,200,80);
            sliders.get(1).setBounds(395,150,250,80);
            for(JSlider slider: sliders){
                add(slider);
            }
            repaint();
        }
        else if(etap==1){
            etap++;
        }

    }
    public void stateChanged(ChangeEvent e) {
        for(int i=0; i<sliders.size();i++){
            samochody.set(i,sliders.get(i).getValue());
        }
        repaint();
    }
}
