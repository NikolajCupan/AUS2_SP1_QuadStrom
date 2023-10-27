package GUI;

import Aplikacia.Aplikacia;

import javax.swing.*;

public class GUI extends JFrame
{
    private final Aplikacia aplikacia;
    private JPanel panel;
    private JButton button_nacitajZoSuboru;
    private JButton button_zacniPrazdny;

    public GUI()
    {
        this.aplikacia = new Aplikacia();

        setContentPane(this.panel);

        setTitle("Aplikacia - Nikolaj Cupan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        this.button_nacitajZoSuboru.addActionListener(e -> GUI.this.zobrazStartSubor());
        this.button_zacniPrazdny.addActionListener(e -> GUI.this.zobrazStartPrazdne());
    }

    public void zobrazHlavneOkno()
    {
        HlavneOkno hlavneOkno = new HlavneOkno(this.aplikacia, this);
        this.zmenObsah(hlavneOkno.getJPanel());
    }

    private void zobrazStartSubor()
    {
        StartSubor startSubor = new StartSubor(this.aplikacia, this);
        this.zmenObsah(startSubor.getJPanel());
    }

    private void zobrazStartPrazdne()
    {
        StartPrazdne startPrazdne = new StartPrazdne(this.aplikacia, this);
        this.zmenObsah(startPrazdne.getJPanel());
    }

    private void zmenObsah(JPanel obsah)
    {
        setContentPane(obsah);
        revalidate();
        repaint();
    }
}
