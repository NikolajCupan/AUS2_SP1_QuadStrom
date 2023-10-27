package GUI.Start;

import Aplikacia.Prezenter;
import GUI.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPrazdne extends JFrame
{
    private JPanel panel;

    private JLabel minX;
    private JLabel minY;
    private JLabel maxX;
    private JTextField input_minX;
    private JTextField input_minY;
    private JTextField input_maxX;
    private JTextField input_maxY;
    private JTextField input_maxUroven;
    private JLabel maxY;
    private JButton button_potvrdStruktura;
    private JButton button_naVyber;

    public StartPrazdne(Prezenter prezenter, GUI gui)
    {
        this.button_potvrdStruktura.addActionListener(e -> {
            try
            {
                double minX = Double.parseDouble(this.input_minX.getText());
                double minY = Double.parseDouble(this.input_minY.getText());
                double maxX = Double.parseDouble(this.input_maxX.getText());
                double maxY = Double.parseDouble(this.input_maxY.getText());
                int maxUroven = Integer.parseInt(this.input_maxUroven.getText());

                prezenter.inicializujLogiku(minX, minY, maxX, maxY, maxUroven);
                gui.zobrazHlavneOkno();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(StartPrazdne.this, "Neplatné vstupy!");
            }
        });

        this.button_naVyber.addActionListener(e -> gui.zobrazVyberStart());
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
