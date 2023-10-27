package GUI;

import Aplikacia.Prezenter;
import Objekty.Nehnutelnost;
import Objekty.Parcela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HlavneOkno
{
    private final Prezenter prezenter;
    private final GUI gui;

    private JPanel panel;

    private JLabel minX;
    private JLabel minY;
    private JLabel maxX;
    private JLabel maxY;
    private JButton button_generujData;
    private JLabel noveData;
    private JButton button_pridajNehnutelnost;
    private JButton button_pridajParcelu;
    private JButton button_obnovPocet;
    private JLabel label_pocetElementov;
    private JButton button_zobrazNehnutelnosti;
    private JButton button_zobrazParcely;
    private JButton button_zobrazPolygony;
    private JLabel maxUrovenNehnutelnosti;
    private JButton button_obnovUrovenNehnutelnosti;
    private JLabel maxUrovenParcely;
    private JButton button_obnovUrovenParcely;

    public HlavneOkno(Prezenter prezenter, GUI gui)
    {
        this.prezenter = prezenter;
        this.gui = gui;

        this.minX.setText("Min x: " + this.prezenter.getLogika().getNehnutelnostiStrom().getRootQuad().getVlavoDoleX());
        this.minY.setText("Min y: " + this.prezenter.getLogika().getNehnutelnostiStrom().getRootQuad().getVlavoDoleY());
        this.maxX.setText("Max x: " + this.prezenter.getLogika().getNehnutelnostiStrom().getRootQuad().getVpravoHoreX());
        this.maxY.setText("Max y: " + this.prezenter.getLogika().getNehnutelnostiStrom().getRootQuad().getVpravoHoreY());

        this.button_generujData.addActionListener(e -> gui.zobrazGenerovanie());
        this.button_obnovPocet.addActionListener(e -> this.obnovPocet());
        this.button_pridajNehnutelnost.addActionListener(e -> gui.zobrazPridavanie(Nehnutelnost.class));
        this.button_pridajParcelu.addActionListener(e -> gui.zobrazPridavanie(Parcela.class));
        this.button_zobrazNehnutelnosti.addActionListener(e -> gui.zobrazZoznamNehnutelnosti());
        this.button_zobrazParcely.addActionListener(e -> gui.zobrazZoznamParciel());
        this.button_zobrazPolygony.addActionListener(e -> gui.zobrazZoznamPolygonov());
        this.button_obnovUrovenNehnutelnosti.addActionListener(e -> this.obnovMaxUrovenNehnutelnosti());
        this.button_obnovUrovenParcely.addActionListener(e -> this.obnovMaxUrovenParcely());
    }

    public void obnovMaxUrovenNehnutelnosti()
    {
        this.maxUrovenNehnutelnosti.setText("Maximálna úroveň (hĺbka) N: " + prezenter.getLogika().getNehnutelnostiStrom().getMaxUroven());
    }

    public void obnovMaxUrovenParcely()
    {
        this.maxUrovenParcely.setText("Maximálna úroveň (hĺbka) P: " + prezenter.getLogika().getParcelyStrom().getMaxUroven());
    }

    public void obnovPocet()
    {
        int pocetNehnutelnosti = prezenter.getLogika().getNehnutelnostiStrom().getPocetElementov();
        int pocetParcely = prezenter.getLogika().getParcelyStrom().getPocetElementov();
        int pocetSpolu = pocetNehnutelnosti + pocetParcely;

        this.label_pocetElementov.setText("Počet prvkov (N/P/S): " + pocetNehnutelnosti + "/" + pocetParcely + "/" + pocetSpolu);
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
