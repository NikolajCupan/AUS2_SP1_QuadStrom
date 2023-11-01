package Testovanie;

import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.Generator;
import Objekty.Nehnutelnost;
import QuadStrom.QuadStrom;

import java.util.ArrayList;
import java.util.Random;

public class Tester
{
    // Hranice quad stromu a generovanych suradnic
    private static final double GEN_X_MIN = -10000;
    private static final double GEN_Y_MIN = -10000;
    private static final double GEN_X_MAX = 10000;
    private static final double GEN_Y_MAX = 10000;

    // Hranice pre faktor zmensenia vygenerovanych suradnic
    private static final double FAKTOR_ZMENSENIA_MIN = 1;
    private static final double FAKTOR_ZMENSENIA_MAX = 10000;

    private static final int DEAULT_MAX_UROVEN_MIN = 0;
    private static final int DEFAULT_MAX_UROVEN_MAX = 15;

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

    public void replikacie(int opakovania)
    {
        for (int i = 0; i < opakovania; i++)
        {
            double x1 = this.randomDouble(GEN_X_MIN, GEN_X_MAX);
            double y1 = this.randomDouble(GEN_Y_MIN, GEN_Y_MAX);
            double x2 = this.randomDouble(GEN_X_MIN, GEN_X_MAX);
            double y2 = this.randomDouble(GEN_Y_MIN, GEN_Y_MAX);

            // Hranice najvacsieho quadu
            double minX = Math.min(x1, x2);
            double minY = Math.min(y1, y2);
            double maxX = Math.max(x1, x2);
            double maxY = Math.max(y1, y2);

            // O kolko su vygenerovane nehnutelnosti zmensene pred vkladanim
            double faktorZmensenia = this.randomDouble(FAKTOR_ZMENSENIA_MIN, FAKTOR_ZMENSENIA_MAX);
            int maxUroven = this.randomInt(DEAULT_MAX_UROVEN_MIN, DEFAULT_MAX_UROVEN_MAX);

            long seedReplikacia = this.random.nextLong();
            Generator generator = new Generator(1, 1, minX, minY, maxX, maxY, 5, faktorZmensenia, seedReplikacia);

            System.out.println("Spusta sa replikacia cislo: " + i + ", seed: " + seedReplikacia);

            // Semotne testy
            this.testZakladneOperacie(minX, minY, maxX, maxY, maxUroven, generator);
            this.testPocetElementov(minX, minY, maxX, maxY, maxUroven, generator);
            this.testPresunPlytsie(minX, minY, maxX, maxY, generator);
            this.testPomerUroven(minX, minY, maxX, maxY, generator);

            System.gc();
        }
    }

    // Kontrola operacii vyhladaj, vloz, vymaz
    private void testZakladneOperacie(double minX, double minY, double maxX, double maxY, int maxUroven, Generator generator)
    {
        // Vlastne parametre testu
        final String NAZOV_SUBORU = "Test_zakladne_operacie.txt";

        final int ZACIATOCNA_VELKOST = 5000;
        final int POCET_OPERACII = 50000;

        final int PRST_VYHLADAJ_BOD = 50;
        final int PRST_VYHLADAJ_POL = 50;

        final int PRST_VYHLADAJ = 10;
        final int PRST_VLOZ = 45;
        final int PRST_VYMAZ = 45;

        ArrayList<Nehnutelnost> zoznam = new ArrayList<>();
        QuadStrom<Nehnutelnost> strom = new QuadStrom<Nehnutelnost>(minX, minY, maxX, maxY, maxUroven);

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
                ArrayList<Nehnutelnost> najdeneZoznam;
                ArrayList<Nehnutelnost> najdeneStrom;

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

                    najdeneZoznam = this.vyhladaj(x1, y1, x2, y2, zoznam);
                    najdeneStrom = strom.vyhladaj(x1, y1, x2, y2);
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

                int index = this.randomInt(0, zoznam.size() - 1);

                Nehnutelnost zmazanaZoznam = zoznam.remove(index);
                double stredX = (zmazanaZoznam.getVlavoDoleX() + zmazanaZoznam.getVpravoHoreX()) / 2;
                double stredY = (zmazanaZoznam.getVlavoDoleY() + zmazanaZoznam.getVpravoHoreY()) / 2;
                Nehnutelnost zmazanaStrom = strom.vymaz(stredX, stredY, zmazanaZoznam);

                if (zmazanaZoznam != zmazanaStrom)
                {
                    throw new RuntimeException("Zmazane elementy sa nezhoduju!");
                }
            }
        }

        if (zoznam.size() != strom.getPocetElementov())
        {
            throw new RuntimeException("Pocet elementov po vykonani vsetkych operacii nie je rovnaky!");
        }

        KontrolaStromu.prilisPlytko(strom);
        KontrolaStromu.prazdnePodStromy(strom);
        KontrolaStromu.mimoQuad(strom, NAZOV_SUBORU);
    }

    private void testPocetElementov(double minX, double minY, double maxX, double maxY, int maxUroven, Generator generator)
    {
        // Vlastne parametre testu
        final String NAZOV_SUBORU = "Test_pocet_elementov.txt";

        final int MIN_GEN_ELEMENTOV = 0;
        final int MAX_GEN_ELEMENTOV = 10000;

        QuadStrom<Nehnutelnost> strom = new QuadStrom<Nehnutelnost>(minX, minY, maxX, maxY, maxUroven);

        int generujElementov = this.randomInt(MIN_GEN_ELEMENTOV, MAX_GEN_ELEMENTOV);
        for (int i = 0; i < generujElementov; i++)
        {
            Nehnutelnost nehnutelnost = generator.getNehnutelnost();
            strom.vloz(nehnutelnost);
        }

        if (generujElementov != strom.getPocetElementov())
        {
            throw new RuntimeException("Pocet elementov v strome sa nezhoduje s ocakavanym poctom!");
        }

        KontrolaStromu.prilisPlytko(strom);
        KontrolaStromu.prazdnePodStromy(strom);
        KontrolaStromu.mimoQuad(strom, NAZOV_SUBORU);
    }

    private void testPresunPlytsie(double minX, double minY, double maxX, double maxY, Generator generator)
    {
        // Vlastne parametre testu
        final String NAZOV_SUBORU = "Test_presun_plytsie.txt";

        final int DH_MAX_PRVOTNA_UROVEN = 0;
        final int HH_MAX_PRVOTNA_UROVEN = 50;

        final int MIN_GEN_ELEMENTOV = 0;
        final int MAX_GEN_ELEMENTOV = 10000;

        int prvotnaMaxUroven = this.randomInt(DH_MAX_PRVOTNA_UROVEN, HH_MAX_PRVOTNA_UROVEN);
        QuadStrom<Nehnutelnost> strom = new QuadStrom<Nehnutelnost>(minX, minY, maxX, maxY, prvotnaMaxUroven);

        int generujElementov = this.randomInt(MIN_GEN_ELEMENTOV, MAX_GEN_ELEMENTOV);
        for (int i = 0; i < generujElementov; i++)
        {
            Nehnutelnost nehnutelnost = generator.getNehnutelnost();
            strom.vloz(nehnutelnost);
        }

        int novaMaxUroven = this.randomInt(0, prvotnaMaxUroven);
        strom.presunPlytsie(novaMaxUroven);

        if (generujElementov != strom.getPocetElementov())
        {
            throw new RuntimeException("Po zmene hlbky doslo k strate dat!");
        }

        if (strom.getNajhlbsiaUroven() > novaMaxUroven)
        {
            throw new RuntimeException("Najhlbsia uroven je hlbsia ako bolo nastavene!");
        }

        strom.setMaxUroven(novaMaxUroven);

        KontrolaStromu.prilisPlytko(strom);
        KontrolaStromu.prazdnePodStromy(strom);
        KontrolaStromu.mimoQuad(strom, NAZOV_SUBORU);
    }

    private void testPomerUroven(double minX, double minY, double maxX, double maxY, Generator generator)
    {
        // Vlastne parametre testu
        final String NAZOV_SUBORU = "Test_pomer_uroven.txt";
        final double EPSILON = 0.001;

        final int DH_MAX_PRVOTNA_UROVEN = 10;
        final int HH_MAX_PRVOTNA_UROVEN = 20;

        final int MIN_GEN_ELEMENTOV = 0;
        final int MAX_GEN_ELEMENTOV = 10000;

        int prvotnaMaxUroven = this.randomInt(DH_MAX_PRVOTNA_UROVEN, HH_MAX_PRVOTNA_UROVEN);
        QuadStrom<Nehnutelnost> strom = new QuadStrom<Nehnutelnost>(minX, minY, maxX, maxY, prvotnaMaxUroven);

        int generujElementov = this.randomInt(MIN_GEN_ELEMENTOV, MAX_GEN_ELEMENTOV);
        for (int i = 0; i < generujElementov; i++)
        {
            Nehnutelnost nehnutelnost = generator.getNehnutelnost();
            strom.vloz(nehnutelnost);
        }

        double[] pomerUroven = strom.getPomerUroven();
        double spoluPomer = 0;
        for (int i = 0; i < pomerUroven.length; i++)
        {
            spoluPomer += pomerUroven[i];
        }

        if (Math.abs(spoluPomer - 1.0) > EPSILON)
        {
            throw new RuntimeException("Sucet jednotlivych pomerov nie je rovny 1!");
        }

        int novaMaxUroven = this.randomInt(0, prvotnaMaxUroven);

        // Spocitaj kolko percent dat sa nachadza
        // na urovni novaMaxUroven a hlbsich
        double sucetPomerHlbsie = 0;
        for (int i = novaMaxUroven; i < pomerUroven.length; i++)
        {
            sucetPomerHlbsie += pomerUroven[i];
        }

        strom.presunPlytsie(novaMaxUroven);
        double[] novyPomerUroven = strom.getPomerUroven();

        if (Math.abs(novyPomerUroven[novaMaxUroven] - sucetPomerHlbsie) > EPSILON)
        {
            throw new RuntimeException("Pomery dat na jednotlivych urovniach po presune dat nesedia!");
        }

        strom.setMaxUroven(novaMaxUroven);

        KontrolaStromu.prilisPlytko(strom);
        KontrolaStromu.prazdnePodStromy(strom);
        KontrolaStromu.mimoQuad(strom, NAZOV_SUBORU);
    }

    public void moje()
    {
        QuadStrom<Nehnutelnost> strom = new QuadStrom<Nehnutelnost>(-100, -100, 100, 100, 5);
        Generator generator = new Generator(1, 1, -100, -100, 100, 100, 5, 10000, 420);

        for (int i = 0; i < 10000; i++)
        {
            Nehnutelnost nehnutelnost = generator.getNehnutelnost();
            strom.vloz(nehnutelnost);
        }

        double[] pomer = strom.getPomerUroven();

        System.out.println("Max uroven: " + strom.getMaxUroven());
        for (int i = 0; i < strom.getMaxUroven() + 1; i++)
        {
            System.out.println("U: " + i + ", pomer: " + pomer[i]);
        }
    }

    private double randomDouble(double min, double max)
    {
        return min + (max - min) * this.random.nextDouble();
    }

    private int randomInt(int min, int max)
    {
        if (min == max)
        {
            return min;
        }

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
