import Objekty.Nehnutelnost;
import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.IPolygon;
import Ostatne.Konstanty;
import QuadStrom.QuadStrom;

public class Aplikacia
{
    public void vykonavaj()
    {
        QuadStrom<Polygon> strom = new QuadStrom<Polygon>(new Suradnica(Konstanty.X_MIN, Konstanty.Y_MIN),
                                                          new Suradnica(Konstanty.X_MAX, Konstanty.Y_MAX));
        strom.quad.foo(new Nehnutelnost());
        /*
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
        quad.vloz(nehnutelnost);
        quad.vloz(nehnutelnost2);
        quad.vloz(nehnutelnost3);
        quad.vloz(nehnutelnost4);
        quad.vloz(parcela);
        quad.vloz(parcela2);

        ArrayList<Nehnutelnost> n = quad.vyhladajObjekty(new Suradnica(0, 0), new Nehnutelnost());
        ArrayList<Parcela> p = quad.vyhladajObjekty(new Suradnica(0, 0), new Parcela());
        */

        int x = 100;
    }
}
