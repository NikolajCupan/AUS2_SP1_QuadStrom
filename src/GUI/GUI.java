package GUI;

import Aplikacia.Prezenter;
import GUI.Start.StartPrazdne;
import GUI.Start.StartSubor;
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

        setTitle("Aplikacia - Nikolaj Cupan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        this.button_nacitajZoSuboru.addActionListener(e -> GUI.this.zobrazStartSubor());
        this.button_zacniPrazdny.addActionListener(e -> GUI.this.zobrazStartPrazdne());
    }

    public void zobrazZoznamPolygonov()
    {
        ArrayList<Polygon> polygony = this.prezenter.getPolygony();
        Zoznam<Polygon> zoznam = new Zoznam<Polygon>(this.prezenter, this, polygony);
        this.zmenObsah(zoznam.getJPanel());
    }

    public void zobrazZoznamParciel()
    {
        ArrayList<Parcela> parcely = this.prezenter.getParcely();
        Zoznam<Parcela> zoznam = new Zoznam<Parcela>(this.prezenter, this, parcely);
        this.zmenObsah(zoznam.getJPanel());
    }

    public void zobrazZoznamNehnutelnosti()
    {
        ArrayList<Nehnutelnost> nehnutelnosti = this.prezenter.getNehnutelnosti();
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
