import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.IPolygon;
import Ostatne.Konstanty;
import QuadStrom.QuadStrom;

import java.util.ArrayList;

public class Aplikacia
{
    public void vykonavaj()
    {
        QuadStrom<Polygon> strom = new QuadStrom<Polygon>(Konstanty.X_MIN, Konstanty.Y_MIN, Konstanty.X_MAX, Konstanty.Y_MAX);
        Nehnutelnost nehnutelnost = new Nehnutelnost(1, "Dom",
                                                     new Suradnica(-89, -44),
                                                     new Suradnica(10, 10));
        Nehnutelnost nehnutelnost2 = new Nehnutelnost(1, "Dom",
                                                      new Suradnica(-179, -89),
                                                      new Suradnica(179, 89));
        Nehnutelnost nehnutelnost3 = new Nehnutelnost(1, "Dom",
                                                      new Suradnica(-180, 0),
                                                      new Suradnica(0, 90));
        Nehnutelnost nehnutelnost4 = new Nehnutelnost(1, "Dom",
                                                      new Suradnica(0, 1),
                                                      new Suradnica(179, 89));
        Parcela parcela = new Parcela(1, "Parcela",
                                      new Suradnica(-89, -44),
                                      new Suradnica(-1, -1));
        Parcela parcela2 = new Parcela(1, "Parcela2",
                                       new Suradnica(-179, -89),
                                       new Suradnica(179, 89));
        strom.vloz(nehnutelnost);
        strom.vloz(nehnutelnost2);
        strom.vloz(nehnutelnost3);
        strom.vloz(nehnutelnost4);
        strom.vloz(parcela);
        strom.vloz(parcela2);
        ArrayList<Nehnutelnost> p = strom.vyhladaj(1, 1, Nehnutelnost.class);
        ArrayList<Parcela> pp = strom.vyhladaj(1, 1, Parcela.class);
        int z = strom.getPocetElementov();

        int x = 100;
    }
}
