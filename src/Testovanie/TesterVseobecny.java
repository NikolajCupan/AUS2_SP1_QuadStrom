package Testovanie;

import Objekty.Nehnutelnost;
import Ostatne.Generator;
import QuadStrom.QuadStrom;

import java.util.Random;

public class TesterVseobecny
{
    private final Random random;
    private final Generator generator;

    public TesterVseobecny()
    {
        this.random = new Random();
        this.generator = new Generator(1, 1, -100, -100, 100, 100, 5, 1);

        this.testZdravie();
        //this.testPocetElementov();
        //this.testPresunData();
    }

    public void testPocetElementov()
    {
        QuadStrom<Nehnutelnost> strom = new QuadStrom<Nehnutelnost>(-100, -100, 100, 100, 15);

        int pocetElementov = this.randomInt(0, 1000000);
        for (int i = 0; i < pocetElementov; i++)
        {
            Nehnutelnost nehnutelnost = this.generator.getNehnutelnost();
            strom.vloz(nehnutelnost);
        }

        if (pocetElementov != strom.getPocetElementov())
        {
            throw new RuntimeException("Pocet elementov v strome sa nezhoduje s ocakavanym poctom!");
        }
    }

    public void testZdravie()
    {
        QuadStrom<Nehnutelnost> strom = new QuadStrom<Nehnutelnost>(-100, -100, 100, 100, 15);

        for (int i = 0; i < 3; i++)
        {
            Nehnutelnost nehnutelnost = this.generator.getNehnutelnost();
            strom.vloz(nehnutelnost);
        }

        //strom.optimalizacia();

        KontrolaStromu.kontrolaStromu(strom, "Testovanie_vystup.txt");
    }

    public void testPresunData()
    {
        int prvotnaMaxHlbka = this.randomInt(10, 50);
        QuadStrom<Nehnutelnost> strom = new QuadStrom<Nehnutelnost>(-100, -100, 100, 100, prvotnaMaxHlbka);

        int pocetElementov = this.randomInt(0, 1000000);
        for (int i = 0; i < pocetElementov; i++)
        {
            Nehnutelnost nehnutelnost = this.generator.getNehnutelnost();
            strom.vloz(nehnutelnost);
        }

        int zmenenaMaxHlbka = this.randomInt(0, prvotnaMaxHlbka);
        strom.presunDataPlytsie(zmenenaMaxHlbka);

        if (pocetElementov != strom.getPocetElementov())
        {
            throw new RuntimeException("Po zmene hlbky doslo k strate dat!");
        }

        if (strom.getCurHlbka() > zmenenaMaxHlbka)
        {
            throw new RuntimeException("Hlbka stromu je vyssia ako bolo nastavene!");
        }
    }

    private int randomInt(int min, int max)
    {
        if (min == max)
        {
            return min;
        }

        return min + this.random.nextInt(max - min + 1);
    }
}
