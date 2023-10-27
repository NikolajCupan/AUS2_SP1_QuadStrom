package GUI;

import Aplikacia.Prezenter;
import Objekty.Nehnutelnost;
import Objekty.Parcela;

import javax.swing.*;

public class Generovanie extends JFrame
{
    private JPanel panel;
    private JComboBox combo_typ;
    private JTextField input_pocetGenerovanych;
    private JTextField input_faktorZmensenia;
    private JTextField input_zaciatocneCislo;
    private JButton button_generuj;

    public Generovanie(Prezenter prezenter, GUI gui)
    {
        this.button_generuj.addActionListener(e -> {
            try
            {
                String typString = String.valueOf(this.combo_typ.getSelectedItem());
                int pocetGenerovanych = Integer.parseInt(Generovanie.this.input_pocetGenerovanych.getText());
                double faktorZmensenia = Double.parseDouble(Generovanie.this.input_faktorZmensenia.getText());
                int zaciatocneCislo = Integer.parseInt(Generovanie.this.input_zaciatocneCislo.getText());

                Class typ = (typString.equals("Nehnuteľnosť") ? Nehnutelnost.class : Parcela.class);
                prezenter.generujData(zaciatocneCislo, pocetGenerovanych, faktorZmensenia, typ);
                JOptionPane.showMessageDialog(Generovanie.this, "Dáta boli úspešne vygenerované");
                gui.zobrazHlavneOkno();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(Generovanie.this, "Neplatné vstupy!");
            }
        });
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
