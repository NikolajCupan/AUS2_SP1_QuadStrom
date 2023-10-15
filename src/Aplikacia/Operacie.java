package Aplikacia;

import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;
import QuadStrom.QuadStrom;
import QuadStrom.Quad;

import java.util.ArrayList;
import java.util.Stack;

public class Operacie
{
    public static void vlozPolygon(QuadStrom<Polygon> strom, Polygon pridavany)
    {
        if (pridavany instanceof Nehnutelnost)
        {
            Operacie.vlozNehnutelnost(strom, (Nehnutelnost)pridavany);
        }
        else
        {
            Operacie.vlozParcelu(strom, (Parcela)pridavany);
        }
    }

    public static void vlozNehnutelnost(QuadStrom<Polygon> strom, Nehnutelnost pridavana)
    {
        strom.vloz(pridavana);
        ArrayList<Parcela> parcely = strom.vyhladaj(pridavana.getVlavoDoleX(), pridavana.getVlavoDoleY(),
                                                    pridavana.getVpravoHoreX(), pridavana.getVpravoHoreY(),
                                                    Parcela.class);

        for (Parcela parcela : parcely)
        {
            parcela.skusPridatNehnutelnost(pridavana);
            pridavana.skusPridatParcelu(parcela);
        }
    }

    public static void vlozParcelu(QuadStrom<Polygon> strom, Parcela pridavana)
    {
        strom.vloz(pridavana);
        ArrayList<Nehnutelnost> nehnutelnosti = strom.vyhladaj(pridavana.getVlavoDoleX(), pridavana.getVlavoDoleY(),
                                                               pridavana.getVpravoHoreX(), pridavana.getVpravoHoreY(),
                                                               Nehnutelnost.class);

        for (Nehnutelnost nehnutelnost : nehnutelnosti)
        {
            nehnutelnost.skusPridatParcelu(pridavana);
            pridavana.skusPridatNehnutelnost(nehnutelnost);
        }
    }

    public static void vymazNehnutelnost(QuadStrom<Polygon> strom, double x, double y, double hladanyKluc)
    {
        Nehnutelnost vymazana = strom.vymaz(x, y, hladanyKluc, Nehnutelnost.class);

        if (vymazana == null)
        {
            return;
        }

        for (Parcela parcela : vymazana.getParcely())
        {
            parcela.skusOdobratNehnutelnost(vymazana);
        }
    }

    public static void vymazParcelu(QuadStrom<Polygon> strom, double x, double y, double hladanyKluc)
    {
        Parcela vymazana = strom.vymaz(x, y, hladanyKluc, Parcela.class);

        if (vymazana == null)
        {
            return;
        }

        for (Nehnutelnost nehnutelnosta : vymazana.getNehnutelnosti())
        {
            nehnutelnosta.skusOdobratParcelu(vymazana);
        }
    }

    public static void skontrolujStrukturu(QuadStrom<Polygon> strom)
    {
        Stack<Quad<Polygon>> zasobnik = new Stack<>();
        zasobnik.push(strom.getQuad());

        int spracovaneElementy = 0;
        int maxHlbka = -1;

        while (!zasobnik.isEmpty())
        {
            Quad<Polygon> curQuad = zasobnik.pop();

            int hlbka = curQuad.getHlbkaQuady();
            if (hlbka > maxHlbka)
            {
                maxHlbka = hlbka;
            }

            if (curQuad.jeRozdeleny())
            {
                for (Quad<Polygon> podquad : curQuad.getPodQuady())
                {
                    zasobnik.push(podquad);
                }
            }

            Operacie.vypis(hlbka,"{" + curQuad.getVlavoDoleX() + ", " + curQuad.getVlavoDoleY() + "}, {" + curQuad.getVpravoHoreX() + ", " + curQuad.getVpravoHoreY() + "}", false);
            if (curQuad.getData().isEmpty())
            {
                Operacie.vypis(hlbka, "Quad je prazdny!", true);
            }

            for (Polygon polygon : curQuad.getData())
            {
                spracovaneElementy++;
                if (curQuad.leziVnutri(polygon))
                {
                    Operacie.vypis(hlbka, "OK: {" + polygon.getVlavoDoleX() + ", " + polygon.getVlavoDoleY() + "}, {" + polygon.getVpravoHoreX() + ", " + polygon.getVpravoHoreY() + "}", true);
                }
                else
                {
                    throw new RuntimeException("Chyba!");
                }
            }
        }

        int pocet = strom.getPocetElementov();
        if (pocet == spracovaneElementy)
        {
            System.out.println("OK: " + pocet + " = " + spracovaneElementy);
        }
        else
        {
            throw new RuntimeException("Chyba!");
        }

        System.out.println("Maximalna hlbka v strome: " + maxHlbka);
    }

    private static void vypis(int hlbka, String text, boolean medzera)
    {
        for (int i = 0; i < hlbka * 5; i++)
        {
            System.out.print(' ');
        }

        if (medzera)
        {
            System.out.print("-> ");
        }

        System.out.println(hlbka + ":   " + text);
    }
}
