package Generator;

import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;
import Objekty.Suradnica;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;

public class GeneratorDat
{
    public int curSupisneCislo;
    public int curCisloParcely;

    public double minX;
    public double minY;
    public double maxX;
    public double maxY;

    public int dlzkaString;

    public Random random;
    public String znaky = "abcdefghijklmnopqrstuvwxyz";

    public GeneratorDat(int startSupisneCislo, int startCisloParcely, double minX, double minY, double maxX, double maxY, int dlzkaString, long seed)
    {
        this.curSupisneCislo = startSupisneCislo;
        this.curCisloParcely = startCisloParcely;

        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;

        this.dlzkaString = dlzkaString;
        this.random = new Random();
        this.random.setSeed(seed);
    }

    public Polygon getPolygon()
    {
        int nahoda = abs(random.nextInt() % 2);
        if (nahoda == 0)
        {
            return this.getNehnutelnost();
        }
        else
        {
            return this.getParcela();
        }
    }

    public Nehnutelnost getNehnutelnost()
    {
        Suradnica suradnica1 = new Suradnica(this.randomDouble(this.minX, this.maxX), this.randomDouble(this.minY, this.maxY));
        Suradnica suradnica2 = new Suradnica(this.randomDouble(this.minX, this.maxX), this.randomDouble(this.minY, this.maxY));

        String popis = this.randomString();

        Nehnutelnost nehnutelnost = new Nehnutelnost(this.curSupisneCislo, popis, suradnica1, suradnica2);
        this.curSupisneCislo++;

        return nehnutelnost;
    }

    public Parcela getParcela()
    {
        Suradnica suradnica1 = new Suradnica(this.randomDouble(this.minX, this.maxX), this.randomDouble(this.minY, this.maxY));
        Suradnica suradnica2 = new Suradnica(this.randomDouble(this.minX, this.maxX), this.randomDouble(this.minY, this.maxY));

        String popis = this.randomString();

        Parcela parcela = new Parcela(this.curSupisneCislo, popis, suradnica1, suradnica2);
        this.curSupisneCislo++;

        return parcela;
    }

    private double randomDouble(double min, double max)
    {
        double nahoda = abs(random.nextDouble() % 2);
        return min + nahoda * (max - min);
    }

    private String randomString()
    {
        StringBuilder vysledok = new StringBuilder();

        for (int i = 0; i < dlzkaString; i++)
        {
            int index = random.nextInt(znaky.length());
            char randomChar = znaky.charAt(index);
            vysledok.append(randomChar);
        }

        return vysledok.toString();
    }
}
