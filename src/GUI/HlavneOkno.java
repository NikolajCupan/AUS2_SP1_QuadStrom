package GUI;

import Aplikacia.Aplikacia;

import javax.swing.*;

public class HlavneOkno
{
    private final Aplikacia aplikacia;
    private final GUI gui;

    private JPanel panel;

    private JLabel minX;
    private JLabel minY;
    private JLabel maxX;
    private JLabel maxY;
    private JLabel maxUroven;

    public HlavneOkno(Aplikacia aplikacia, GUI gui)
    {
        this.aplikacia = aplikacia;
        this.gui = gui;

        this.minX.setText("Min x: " + this.aplikacia.getLogika().getNehnutelnostiStrom().getRootQuad().getVlavoDoleX());
        this.minY.setText("Min y: " + this.aplikacia.getLogika().getNehnutelnostiStrom().getRootQuad().getVlavoDoleY());
        this.maxX.setText("Max x: " + this.aplikacia.getLogika().getNehnutelnostiStrom().getRootQuad().getVpravoHoreX());
        this.maxY.setText("Max y: " + this.aplikacia.getLogika().getNehnutelnostiStrom().getRootQuad().getVpravoHoreY());
        this.maxUroven.setText("Max úroveň (hĺbka): " + this.aplikacia.getLogika().getNehnutelnostiStrom().getMaxUroven());
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
