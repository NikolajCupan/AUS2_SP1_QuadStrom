package Aplikacia;

import Ostatne.Generator;
import Objekty.Nehnutelnost;
import Objekty.Parcela;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class Prezenter
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

                this.inicializujLogiku(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY, maxUroven);

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

    public void inicializujLogiku(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY, int maxUroven)
    {
        if (maxUroven < 0)
        {
            throw new RuntimeException("Maximalna uroven nemoze byt zaporna!");
        }

        if (vlavoDoleX >= vpravoHoreX || vlavoDoleY >= vpravoHoreY)
        {
            throw new RuntimeException("Neplatne zadane rozmery najvacsieho quadu!");
        }

        this.logika = new Logika(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY, maxUroven);
    }

    public <T> void generujData(int zaciatocneCislo, int pocetGenerovanych, double faktorZmensenia, Class<T> typ)
    {
        if (this.generator == null)
        {
            this.inicializujGenerator();
        }

        this.generator.setCurSupisneCislo(zaciatocneCislo);
        this.generator.setCurCisloParcely(zaciatocneCislo);
        this.generator.setFaktorZmensenia(faktorZmensenia);

        for (int i = 0; i < pocetGenerovanych; i++)
        {
            if (typ.equals(Nehnutelnost.class))
            {
                Nehnutelnost nehnutelnost = this.generator.getNehnutelnost();
                this.logika.getNehnutelnostiStrom().vloz(nehnutelnost);
            }
            else if (typ.equals(Parcela.class))
            {
                Parcela parcela = this.generator.getParcela();
                this.logika.getParcelyStrom().vloz(parcela);
            }
        }
    }

    private void inicializujGenerator()
    {
        this.generator = new Generator(1, 1,
                                       this.logika.getNehnutelnostiStrom().getRootQuad().getVlavoDoleX(),
                                       this.logika.getNehnutelnostiStrom().getRootQuad().getVlavoDoleY(),
                                       this.logika.getNehnutelnostiStrom().getRootQuad().getVpravoHoreX(),
                                       this.logika.getNehnutelnostiStrom().getRootQuad().getVpravoHoreY(),
                                       5, 1);
    }

    public Logika getLogika()
    {
        return this.logika;
    }
}
