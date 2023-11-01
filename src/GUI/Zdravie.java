package GUI;

import Aplikacia.Prezenter;
import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Ostatne.IPolygon;
import QuadStrom.QuadStrom;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Zdravie extends JPanel
{
    private JPanel panel;
    private JButton button_naHlavne;
    private JLabel text_minX;
    private JLabel text_minY;
    private JLabel text_maxX;
    private JLabel text_maxY;
    private JLabel text_pocetNehnutelnosti;
    private JLabel text_pocetParcely;
    private JTextArea text_pomerNehnutelnosti;
    private JTextArea text_pomerParcely;
    private JLabel text_pocetSpolu;
    private JLabel text_zdravieNehnutelnosti;
    private JLabel text_zdravieParcely;
    private JLabel text_statusNehnutelnosti;
    private JLabel text_statusParcely;
    private JButton button_optimalizujNehnutelnosti;
    private JButton button_optimalizujParcely;
    private JLabel text_autoNehnutelnosti;
    private JLabel text_autoParcely;
    private JLabel text_prilisPrazdne;
    private JLabel text_prilisPlne;

    public Zdravie(Prezenter prezenter, GUI gui)
    {
        QuadStrom<Nehnutelnost> nehnutelnostiStrom = prezenter.getDatabaza().getNehnutelnostiStrom();
        QuadStrom<Parcela> parcelyStrom = prezenter.getDatabaza().getParcelyStrom();

        this.text_minX.setText("Min x: " + nehnutelnostiStrom.getRootQuad().getVlavoDoleX());
        this.text_minY.setText("Min y: " + nehnutelnostiStrom.getRootQuad().getVlavoDoleY());
        this.text_maxX.setText("Max x: " + nehnutelnostiStrom.getRootQuad().getVpravoHoreX());
        this.text_maxY.setText("Max y: " + nehnutelnostiStrom.getRootQuad().getVpravoHoreY());
        this.text_prilisPrazdne.setText("Príliš prázdne pod " + (nehnutelnostiStrom.getPrilisPrazdne() * 100) + " percent dát");
        this.text_prilisPlne.setText("Príliš plné nad " + (nehnutelnostiStrom.getPrilisPlne() * 100) + " percent dát");

        this.nastavInformacie(nehnutelnostiStrom, this.text_pocetNehnutelnosti, this.text_pomerNehnutelnosti, this.text_zdravieNehnutelnosti,
                              this.text_statusNehnutelnosti, this.text_autoNehnutelnosti);
        this.nastavInformacie(parcelyStrom, this.text_pocetParcely, this.text_pomerParcely, this.text_zdravieParcely,
                              this.text_statusParcely, this.text_autoParcely);

        int pocetNehnutelnosti = nehnutelnostiStrom.getPocetElementov();
        int pocetParcely = parcelyStrom.getPocetElementov();
        this.text_pocetSpolu.setText("Celkový počet prvkov: " + (pocetNehnutelnosti + pocetParcely));

        this.button_naHlavne.addActionListener(e -> gui.zobrazHlavneOkno());
        this.button_optimalizujNehnutelnosti.addActionListener(e -> {
            prezenter.getDatabaza().getNehnutelnostiStrom().forceOptimalizuj();
            gui.zobrazZdravie();
        });
        this.button_optimalizujParcely.addActionListener(e -> {
            prezenter.getDatabaza().getParcelyStrom().forceOptimalizuj();
            gui.zobrazZdravie();
        });
    }

    private <T extends IPolygon> void nastavInformacie(QuadStrom<T> strom, JLabel text_pocet, JTextArea text_pomer, JLabel text_zdravie,
                                                       JLabel text_status, JLabel text_auto)
    {
        text_pocet.setText("Počet prvkov: " + strom.getPocetElementov());

        double[] pomerUroven = strom.getPomerUroven();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < pomerUroven.length; i++)
        {
            builder.append("Úroveň: " + i + ", percent dát: " + (pomerUroven[i] * 100)).append('\n');
        }
        text_pomer.setText(builder.toString());

        double zdravieStrom = strom.getZdravie(pomerUroven[strom.getMaxUroven()]);
        text_zdravie.setText("Aktuálne zdravie: " + zdravieStrom);

        if (zdravieStrom == 0.0)
        {
            text_status.setText("Hlboké úrovne sú príliš prázdne, vhodná optimalizácia");
        }
        else if (zdravieStrom == 1.0)
        {
            text_status.setText("Hlboké úrovne sú príliš plné, vhodná optimalizácia");
        }
        else
        {
            text_status.setText("Strom má dobré zdravie, optimalizácia nie je potrebná");
        }

        text_auto.setText("Automatická optimalizácia: " + strom.getPocitadloOperacii() + "/" + strom.getOptimalizujNa());
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
