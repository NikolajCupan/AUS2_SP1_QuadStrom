import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Suradnica;

public class Aplikacia
{
    public void vykonavaj()
    {
        Nehnutelnost nehnutelnost = new Nehnutelnost(1, "Dom",
                new Suradnica(-20.0, -10.0),
                new Suradnica(100.0, -50.0));
        Parcela parcela = new Parcela(1, "Parcela jedna",
                new Suradnica(-10.0, -5.0),
                new Suradnica(-30.0, -15.0));
        nehnutelnost.skusPridatParcelu(parcela);
        parcela.skusPridatNehnutelnost(nehnutelnost);

        int x = 100;
    }
}
