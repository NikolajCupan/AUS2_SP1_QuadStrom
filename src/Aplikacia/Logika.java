package Aplikacia;

import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;
import Ostatne.Konstanty;
import QuadStrom.QuadStrom;

import java.util.ArrayList;

public class Logika
{
    private QuadStrom<Nehnutelnost> nehnutelnosti;
    private QuadStrom<Parcela> parcely;

    public Logika(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        this.inicializujStromy(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
    }

    public void inicializujStromy(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        this.nehnutelnosti = new QuadStrom<Nehnutelnost>(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY, Konstanty.DEFAULT_MAX_HLBKA);
        this.parcely = new QuadStrom<Parcela>(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY, Konstanty.DEFAULT_MAX_HLBKA);
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

    public QuadStrom<Nehnutelnost> getNehnutelnostiStrom()
    {
        return this.nehnutelnosti;
    }

    public QuadStrom<Parcela> getParcelyStrom()
    {
        return this.parcely;
    }
}
