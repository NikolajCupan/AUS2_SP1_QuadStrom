package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame
{
    private JLabel nadpis;
    private JTextField text;
    private JButton tlacidlo;
    private JPanel mainPanel;

    public GUI()
    {
        setContentPane(this.mainPanel);

        setTitle("Aplikacia - Nikolaj Cupan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        this.tlacidlo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String meno = GUI.this.text.getText();
                JOptionPane.showMessageDialog(GUI.this, meno);
            }
        });
    }
}
