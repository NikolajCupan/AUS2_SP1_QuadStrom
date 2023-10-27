package GUI;

import Aplikacia.Prezenter;
import Objekty.Nehnutelnost;

import javax.swing.*;
import java.util.ArrayList;

public class Zoznam
{
    private JPanel panel;
    private JList<Nehnutelnost> list;
    private JButton button_detail;
    private JTextArea detail;
    private DefaultListModel<Nehnutelnost> model;

    public Zoznam(Prezenter prezenter, GUI gui, ArrayList<Nehnutelnost> zoznam)
    {
        this.model = new DefaultListModel<>();
        this.list.setModel(this.model);

        for (Nehnutelnost nehnutelnost : zoznam)
        {
            this.model.addElement(nehnutelnost);
        }

        this.button_detail.addActionListener(e -> {
            Nehnutelnost nehnutelnost = Zoznam.this.list.getSelectedValue();

            StringBuilder builder = new StringBuilder();
            builder.append("Nehnuteľnosť\n");
            builder.append("Súpisné číslo: ").append(nehnutelnost.getKluc()).append("\n");
            builder.append("Popis: ").append(nehnutelnost.getPopis()).append("\n");
            builder.append("Vľavo dole x: ").append(nehnutelnost.getVlavoDoleX()).append("\n");
            builder.append("Vľavo dole y: ").append(nehnutelnost.getVlavoDoleY()).append("\n");
            builder.append("Vpravo hore x: ").append(nehnutelnost.getVpravoHoreX()).append("\n");
            builder.append("Vpravo hore y: ").append(nehnutelnost.getVpravoHoreY()).append("\n");
            String detail = builder.toString();
            this.detail.setText(detail);
        });
    }

    public JPanel getJPanel()
    {
        return this.panel;
    }
}
