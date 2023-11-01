package Aplikacia;

import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;
import QuadStrom.QuadStrom;
import QuadStrom.Quad;

import java.util.ArrayList;

public class Databaza
{
    private QuadStrom<Nehnutelnost> nehnutelnosti;
    private QuadStrom<Parcela> parcely;

    public Databaza(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY,
                    int maxUrovenNehnutelnosti, int maxUrovenParcely)
    {
        this.inicializujStromy(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY, maxUrovenNehnutelnosti, maxUrovenParcely);
    }

    private void inicializujStromy(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY,
                                   int maxUrovenNehnutelnosti, int maxUrovenParcely)
    {
        this.nehnutelnosti = new QuadStrom<Nehnutelnost>(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY, maxUrovenNehnutelnosti);
        this.parcely = new QuadStrom<Parcela>(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY, maxUrovenParcely);
    }

    public void resetujStromy()
    {
        this.nehnutelnosti = null;
        this.parcely = null;
    }

    public void vlozNehnutelnost(Nehnutelnost pridavana)
    {
        this.nehnutelnosti.vloz(pridavana);
        ArrayList<Parcela> parcely = this.parcely.vyhladaj(pridavana.getVlavoDoleX(), pridavana.getVlavoDoleY(),
                                                           pridavana.getVpravoHoreX(), pridavana.getVpravoHoreY());

        // Nastavenie vzajomnych referencii
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

        // Nastavenie vzajomnych referencii
        for (Nehnutelnost nehnutelnost : nehnutelnosti)
        {
            nehnutelnost.skusPridatParcelu(pridavana);
            pridavana.skusPridatNehnutelnost(nehnutelnost);
        }
    }

    public void vymazNehnutelnost(double x, double y, Nehnutelnost vymazavana)
    {
        Nehnutelnost vymazana = this.nehnutelnosti.vymaz(x, y, vymazavana);

        if (vymazana == null)
        {
            return;
        }

        for (Parcela parcela : vymazana.getParcely())
        {
            parcela.skusOdobratNehnutelnost(vymazana);
        }
        vymazana.getParcely().clear();
    }

    public void vymazParcelu(double x, double y, Parcela vymazavana)
    {
        Parcela vymazana = this.parcely.vymaz(x, y, vymazavana);

        if (vymazana == null)
        {
            return;
        }

        for (Nehnutelnost nehnutelnosta : vymazana.getNehnutelnosti())
        {
            nehnutelnosta.skusOdobratParcelu(vymazana);
        }
        vymazana.getNehnutelnosti().clear();
    }

    public ArrayList<Nehnutelnost> vyhladajNehnutelnosti(double x, double y)
    {
        return this.nehnutelnosti.vyhladaj(x, y);
    }

    public ArrayList<Parcela> vyhladajParcely(double x, double y)
    {
        return this.parcely.vyhladaj(x, y);
    }

    public ArrayList<Nehnutelnost> vyhladajNehnutelnosti(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        return this.nehnutelnosti.vyhladaj(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
    }

    public ArrayList<Parcela> vyhladajParcely(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        return this.parcely.vyhladaj(vlavoDoleX, vlavoDoleY, vpravoHoreX, vpravoHoreY);
    }

    public ArrayList<Polygon> getPolygony()
    {
        ArrayList<Polygon> polygony = new ArrayList<>();
        polygony.addAll(this.getParcely());
        polygony.addAll(this.getNehnutelnosti());
        return polygony;
    }

    public ArrayList<Parcela> getParcely()
    {
        ArrayList<Parcela> parcely = new ArrayList<>();

        for (Quad<Parcela> quad : this.parcely)
        {
            parcely.addAll(quad.getData());
        }

        return parcely;
    }


    public ArrayList<Nehnutelnost> getNehnutelnosti()
    {
        ArrayList<Nehnutelnost> nehnutelnosti = new ArrayList<>();

        for (Quad<Nehnutelnost> quad : this.nehnutelnosti)
        {
            nehnutelnosti.addAll(quad.getData());
        }

        return nehnutelnosti;
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
