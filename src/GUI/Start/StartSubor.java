package GUI.Start;

import Aplikacia.Prezenter;
import GUI.GUI;

import javax.swing.*;

public class StartSubor extends JPanel
{
    private JPanel panel;
    private JTextField input_nazovSuboruNehnutelnosti;
    private JButton button_potvrd;
    private JButton button_naVyber;
    private JTextField input_nazovSuboruParcely;

    public StartSubor(Prezenter prezenter, GUI gui)
    {
        this.button_potvrd.addActionListener(e -> {
            String nazovSuboruNehnutelnosti = StartSubor.this.input_nazovSuboruNehnutelnosti.getText();
            String nazovSuboruParcely = StartSubor.this.input_nazovSuboruParcely.getText();
            boolean uspesneNacitanie = prezenter.nacitajZoSoboru(nazovSuboruNehnutelnosti, nazovSuboruParcely);

            if (nazovSuboruNehnutelnosti != null && !nazovSuboruNehnutelnosti.isEmpty() &&
                nazovSuboruParcely != null && !nazovSuboruParcely.isEmpty())
            {
                if (!uspesneNacitanie)
                {
                    JOptionPane.showMessageDialog(StartSubor.this, "Súbory sa nenašli alebo nie sú validné!");
                }
                else
                {
                    JOptionPane.showMessageDialog(StartSubor.this, "Načítanie zo súboru bolo úspešné");
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
