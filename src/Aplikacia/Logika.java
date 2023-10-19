package Aplikacia;

import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;
import Ostatne.Konstanty;
import QuadStrom.QuadStrom;

import java.util.ArrayList;

public class Logika
{
    private final QuadStrom<Nehnutelnost> nehnutelnosti;
    private final QuadStrom<Parcela> parcely;

    public Logika(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        this.nehnutelnosti = new QuadStrom<Nehnutelnost>(Konstanty.X_MIN, Konstanty.Y_MIN, Konstanty.X_MAX, Konstanty.Y_MAX);
        this.parcely = new QuadStrom<Parcela>(Konstanty.X_MIN, Konstanty.Y_MIN, Konstanty.X_MAX, Konstanty.Y_MAX);
    }

    public void vlozPolygon(Polygon pridavany)
    {
        if (pridavany instanceof Nehnutelnost)
        {
            this.vlozNehnutelnost((Nehnutelnost)pridavany);
        }
        else
        {
            this.vlozParcelu((Parcela)pridavany);
        }
    }

    public void vlozNehnutelnost(Nehnutelnost pridavana)
    {
        this.nehnutelnosti.vloz(pridavana);
        ArrayList<Parcela> parcely = this.parcely.vyhladaj(pridavana.getVlavoDoleX(), pridavana.getVlavoDoleY(),
                                                           pridavana.getVpravoHoreX(), pridavana.getVpravoHoreY());

        for (Parcela parcela : parcely)
        {
            parcela.skusPridatNehnutelnost(pridavana);
            pridavana.skusPridatParcelu(parcela);
        }
    }

    public void vlozParcelu(Parcela pridavana)
    {
        this.parcely.vloz(pridavana);
        ArrayList<Nehnutelnost> nehnutelnosti = this.nehnutelnosti.vyhladaj(pridavana.getVlavoDoleX(), pridavana.getVlavoDoleY(),
                                                                            pridavana.getVpravoHoreX(), pridavana.getVpravoHoreY());
        for (Nehnutelnost nehnutelnost : nehnutelnosti)
        {
            nehnutelnost.skusPridatParcelu(pridavana);
            pridavana.skusPridatNehnutelnost(nehnutelnost);
        }
    }

    public void vymazNehnutelnost(double x, double y, double hladanyKluc)
    {
        Nehnutelnost vymazana = this.nehnutelnosti.vymaz(x, y, hladanyKluc);

        if (vymazana == null)
        {
            return;
        }

        for (Parcela parcela : vymazana.getParcely())
        {
            parcela.skusOdobratNehnutelnost(vymazana);
        }
    }

    public void vymazParcelu(double x, double y, double hladanyKluc)
    {
        Parcela vymazana = this.parcely.vymaz(x, y, hladanyKluc);

        if (vymazana == null)
        {
            return;
        }

        for (Nehnutelnost nehnutelnosta : vymazana.getNehnutelnosti())
        {
            nehnutelnosta.skusOdobratParcelu(vymazana);
        }
    }

    public void kontrola()
    {
        Tester.skontrolujStrukturu(this.nehnutelnosti, "Parcely.txt");
        Tester.skontrolujStrukturu(this.parcely, "Nehnutelnosti.txt");
    }
}
