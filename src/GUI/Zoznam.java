package GUI;

import Aplikacia.Prezenter;
import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;
import Ostatne.IPolygon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Zoznam<T extends IPolygon> extends JFrame
{
    private JPanel panel;
    private JList<T> list;
    private JButton button_detail;
    private JButton button_naHlavne;
    private JTextArea detail;
    private JButton button_edituj;
    private JButton button_vymaz;
    private DefaultListModel<T> model;
    private T zobrazeny;

    public Zoznam(Prezenter prezenter, GUI gui, ArrayList<T> zoznam)
    {
        this.model = new DefaultListModel<>();
        this.list.setModel(this.model);

        for (T element : zoznam)
        {
            this.model.addElement(element);
        }

        this.button_detail.addActionListener(e -> {
            T element = Zoznam.this.list.getSelectedValue();
            if (element == null)
            {
                return;
            }

            this.zobrazeny = element;
            this.button_edituj.setEnabled(true);
            this.button_vymaz.setEnabled(true);

            StringBuilder builder = new StringBuilder();

            if (element instanceof Nehnutelnost)
            {
                builder.append("Nehnuteľnosť: ").append("\n");
                builder.append("Súpisné číslo: ").append(element.getKluc()).append("\n");
                builder.append("Popis: ").append(((Nehnutelnost)element).getPopis()).append("\n");
            }
            else
            {
                builder.append("Parcela: ").append("\n");
                builder.append("Číslo parcely: ").append(element.getKluc()).append("\n");
                builder.append("Popis: ").append(((Parcela)element).getPopis()).append("\n");
            }

            builder.append("Vľavo dole x: ").append(element.getVlavoDoleX()).append("\n");
            builder.append("Vľavo dole y: ").append(element.getVlavoDoleY()).append("\n");
            builder.append("Vpravo hore x: ").append(element.getVpravoHoreX()).append("\n");
            builder.append("Vpravo hore y: ").append(element.getVpravoHoreY()).append("\n").append("\n");
            builder.append("Zoznam referencií:").append("\n");

            if (element instanceof Nehnutelnost)
            {
                Nehnutelnost nehnutelnost = (Nehnutelnost)element;
                for (Parcela parcela : nehnutelnost.getParcely())
                {
                    builder.append(parcela).append("\n");
                }
            }
            else
            {
                Parcela parcela = (Parcela) element;
                for (Nehnutelnost nehnutelnost : parcela.getNehnutelnosti())
                {
                    builder.append(nehnutelnost).append("\n");
                }
            }

            String detail = builder.toString();
            this.detail.setText(detail);
        });

        this.button_naHlavne.addActionListener(e -> gui.zobrazHlavneOkno());

        this.button_edituj.addActionListener(e -> {
            if (this.zobrazeny == null)
            {
                return;
            }

            gui.zobrazEditovanie((Polygon)this.zobrazeny);
        });

        this.button_vymaz.addActionListener(e -> {
            if (this.zobrazeny == null)
            {
                return;
            }

            prezenter.vymazPolygon((Polygon)zobrazeny);
            JOptionPane.showMessageDialog(Zoznam.this, "Zmazanie bolo úspešné");
            gui.zobrazHlavneOkno();
        });
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
