import javax.swing.*;
import java.awt.*;

public class Main{
    public static void main(String[] args) {
        //Stworzenie okna
        JFrame window= new JFrame();
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Derby samochodowe");

        Silnik silnik=new Silnik();
        window.add(silnik);
        window.pack();
        window.setVisible(true);
        /* EKRAN STARTOWY Z WYBORAMI, POKI CO USELESS
        EkranGlowny main_menu=new EkranGlowny(window);
        main_menu.start();
        //Czyszczenie ekranu
        window.getContentPane().removeAll();
        window.repaint(); */
        silnik.start();

    }
}