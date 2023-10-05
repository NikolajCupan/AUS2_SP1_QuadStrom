import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Podorys;
import Objekty.Suradnica;
import QuadStrom.Quad;

import java.util.ArrayList;

public class Aplikacia
{
    public void vykonavaj()
    {
        Quad<Podorys> quad = new Quad<>();
        Nehnutelnost nehnutelnost = new Nehnutelnost(1, "Dom",
                                                     new Suradnica(-89, -44),
                                                     new Suradnica(-1, -1));
        Nehnutelnost nehnutelnost2 = new Nehnutelnost(1, "Dom",
                                                      new Suradnica(-179, -89),
                                                      new Suradnica(179, 89));
        Parcela parcela = new Parcela(1, "Parcela",
                                      new Suradnica(-89, -44),
                                      new Suradnica(-1, -1));
        Parcela parcela2 = new Parcela(1, "Parcela2",
                                       new Suradnica(-179, -89),
                                       new Suradnica(179, 89));
        quad.vloz(nehnutelnost);
        quad.vloz(nehnutelnost2);
        quad.vloz(parcela);
        quad.vloz(parcela2);
        System.out.println(quad.getPocetElementov());
        int x = 100;
    }
}
