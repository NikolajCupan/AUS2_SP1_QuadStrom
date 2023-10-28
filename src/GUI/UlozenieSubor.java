package GUI;

import Aplikacia.Prezenter;

import javax.swing.*;

public class UlozenieSubor extends JPanel
{
    private JPanel panel;
    private JTextField input_nazovSuboruNehnutelnosti;
    private JTextField input_nazovSuboruParcely;
    private JButton button_naHlavne;
    private JButton button_ulozit;

    public UlozenieSubor(Prezenter prezenter, GUI gui)
    {
        this.button_naHlavne.addActionListener(e -> gui.zobrazHlavneOkno());
        this.button_ulozit.addActionListener(e -> {
            try
            {
                String nazovSuboruNehnutelnosti = this.input_nazovSuboruNehnutelnosti.getText();
                String nazovSuboruParcely = this.input_nazovSuboruParcely.getText();

                if (prezenter.ulozDoSuboru(nazovSuboruNehnutelnosti, nazovSuboruParcely))
                {
                    JOptionPane.showMessageDialog(UlozenieSubor.this.panel, "Uloženie do súboru bolo úspešné");
                    gui.zobrazHlavneOkno();
                }
                else
                {
                    JOptionPane.showMessageDialog(UlozenieSubor.this.panel, "Pri ukladaní dát do súboru nastal problém!");
                }
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(UlozenieSubor.this.panel, "Neplatné vstupy!");
            }
        });
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
