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
                                                     new Suradnica(-90, -45),
                                                     new Suradnica(90, 45));
        Nehnutelnost nehnutelnost2 = new Nehnutelnost(1, "Dom",
                                                      new Suradnica(-40, 10),
                                                      new Suradnica(-10, 30));
        Nehnutelnost nehnutelnost3 = new Nehnutelnost(1, "Dom",
                                                      new Suradnica(150, 45),
                                                      new Suradnica(180, 90));
        strom.vloz(nehnutelnost);
        strom.vloz(nehnutelnost2);
        strom.vloz(nehnutelnost3);
        ArrayList<Polygon> vysledok = strom.vyhladaj(179, 89, 180, 90, Polygon.class);
        int z = strom.getPocetElementov();

        int x = 100;
    }
}
