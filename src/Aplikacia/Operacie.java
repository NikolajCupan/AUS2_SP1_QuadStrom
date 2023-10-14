package Aplikacia;

import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;
import QuadStrom.QuadStrom;

import java.util.ArrayList;

public class Operacie
{
    public static void pridajNehnutelnost(QuadStrom<Polygon> strom, Nehnutelnost pridavana)
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

    public static void pridajParcelu(QuadStrom<Polygon> strom, Parcela pridavana)
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
}
