package GUI;

import Aplikacia.Prezenter;
import Objekty.Nehnutelnost;
import Objekty.Parcela;

import javax.swing.*;

public class HlavneOkno
{
    private final Prezenter prezenter;
    private final GUI gui;

    private JPanel panel;

    private JLabel minX;
    private JLabel minY;
    private JLabel maxX;
    private JLabel maxY;
    private JLabel maxUroven;
    private JButton button_generujData;
    private JLabel noveData;
    private JButton button_pridajNehnutelnost;
    private JButton button_pridajParcelu;
    private JButton button_obnovPocet;
    private JLabel label_pocetElementov;

    public HlavneOkno(Prezenter prezenter, GUI gui)
    {
        this.prezenter = prezenter;
        this.gui = gui;

        this.minX.setText("Min x: " + this.prezenter.getLogika().getNehnutelnostiStrom().getRootQuad().getVlavoDoleX());
        this.minY.setText("Min y: " + this.prezenter.getLogika().getNehnutelnostiStrom().getRootQuad().getVlavoDoleY());
        this.maxX.setText("Max x: " + this.prezenter.getLogika().getNehnutelnostiStrom().getRootQuad().getVpravoHoreX());
        this.maxY.setText("Max y: " + this.prezenter.getLogika().getNehnutelnostiStrom().getRootQuad().getVpravoHoreY());
        this.maxUroven.setText("Max úroveň (hĺbka): " + this.prezenter.getLogika().getNehnutelnostiStrom().getMaxUroven());

        this.button_generujData.addActionListener(e -> gui.zobrazGenerovanie());
        this.button_obnovPocet.addActionListener(e -> this.obnovPocet());
        this.button_pridajNehnutelnost.addActionListener(e -> gui.zobrazPridavanie(Nehnutelnost.class));
        this.button_pridajParcelu.addActionListener(e -> gui.zobrazPridavanie(Parcela.class));
    }

    public void obnovPocet()
    {
        int pocetNehnutelnosti = prezenter.getLogika().getNehnutelnostiStrom().getPocetElementov();
        int pocetParcely = prezenter.getLogika().getParcelyStrom().getPocetElementov();
        int pocetSpolu = pocetNehnutelnosti + pocetParcely;

        this.label_pocetElementov.setText("Počet prvkov N/P/S: " + pocetNehnutelnosti + "/" + pocetParcely + "/" + pocetSpolu);
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
