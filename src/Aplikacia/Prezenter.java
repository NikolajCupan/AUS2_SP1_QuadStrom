package Aplikacia;

import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.Generator;
import Objekty.Nehnutelnost;
import Objekty.Parcela;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

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
                this.logika.vlozNehnutelnost(nehnutelnost);
            }
            else if (typ.equals(Parcela.class))
            {
                Parcela parcela = this.generator.getParcela();
                this.logika.vlozParcelu(parcela);
            }
        }
    }

    public void vlozNehnutelnost(int supisneCislo, String popis, double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        this.skontrolujVstupy(supisneCislo, vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
        Nehnutelnost nehnutelnost = new Nehnutelnost(supisneCislo, popis,
                                                     new Suradnica(vlavoDoleX, vlavoDoleY),
                                                     new Suradnica(vpravoHoreX, vpravoHoreY));
        this.logika.vlozNehnutelnost(nehnutelnost);
    }

    public void vlozParcelu(int cisloParcely, String popis, double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        this.skontrolujVstupy(cisloParcely, vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
        Parcela parcela = new Parcela(cisloParcely, popis,
                                      new Suradnica(vlavoDoleX, vlavoDoleY),
                                      new Suradnica(vpravoHoreX, vpravoHoreY));
        this.logika.vlozParcelu(parcela);
    }

    public void editujPolygon(Polygon polygon, int noveCislo, String popis, double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        this.skontrolujVstupy(noveCislo, vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);

        if (polygon instanceof Nehnutelnost nehnutelnost)
        {
            nehnutelnost.setPopis(popis);
            nehnutelnost.setSupisneCislo(noveCislo);
        }
        else if (polygon instanceof Parcela parcela)
        {
            parcela.setPopis(popis);
            parcela.setCisloParcely(noveCislo);
        }

        if (polygon.getVlavoDoleX() != vlavoDoleX ||
            polygon.getVlavoDoleY() != vlavoDoleY ||
            polygon.getVpravoHoreX() != vpravoHoreX ||
            polygon.getVpravoHoreY() != vpravoHoreY)
        {
            // Meni sa pozicia polygonu
            this.vymazPolygon(polygon);
            Suradnica surVlavoDole = new Suradnica(vlavoDoleX, vlavoDoleY);
            Suradnica surVpravoHore = new Suradnica(vpravoHoreX, vpravoHoreY);

            if (polygon instanceof Nehnutelnost)
            {
                Nehnutelnost novaNehnutelnost = new Nehnutelnost(noveCislo, popis, surVlavoDole, surVpravoHore);
                this.logika.vlozNehnutelnost(novaNehnutelnost);
            }
            else if (polygon instanceof Parcela)
            {
                Parcela novaParcela = new Parcela(noveCislo, popis, surVlavoDole, surVpravoHore);
                this.logika.vlozParcelu(novaParcela);
            }
        }
    }

    public void vymazPolygon(Polygon polygon)
    {
        double stredX = (polygon.getVlavoDoleX() + polygon.getVpravoHoreX()) / 2;
        double stredY = (polygon.getVlavoDoleY() + polygon.getVpravoHoreY()) / 2;

        if (polygon instanceof Nehnutelnost nehnutelnost)
        {
            this.logika.vymazNehnutelnost(stredX, stredY, nehnutelnost.getKluc());
        }
        else if (polygon instanceof Parcela parcela)
        {
            this.logika.vymazParcelu(stredX, stredY, parcela.getKluc());
        }
    }

    public ArrayList<Polygon> vyhladajPolygony(double x, double y)
    {
        ArrayList<Polygon> polygony = new ArrayList<>();
        polygony.addAll(this.logika.vyhladajNehnutelnosti(x, y));
        polygony.addAll(this.logika.vyhladajParcely(x, y));
        return polygony;
    }

    public ArrayList<Nehnutelnost> vyhladajNehnutelnosti(double x, double y)
    {
        return this.logika.vyhladajNehnutelnosti(x, y);
    }

    public ArrayList<Parcela> vyhladajParcely(double x, double y)
    {
        return this.logika.vyhladajParcely(x, y);
    }

    private void skontrolujVstupy(int cislo, double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        if (vlavoDoleX >= vpravoHoreX || vlavoDoleY >= vpravoHoreY)
        {
            throw new RuntimeException("Neplatne zadane rozmery elementu!");
        }

        if (vlavoDoleX < this.logika.getNehnutelnostiStrom().getRootQuad().getVlavoDoleX() ||
            vlavoDoleY < this.logika.getNehnutelnostiStrom().getRootQuad().getVlavoDoleY() ||
            vpravoHoreX > this.logika.getNehnutelnostiStrom().getRootQuad().getVpravoHoreX() ||
            vpravoHoreY > this.logika.getNehnutelnostiStrom().getRootQuad().getVpravoHoreY())
        {
            throw new RuntimeException("Vkladany element je prilis velky!");
        }

        if (cislo < 0)
        {
            throw new RuntimeException("Cislo elementu nemoze byt zaporne!");
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

    public ArrayList<Polygon> getPolygony()
    {
        return this.logika.getPolygony();
    }

    public ArrayList<Parcela> getParcely()
    {
        return this.logika.getParcely();
    }

    public ArrayList<Nehnutelnost> getNehnutelnosti()
    {
        return this.logika.getNehnutelnosti();
    }

    public Logika getLogika()
    {
        return this.logika;
    }
}
