package Ostatne;

import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;
import Objekty.Suradnica;

import java.util.Random;

import static java.lang.Math.abs;

public class Generator
{
    private double faktorZmensenia;

    private int curSupisneCislo;
    private int curCisloParcely;

    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    private int dlzkaString;

    private Random random;
    private String znaky = "abcdefghijklmnopqrstuvwxyz";

    public Generator(int startSupisneCislo, int startCisloParcely, double minX, double minY, double maxX, double maxY, int dlzkaString, double faktorZmensenia)
    {
        this.nastavPremenne(startSupisneCislo, startCisloParcely, minX, minY, maxX, maxY, dlzkaString, faktorZmensenia);
    }

    public Generator(int startSupisneCislo, int startCisloParcely, double minX, double minY, double maxX, double maxY, int dlzkaString, double faktorZmensenia, long seed)
    {
        this.nastavPremenne(startSupisneCislo, startCisloParcely, minX, minY, maxX, maxY, dlzkaString, faktorZmensenia);
        this.random.setSeed(seed);
    }

    private void nastavPremenne(int startSupisneCislo, int startCisloParcely, double minX, double minY, double maxX, double maxY, int dlzkaString, double faktor)
    {
        this.faktorZmensenia = faktor;

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
        return (this.random.nextBoolean()) ? this.getNehnutelnost() : this.getParcela();
    }

    public Nehnutelnost getNehnutelnost()
    {
        Suradnica suradnica1 = new Suradnica(0, 0);
        Suradnica suradnica2 = new Suradnica(0, 0);
        this.vygenerujSuradnice(suradnica1, suradnica2);

        String popis = this.randomString();

        Nehnutelnost nehnutelnost = new Nehnutelnost(this.curSupisneCislo, popis, suradnica1, suradnica2);
        this.curSupisneCislo++;

        return nehnutelnost;
    }

    public Parcela getParcela()
    {
        Suradnica suradnica1 = new Suradnica(0, 0);
        Suradnica suradnica2 = new Suradnica(0, 0);
        this.vygenerujSuradnice(suradnica1, suradnica2);

        String popis = this.randomString();

        Parcela parcela = new Parcela(this.curCisloParcely, popis, suradnica1, suradnica2);
        this.curCisloParcely++;

        return parcela;
    }

    private void vygenerujSuradnice(Suradnica surVlavoDole, Suradnica surVpravoHore)
    {
        double x1 = this.randomDouble(this.minX, this.maxX);
        double y1 = this.randomDouble(this.minY, this.maxY);
        double x2 = this.randomDouble(this.minX, this.maxX);
        double y2 = this.randomDouble(this.minY, this.maxY);

        double minX = Math.min(x1, x2);
        double minY = Math.min(y1, y2);

        double maxX = Math.max(x1, x2);
        double maxY = Math.max(y1, y2);

        boolean zmensiDolava = this.random.nextBoolean();

        double vzdialenostX = abs(maxX - minX);
        double vzdialenostY = abs(maxY - minY);
        vzdialenostX /= this.faktorZmensenia;
        vzdialenostY /= this.faktorZmensenia;

        if (zmensiDolava)
        {
            maxX = minX + vzdialenostX;
            maxY = minY + vzdialenostY;
        }
        else
        {
            minX = maxX - vzdialenostX;
            minY = maxY - vzdialenostY;
        }

        surVlavoDole.setX(minX);
        surVlavoDole.setY(minY);

        surVpravoHore.setX(maxX);
        surVpravoHore.setY(maxY);
    }

    public double randomDouble(double min, double max)
    {
        return min + (max - min) * this.random.nextDouble();
    }

    public int randomInt(int min, int max)
    {
        return min + this.random.nextInt(max - min + 1);
    }

    private String randomString()
    {
        StringBuilder vysledok = new StringBuilder();

        for (int i = 0; i < dlzkaString; i++)
        {
            int index = this.random.nextInt(znaky.length());
            char randomChar = znaky.charAt(index);
            vysledok.append(randomChar);
        }

        return vysledok.toString();
    }
}
