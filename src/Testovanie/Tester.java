package Testovanie;

import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.Generator;
import Objekty.Nehnutelnost;
import Ostatne.IPolygon;
import Ostatne.Konstanty;
import QuadStrom.QuadStrom;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Tester
{
    private static final String NAZOV_SUBORU = "Testovanie_vystup.txt";

    private static final int ZACIATOCNA_VELKOST = 10000;

    private static final int PRST_VYHLADAJ_BOD = 50;
    private static final int PRST_VYHLADAJ_POL = 50;

    private static final int PRST_VYHLADAJ = 40;
    private static final int PRST_VLOZ = 20;
    private static final int PRST_VYMAZ = 40;
    private static final int POCET_OPERACII = 5000;

    private static final double X_GEN_MIN = -1000;
    private static final double Y_GEN_MIN = -1000;
    private static final double X_GEN_MAX = 1000;
    private static final double Y_GEN_MAX = 1000;

    private static final double FAKTOR_ZMENSENIA_MIN = 1;
    private static final double FAKTOR_ZMENSENIA_MAX = 10000;

    private final Random random;

    public Tester()
    {
        this.random = new Random();
    }

    public Tester(long seed)
    {
        this.random = new Random();
        this.random.setSeed(seed);
    }

    public void testuj(int opakovania)
    {
        for (int i = 0; i < opakovania; i++)
        {
            double x1 = this.randomDouble(X_GEN_MIN, X_GEN_MAX);
            double y1 = this.randomDouble(Y_GEN_MIN, Y_GEN_MAX);
            double x2 = this.randomDouble(X_GEN_MIN, X_GEN_MAX);
            double y2 = this.randomDouble(Y_GEN_MIN, Y_GEN_MAX);

            double minX = Math.min(x1, x2);
            double minY = Math.min(y1, y2);
            double maxX = Math.max(x1, x2);
            double maxY = Math.max(y1, y2);

            double faktorZmensenia = this.randomDouble(FAKTOR_ZMENSENIA_MIN, FAKTOR_ZMENSENIA_MAX);

            long seedReplikacia = this.random.nextLong();

            System.out.println("Spusta sa replikacia cislo: " + i + ", seed: " + seedReplikacia);
            this.test(minX, minY, maxX, maxY, faktorZmensenia, seedReplikacia);

            System.gc();
        }
    }

    public void test(double minX, double minY, double maxX, double maxY, double faktorZmensenia, long seedReplikacia)
    {
        Generator generator = new Generator(1, 1, minX, minY, maxX, maxY, 5, faktorZmensenia, seedReplikacia);
        ArrayList<Nehnutelnost> zoznam = new ArrayList<>();
        QuadStrom<Nehnutelnost> strom = new QuadStrom<Nehnutelnost>(minX, minY, maxX, maxY, Konstanty.DEFAULT_MAX_HLBKA);

        for (int i = 0; i < ZACIATOCNA_VELKOST; i++)
        {
            Nehnutelnost nehnutelnost = generator.getNehnutelnost();
            zoznam.add(nehnutelnost);
            strom.vloz(nehnutelnost);
        }

        if (zoznam.size() != strom.getPocetElementov())
        {
            throw new RuntimeException("Prvotna velkost stromu a zoznamu nie je rovnaka!");
        }

        for (int i = 0; i < POCET_OPERACII; i++)
        {
            int nahoda = this.randomInt(0, PRST_VYHLADAJ + PRST_VLOZ + PRST_VYMAZ);

            if (nahoda < PRST_VYHLADAJ)
            {
                // Vyhladavanie
                int sposobHladania = this.randomInt(0, PRST_VYHLADAJ_BOD + PRST_VYHLADAJ_POL);
                ArrayList<Nehnutelnost> najdeneZoznam = new ArrayList<>();
                ArrayList<Nehnutelnost> najdeneStrom = new ArrayList<>();

                if (sposobHladania < PRST_VYHLADAJ_BOD)
                {
                    double x = this.randomDouble(minX, maxX);
                    double y = this.randomDouble(minY, maxY);
                    najdeneZoznam = this.vyhladaj(x, y, zoznam);
                    najdeneStrom = strom.vyhladaj(x, y);
                }
                else
                {
                    double x1 = this.randomDouble(minX, maxX);
                    double y1 = this.randomDouble(minY, maxY);
                    double x2 = this.randomDouble(minX, maxX);
                    double y2 = this.randomDouble(minY, maxY);

                    double vlavoDoleX = Math.min(x1, x2);
                    double vlavoDoleY = Math.min(y1, y2);
                    double vpravoHoreX = Math.max(x1, x2);
                    double vpravoHoreY = Math.max(y1, y2);

                    najdeneZoznam = this.vyhladaj(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY, zoznam);
                    najdeneStrom = strom.vyhladaj(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
                }

                if (najdeneZoznam.size() != najdeneStrom.size())
                {
                    throw new RuntimeException("Pocet najdenych elementov nie je rovnaky!");
                }

                if (!najdeneZoznam.containsAll(najdeneStrom) || !najdeneStrom.containsAll(najdeneZoznam))
                {
                    throw new RuntimeException("Najdene elementy sa nezhoduju!");
                }
            }
            else if (nahoda < (PRST_VYHLADAJ + PRST_VLOZ))
            {
                // Vkladanie
                Nehnutelnost nehnutelnost = generator.getNehnutelnost();
                zoznam.add(nehnutelnost);
                strom.vloz(nehnutelnost);
            }
            else
            {
                // Vymazavanie
                if (zoznam.isEmpty())
                {
                    continue;
                }

                int index;
                if (zoznam.size() == 1)
                {
                    index = 0;
                }
                else
                {
                    index = this.randomInt(0, zoznam.size() - 1);
                }

                int velkost = strom.getPocetElementov();
                Nehnutelnost zmazanaZoznam = zoznam.remove(index);
                double stredX = (zmazanaZoznam.getVlavoDoleX() + zmazanaZoznam.getVpravoHoreX()) / 2;
                double stredY = (zmazanaZoznam.getVlavoDoleY() + zmazanaZoznam.getVpravoHoreY()) / 2;
                Nehnutelnost zmazanaStrom = strom.vymaz(stredX, stredY, zmazanaZoznam.getUnikatnyKluc());

                if (zmazanaZoznam != zmazanaStrom)
                {
                    throw new RuntimeException("Rozdielne!");
                }
            }
        }

        KontrolaStromu.prilisPlytko(strom);
        KontrolaStromu.prazdnePodstromy(strom);
        KontrolaStromu.kontrolaStromu(strom, NAZOV_SUBORU);
    }

    private double randomDouble(double min, double max)
    {
        return min + (max - min) * this.random.nextDouble();
    }

    private int randomInt(int min, int max)
    {
        return min + this.random.nextInt(max - min + 1);
    }

    private ArrayList<Nehnutelnost> vyhladaj(double x, double y, ArrayList<Nehnutelnost> nehnutelnosti)
    {
        ArrayList<Nehnutelnost> najdene = new ArrayList<>();

        for (Nehnutelnost nehnutelnost : nehnutelnosti)
        {
            if (nehnutelnost.leziVnutri(x, y))
            {
                najdene.add(nehnutelnost);
            }
        }

        return najdene;
    }

    private ArrayList<Nehnutelnost> vyhladaj(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY, ArrayList<Nehnutelnost> nehnutelnosti)
    {
        Polygon prehladavanaOblast = new Polygon();
        prehladavanaOblast.nastavSuradnice(new Suradnica(vlavoDoleX, vlavoDoleY),
                                           new Suradnica(vpravoHoreX, vpravoHoreY));
        ArrayList<Nehnutelnost> najdene = new ArrayList<>();

        for (Nehnutelnost nehnutelnost : nehnutelnosti)
        {
            if (nehnutelnost.prekryva(prehladavanaOblast))
            {
                najdene.add(nehnutelnost);
            }
        }

        return najdene;
    }
}
