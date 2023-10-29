package Aplikacia;

import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.Generator;
import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Ostatne.IPolygon;
import QuadStrom.QuadStrom;
import QuadStrom.Quad;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Prezenter
{
    private Databaza databaza;
    private Generator generator;

    public boolean ulozDoSuboru(String nazovSuboruNehnutelnosti, String nazovSuboruParcely)
    {
        if (!this.zapisDoSuboru(nazovSuboruNehnutelnosti, this.databaza.getNehnutelnostiStrom(), Nehnutelnost.class) ||
            !this.zapisDoSuboru(nazovSuboruParcely, this.databaza.getParcelyStrom(), Parcela.class))
        {
            return false;
        }

        return true;
    }

    private <T extends IPolygon> boolean zapisDoSuboru(String nazovSuboru, QuadStrom<T> strom, Class<T> typ)
    {
        try
        {
            PrintWriter zapisovac = new PrintWriter(nazovSuboru, StandardCharsets.UTF_8);

            zapisovac.println("" + strom.getRootQuad().getVlavoDoleX());
            zapisovac.println("" + strom.getRootQuad().getVlavoDoleY());
            zapisovac.println("" + strom.getRootQuad().getVpravoHoreX());
            zapisovac.println("" + strom.getRootQuad().getVpravoHoreY());
            zapisovac.println("" + strom.getMaxUroven());

            for (Quad<T> quad : strom)
            {
                for (T element : quad.getData())
                {
                    if (typ.equals(Nehnutelnost.class))
                    {
                        Nehnutelnost nehnutelnost = (Nehnutelnost)element;
                        zapisovac.println(nehnutelnost.getKluc() + ";" + nehnutelnost.getPopis() + ";" +
                                        nehnutelnost.getVlavoDoleX() + ";" + nehnutelnost.getVlavoDoleY() + ";" +
                                        nehnutelnost.getVpravoHoreX() + ";" + nehnutelnost.getVpravoHoreY());
                    }
                    else if (typ.equals(Parcela.class))
                    {
                        Parcela parcela = (Parcela)element;
                        zapisovac.println(parcela.getKluc() + ";" + parcela.getPopis() + ";" +
                                        parcela.getVlavoDoleX() + ";" + parcela.getVlavoDoleY() + ";" +
                                        parcela.getVpravoHoreX() + ";" + parcela.getVpravoHoreY());
                    }
                }
            }

            zapisovac.close();

            // Vsetky data boli uspesne zapisane do suboru
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public boolean nacitajZoSoboru(String nazovSuboruNehnutelnosti, String nazovSuboruParcely)
    {
        File suborNehnutelnosti = new File(nazovSuboruNehnutelnosti);
        File suborParcely = new File(nazovSuboruParcely);

        if (!suborNehnutelnosti.exists() || !suborParcely.exists() ||
            !suborNehnutelnosti.isFile() || !suborParcely.isFile())
        {
            // Problem s niektorym zo suborov
            return false;
        }

        // Informacie o velkosti a hlbke
        if (!this.nacitajParametreZoSuboru(suborNehnutelnosti, suborParcely))
        {
            return false;
        }

        // Ak som sa dostal sem, tak stromy su uspesne vytvorene,
        // teraz ich naplnim datamy zo suborov
        if (!this.nacitajDataZoSuboru(suborNehnutelnosti, this.databaza, Nehnutelnost.class) ||
            !this.nacitajDataZoSuboru(suborParcely, this.databaza, Parcela.class))
        {
            // Pri nacitavani samotnych dat nastal problem,
            // data, ktore som nacital vymazem
            this.databaza.resetujStromy();
        }

        return true;
    }

    private boolean nacitajParametreZoSuboru(File suborNehnutelnosti, File suborParcely)
    {
        try
        {
            FileReader fCitacNehnutelnosti = new FileReader(suborNehnutelnosti);
            FileReader fCitacParcely = new FileReader(suborParcely);

            BufferedReader bCitacNehnutelnosti = new BufferedReader(fCitacNehnutelnosti);
            BufferedReader bCitacParcely = new BufferedReader(fCitacParcely);

            double vlavoDoleXNehnutelnosti = Double.parseDouble(bCitacNehnutelnosti.readLine());
            double vlavoDoleYNehnutelnosti = Double.parseDouble(bCitacNehnutelnosti.readLine());
            double vpravoHoreXNehnutelnosti = Double.parseDouble(bCitacNehnutelnosti.readLine());
            double vpravoHoreYNehnutelnosti = Double.parseDouble(bCitacNehnutelnosti.readLine());

            double vlavoDoleXParcely = Double.parseDouble(bCitacParcely.readLine());
            double vlavoDoleYParcely = Double.parseDouble(bCitacParcely.readLine());
            double vpravoHoreXParcely = Double.parseDouble(bCitacParcely.readLine());
            double vpravoHoreYParcely = Double.parseDouble(bCitacParcely.readLine());

            if (vlavoDoleXNehnutelnosti != vlavoDoleXParcely ||
                vlavoDoleYNehnutelnosti != vlavoDoleYParcely ||
                vpravoHoreXNehnutelnosti != vpravoHoreXParcely ||
                vpravoHoreYNehnutelnosti != vpravoHoreYParcely)
            {
                return false;
            }

            int maxUrovenNehnutelnosti = Integer.parseInt(bCitacNehnutelnosti.readLine());
            int maxUrovenParcely = Integer.parseInt(bCitacParcely.readLine());

            // Vytvorim stormy
            this.inicializujDatabazu(vlavoDoleXNehnutelnosti, vlavoDoleYNehnutelnosti, vpravoHoreXNehnutelnosti, vpravoHoreYNehnutelnosti,
                                   maxUrovenNehnutelnosti, maxUrovenParcely);

            bCitacNehnutelnosti.close();
            bCitacParcely.close();

            fCitacNehnutelnosti.close();
            fCitacParcely.close();

            // Vytvorenie stromov bolo uspesne
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    private <T extends IPolygon> boolean nacitajDataZoSuboru(File subor, Databaza databaza, Class<T> typ)
    {
        try
        {
            FileReader fCitac = new FileReader(subor);
            BufferedReader bCitac = new BufferedReader(fCitac);

            for (int i = 0; i < 5; i++)
            {
                String dummyRiadok = bCitac.readLine();
            }

            String riadok;
            while (true)
            {
                riadok = bCitac.readLine();
                if (riadok == null)
                {
                    // Ukonci citanie, ked dosiahnes koniec suboru
                    break;
                }

                String[] casti = riadok.split(";", 6);

                int cislo = Integer.parseInt(casti[0]);
                String popis = casti[1];

                double vlavoDoleX =  Double.parseDouble(casti[2]);
                double vlavoDoleY =  Double.parseDouble(casti[3]);
                double vpravoHoreX = Double.parseDouble(casti[4]);
                double vpravoHoreY = Double.parseDouble(casti[5]);

                Suradnica surVlavoDole = new Suradnica(vlavoDoleX, vlavoDoleY);
                Suradnica surVpravoHore = new Suradnica(vpravoHoreX, vpravoHoreY);

                if (typ.equals(Nehnutelnost.class))
                {
                    Nehnutelnost nehnutelnost = new Nehnutelnost(cislo, popis, surVlavoDole, surVpravoHore);
                    this.databaza.vlozNehnutelnost(nehnutelnost);
                }
                else if (typ.equals(Parcela.class))
                {
                    Parcela parcela = new Parcela(cislo, popis, surVlavoDole, surVpravoHore);
                    this.databaza.vlozParcelu(parcela);
                }
            }

            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public void inicializujDatabazu(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY,
                                    int maxUrovenNehnutelnosti, int maxUrovenParcely)
    {
        if (maxUrovenNehnutelnosti < 0 || maxUrovenParcely < 0)
        {
            throw new RuntimeException("Maximalna uroven nemoze byt zaporna!");
        }

        if (vlavoDoleX >= vpravoHoreX || vlavoDoleY >= vpravoHoreY)
        {
            throw new RuntimeException("Neplatne zadane rozmery najvacsieho quadu!");
        }

        this.databaza = new Databaza(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY, maxUrovenNehnutelnosti, maxUrovenParcely);
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
                this.databaza.vlozNehnutelnost(nehnutelnost);
            }
            else if (typ.equals(Parcela.class))
            {
                Parcela parcela = this.generator.getParcela();
                this.databaza.vlozParcelu(parcela);
            }
        }
    }

    public void vlozNehnutelnost(int supisneCislo, String popis, double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        this.skontrolujVstupy(supisneCislo, vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
        Nehnutelnost nehnutelnost = new Nehnutelnost(supisneCislo, popis,
                                                     new Suradnica(vlavoDoleX, vlavoDoleY),
                                                     new Suradnica(vpravoHoreX, vpravoHoreY));
        this.databaza.vlozNehnutelnost(nehnutelnost);
    }

    public void vlozParcelu(int cisloParcely, String popis, double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        this.skontrolujVstupy(cisloParcely, vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
        Parcela parcela = new Parcela(cisloParcely, popis,
                                      new Suradnica(vlavoDoleX, vlavoDoleY),
                                      new Suradnica(vpravoHoreX, vpravoHoreY));
        this.databaza.vlozParcelu(parcela);
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
                this.databaza.vlozNehnutelnost(novaNehnutelnost);
            }
            else if (polygon instanceof Parcela)
            {
                Parcela novaParcela = new Parcela(noveCislo, popis, surVlavoDole, surVpravoHore);
                this.databaza.vlozParcelu(novaParcela);
            }
        }
    }

    public void vymazPolygon(Polygon polygon)
    {
        double stredX = (polygon.getVlavoDoleX() + polygon.getVpravoHoreX()) / 2;
        double stredY = (polygon.getVlavoDoleY() + polygon.getVpravoHoreY()) / 2;

        if (polygon instanceof Nehnutelnost nehnutelnost)
        {
            this.databaza.vymazNehnutelnost(stredX, stredY, nehnutelnost.getKluc());
        }
        else if (polygon instanceof Parcela parcela)
        {
            this.databaza.vymazParcelu(stredX, stredY, parcela.getKluc());
        }
    }

    public ArrayList<Polygon> vyhladajPolygony(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        int dummyCislo = 0;
        this.skontrolujVstupy(dummyCislo, vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);

        ArrayList<Polygon> polygony = new ArrayList<>();
        polygony.addAll(this.databaza.vyhladajNehnutelnosti(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY));
        polygony.addAll(this.databaza.vyhladajParcely(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY));
        return polygony;
    }

    public ArrayList<Nehnutelnost> vyhladajNehnutelnosti(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        int dummyCislo = 0;
        this.skontrolujVstupy(dummyCislo, vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);

        return this.databaza.vyhladajNehnutelnosti(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
    }

    public ArrayList<Parcela> vyhladajParcely(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        int dummyCislo = 0;
        this.skontrolujVstupy(dummyCislo, vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);

        return this.databaza.vyhladajParcely(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
    }


    public ArrayList<Polygon> vyhladajPolygony(double x, double y)
    {
        ArrayList<Polygon> polygony = new ArrayList<>();
        polygony.addAll(this.databaza.vyhladajNehnutelnosti(x, y));
        polygony.addAll(this.databaza.vyhladajParcely(x, y));
        return polygony;
    }

    public ArrayList<Nehnutelnost> vyhladajNehnutelnosti(double x, double y)
    {
        return this.databaza.vyhladajNehnutelnosti(x, y);
    }

    public ArrayList<Parcela> vyhladajParcely(double x, double y)
    {
        return this.databaza.vyhladajParcely(x, y);
    }

    private void skontrolujVstupy(int cislo, double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        if (vlavoDoleX >= vpravoHoreX || vlavoDoleY >= vpravoHoreY)
        {
            throw new RuntimeException("Neplatne zadane rozmery elementu!");
        }

        if (vlavoDoleX < this.databaza.getNehnutelnostiStrom().getRootQuad().getVlavoDoleX() ||
            vlavoDoleY < this.databaza.getNehnutelnostiStrom().getRootQuad().getVlavoDoleY() ||
            vpravoHoreX > this.databaza.getNehnutelnostiStrom().getRootQuad().getVpravoHoreX() ||
            vpravoHoreY > this.databaza.getNehnutelnostiStrom().getRootQuad().getVpravoHoreY())
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
                                       this.databaza.getNehnutelnostiStrom().getRootQuad().getVlavoDoleX(),
                                       this.databaza.getNehnutelnostiStrom().getRootQuad().getVlavoDoleY(),
                                       this.databaza.getNehnutelnostiStrom().getRootQuad().getVpravoHoreX(),
                                       this.databaza.getNehnutelnostiStrom().getRootQuad().getVpravoHoreY(),
                                       5, 1);
    }

    public ArrayList<Polygon> getPolygony()
    {
        return this.databaza.getPolygony();
    }

    public ArrayList<Parcela> getParcely()
    {
        return this.databaza.getParcely();
    }

    public ArrayList<Nehnutelnost> getNehnutelnosti()
    {
        return this.databaza.getNehnutelnosti();
    }

    public Databaza getDatabaza()
    {
        return this.databaza;
    }
}
