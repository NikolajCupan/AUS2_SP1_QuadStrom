package Aplikacia;

import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;
import QuadStrom.QuadStrom;

import java.util.ArrayList;

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
}
