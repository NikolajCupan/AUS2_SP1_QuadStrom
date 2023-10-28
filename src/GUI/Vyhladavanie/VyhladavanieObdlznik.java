package GUI.Vyhladavanie;

import Aplikacia.Prezenter;
import GUI.GUI;
import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VyhladavanieObdlznik extends JPanel
{
    private JPanel panel;
    private JComboBox combo_typ;
    private JTextField input_vlavoDoleX;
    private JTextField input_vlavoDoleY;
    private JTextField input_vpravoHoreX;
    private JTextField input_vpravoHoreY;
    private JButton button_naHlavne;
    private JButton button_vyhladaj;

    public VyhladavanieObdlznik(Prezenter prezenter, GUI gui)
    {
        this.button_naHlavne.addActionListener(e -> gui.zobrazHlavneOkno());
        this.button_vyhladaj.addActionListener(e -> {
            try
            {
                double vlavoDoleX =  Double.parseDouble(VyhladavanieObdlznik.this.input_vlavoDoleX.getText());
                double vlavoDoleY =  Double.parseDouble(VyhladavanieObdlznik.this.input_vlavoDoleY.getText());
                double vpravoHoreX = Double.parseDouble(VyhladavanieObdlznik.this.input_vpravoHoreX.getText());
                double vpravoHoreY = Double.parseDouble(VyhladavanieObdlznik.this.input_vpravoHoreY.getText());

                String typString = String.valueOf(VyhladavanieObdlznik.this.combo_typ.getSelectedItem());
                if (typString.equals("Nehnuteľnosti"))
                {
                    ArrayList<Nehnutelnost> nehnutelnosti = prezenter.vyhladajNehnutelnosti(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
                    gui.zobrazZoznamNehnutelnosti(nehnutelnosti);
                }
                else if (typString.equals("Parcely"))
                {
                    ArrayList<Parcela> parcely = prezenter.vyhladajParcely(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
                    gui.zobrazZoznamParciel(parcely);
                }
                else
                {
                    ArrayList<Polygon> polygony = prezenter.vyhladajPolygony(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
                    gui.zobrazZoznamPolygonov(polygony);
                }
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(VyhladavanieObdlznik.this, "Neplatné vstupy!");
            }
        });
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
