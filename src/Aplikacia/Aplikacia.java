package Aplikacia;

import Ostatne.Generator;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class Aplikacia
{
    private Logika logika;
    private Generator generator;

    public boolean nacitajZoSoboru(String nazovSuboru)
    {
        try
        {
            File subor = new File(nazovSuboru);

            if (subor.exists() && subor.isFile())
            {
                FileReader fCitac = new FileReader(subor);
                BufferedReader bCitac = new BufferedReader(fCitac);

                double vlavoDoleX = Double.parseDouble(bCitac.readLine());
                double vlavoDoleY = Double.parseDouble(bCitac.readLine());
                double vpravoHoreX = Double.parseDouble(bCitac.readLine());
                double vpravoHoreY = Double.parseDouble(bCitac.readLine());
                int maxUroven = Integer.parseInt(bCitac.readLine());

                this.logika = new Logika(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY, maxUroven);

                bCitac.close();
                fCitac.close();
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public void inicializujLogiku(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY, int maxHlbka)
    {
        this.logika = new Logika(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY, maxHlbka);
    }

    public Logika getLogika()
    {
        return this.logika;
    }
}
