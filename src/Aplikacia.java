import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Pozemok;
import Objekty.Suradnica;
import QuadStrom.Quad;

public class Aplikacia
{
    public void vykonavaj()
    {
        Quad<Pozemok> quad = new Quad<>();
        Nehnutelnost nehnutelnost = new Nehnutelnost(1, "Dom",
                                                     new Suradnica(-89, -44),
                                                     new Suradnica(-1, -1));
        quad.vloz(nehnutelnost);
        int x = 100;
    }
}
