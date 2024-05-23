import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class EkranGlowny extends JPanel{
    JFrame window;
    Image background_image;
    Image button_image;
    EkranGlowny(JFrame given_window){
        window = given_window;
        try{
            background_image = ImageIO.read(getClass().getResource("/startmenu.png"));
            button_image = ImageIO.read(getClass().getResource("/wybor.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //wyswietlenie tła
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background_image,0,0,680,680,this);
    }
    public void start(){
        window.add(this);
        window.setVisible(true);

        ButtonGroup zwykly=new ButtonGroup();
        JRadioButton zwykly_1=new JRadioButton();
        JRadioButton zwykly_2=new JRadioButton();
        JRadioButton zwykly_3=new JRadioButton();
        zwykly.add(zwykly_1);
        zwykly.add(zwykly_2);
        zwykly.add(zwykly_3);

    }
}
