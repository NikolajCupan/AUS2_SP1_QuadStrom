package GUI;

import Aplikacia.Prezenter;

import javax.swing.*;

public class StartSubor
{
    private JPanel panel;

    private JLabel nazov;
    private JTextField input_nazovSuboru;
    private JButton button_potvrd;

    public StartSubor(Prezenter prezenter, GUI gui)
    {
        this.button_potvrd.addActionListener(e -> {
            String nazovSuboru = StartSubor.this.input_nazovSuboru.getText();
            boolean uspesneNacitanie = prezenter.nacitajZoSoboru(nazovSuboru);

            if (nazovSuboru != null && !nazovSuboru.isEmpty())
            {
                if (!uspesneNacitanie)
                {
                    JOptionPane.showMessageDialog(StartSubor.this.panel, "Súbor s daným názvom sa nenašiel!");
                }
                else
                {
                    JOptionPane.showMessageDialog(StartSubor.this.panel, "Načítanie zo súboru bolo úspešné");
                    gui.zobrazHlavneOkno();
                }
            }
        });
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
