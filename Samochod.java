package org.example;

public class Samochod {
    private int zycie;
    int predkosc;
    int waga;
    int promien_skretu;
    int liczba_zabojstw;
    int id;
    int opony;
    public void poruszanie(int cel_id)
    {

    }
    Samochod()
    {

    }

    public void strata_zycia()
    {
        if(zycie == 0)
        {
            predkosc = 0; // potem zachowanie jak ściana??
        }
    }
    public void uszkodzenie_kola()
    {

    }
    public void odwroc()
    {

    }
    public int get_ID()
    {
        return this.id;
    }
}
class Samochod_wybuchowy extends Samochod
{
    double promien;
    public void Eksplozja()
    {
        if(predkosc == 0)
        {
            // promien idacy od wsp. samochodu
            //
        }
    }
}

class Samochod_oponowy extends Samochod
{
    @Override
    public void strata_zycia()
    {

    }
    public void uszkodz_opone()
    {

    }
}