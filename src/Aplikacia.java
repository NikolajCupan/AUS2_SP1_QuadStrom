import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Suradnica;

public class Aplikacia
{
    public Aplikacia()
    {}

    public void vykonavaj()
    {
        Nehnutelnost nehnutelnost = new Nehnutelnost(1, "Dom",
                new Suradnica(-20.0, -10.0),
                new Suradnica(100.0, -50.0));
        Parcela p1 = new Parcela(1, "Parcela jedna",
                new Suradnica(-10.0, -5.0),
                new Suradnica(100.0, 90.0));
        Parcela p2 = new Parcela(1, "Parcela jedna",
                new Suradnica(-10.0, -5.0),
                new Suradnica(-30.0, -15.0));
        boolean v1 = nehnutelnost.leziNaParcele(p1);
        boolean v2 = nehnutelnost.leziNaParcele(p2);

        int x = 100;
    }
}
