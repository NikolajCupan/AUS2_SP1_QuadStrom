package GUI;

import Aplikacia.Prezenter;
import Objekty.Nehnutelnost;
import Objekty.Parcela;

import javax.swing.*;

public class Pridanie extends JFrame
{
    private JPanel panel;
    private JLabel label_cisloNazov;
    private JTextField input_cislo;
    private JTextArea text_popis;
    private JTextField input_vlavoDoleX;
    private JTextField input_vlavoDoleY;
    private JTextField input_vpravoHoreX;
    private JTextField input_vpravoHoreY;
    private JButton button_potvrdPridanie;
    private JButton button_naHlavne;
    private JLabel label_nadpis;

    public <T> Pridanie(Class<T> typ, Prezenter prezenter, GUI gui)
    {
        if (typ.equals(Nehnutelnost.class))
        {
            this.label_nadpis.setText("Vytvorenie novej nehnuteľnosti");
            this.label_cisloNazov.setText("Súpisné číslo");
        }
        else if (typ.equals(Parcela.class))
        {
            this.label_nadpis.setText("Vytvorenie novej parcely");
            this.label_cisloNazov.setText("Číslo parcely");
        }

        this.button_potvrdPridanie.addActionListener(e -> {
            try
            {
                int cislo = Integer.parseInt(Pridanie.this.input_cislo.getText());
                String popis = Pridanie.this.text_popis.getText();

                double vlavoDoleX =  Double.parseDouble(Pridanie.this.input_vlavoDoleX.getText());
                double vlavoDoleY =  Double.parseDouble(Pridanie.this.input_vlavoDoleY.getText());
                double vpravoHoreX = Double.parseDouble(Pridanie.this.input_vpravoHoreX.getText());
                double vpravoHoreY = Double.parseDouble(Pridanie.this.input_vpravoHoreY.getText());

                if (typ.equals(Nehnutelnost.class))
                {
                    prezenter.vlozNehnutelnost(cislo, popis, vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
                    JOptionPane.showMessageDialog(Pridanie.this, "Pridanie novej nehnuteľnosti bolo úspešné");
                }
                else if (typ.equals(Parcela.class))
                {
                    prezenter.vlozParcelu(cislo, popis, vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
                    JOptionPane.showMessageDialog(Pridanie.this, "Pridanie novej parcely bolo úspešné");
                }

                gui.zobrazHlavneOkno();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(Pridanie.this, "Neplatné vstupy!");
            }
        });

        this.button_naHlavne.addActionListener(e -> gui.zobrazHlavneOkno());
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
