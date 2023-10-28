package GUI.Vyhladavanie;

import Aplikacia.Prezenter;
import GUI.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
