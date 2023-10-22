package Testovanie;

import Objekty.Nehnutelnost;
import Ostatne.Generator;
import QuadStrom.QuadStrom;

import java.util.Random;

public class TesterVseobecny
{
    public TesterVseobecny()
    {
        this.testPocetElementov();
        this.testPresunData();
    }

    public void testPocetElementov()
    {
        Random random = new Random();
        Generator generator = new Generator(1, 1, -100, - 100, 100, 100, 5, 100);
        QuadStrom<Nehnutelnost> strom = new QuadStrom<Nehnutelnost>(-100, -100, 100, 100, 10);

        int generujElementov = this.randomInt(0, 1000000, random);
        for (int i = 0; i < generujElementov; i++)
        {
            Nehnutelnost nehnutelnost = generator.getNehnutelnost();
            strom.vloz(nehnutelnost);
        }

        if (generujElementov != strom.getPocetElementov())
        {
            throw new RuntimeException("Pocet elementov v strome sa nezhoduje s ocakavanym poctom!");
        }
    }

    public void testPresunData()
    {
        Random random = new Random();
        Generator generator = new Generator(1, 1, -100, - 100, 100, 100, 5, 100);

        int prvotnaMaxUroven = this.randomInt(10, 50, random);
        QuadStrom<Nehnutelnost> strom = new QuadStrom<Nehnutelnost>(-100, -100, 100, 100, prvotnaMaxUroven);

        int generujElementov = this.randomInt(0, 1000000, random);
        for (int i = 0; i < generujElementov; i++)
        {
            Nehnutelnost nehnutelnost = generator.getNehnutelnost();
            strom.vloz(nehnutelnost);
        }

        int novaMaxUroven = this.randomInt(0, prvotnaMaxUroven, random);
        strom.presunPlytsie(novaMaxUroven);

        if (generujElementov != strom.getPocetElementov())
        {
            throw new RuntimeException("Po zmene hlbky doslo k strate dat!");
        }

        if (strom.getNajhlbsiaUroven() > novaMaxUroven)
        {
            throw new RuntimeException("Najhlbsia uroven je hlbsia ako bolo nastavene!");
        }
    }

    private int randomInt(int min, int max, Random random)
    {
        if (min == max)
        {
            return min;
        }

        return min + random.nextInt(max - min + 1);
    }
}
