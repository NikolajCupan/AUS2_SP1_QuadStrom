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
        Stack<Quad<Polygon>> zasobnik = new Stack<Quad<Polygon>>();
        zasobnik.push(strom.getQuad());

        int spracovane = 0;
        while (!zasobnik.isEmpty())
        {
            Quad<Polygon> curQuad = zasobnik.pop();

            if (curQuad.jeRozdeleny())
            {
                for (Quad<Polygon> podquad : curQuad.getPodQuady())
                {
                    zasobnik.push(podquad);
                }
            }

            System.out.println("{" + curQuad.getVlavoDoleX() + ", " + curQuad.getVlavoDoleY() + "}, {" + curQuad.getVpravoHoreX() + ", " + curQuad.getVpravoHoreY() + "}");
            if (curQuad.getData().isEmpty())
            {
                System.out.println("Quad je prazdny!");
            }

            for (Polygon polygon : curQuad.getData())
            {
                spracovane++;
                if (curQuad.leziVnutri(polygon))
                {
                    System.out.println("OK: {" + polygon.getVlavoDoleX() + ", " + polygon.getVlavoDoleY() + "}, {" + polygon.getVpravoHoreX() + ", " + polygon.getVpravoHoreY() + "}");
                }
                else
                {
                    throw new RuntimeException("Chyba!");
                }
            }
        }

        int pocet = strom.getPocetElementov();
        if (pocet == spracovane)
        {
            System.out.println("OK: " + pocet + " = " + spracovane);
        }
        else
        {
            throw new RuntimeException("Chyba!");
        }
    }
}
