package GUI;

import Aplikacia.Prezenter;
import GUI.Start.StartPrazdne;
import GUI.Start.StartSubor;
import Objekty.Nehnutelnost;

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
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        this.button_nacitajZoSuboru.addActionListener(e -> GUI.this.zobrazStartSubor());
        this.button_zacniPrazdny.addActionListener(e -> GUI.this.zobrazStartPrazdne());
    }

    public void zobrazZoznamNehnutelnosti()
    {
        ArrayList<Nehnutelnost> nehnutelnosti = this.prezenter.getNehnutelnosti();
        Zoznam zoznam = new Zoznam(this.prezenter, this, nehnutelnosti);
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
        this.zmenObsah(hlavneOkno.getJPanel());
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
