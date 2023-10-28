package GUI.Vyhladavanie;

import Aplikacia.Prezenter;
import GUI.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VyhladavanieObdlznik extends JPanel
{
    private JPanel panel;
    private JComboBox combo_typ;
    private JTextField input_vlavoDoleX;
    private JTextField input_vlavoDoleY;
    private JTextField textField3;
    private JTextField textField4;
    private JButton button_naHlavne;
    private JButton button_vyhladaj;
    private JLabel input_vpravoHoreX;
    private JLabel input_vpravoHoreY;

    public VyhladavanieObdlznik(Prezenter prezenter, GUI gui)
    {
        this.button_naHlavne.addActionListener(e -> gui.zobrazHlavneOkno());
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
