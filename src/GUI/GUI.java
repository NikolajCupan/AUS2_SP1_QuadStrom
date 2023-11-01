package GUI;

import Aplikacia.Prezenter;
import GUI.Start.StartPrazdne;
import GUI.Start.StartSubor;
import GUI.Vyhladavanie.VyhladavanieObdlznik;
import GUI.Vyhladavanie.VyhladavanieSuradnica;
import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;

import javax.swing.*;
import java.util.ArrayList;

public class GUI extends JFrame
{
    private final Prezenter prezenter;
    private JPanel panel;
    private JButton button_nacitajZoSuboru;
    private JButton button_zacniPrazdny;

    public GUI()
    {
        this.prezenter = new Prezenter();

        setContentPane(this.panel);

        setTitle("Aplikácia - Nikolaj Cupan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 750);
        setLocationRelativeTo(null);
        setVisible(true);

        this.button_nacitajZoSuboru.addActionListener(e -> GUI.this.zobrazStartSubor());
        this.button_zacniPrazdny.addActionListener(e -> GUI.this.zobrazStartPrazdne());
    }

    public void zobrazZdravie()
    {
        Zdravie zdravie = new Zdravie(this.prezenter, this);
        this.zmenObsah(zdravie.getJPanel());
    }

    public void zobrazUlozenieDoSuborov()
    {
        UlozenieSubor ulozenieSubor = new UlozenieSubor(this.prezenter, this);
        this.zmenObsah(ulozenieSubor.getJPanel());
    }

    public void zobrazVyhladavanieObdlznik()
    {
        VyhladavanieObdlznik vyhladavanieObdlznik = new VyhladavanieObdlznik(this.prezenter, this);
        this.zmenObsah(vyhladavanieObdlznik.getJPanel());
    }

    public void zobrazVyhladavanieSuradnica()
    {
        VyhladavanieSuradnica vyhladavanieSuradnica = new VyhladavanieSuradnica(this.prezenter, this);
        this.zmenObsah(vyhladavanieSuradnica.getJPanel());
    }

    public void zobrazEditovanie(Polygon polygon)
    {
        Editovanie editovanie = new Editovanie(this.prezenter, this, polygon);
        this.zmenObsah(editovanie.getJpanel());
    }

    public void zobrazZoznamPolygonov(ArrayList<Polygon> polygony)
    {
        Zoznam<Polygon> zoznam = new Zoznam<Polygon>(this.prezenter, this, polygony);
        this.zmenObsah(zoznam.getJPanel());
    }

    public void zobrazZoznamParciel(ArrayList<Parcela> parcely)
    {
        Zoznam<Parcela> zoznam = new Zoznam<Parcela>(this.prezenter, this, parcely);
        this.zmenObsah(zoznam.getJPanel());
    }

    public void zobrazZoznamNehnutelnosti(ArrayList<Nehnutelnost> nehnutelnosti)
    {
        Zoznam<Nehnutelnost> zoznam = new Zoznam<Nehnutelnost>(this.prezenter, this, nehnutelnosti);
        this.zmenObsah(zoznam.getJPanel());
    }

    public <T> void zobrazPridavanie(Class<T> typ)
    {
        Pridanie pridanie = new Pridanie(typ, this.prezenter,this);
        this.zmenObsah(pridanie.getJPanel());
    }

    public void zobrazGenerovanie()
    {
        Generovanie generovanie = new Generovanie(this.prezenter, this);
        this.zmenObsah(generovanie.getJPanel());
    }

    public void zobrazHlavneOkno()
    {
        HlavneOkno hlavneOkno = new HlavneOkno(this.prezenter, this);
        hlavneOkno.obnovPocet();
        hlavneOkno.obnovMaxUrovenNehnutelnosti();
        hlavneOkno.obnovMaxUrovenParcely();
        this.zmenObsah(hlavneOkno.getJPanel());
    }

    public void zobrazVyberStart()
    {
        this.zmenObsah(this.panel);
    }

    private void zobrazStartSubor()
    {
        StartSubor startSubor = new StartSubor(this.prezenter, this);
        this.zmenObsah(startSubor.getJPanel());
    }

    private void zobrazStartPrazdne()
    {
        StartPrazdne startPrazdne = new StartPrazdne(this.prezenter, this);
        this.zmenObsah(startPrazdne.getJPanel());
    }

    private void zmenObsah(JPanel obsah)
    {
        setContentPane(obsah);
        revalidate();
        repaint();
    }
}
