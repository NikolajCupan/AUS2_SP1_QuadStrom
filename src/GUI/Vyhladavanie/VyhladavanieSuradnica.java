package GUI.Vyhladavanie;

import Aplikacia.Prezenter;
import GUI.GUI;
import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;

import javax.swing.*;
import java.util.ArrayList;

public class VyhladavanieSuradnica extends JPanel
{
    private JPanel panel;
    private JComboBox combo_typ;
    private JTextField input_x;
    private JTextField input_y;
    private JButton button_vyhladaj;
    private JButton button_naHlavne;

    public VyhladavanieSuradnica(Prezenter prezenter, GUI gui)
    {
        this.button_naHlavne.addActionListener(e -> gui.zobrazHlavneOkno());
        this.button_vyhladaj.addActionListener(e -> {
            try
            {
                double x = Double.parseDouble(VyhladavanieSuradnica.this.input_x.getText());
                double y = Double.parseDouble(VyhladavanieSuradnica.this.input_y.getText());

                String typString = String.valueOf(VyhladavanieSuradnica.this.combo_typ.getSelectedItem());
                if (typString.equals("Nehnuteľnosti"))
                {
                    ArrayList<Nehnutelnost> nehnutelnosti = prezenter.vyhladajNehnutelnosti(x, y);
                    gui.zobrazZoznamNehnutelnosti(nehnutelnosti);
                }
                else if (typString.equals("Parcely"))
                {
                    ArrayList<Parcela> parcely = prezenter.vyhladajParcely(x, y);
                    gui.zobrazZoznamParciel(parcely);
                }
                else
                {
                    ArrayList<Polygon> polygony = prezenter.vyhladajPolygony(x, y);
                    gui.zobrazZoznamPolygonov(polygony);
                }
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(VyhladavanieSuradnica.this, "Neplatné vstupy!");
            }
        });
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
