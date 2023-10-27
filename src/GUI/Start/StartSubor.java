package GUI.Start;

import Aplikacia.Prezenter;
import GUI.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartSubor
{
    private JPanel panel;

    private JLabel nazov;
    private JTextField input_nazovSuboru;
    private JButton button_potvrd;
    private JButton button_naVyber;

    public StartSubor(Prezenter prezenter, GUI gui)
    {
        this.button_potvrd.addActionListener(e -> {
            String nazovSuboru = StartSubor.this.input_nazovSuboru.getText();
            boolean uspesneNacitanie = prezenter.nacitajZoSoboru(nazovSuboru);

            if (nazovSuboru != null && !nazovSuboru.isEmpty())
            {
                if (!uspesneNacitanie)
                {
                    JOptionPane.showMessageDialog(StartSubor.this.panel, "Súbor s daným názvom sa nenašiel alebo nie je validný!");
                }
                else
                {
                    JOptionPane.showMessageDialog(StartSubor.this.panel, "Načítanie zo súboru bolo úspešné");
                    gui.zobrazHlavneOkno();
                }
            }
        });

        this.button_naVyber.addActionListener(e -> gui.zobrazVyberStart());
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
