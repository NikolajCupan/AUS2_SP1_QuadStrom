package GUI;

import Aplikacia.Prezenter;
import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;

import javax.swing.*;

public class Editovanie extends JFrame
{
    private JPanel panel;
    private JLabel label_nadpis;
    private JLabel label_cisloNazov;
    private JTextField input_cislo;
    private JTextArea text_popis;
    private JTextField input_vpravoHoreX;
    private JTextField input_vlavoDoleX;
    private JTextField input_vlavoDoleY;
    private JTextField input_vpravoHoreY;
    private JButton button_naHlavne;
    private JButton button_edituj;

    public Editovanie(Prezenter prezenter, GUI gui, Polygon polygon)
    {
        if (polygon instanceof Nehnutelnost nehnutelnost)
        {
            this.label_nadpis.setText("Editovanie nehnuteľnosti");
            this.label_cisloNazov.setText("Súpisné číslo");
            this.text_popis.setText(nehnutelnost.getPopis());
            this.input_cislo.setText("" + nehnutelnost.getKluc());
        }
        else if (polygon instanceof Parcela parcela)
        {
            this.label_nadpis.setText("Editovanie parcely");
            this.label_cisloNazov.setText("Číslo parcely");
            this.text_popis.setText(parcela.getPopis());
            this.input_cislo.setText("" + parcela.getKluc());
        }

        this.input_vlavoDoleX.setText("" + polygon.getVlavoDoleX());
        this.input_vlavoDoleY.setText("" + polygon.getVlavoDoleY());
        this.input_vpravoHoreX.setText("" + polygon.getVpravoHoreX());
        this.input_vpravoHoreY.setText("" + polygon.getVpravoHoreY());

        this.button_naHlavne.addActionListener(e -> gui.zobrazHlavneOkno());
        this.button_edituj.addActionListener(e -> {
            try
            {
                int noveCislo = Integer.parseInt(Editovanie.this.input_cislo.getText());
                String novyPopis = Editovanie.this.text_popis.getText();
                double vlavoDoleX = Double.parseDouble(Editovanie.this.input_vlavoDoleX.getText());
                double vlavoDoleY = Double.parseDouble(Editovanie.this.input_vlavoDoleY.getText());
                double vpravoHoreX = Double.parseDouble(Editovanie.this.input_vpravoHoreX.getText());
                double vpravoHoreY = Double.parseDouble(Editovanie.this.input_vpravoHoreY.getText());

                prezenter.editujPolygon(polygon, noveCislo, novyPopis, vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
                JOptionPane.showMessageDialog(Editovanie.this, "Editácia bola úspešná");
                gui.zobrazHlavneOkno();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(Editovanie.this, "Neplatné vstupy!");
            }
        });
    }

    public JPanel getJpanel()
    {
        return this.panel;
    }
}
