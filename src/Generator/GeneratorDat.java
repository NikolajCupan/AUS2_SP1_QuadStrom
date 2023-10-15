package Generator;

import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;
import Objekty.Suradnica;

import java.util.Random;

import static java.lang.Math.abs;

public class GeneratorDat
{
    public static final double FAKTOR = 100.0;

    public int curSupisneCislo;
    public int curCisloParcely;

    public double minX;
    public double minY;
    public double maxX;
    public double maxY;

    public int dlzkaString;

    public Random random;
    public String znaky = "abcdefghijklmnopqrstuvwxyz";

    public GeneratorDat(int startSupisneCislo, int startCisloParcely, double minX, double minY, double maxX, double maxY, int dlzkaString)
    {
        this.nastavPremenne(startSupisneCislo, startCisloParcely, minX, minY, maxX, maxY, dlzkaString);
    }

    public GeneratorDat(int startSupisneCislo, int startCisloParcely, double minX, double minY, double maxX, double maxY, int dlzkaString, long seed)
    {
        this.nastavPremenne(startSupisneCislo, startCisloParcely, minX, minY, maxX, maxY, dlzkaString);
        this.random.setSeed(seed);
    }

    private void nastavPremenne(int startSupisneCislo, int startCisloParcely, double minX, double minY, double maxX, double maxY, int dlzkaString)
    {
        this.curSupisneCislo = startSupisneCislo;
        this.curCisloParcely = startCisloParcely;

        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;

        this.dlzkaString = dlzkaString;
        this.random = new Random();
    }

    public Polygon getPolygon()
    {
        int nahoda = abs(random.nextInt() % 2);
        return (nahoda == 0) ? this.getNehnutelnost() : this.getParcela();
    }

    public Nehnutelnost getNehnutelnost()
    {
        Suradnica suradnica1 = new Suradnica(0, 0);
        Suradnica suradnica2 = new Suradnica(0, 0);
        this.nastavSuradnice(suradnica1, suradnica2);

        String popis = this.randomString();

        Nehnutelnost nehnutelnost = new Nehnutelnost(this.curSupisneCislo, popis, suradnica1, suradnica2);
        this.curSupisneCislo++;

        return nehnutelnost;
    }

    public Parcela getParcela()
    {
        Suradnica suradnica1 = new Suradnica(0, 0);
        Suradnica suradnica2 = new Suradnica(0, 0);
        this.nastavSuradnice(suradnica1, suradnica2);

        String popis = this.randomString();

        Parcela parcela = new Parcela(this.curCisloParcely, popis, suradnica1, suradnica2);
        this.curCisloParcely++;

        return parcela;
    }

    private void nastavSuradnice(Suradnica surVlavoDole, Suradnica surVpravoHore)
    {
        double x1 = this.randomDouble(this.minX, this.maxX);
        double y1 = this.randomDouble(this.minY, this.maxY);
        double x2 = this.randomDouble(this.minX, this.maxX);
        double y2 = this.randomDouble(this.minY, this.maxY);

        double absMinX = Math.abs(x1) < Math.abs(x2) ? x1 : x2;
        double absMinY = Math.abs(y1) < Math.abs(y2) ? y1 : y2;

        double absMaxX = Math.abs(x1) > Math.abs(x2) ? x1 : x2;
        double absMaxY = Math.abs(y1) > Math.abs(y2) ? y1 : y2;

        surVlavoDole.setX(absMinX);
        surVlavoDole.setY(absMinY);

        double vpravoHoreX = absMinX + ((absMaxX - absMinX) / FAKTOR);
        double vpravoHoreY = absMinY + ((absMaxY - absMinY) / FAKTOR);

        surVpravoHore.setX(vpravoHoreX);
        surVpravoHore.setY(vpravoHoreY);
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
