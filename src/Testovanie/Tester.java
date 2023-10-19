package Testovanie;

import Aplikacia.KontrolaStromu;
import Generator.Generator;
import Objekty.Nehnutelnost;
import Ostatne.Konstanty;
import QuadStrom.QuadStrom;

import java.util.ArrayList;
import java.util.Random;

public class Tester
{
    private static final int ZACIATOCNA_VELKOST = 1000;
    private static final int PRST_VLOZ = 1;
    private static final int PRST_VYMAZ = 99;
    private static final int POCET_OPERACII = 10000;

    private static final double X_GEN_MIN = -1000;
    private static final double Y_GEN_MIN = -1000;
    private static final double X_GEN_MAX = 1000;
    private static final double Y_GEN_MAX = 1000;

    private static final double FAKTOR_ZMENSENIA_MIN = 1;
    private static final double FAKTOR_ZMENSENIA_MAX = 10000;

    private Random random;

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
            this.test(minX, minY, maxX, maxY, faktorZmensenia, seedReplikacia);
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

        for (int i = 0; i < POCET_OPERACII; i++)
        {
            int nahoda = this.randomInt(0, PRST_VLOZ + PRST_VYMAZ);
            if (nahoda < PRST_VLOZ)
            {
                Nehnutelnost nehnutelnost = generator.getNehnutelnost();
                zoznam.add(nehnutelnost);
                strom.vloz(nehnutelnost);
            }
            else
            {
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

        //KontrolaStromu.prilisPlytko(strom);
        //KontrolaStromu.prazdnePodstromy(strom);
        KontrolaStromu.kontrolaStromu(strom, "Nehnutelnosti.txt");
    }

    public double randomDouble(double min, double max)
    {
        return min + (max - min) * this.random.nextDouble();
    }

    public int randomInt(int min, int max)
    {
        return min + this.random.nextInt(max - min + 1);
    }
}
